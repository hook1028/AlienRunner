import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.*;

public class ExerciseTab extends JPanel
{
	JTabbedPane tab;
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String sql;
	
	ExerciseTab()
	{
		super();
		
		setLayout(new BorderLayout());
		
		tab = new JTabbedPane();
		tab.setFont(new Font("Serif",Font.BOLD,15));
		tab.addTab("보기", new RecordView());
		tab.addTab("기록", new ExerciseRecord1());
		tab.addTab("검색", new ExerciseSearch());
		tab.addTab("등록", new ExerciseAdd());
		
		add(tab,"Center");
	}
	
	class RecordView extends JPanel
	{
		JLabel label;
		JButton prev;
		JButton next;
		JTable yusanso;
		JTable musanso;
		JScrollPane pane1;
		JScrollPane pane2;
		JPanel panel1;
		JPanel panel2;
		
		Vector columns1;
		Vector columns2;
		Vector data1;
		Vector data2;
		DefaultTableModel dtm1;
		DefaultTableModel dtm2;
		
		Calendar cal = Calendar.getInstance();
		
		RecordView()
		{			
			label = new JLabel(cal.get(Calendar.YEAR) + "-" 
					+ (cal.get(Calendar.MONTH)<9 ? "0"+(cal.get(Calendar.MONTH)+1) : (cal.get(Calendar.MONTH)+1))
					+ "-" + (cal.get(Calendar.DATE)<9 ? "0"+cal.get(Calendar.DATE) : cal.get(Calendar.DATE)), JLabel.CENTER);
			
			columns1 = new Vector();
			columns1.add("운동명");
			columns1.add("운동 시간(개수)");
			columns1.add("소모 칼로리");
			
			columns2 = new Vector();
			columns2.add("운동명");
			columns2.add("운동 개수(시간)");	
			
			dtm1 = new DefaultTableModel(columns1,0){
	            // JTable 내용 편집 안되게
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			dtm2 = new DefaultTableModel(columns2,0){
	            // JTable 내용 편집 안되게
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
	        yusanso = new JTable(dtm1);
	        musanso = new JTable(dtm2);
	        
	        yusanso.getTableHeader().setReorderingAllowed(false);	//JTable 드래그 못하게 고정
			yusanso.setRowSorter(new TableRowSorter(dtm1));
			
	        musanso.getTableHeader().setReorderingAllowed(false);	//JTable 드래그 못하게 고정
			musanso.setRowSorter(new TableRowSorter(dtm2));
	        
	        makingTable();
			
	        setLayout(null);
	        
	        pane1 = new JScrollPane(yusanso);
	        
	        panel1 = new JPanel();
			panel1.setSize(500,150);
			panel1.setLocation(90,50);
			panel1.setLayout(new BorderLayout());
			panel1.add(pane1, BorderLayout.CENTER);
			
	        pane2 = new JScrollPane(musanso);
	        
	        panel2 = new JPanel();
			panel2.setSize(500,150);
			panel2.setLocation(90,230);
			panel2.setLayout(new BorderLayout());
			panel2.add(pane2, BorderLayout.CENTER);
	      
			label.setSize(100,20);
			label.setFont(new Font("Serif", Font.BOLD, 20));
			label.setLocation(290, 10);
			
			prev = new JButton("◀");
			prev.setSize(50,30);
			prev.setLocation(225, 5);
			
			next = new JButton("▶");
			next.setSize(50,30);
			next.setLocation(410,5);
			
			Handler handler = new Handler();
			prev.addActionListener(handler);
			next.addActionListener(handler);
			
			add(label);
			add(prev);
			add(next);
			add(panel1);
			add(panel2);
		}
		
		void setLabel()
		{
			label.setText(cal.get(Calendar.YEAR) + "-" 
					+ (cal.get(Calendar.MONTH)<9 ? "0"+(cal.get(Calendar.MONTH)+1) : (cal.get(Calendar.MONTH)+1))
					+ "-" + (cal.get(Calendar.DATE)<9 ? "0"+cal.get(Calendar.DATE) : cal.get(Calendar.DATE)));
		}
		
		void makingTable()
		{
			String day = label.getText();
			
			if(yusanso.getRowCount()>0)
			{ 
				//DefaultTableModel의 Row를 초기화 시켜 테이블의 자료를 전부 삭제한다. (새로고침과 같은 효과)
				dtm1.setDataVector(new Vector(), columns1);
			}
			
			if(musanso.getRowCount()>0)
			{ 
				//DefaultTableModel의 Row를 초기화 시켜 테이블의 자료를 전부 삭제한다. (새로고침과 같은 효과)
				dtm2.setDataVector(new Vector(), columns2);
			}
			
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
				sql = "select exercise, exnum, cal from exerciserecord where day = '" + day + "' and extype = '유산소' order by exercise asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data1 = new Vector();
					data1.add(rs.getString(1));
					data1.add(rs.getInt(2));
					data1.add(rs.getFloat(3));
					dtm1.addRow(data1);
				}
				
				sql = "select exercise, exnum from exerciserecord where day = '" + day + "' and extype = '무산소' order by exercise asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					data2 = new Vector();
					data2.add(rs.getString(1));
					data2.add(rs.getInt(2));
					dtm2.addRow(data2);
				}
				
				rs.close();
				stmt.close();
				conn.close();

			} catch(SQLException e)
			{
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == prev)
				{
					cal.add(Calendar.DATE, -1);
					setLabel();
					makingTable();
				}
				if(gs == next)
				{
					cal.add(Calendar.DATE, 1);
					setLabel();
					makingTable();
				}
			}
		}
	}
	
	class ExerciseRecord1 extends JPanel
	{
		Choice choice;
		JPanel panel;
		JScrollPane pane;
		JTable table;
		JLabel title;
		JLabel number;
		JTextField tf;
		JButton add;
		JButton delete;
		JButton next;
		
		Vector data;
		Vector columns;
		DefaultTableModel dtm;
		
		ExerciseRecord1()
		{
			setLayout(null);
			
			title = new JLabel("1. 무산소 운동", JLabel.CENTER);
			title.setFont(new Font("Serif", Font.BOLD, 20));
			title.setSize(150,30);
			title.setLocation(30,30);
			
			choice = new Choice();
			choice.setSize(600,30);
			choice.setLocation(40,85);
			
			makingChoice();
			
			number = new JLabel("운동 횟수 (시간): ", JLabel.RIGHT);
			number.setFont(new Font("Serif", Font.PLAIN, 15));
			number.setSize(130,20);
			number.setLocation(195,130);
			
			tf = new JTextField(3);
			tf.setSize(50,25);
			tf.setLocation(330,130);
			
			add = new JButton("등록");
			add.setSize(60,30);
			add.setLocation(390, 127);
			
			columns = new Vector();
			columns.add("운동명");
			columns.add("운동 횟수(시간)");
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable 내용 편집 안되게
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
			
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable 드래그 못하게 고정
			table.setRowSorter(new TableRowSorter(dtm));
			
			makingTable();
			
			pane = new JScrollPane(table);
			panel = new JPanel();
			panel.setSize(600,130);
			panel.setLocation(40,180);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			
			delete = new JButton("삭제");
			delete.setSize(60,30);
			delete.setLocation(50,340);
			
			next = new JButton("다음");
			next.setSize(60,30);
			next.setLocation(560,340);
			
			Handler handler = new Handler();
			add.addActionListener(handler);
			delete.addActionListener(handler);
			next.addActionListener(handler);
			
			add(title);
			add(choice);
			add(number);
			add(tf);
			add(add);
			add(panel);
			add(delete);
			add(next);
		}
		
		void makingChoice() 
		{
			choice.add("무산소 운동을 선택해주세요.");
			
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
				
				sql = "select exercise from exercise where extype = '무산소'";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					choice.add(rs.getString("exercise"));
				}
				
				rs.close();
				stmt.close();
				conn.close();

			} catch(SQLException e)
			{
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		void makingTable()
		{	
			if(table.getRowCount()>0)
			{ 
				//DefaultTableModel의 Row를 초기화 시켜 테이블의 자료를 전부 삭제한다. (새로고침과 같은 효과)
				dtm.setDataVector(new Vector(), columns);
			}
			
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
				
				sql = "select exercise, exnum from exerciserecord where extype = '무산소' and day = (to_char(sysdate, 'yy-mm-dd'))";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString("exercise"));
					data.add(rs.getInt("exnum"));
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
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == add)
				{
					int num = 0;
					String tmp = choice.getSelectedItem();
					
					if(tf.getText().equals(""))
					{
						RecordTab.errDialog("등록 실패", "운동 횟수를 입력해주세요.", tf);
						return;
					}
						
					try
					{
						num = Integer.parseInt(tf.getText());
					}
					catch(NumberFormatException ne)
					{
						RecordTab.errDialog("등록 실패", "숫자만 입력 가능합니다.", tf);
						return;
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
						
						sql = "insert into exerciserecord (day, exercise, exnum, extype) values (to_char(sysdate, 'yy-mm-dd'), '"
								+ tmp + "', " + num + ", '무산소')";
						int result = stmt.executeUpdate(sql);
						
						if(result>0)
						{
							DietTab.popDialog("등록 성공", "성공적으로 등록하였습니다");
							tf.setText("");
							choice.select(0);
							makingTable();
						}
						
						rs.close();
						stmt.close();
						conn.close();

					} catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
				
				if(gs == delete)
				{
					ArrayList selected = new ArrayList();
					int[] row = table.getSelectedRows();
					
					for(int i=0; i<row.length; i++)
					{
						for(int col=0; col<columns.size(); col++)
						{
							if(col==0)
							{
								String value1 = (String)table.getValueAt(row[i],col);
								selected.add(value1);
							}
							else
							{
								int value2 = (int)table.getValueAt(row[i],col);
								selected.add(value2);
							}
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
						
						Iterator it = selected.iterator();
						int delresult = 0;
						
						while(it.hasNext())
						{
							String selex = (String) it.next();
							int selnum = (int) it.next();

							sql = "delete from exerciserecord where exercise = '" + selex
									+ "' and exnum = " + selnum;
							
							int result = stmt.executeUpdate(sql);
							if(result>0)
							{
								delresult++;
							}
						}
						
						if(delresult>0)
						{
							makingTable();
							DietTab.popDialog("삭제 성공", delresult + "건이 삭제되었습니다.");
						}
							
						rs.close();
						stmt.close();
						conn.close();

					} catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
				
				if(gs == next)
				{
					tab.setComponentAt(1, new ExerciseRecord2());
				}
			}
		}
	}
	
	class ExerciseRecord2 extends JPanel
	{
		Choice choice;
		JPanel panel;
		JScrollPane pane;
		JTable table;
		JLabel title;
		JLabel number;
		JLabel label;
		JTextField tf;
		JButton add;
		JButton delete;
		JButton prev;
		JButton next;
		
		Vector data;
		Vector columns;
		DefaultTableModel dtm;
		
		ExerciseRecord2()
		{
			setLayout(null);
			
			title = new JLabel("2. 유산소 운동", JLabel.CENTER);
			title.setFont(new Font("Serif", Font.BOLD, 20));
			title.setSize(150,30);
			title.setLocation(30,30);
			
			choice = new Choice();
			choice.setSize(600,30);
			choice.setLocation(40,85);
			
			makingChoice();
			
			number = new JLabel("운동 시간 (분): ", JLabel.RIGHT);
			number.setFont(new Font("Serif", Font.PLAIN, 15));
			number.setSize(130,20);
			number.setLocation(190,130);
			
			tf = new JTextField(3);
			tf.setSize(50,25);
			tf.setLocation(325,130);
			
			add = new JButton("등록");
			add.setSize(60,30);
			add.setLocation(385, 127);
			
			columns = new Vector();
			columns.add("운동명");
			columns.add("운동 시간(횟수)");
			columns.add("소모 칼로리");
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable 내용 편집 안되게
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
			
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable 드래그 못하게 고정
			table.setRowSorter(new TableRowSorter(dtm));
			
			makingTable();
			
			pane = new JScrollPane(table);
			panel = new JPanel();
			panel.setSize(600,130);
			panel.setLocation(40,180);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			
			delete = new JButton("삭제");
			delete.setSize(60,30);
			delete.setLocation(50,340);
			
			prev = new JButton("이전");
			prev.setSize(60,30);
			prev.setLocation(480,340);
			
			next = new JButton("등록");
			next.setSize(60,30);
			next.setLocation(560,340);
			
			Handler handler = new Handler();
			add.addActionListener(handler);
			delete.addActionListener(handler);
			next.addActionListener(handler);
			prev.addActionListener(handler);
			
			add(title);
			add(choice);
			add(number);
			add(tf);
			add(add);
			add(panel);
			add(delete);
			add(prev);
			add(next);
		}
		
		void makingChoice() 
		{
			choice.add("유산소 운동을 선택해주세요.");
			
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
				
				sql = "select exercise from exercise where extype = '유산소'";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					choice.add(rs.getString("exercise"));
				}
				
				rs.close();
				stmt.close();
				conn.close();

			} catch(SQLException e)
			{
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		void makingTable()
		{	
			if(table.getRowCount()>0)
			{ 
				//DefaultTableModel의 Row를 초기화 시켜 테이블의 자료를 전부 삭제한다. (새로고침과 같은 효과)
				dtm.setDataVector(new Vector(), columns);
			}

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
				
				sql = "select exercise, exnum, cal from exerciserecord where extype = '유산소' and day = (to_char(sysdate, 'yy-mm-dd'))";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString("exercise"));
					data.add(rs.getInt("exnum"));
					data.add(rs.getFloat("cal"));
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
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == add)
				{
					int num = 0;
					int cal = 0;
					String tmp = choice.getSelectedItem();
					
					if(tf.getText().equals(""))
					{
						RecordTab.errDialog("등록 실패", "운동 시간을 입력해주세요.", tf);
						return;
					}
						
					try
					{
						num = Integer.parseInt(tf.getText());
					}
					catch(NumberFormatException ne)
					{
						RecordTab.errDialog("등록 실패", "숫자만 입력 가능합니다.", tf);
						return;
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
						
						sql = "select cal from exercise where exercise = '" + tmp + "'";
						rs = stmt.executeQuery(sql);
						
						if(rs.next())
						{
							cal = rs.getInt("cal");
						}	
						
						sql = "insert into exerciserecord (day, exercise, exnum, cal, extype) values (to_char(sysdate, 'yy-mm-dd'), '"
								+ tmp +"', " + num + ", " + (num/10f)*cal + ", '유산소')";
						int result = stmt.executeUpdate(sql);
						
						if(result>0)
						{
							DietTab.popDialog("등록 성공", "성공적으로 등록하였습니다");
							tf.setText("");
							choice.select(0);
							makingTable();
						}
						
						rs.close();
						stmt.close();
						conn.close();

					} catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
				
				if(gs == delete)
				{
					ArrayList selected = new ArrayList();
					int[] row = table.getSelectedRows();
					
					for(int i=0; i<row.length; i++)
					{
						for(int col=0; col<columns.size()-1; col++)
						{
							if(col==0)
							{
								String value1 = (String)table.getValueAt(row[i],col);
								selected.add(value1);
							}
							else
							{
								int value2 = (int)table.getValueAt(row[i],col);
								selected.add(value2);
							}
						}
					}
					
					for(int i=0; i<selected.size(); i++)
					{
						System.out.println(selected.get(i));
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
						
						Iterator it = selected.iterator();
						int delresult = 0;
						
						while(it.hasNext())
						{
							String selex = (String) it.next();
							int selnum = (int) it.next();

							sql = "delete from exerciserecord where exercise = '" + selex
									+ "' and exnum = " + selnum;
							
							int result = stmt.executeUpdate(sql);
							if(result>0)
							{
								delresult++;
							}
						}
						
						if(delresult>0)
						{
							makingTable();
							DietTab.popDialog("삭제 성공", delresult + "건이 삭제되었습니다.");
						}
							
						rs.close();
						stmt.close();
						conn.close();

					} catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
				
				if(gs == prev)
				{
					tab.setComponentAt(1, new ExerciseRecord1());
				}
				if(gs == next)
				{
					tab.setComponentAt(0, new RecordView());
					tab.setSelectedIndex(0);
					DietTab.popDialog("등록 완료", "등록이 완료되었습니다.");
				}
			}
		}
	}
	
	class ExerciseSearch extends JPanel
	{
		Connection conn;
		Statement stmt;
		ResultSet rs;
		
		JLabel search;
		JLabel waadd;
		JTextField tf;
		JButton ok;
		JButton all;
		JButton add;
		
		JPanel panel;
		JScrollPane pane;
		JTable table;
		DefaultTableModel dtm;
		Vector columns;
		Vector data;
		
		ExerciseSearch()
		{
			setLayout(null);
			
			search = new JLabel("운동 검색: ", JLabel.RIGHT);
			search.setFont(new Font("Serif", Font.BOLD, 20));
			search.setSize(150,20);
			search.setLocation(23,30);
			
			tf = new JTextField(30);
			tf.setSize(250,25);
			tf.setLocation(183,29);
			
			ok = new JButton("검색");
			ok.setSize(60,30);
			ok.setLocation(443,25);
			
			all = new JButton("전체보기");
			all.setSize(90,30);
			all.setLocation(513, 25);
			
			columns = new Vector();
			columns.add("운동명");
			columns.add("분류");
			columns.add("칼로리");
			
			dtm = new DefaultTableModel(columns, 0);
			table = new JTable(dtm);
			makingTable();
			
			pane = new JScrollPane(table);
			panel = new JPanel();
			panel.setSize(600,210);
			panel.setLocation(40,80);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			
			waadd = new JLabel("찾으시는 운동이 없으세요?", JLabel.RIGHT);
			waadd.setSize(180,15);
			waadd.setLocation(300,340);
			
			add = new JButton("등록하기");
			add.setLocation(500,325);
			add.setSize(100,40);
			
			Handler handler = new Handler();
			ok.addActionListener(handler);
			tf.addActionListener(handler);
			add.addActionListener(handler);
			all.addActionListener(handler);
			
			add(search);
			add(tf);
			add(ok);
			add(all);
			add(panel);
			add(waadd);
			add(add);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == add)
				{
					tab.setSelectedIndex(3);
				}
				
				if(gs == ok || gs == tf)
				{
					searchTable();
				}
				
				if(gs == all)
				{
					makingTable();
				}
			}
		}
		
		void makingTable()
		{	
			if(table.getRowCount()>0)
			{ 
				//DefaultTableModel의 Row를 초기화 시켜 테이블의 자료를 전부 삭제한다. (새로고침과 같은 효과)
				dtm.setDataVector(new Vector(), columns);
			}
			
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
				
				sql = "select exercise, extype, cal from exercise order by exercise asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString("exercise"));
					data.add(rs.getString("extype"));
					data.add(rs.getInt("cal"));
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
		
		void searchTable()
		{
			String tmp = tf.getText();
			
			if(tmp.equals(""))
			{
				RecordTab.errDialog("검색 실패", "검색어를 입력해주세요.", tf);
				return;
			}
			
			if(table.getRowCount()>0)
			{ 
				//DefaultTableModel의 Row를 초기화 시켜 테이블의 자료를 전부 삭제한다. (새로고침과 같은 효과)
				dtm.setDataVector(new Vector(), columns);
			}
			
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
				
				sql = "select exercise, extype, cal from exercise where exercise like '%" + tmp + "%'";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString("exercise"));
					data.add(rs.getString("extype"));
					data.add(rs.getInt("cal"));
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
	}
	
	class ExerciseAdd extends JPanel
	{
		JLabel title;
		JLabel exercise;
		JLabel cal;
		JLabel yumu;
		JLabel label;
		JTextField tf1;
		JTextField tf2;
		ButtonGroup group;
		JRadioButton[] choose;
		JButton ok;
		JButton cancel;
		
		ExerciseAdd()
		{
			setLayout(null);
			
			title = new JLabel("＊운동 등록하기", JLabel.CENTER);
			title.setFont(new Font("Serif", Font.BOLD, 25));
			title.setSize(200,45);
			title.setLocation(30,30);
			
			Font font = new Font("Serif", Font.PLAIN, 20);
			
			exercise = new JLabel("운동명: ",JLabel.RIGHT);
			exercise.setFont(font);
			exercise.setSize(100,20);
			exercise.setLocation(130,120);
			
			tf1 = new JTextField(30);
			tf1.setSize(270,30);
			tf1.setLocation(235,117);
			
			cal = new JLabel("소모 칼로리: ", JLabel.RIGHT);
			cal.setFont(font);
			cal.setSize(130,20);
			cal.setLocation(100,170);
			
			tf2 = new JTextField(3);
			tf2.setSize(100,30);
			tf2.setLocation(235,167);
			
			label = new JLabel("칼로리는 10분 단위로 입력해주세요.", JLabel.LEFT);
			label.setSize(220,20);
			label.setLocation(340, 173);
			
			yumu = new JLabel("운동 구분: ", JLabel.RIGHT);
			yumu.setFont(font);
			yumu.setSize(130,20);
			yumu.setLocation(100, 220);
			
			ok = new JButton("확인");
			ok.setLocation(420,348);
			ok.setSize(100,40);

			cancel = new JButton("취소");
			cancel.setLocation(530,348);
			cancel.setSize(100,40);
			
			group = new ButtonGroup();
			choose = new JRadioButton[2];
			choose[0] = new JRadioButton("유산소");
			choose[1] = new JRadioButton("무산소");
			
			for(int i=0;i<choose.length;i++)
			{
				group.add(choose[i]);
				choose[i].setFont(new Font("Serif", Font.PLAIN, 17));
				choose[i].setSize(100,30);
				choose[i].setLocation(250+(100*i), 217);
				add(choose[i]);
			}
			
			Handler handler = new Handler();
			ok.addActionListener(handler);
			cancel.addActionListener(handler);
			
			add(title);
			add(exercise);
			add(tf1);
			add(cal);
			add(tf2);
			add(label);
			add(yumu);
			add(ok);
			add(cancel);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs==ok)
				{
					String ys = "";
					String exname = "";
					int excal = 0;
					
					if((tf1.getText()).equals(""))
					{
						RecordTab.errDialog("등록 실패", "운동명을 정확히 입력해주세요", tf1);
						return;
					}
					if((tf1.getText()).length()>30)
					{
						RecordTab.errDialog("등록 실패", "30자 이내로 입력해주세요.", tf1);
						return;
					}
					
					exname = tf1.getText();
					
					if(choose[0].isSelected() && (tf2.getText()).equals(""))
					{
						RecordTab.errDialog("등록 실패", "칼로리를 숫자로 입력해주세요.", tf2);
						return;
					}
					
					if(choose[1].isSelected())
					{
						excal = 0;
					}
					else
					{
						excal = Integer.parseInt(tf2.getText());
					}
					
					for(int i=0; i<choose.length; i++)
					{
						if(choose[i].isSelected())
						{
							ys = choose[i].getText();	
						}
					}
					
					if(ys.equals(""))
					{
						DietTab.popDialog("등록 실패", "유산소/무산소를 선택해주세요.");
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
						sql = "insert into exercise values('" + exname + "', " + excal + ", '" + ys + "')";
						int result = stmt.executeUpdate(sql);
						if(result>0)
						{
							RecordTab.errDialog("등록 완료", "운동이 등록되었습니다.", tf1);
							tf1.setText("");
							tf2.setText("");
							choose[0].setSelected(false);
							choose[1].setSelected(false);
							
							tab.setComponentAt(1, new ExerciseRecord1());
							tab.setComponentAt(2, new ExerciseSearch());
						}
						
						rs.close();
						stmt.close();
						conn.close();
						
					} catch(SQLException se)
					{
						//System.err.println("SQLException: " + se.getMessage());
						RecordTab.errDialog("등록 실패", "동일한 운동명이 있습니다.", tf1);
					}
				}
				if(gs==cancel)
				{
					tab.setSelectedIndex(0);
				}
			}
		}
		
	}

}
