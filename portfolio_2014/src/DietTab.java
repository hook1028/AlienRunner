import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;


public class DietTab extends JPanel
{
	JTabbedPane tab;
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String sql;
	
	JList list;
	DefaultListModel food;
	
	DietTab()
	{
		super();
		
		setLayout(new BorderLayout());
		
		tab = new JTabbedPane();
		tab.setFont(new Font("Serif",Font.BOLD,15));
		tab.addTab("기록하기", new DietList());
		tab.addTab("아침", new Breakfast());
		tab.addTab("점심", new Lunch());
		tab.addTab("저녁", new Dinner());
		tab.addTab("간식", new Snack());
		tab.addTab("식단 등록", new DietAdd());
		
		add(tab,"Center");
	}
	
	static void popDialog(String title, String text)
	{
		JDialog dialog = new JDialog();

		JLabel label = new JLabel(text);
		JButton button = new JButton("확인");
		
		dialog.setModal(false);
		dialog.setTitle(title);
		dialog.setBounds(MainFrame.center().width/2 - 100, MainFrame.center().height/2 - 50, 200,100);
		dialog.setLayout(new FlowLayout());
		dialog.add(label);
		dialog.add(button);
		dialog.setResizable(false);
		dialog.setVisible(true);
		
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dialog.dispose();
			}
		});
	}
	
	DefaultListModel makingList(String sql)
	{	
		food = new DefaultListModel();
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e)
		{
			System.err.println("ClassNotFoundException: " + e.getMessage());
		}
		
		try
		{
			conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
			stmt = conn.createStatement();
			
	//		sql = "select food from diet";
			rs = stmt.executeQuery(sql);
						
			while(rs.next())
			{							
				food.addElement(rs.getString(1));
			}
			
			rs.close();
			stmt.close();
			conn.close();

		} catch(SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
		
		return food;
	}
	
	class DietAdd extends JPanel
	{
		JLabel add;
		JLabel name;
		JLabel mainly;
		JLabel notice;
		JTextField tf;
		ButtonGroup group;
		JRadioButton[] choose;
		JButton ok;
		JButton cancel;
		JButton all;
		
		Connection conn;
		Statement stmt;
		
		DietAdd()
		{
			super();
			setLayout(null);
			
			add = new JLabel("식단 등록하기");
			add.setSize(200,32);
			add.setFont(new Font("Serif", Font.BOLD, 25));
			add.setLocation(40,40);
			
			name = new JLabel("식단명 : ");
			name.setSize(200,20);
			name.setFont(new Font("Serif", Font.PLAIN, 20));
			name.setLocation(100, 120);
			
			tf = new JTextField(15);
			tf.setSize(200,30);
			tf.setLocation(180, 118);
			
			notice = new JLabel("(30자 이내로 입력해주세요.)");
			notice.setSize(200,20);
			notice.setFont(new Font("Serif", Font.PLAIN, 12));
			notice.setLocation(385, 128);
			
			mainly = new JLabel("주요 영양소");
			mainly.setSize(200,20);
			mainly.setFont(new Font("Serif", Font.BOLD, 22));
			mainly.setLocation(60, 200);
			
			ok = new JButton("확인");
			ok.setLocation(500,350);
			ok.setSize(70,40);

			cancel = new JButton("취소");
			cancel.setLocation(590,350);
			cancel.setSize(70,40);
			
			group = new ButtonGroup();
			choose = new JRadioButton[4];
			choose[0] = new JRadioButton("탄수화물");
			choose[1] = new JRadioButton("단백질");
			choose[2] =	new JRadioButton("지방");
			choose[3] = new JRadioButton("물");
			
			for(int i=0;i<choose.length;i++)
			{
				group.add(choose[i]);
				choose[i].setFont(new Font("Serif", Font.PLAIN, 17));
				choose[i].setSize(100,30);
				choose[i].setLocation(100+(100*i), 250);
				add(choose[i]);
			}
			
			add(add);
			add(name);
			add(tf);
			add(notice);
			add(mainly);
			add(ok);
			add(cancel);
			
			Handler handler = new Handler();
			ok.addActionListener(handler);
			cancel.addActionListener(handler);
			
		}
		
		class Handler implements ActionListener
		{	
			public void actionPerformed(ActionEvent e)
			{
				if(e.getSource()==ok)
				{
					String food = tf.getText();
					String mainly = "";
					
					if(food.length()>30)
					{
						RecordTab.errDialog("등록 실패", "식단명은 30자 이내로 입력해주세요.", tf);
						return;
					}
				
					for(int i=0; i<choose.length; i++)
					{
						if(choose[i].isSelected())
						{
							mainly = choose[i].getText();
						}
					}
				
					String sql = "insert into diet values ('" + food + "', '" + mainly + "')";
				
					try
					{
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch(ClassNotFoundException ce)
					{
						System.err.println("ClassNotFoundException: " + ce.getMessage());
					}
				
					try
					{
						conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
						stmt = conn.createStatement();
						int result = stmt.executeUpdate(sql);
						if (result!=0)
						{
							RecordTab.errDialog("등록 성공", "메뉴 등록에 성공했습니다.", tf);
							tf.setText("");
							group.clearSelection();
							
							tab.setComponentAt(0, new DietList());
							
						}
					
						stmt.close();
						conn.close();
					}catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
						RecordTab.errDialog("등록 실패", "동일한 음식명이 있습니다.", tf);
						return;
					}
				}
				if(e.getSource()==cancel)
				{
					tab.setSelectedIndex(0);
				}
			}
		}
	}
	
	class DietList extends JPanel
	{	
		JButton ok;
		JButton add;
		JButton all;
		JLabel label;
		JTextField tf;
		ScrollPane pane;
		
		ButtonGroup group;
		JRadioButton[] choose;
		
		DietList()
		{			
			setLayout(null);
						
			list = new JList(makingList("select food from diet"));
			
			pane = new ScrollPane();
			pane.setSize(625,250);
			pane.setLocation(25,80);
			pane.add(list);
			
			label = new JLabel("검색: ");
			label.setFont(new Font("Serif", Font.BOLD, 18));
			label.setSize(50,25);
			label.setLocation(30,355);
			
			tf = new JTextField(30);
			tf.setSize(200, 30);
			tf.setLocation(80, 353);
			
			all = new JButton("전체보기");
			all.setLocation(300, 350);
			all.setSize(90,40);
			
			ok = new JButton("확인");
			ok.setLocation(440,350);
			ok.setSize(90,40);

			add = new JButton("식단 등록");
			add.setLocation(550,350);
			add.setSize(90,40);
			
			group = new ButtonGroup();
			choose = new JRadioButton[4];
			choose[0] = new JRadioButton("아침");
			choose[1] = new JRadioButton("점심");
			choose[2] =	new JRadioButton("저녁");
			choose[3] = new JRadioButton("간식");
			
			for(int i=0;i<choose.length;i++)
			{
				group.add(choose[i]);
				choose[i].setFont(new Font("Serif", Font.PLAIN, 17));
				choose[i].setSize(100,30);
				choose[i].setLocation(140+(100*i),30);
				add(choose[i]);
			}
			
			Handler handler = new Handler();
			add.addActionListener(handler);
			tf.addActionListener(handler);
			all.addActionListener(handler);
			ok.addActionListener(handler);
			
			list.addHierarchyListener(new HierarchyListener()
				{
					public void hierarchyChanged(HierarchyEvent e) 
					{
						if(((Component) e.getSource()).isVisible())
						{
							list.setModel(makingList("select food from diet"));
							list.updateUI();
							list.repaint();
						}
					}
				});
			
			add(pane);
			add(label);
			add(tf);
			add(all);
			add(ok);
			add(add);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == add)
				{
					tab.setSelectedIndex(5);
				}
				
				if(gs == tf)
				{
					list.setModel(makingList("select food from diet where food like '%" + tf.getText() + "%'"));
					list.updateUI();
					list.repaint();
				}
				
				if(gs == ok)
				{
					try
					{
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch(ClassNotFoundException ce)
					{
						System.err.println("ClassNotFoundException: " + ce.getMessage());
					}
				
					try
					{
						conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
						stmt = conn.createStatement();
						
						List selList = list.getSelectedValuesList();
						Iterator it = selList.listIterator();
						int addresult = 0;
						String when = "";
					
						while(it.hasNext())
						{
							String selFood = (String) it.next();
							
							for(int i=0; i<choose.length; i++)
							{
								if(choose[i].isSelected())
								{
									when = choose[i].getText();
									if(when.equals("아침"))
									{
										sql = "insert into breakfast values(to_char(sysdate, 'yy-mm-dd'), "
												+ "(select food from diet where food = '" + selFood + "'), "
												+ "(select mainly from diet where food = '" + selFood + "'))";
									}
									else if(when.equals("점심"))
									{
										sql = "insert into lunch values(to_char(sysdate, 'yy-mm-dd'), "
												+ "(select food from diet where food = '" + selFood + "'), "
												+ "(select mainly from diet where food = '" + selFood + "'))";
									}
									else if(when.equals("저녁"))
									{
										sql = "insert into dinner values(to_char(sysdate, 'yy-mm-dd'), "
												+ "(select food from diet where food = '" + selFood + "'), "
												+ "(select mainly from diet where food = '" + selFood + "'))";
									}
									else if(when.equals("간식"))
									{
										sql = "insert into snack values(to_char(sysdate, 'yy-mm-dd'), "
												+ "(select food from diet where food = '" + selFood + "'), "
												+ "(select mainly from diet where food = '" + selFood + "'))";
									}
								}
							}
							
							int result = stmt.executeUpdate(sql);
							if(result>0)
							{
								addresult++;
							}
						}
							
						if(addresult>0 && !(when.equals("")))
						{
							if(when.equals("아침"))
							{
								tab.setComponentAt(1, new Breakfast());
								tab.setSelectedIndex(1);
							}
							else if(when.equals("점심"))
							{
								tab.setComponentAt(2, new Lunch());
								tab.setSelectedIndex(2);
							}
							else if(when.equals("저녁"))
							{
								tab.setComponentAt(3, new Dinner());
								tab.setSelectedIndex(3);
							}
							else
							{
								tab.setComponentAt(4, new Snack());
								tab.setSelectedIndex(4);
							}
							
							RecordTab.errDialog("등록 성공", addresult + "건이 등록되었습니다.", tf);
						}
						else
						{
							popDialog("등록 실패", "식사 시간을 선택해주세요.");
						}
						
						stmt.close();
						conn.close();
		
					} catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
				
				if(gs == all)
				{
					list.setModel(makingList("select food from diet"));
					list.updateUI();
					list.repaint();
				}
			}
		}
	}
	
	class Breakfast extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JButton delete;
		JButton modify;
		
		Vector columns;
		Vector data;
		
		void makingTable()
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch(ClassNotFoundException e)
			{
				System.err.println("ClassNotFoundException: " + e.getMessage());
			}
			
			try
			{
				conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
				stmt = conn.createStatement();
				sql = "select to_char(day, 'yy-mm-dd'), food, mainly from breakfast order by day asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					data.add(rs.getString(2));
					data.add(rs.getString(3));
					dtm.addRow(data);
				}
				
				rs.close();
				stmt.close();
				conn.close();

			} catch(SQLException e)
			{
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		Breakfast()
		{	
			columns = new Vector();
			columns.add("날짜");
			columns.add("음식");
			columns.add("주요영양소");
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable 내용 편집 안되게
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable 드래그 못하게 고정
			table.setRowSorter(new TableRowSorter(dtm));
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
										
			delete = new JButton("삭제");
			delete.setLocation(420,363);
			delete.setSize(100,40);
			
			modify = new JButton("식단 추가");
			modify.setLocation(530,363);
			modify.setSize(100,40);
			
			Handler handler = new Handler();
			modify.addActionListener(handler);
			delete.addActionListener(handler);
			
			add(panel);
			add(delete);
			add(modify);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs==modify)
				{
					tab.setSelectedIndex(0);
				}
				
				if(gs==delete)
				{
					List selected = new ArrayList();
					int[] row = table.getSelectedRows();
					
					for(int i=0; i<row.length; i++)
					{
						for(int col=0; col<columns.size()-1; col++)
						{
							String value = (String)table.getValueAt(row[i], col);
							selected.add(value);
						}
					}

					try
					{
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch(ClassNotFoundException ce)
					{
						System.err.println("ClassNotFoundException: " + ce.getMessage());
					}
				
					try
					{	
						conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
						stmt = conn.createStatement();
						sql = "";
						
						Iterator it = selected.iterator();
						int delresult = 0;
					
						while(it.hasNext())
						{
							String selDay = (String) it.next();
							String selFood = (String) it.next();

							sql = "delete from breakfast where day = ' " + selDay 
									+ " ' and food = '" + selFood + "'";
							
							int result = stmt.executeUpdate(sql);
							if(result>0)
							{
								delresult++;
							}
						}
						
							if(delresult>0)
							{
								tab.setComponentAt(1, new Breakfast());
								popDialog("삭제 성공", delresult + "건이 삭제되었습니다.");
							}
					
						stmt.close();
						conn.close();
					}catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
			}
		}
	}
	
	class Lunch extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JButton delete;
		JButton modify;
		
		Vector columns;
		Vector data;
		
		void makingTable()
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch(ClassNotFoundException e)
			{
				System.err.println("ClassNotFoundException: " + e.getMessage());
			}
			
			try
			{
				conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
				stmt = conn.createStatement();
				sql = "select to_char(day, 'yy-mm-dd'), food, mainly from lunch order by day asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					data.add(rs.getString(2));
					data.add(rs.getString(3));
					dtm.addRow(data);
				}
				
				rs.close();
				stmt.close();
				conn.close();

			} catch(SQLException e)
			{
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		Lunch()
		{	
			columns = new Vector();
			columns.add("날짜");
			columns.add("음식");
			columns.add("주요영양소");
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable 내용 편집 안되게
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable 드래그 못하게 고정
			table.setRowSorter(new TableRowSorter(dtm));
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
										
			delete = new JButton("삭제");
			delete.setLocation(420,363);
			delete.setSize(100,40);
			
			modify = new JButton("식단 추가");
			modify.setLocation(530,363);
			modify.setSize(100,40);
			
			Handler handler = new Handler();
			modify.addActionListener(handler);
			delete.addActionListener(handler);
			
			add(panel);
			add(delete);
			add(modify);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs==modify)
				{
					tab.setSelectedIndex(0);
				}
				
				if(gs==delete)
				{
					List selected = new ArrayList();
					int[] row = table.getSelectedRows();
					
					for(int i=0; i<row.length; i++)
					{
						for(int col=0; col<columns.size()-1; col++)
						{
							String value = (String)table.getValueAt(row[i], col);
							selected.add(value);
						}
					}
	
					try
					{
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch(ClassNotFoundException ce)
					{
						System.err.println("ClassNotFoundException: " + ce.getMessage());
					}
				
					try
					{	
						conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
						stmt = conn.createStatement();
						sql = "";
						
						Iterator it = selected.iterator();
						int delresult = 0;
					
						while(it.hasNext())
						{
							String selDay = (String) it.next();
							String selFood = (String) it.next();

							sql = "delete from lunch where day = ' " + selDay 
									+ " ' and food = '" + selFood + "'";
							
							int result = stmt.executeUpdate(sql);
							if(result>0)
							{
								delresult++;
							}
						}
						
						if(delresult>0)
						{
							tab.setComponentAt(2, new Lunch());
							popDialog("삭제 성공", delresult + "건이 삭제되었습니다.");
						}
					
						stmt.close();
						conn.close();
					}catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
			}
		}
	}
	
	class Dinner extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JButton delete;
		JButton modify;
		
		Vector columns;
		Vector data;
		
		void makingTable()
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch(ClassNotFoundException e)
			{
				System.err.println("ClassNotFoundException: " + e.getMessage());
			}
			
			try
			{
				conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
				stmt = conn.createStatement();
				sql = "select to_char(day, 'yy-mm-dd'), food, mainly from dinner order by day asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					data.add(rs.getString(2));
					data.add(rs.getString(3));
					dtm.addRow(data);
				}
				
				rs.close();
				stmt.close();
				conn.close();

			} catch(SQLException e)
			{
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		Dinner()
		{	
			columns = new Vector();
			columns.add("날짜");
			columns.add("음식");
			columns.add("주요영양소");
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable 내용 편집 안되게
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable 드래그 못하게 고정
			table.setRowSorter(new TableRowSorter(dtm));
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
										
			delete = new JButton("삭제");
			delete.setLocation(420,363);
			delete.setSize(100,40);
			
			modify = new JButton("식단 추가");
			modify.setLocation(530,363);
			modify.setSize(100,40);
			
			Handler handler = new Handler();
			modify.addActionListener(handler);
			delete.addActionListener(handler);
			
			add(panel);
			add(delete);
			add(modify);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs==modify)
				{
					tab.setSelectedIndex(0);
				}
				
				if(gs==delete)
				{
					List selected = new ArrayList();
					int[] row = table.getSelectedRows();
					
					for(int i=0; i<row.length; i++)
					{
						for(int col=0; col<columns.size()-1; col++)
						{
							String value = (String)table.getValueAt(row[i], col);
							selected.add(value);
						}
					}

					try
					{
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch(ClassNotFoundException ce)
					{
						System.err.println("ClassNotFoundException: " + ce.getMessage());
					}
				
					try
					{	
						conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
						stmt = conn.createStatement();
						sql = "";
						
						Iterator it = selected.iterator();
						int delresult = 0;
					
						while(it.hasNext())
						{
							String selDay = (String) it.next();
							String selFood = (String) it.next();

							sql = "delete from dinner where day = ' " + selDay 
									+ " ' and food = '" + selFood + "'";
							
							int result = stmt.executeUpdate(sql);
							if(result>0)
							{
								delresult++;
							}
						}
						
						if(delresult>0)
						{
							tab.setComponentAt(3, new Dinner());
							popDialog("삭제 성공", delresult + "건이 삭제되었습니다.");
						}
					
						stmt.close();
						conn.close();
					}catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
			}
		}
	}
	
	class Snack extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JButton delete;
		JButton modify;
		
		Vector columns;
		Vector data;
		
		void makingTable()
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch(ClassNotFoundException e)
			{
				System.err.println("ClassNotFoundException: " + e.getMessage());
			}
			
			try
			{
				conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
				stmt = conn.createStatement();
				sql = "select to_char(day, 'yy-mm-dd'), food, mainly from snack order by day asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					data.add(rs.getString(2));
					data.add(rs.getString(3));
					dtm.addRow(data);
				}
				
				rs.close();
				stmt.close();
				conn.close();

			} catch(SQLException e)
			{
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		Snack()
		{	
			columns = new Vector();
			columns.add("날짜");
			columns.add("음식");
			columns.add("주요영양소");
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable 내용 편집 안되게
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable 드래그 못하게 고정
			table.setRowSorter(new TableRowSorter(dtm));
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
										
			delete = new JButton("삭제");
			delete.setLocation(420,363);
			delete.setSize(100,40);
			
			modify = new JButton("식단 추가");
			modify.setLocation(530,363);
			modify.setSize(100,40);
			
			Handler handler = new Handler();
			modify.addActionListener(handler);
			delete.addActionListener(handler);
			
			add(panel);
			add(delete);
			add(modify);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs==modify)
				{
					tab.setSelectedIndex(0);
				}
				
				if(gs==delete)
				{
					List selected = new ArrayList();
					int[] row = table.getSelectedRows();
					
					for(int i=0; i<row.length; i++)
					{
						for(int col=0; col<columns.size()-1; col++)
						{
							String value = (String)table.getValueAt(row[i], col);
							selected.add(value);
						}
					}
					
					try
					{
						Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch(ClassNotFoundException ce)
					{
						System.err.println("ClassNotFoundException: " + ce.getMessage());
					}
				
					try
					{	
						conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
						stmt = conn.createStatement();
						sql = "";
						
						Iterator it = selected.iterator();
						int delresult = 0;
					
						while(it.hasNext())
						{
							String selDay = (String) it.next();
							String selFood = (String) it.next();

							sql = "delete from snack where day = ' " + selDay 
									+ " ' and food = '" + selFood + "'";
							
							int result = stmt.executeUpdate(sql);
							if(result>0)
							{
								delresult++;
							}
						}
						
						if(delresult>0)
						{
							tab.setComponentAt(4, new Snack());
							popDialog("삭제 성공", delresult + "건이 삭제되었습니다.");
						}
					
						stmt.close();
						conn.close();
					}catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
			}
		}
	}
}
