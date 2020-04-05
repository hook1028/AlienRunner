import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

//�ǽð����� BMI �߰��ǰ� �ϴ� ��� - �ӽ���ġ

public class RecordTab extends JPanel
{
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String sql;
	
	JTabbedPane tab;
	BMI bmi;
	
	RecordTab()
	{
		super();
		
		setLayout(new BorderLayout());
		
		tab = new JTabbedPane();
		tab.setFont(new Font("Serif",Font.BOLD,15));
		tab.addTab("BMI", bmi);
		tab.addTab("ü��", new Weight());
		tab.addTab("�����ѷ�", new Bust());
		tab.addTab("�㸮�ѷ�", new Waist());
		tab.addTab("������ѷ�", new Thigh());
		
		add(tab,"Center");
	}
	
	static void errDialog(String title, String text, JTextField tf)
	{
		JDialog dialog = new JDialog();

		JLabel label = new JLabel(text);
		JButton button = new JButton("Ȯ��");
		
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
				tf.requestFocus();
			}
		});
	}
	
	class BMI extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JLabel label;
		JButton hModify;
		
		Vector columns;
		Vector data;
		int height = 0;
		
		BMI()
		{	
			columns = new Vector();
			columns.add("��¥");
			columns.add("BMI");	
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable ���� ���� �ȵǰ�
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable �巡�� ���ϰ� ����
			table.setRowSorter(new TableRowSorter(dtm));	//Jtable ��� Ŭ�� �� ���� ����
			//convertRowIndexToModel(int) : ���� �� ������ �ƴ� ���� �����Ͱ� ����ִ� ������ �ε��� ��ȣ ��ȯ
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			
			label = new JLabel("���� Ű" + height + "cm ����");
			label.setLocation(250, 15);
			label.setSize(300,25);
			label.setFont(new Font("serif", Font.BOLD, 20));
			
			hModify = new JButton("Ű ����");
			hModify.setLocation(530,358);
			hModify.setSize(100,40);

			add(label);
			add(panel);
			add(hModify);
			
			hModify.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							tab.setComponentAt(0, new HeightModify());
						}
					});
		}
		
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
				
				sql = "select height from userdata";
				rs = stmt.executeQuery(sql);
				if (rs.next()) height = rs.getInt("height");
				
				sql = "select to_char(day, 'yy-mm-dd'), weight from weight order by to_char(day, 'yy-mm-dd') asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					float bmi = Math.round((rs.getFloat(2)/((height/100f)*(height/100f)))*10f)/10f;
					data.add(bmi);
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
		
	class HeightModify extends JPanel
	{
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton cancel;
		
		HeightModify()
		{		
			setLayout(null);
			
			label = new JLabel("Ű�� �ٽ� �Է����ּ���.");
			label.setFont(new Font("Serif", Font.BOLD, 20));
			label.setSize(300, 25);
			label.setLocation(230, 120);
			
			tf = new JTextField(3);
			tf.setSize(100,25);
			tf.setLocation(290, 170);
			
			ok = new JButton("����");
			ok.setLocation(420,358);
			ok.setSize(100,40);

			cancel = new JButton("���");
			cancel.setLocation(530,358);
			cancel.setSize(100,40);
			
			add(label);
			add(tf);
			add(ok);
			add(cancel);
			
			ok.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							int height = 0;
							String tmp = tf.getText();
							
							if(tmp.length()>3 || tmp.equals(""))
							{
								errDialog("��Ͻ���", "Ű�� ��Ȯ�� �Է����ּ���", tf);
								return;
							}
							for(int i=0; i<(tf.getText()).length(); i++)
							{
								if(tmp.charAt(i)<'0' || tmp.charAt(i)>'9')
								{
									errDialog("��Ͻ���", "���ڸ� �Է��� �����մϴ�.", tf);
									return;
								}
							}
							
							height = Integer.parseInt(tmp);
							
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
								
								sql = "update userdata set height = " + height;
								int result = stmt.executeUpdate(sql);
								
								if(result>0)
								{
									tab.setComponentAt(0, new BMI());
									
									stmt.close();
									conn.close();
								}
								
							} catch(SQLException se)
							{
								System.err.println("SQLException: " + se.getMessage());
							}
						}
					});
			
			cancel.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							tab.setComponentAt(0, new BMI());
						}
					});
		}
	}
	
	
	class Weight extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton wModify;
		
		Vector columns;
		Vector data;
		
		Weight()
		{	
			columns = new Vector();
			columns.add("��¥");
			columns.add("ü��");	
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable ���� ���� �ȵǰ�
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable �巡�� ���ϰ� ����
			table.setRowSorter(new TableRowSorter(dtm));
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			
			label = new JLabel("�Է� : ");
			label.setSize(200, 20);
			label.setFont(new Font("Serif", Font.BOLD, 17));
			label.setLocation(250, 373);
										
			ok = new JButton("Ȯ��");
			ok.setLocation(420,358);
			ok.setSize(100,40);
			
			tf = new JTextField(10);
			tf.setSize(100,25);
			tf.setLocation(300,370);
			
			wModify = new JButton("ü�� ����");
			wModify.setLocation(530,358);
			wModify.setSize(100,40);

			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					insert();
				}
			});
			tf.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					insert();
		//			makingTable();
				}
			});
			
			wModify.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					tab.setComponentAt(1, new WeightModify());
				}
			});
			
			add(panel);
			add(label);
			add(tf);
			add(ok);
			add(wModify);
		}
		
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
				sql = "select to_char(day, 'yy-mm-dd'), weight from weight order by to_char(day, 'yy-mm-dd') asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					data.add(rs.getInt(2));
					dtm.addRow(data);
				}
				
				tab.setComponentAt(0, new BMI());
				
				rs.close();
				stmt.close();
				conn.close();

			} catch(SQLException e)
			{
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		
		void insert()
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
				if((tf.getText()).equals(""))
				{
					errDialog("��Ͻ���", "ü���� ��Ȯ�� �Է����ּ���", tf);
					return;
				}
				
				conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
				stmt = conn.createStatement();
				sql = "insert into weight values(to_char(sysdate, 'yy-mm-dd')," 
						+ Integer.parseInt(tf.getText()) + ")";
				int result = stmt.executeUpdate(sql);
				if(result>0)
				{
					errDialog("��� �Ϸ�", "ü���� ��ϵǾ����ϴ�.", tf);
					tf.setText("");
				}
				if(table.getRowCount()>0)
				{ 
					//DefaultTableModel�� Row�� �ʱ�ȭ ���� ���̺��� �ڷḦ ���� �����Ѵ�. (���ΰ�ħ�� ���� ȿ��)
					dtm.setDataVector(new Vector(), columns);
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
				makingTable();

			} catch(SQLException se)
			{
				errDialog("��� ����", "������ �̹� ����ϼ̽��ϴ�.", tf);
			}
		}
		
		
	}
	
	class WeightModify extends JPanel
	{
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton cancel;
		
		WeightModify()
		{		
			setLayout(null);
			
			label = new JLabel("ü���� �ٽ� �Է����ּ���.");
			label.setFont(new Font("Serif", Font.BOLD, 20));
			label.setSize(300, 25);
			label.setLocation(230, 120);
			
			tf = new JTextField(3);
			tf.setSize(100,25);
			tf.setLocation(290, 170);
			
			ok = new JButton("����");
			ok.setLocation(420,358);
			ok.setSize(100,40);

			cancel = new JButton("���");
			cancel.setLocation(530,358);
			cancel.setSize(100,40);
			
			add(label);
			add(tf);
			add(ok);
			add(cancel);
			
			ok.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							int weight = 0;
							String tmp = tf.getText();
							
							if(tmp.length()>3 || tmp.equals(""))
							{
								errDialog("��Ͻ���", "ü���� ��Ȯ�� �Է����ּ���", tf);
								return;
							}
							for(int i=0; i<(tf.getText()).length(); i++)
							{
								if(tmp.charAt(i)<'0' || tmp.charAt(i)>'9')
								{
									errDialog("��Ͻ���", "���ڸ� �Է��� �����մϴ�.", tf);
									return;
								}
							}
							
							weight = Integer.parseInt(tf.getText());
							
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
								
								sql = "update weight set weight = " + weight
										+ "where day = to_char(sysdate, 'yy-mm-dd')";
								int result = stmt.executeUpdate(sql);
								
								if(result>0)
								{
									stmt.close();
									conn.close();
									
									tab.setComponentAt(1, new Weight());
								}
								
							} catch(SQLException se)
							{
								System.err.println("SQLException: " + se.getMessage());
							}
						}
					});
			
			cancel.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							tab.setComponentAt(1, new Weight());
						}
					});
		}
	}
	
	class Bust extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton bModify;
		
		Vector columns;
		Vector data;
		
		Bust()
		{	
			columns = new Vector();
			columns.add("��¥");
			columns.add("�����ѷ�");	
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable ���� ���� �ȵǰ�
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable �巡�� ���ϰ� ����
			table.setRowSorter(new TableRowSorter(dtm));
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			
			label = new JLabel("�Է� : ");
			label.setSize(200, 20);
			label.setFont(new Font("Serif", Font.BOLD, 17));
			label.setLocation(250, 373);
										
			ok = new JButton("Ȯ��");
			ok.setLocation(420,358);
			ok.setSize(100,40);
			
			tf = new JTextField(10);
			tf.setSize(100,25);
			tf.setLocation(300,370);
			
			bModify = new JButton("ġ�� ����");
			bModify.setLocation(530,358);
			bModify.setSize(100,40);

			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					insert();
				}
			});
			tf.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					insert();
		//			makingTable();
				}
			});
			
			bModify.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					tab.setComponentAt(2, new BustModify());
				}
			});
			
			add(panel);
			add(label);
			add(tf);
			add(ok);
			add(bModify);
		}
		
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
				sql = "select to_char(day, 'yy-mm-dd'), bust from bust order by to_char(day, 'yy-mm-dd') asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					data.add(rs.getInt(2));
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
		
		void insert()
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
				if((tf.getText()).equals(""))
				{
					errDialog("��� ����", "ġ���� ��Ȯ�ϰ� �Է����ּ���.", tf);
					return;
				}
				
				conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
				stmt = conn.createStatement();
				sql = "insert into bust values(to_char(sysdate, 'yy-mm-dd')," 
						+ Integer.parseInt(tf.getText()) + ")";
				int result = stmt.executeUpdate(sql);
				if(result>0)
				{
					errDialog("��� �Ϸ�", "ġ���� ��ϵǾ����ϴ�.", tf);
					tf.setText("");
					
				}
				if(table.getRowCount()>0)
				{ 
					//DefaultTableModel�� Row�� �ʱ�ȭ ���� ���̺��� �ڷḦ ���� �����Ѵ�. (���ΰ�ħ�� ���� ȿ��)
					dtm.setDataVector(new Vector(), columns);
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
				makingTable();

			} catch(SQLException se)
			{
				System.err.println("SQLException: " + se.getMessage());
				errDialog("��� ����", "������ �̹� ����ϼ̽��ϴ�.", tf);
			}
		}
	}
	
	class BustModify extends JPanel
	{
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton cancel;
		
		BustModify()
		{		
			setLayout(null);
			
			label = new JLabel("ġ���� �ٽ� �Է����ּ���.");
			label.setFont(new Font("Serif", Font.BOLD, 20));
			label.setSize(300, 25);
			label.setLocation(230, 120);
			
			tf = new JTextField(3);
			tf.setSize(100,25);
			tf.setLocation(290, 170);
			
			ok = new JButton("����");
			ok.setLocation(420,358);
			ok.setSize(100,40);

			cancel = new JButton("���");
			cancel.setLocation(530,358);
			cancel.setSize(100,40);
			
			add(label);
			add(tf);
			add(ok);
			add(cancel);
			
			ok.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							int bust = 0;
							String tmp = tf.getText();
							
							if(tmp.length()>3 || tmp.equals(""))
							{
								errDialog("��Ͻ���", "ġ���� ��Ȯ�� �Է����ּ���", tf);
								return;
							}
							for(int i=0; i<(tf.getText()).length(); i++)
							{
								if(tmp.charAt(i)<'0' || tmp.charAt(i)>'9')
								{
									errDialog("��Ͻ���", "���ڸ� �Է��� �����մϴ�.", tf);
									return;
								}
							}
							
							bust = Integer.parseInt(tf.getText());
							
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
								
								sql = "update bust set bust = " + bust
										+ "where day = to_char(sysdate, 'yy-mm-dd')";
								int result = stmt.executeUpdate(sql);
								
								if(result>0)
								{
									stmt.close();
									conn.close();
									
									tab.setComponentAt(2, new Bust());
								}
								
							} catch(SQLException se)
							{
								System.err.println("SQLException: " + se.getMessage());
							}
						}
					});
			
			cancel.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							tab.setComponentAt(2, new Bust());
						}
					});
		}
	}
	
	class Waist extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton wModify;
		
		Vector columns;
		Vector data;
		
		Waist()
		{	
			columns = new Vector();
			columns.add("��¥");
			columns.add("�㸮�ѷ�");	
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable ���� ���� �ȵǰ�
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable �巡�� ���ϰ� ����
			table.setRowSorter(new TableRowSorter(dtm));
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			
			label = new JLabel("�Է� : ");
			label.setSize(200, 20);
			label.setFont(new Font("Serif", Font.BOLD, 17));
			label.setLocation(250, 373);
										
			ok = new JButton("Ȯ��");
			ok.setLocation(420,358);
			ok.setSize(100,40);
			
			tf = new JTextField(10);
			tf.setSize(100,25);
			tf.setLocation(300,370);
			
			wModify = new JButton("ġ�� ����");
			wModify.setLocation(530,358);
			wModify.setSize(100,40);

			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					insert();
				}
			});
			tf.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					insert();
		//			makingTable();
				}
			});
			
			wModify.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					tab.setComponentAt(3, new WaistModify());
				}
			});
			
			add(panel);
			add(label);
			add(tf);
			add(ok);
			add(wModify);
		}
		
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
				sql = "select to_char(day, 'yy-mm-dd'), waist from waist order by to_char(day, 'yy-mm-dd') asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					data.add(rs.getInt(2));
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
		
		void insert()
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
				if((tf.getText()).equals(""))
				{
					errDialog("��� ����", "ġ���� ��Ȯ�ϰ� �Է����ּ���.", tf);
					return;
				}
				
				conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
				stmt = conn.createStatement();
				sql = "insert into waist values(to_char(sysdate, 'yy-mm-dd')," 
						+ Integer.parseInt(tf.getText()) + ")";
				int result = stmt.executeUpdate(sql);
				if(result>0)
				{
					errDialog("��� �Ϸ�", "ġ���� ��ϵǾ����ϴ�.", tf);
					tf.setText("");
					
				}
				if(table.getRowCount()>0)
				{ 
					//DefaultTableModel�� Row�� �ʱ�ȭ ���� ���̺��� �ڷḦ ���� �����Ѵ�. (���ΰ�ħ�� ���� ȿ��)
					dtm.setDataVector(new Vector(), columns);
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
				makingTable();

			} catch(SQLException se)
			{
				System.err.println("SQLException: " + se.getMessage());
				errDialog("��� ����", "������ �̹� ����ϼ̽��ϴ�.", tf);
			}
		}
	}
	
	class WaistModify extends JPanel
	{
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton cancel;
		
		WaistModify()
		{		
			setLayout(null);
			
			label = new JLabel("ġ���� �ٽ� �Է����ּ���.");
			label.setFont(new Font("Serif", Font.BOLD, 20));
			label.setSize(300, 25);
			label.setLocation(230, 120);
			
			tf = new JTextField(3);
			tf.setSize(100,25);
			tf.setLocation(290, 170);
			
			ok = new JButton("����");
			ok.setLocation(420,358);
			ok.setSize(100,40);

			cancel = new JButton("���");
			cancel.setLocation(530,358);
			cancel.setSize(100,40);
			
			add(label);
			add(tf);
			add(ok);
			add(cancel);
			
			ok.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							int waist = 0;
							String tmp = tf.getText();
							
							if(tmp.length()>3 || tmp.equals(""))
							{
								errDialog("��Ͻ���", "ġ���� ��Ȯ�� �Է����ּ���", tf);
								return;
							}
							
							//�Ʒ� ������� ������ try-catch������ �ٲ㺼 �� (�Ҽ� �Էµ� �����ϰ�)
							for(int i=0; i<(tf.getText()).length(); i++)
							{
								if(tmp.charAt(i)<'0' || tmp.charAt(i)>'9')
								{
									errDialog("��Ͻ���", "���ڸ� �Է��� �����մϴ�.", tf);
									return;
								}
							}
							
							waist = Integer.parseInt(tf.getText());
							
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
								
								sql = "update waist set waist = " + waist
										+ "where day = to_char(sysdate, 'yy-mm-dd')";
								int result = stmt.executeUpdate(sql);
								
								if(result>0)
								{
									stmt.close();
									conn.close();
									
									tab.setComponentAt(3, new Waist());
								}
								
							} catch(SQLException se)
							{
								System.err.println("SQLException: " + se.getMessage());
							}
						}
					});
			
			cancel.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							tab.setComponentAt(3, new Waist());
						}
					});
		}
	}
	
	class Thigh extends JPanel
	{
		JPanel panel;
		JScrollPane pane;
		DefaultTableModel dtm;
		JTable table;
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton tModify;
		
		Vector columns;
		Vector data;

		Thigh()
		{	
			columns = new Vector();
			columns.add("��¥");
			columns.add("������ѷ�");	
			
			dtm = new DefaultTableModel(columns,0){
	            // JTable ���� ���� �ȵǰ�
	            public boolean isCellEditable(int i, int c) {
	                return false;
	            }
	        };
	        
			makingTable();
	        
			setLayout(null);
	        
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);	//JTable �巡�� ���ϰ� ����
			table.setRowSorter(new TableRowSorter(dtm));
			pane = new JScrollPane(table);
			
			panel = new JPanel();
			panel.setSize(600,300);
			panel.setLocation(45,50);
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			
			label = new JLabel("�Է� : ");
			label.setSize(200, 20);
			label.setFont(new Font("Serif", Font.BOLD, 17));
			label.setLocation(250, 373);
										
			ok = new JButton("Ȯ��");
			ok.setLocation(420,358);
			ok.setSize(100,40);
			
			tf = new JTextField(10);
			tf.setSize(100,25);
			tf.setLocation(300,370);
			
			tModify = new JButton("ġ�� ����");
			tModify.setLocation(530,358);
			tModify.setSize(100,40);

			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					insert();
				}
			});
			tf.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					insert();
				}
			});
			
			tModify.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					tab.setComponentAt(4, new ThighModify());
				}
			});
			
			add(panel);
			add(label);
			add(tf);
			add(ok);
			add(tModify);
		}
		
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
				sql = "select to_char(day, 'yy-mm-dd'), thigh from thigh order by to_char(day, 'yy-mm-dd') asc";
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{							
					data = new Vector();
					data.add(rs.getString(1));
					data.add(rs.getInt(2));
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
		
		void insert()
		{
			if((tf.getText()).equals(""))
			{
				errDialog("��� ����", "ġ���� ��Ȯ�ϰ� �Է����ּ���.", tf);
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
				sql = "insert into thigh values(to_char(sysdate, 'yy-mm-dd')," 
						+ Integer.parseInt(tf.getText()) + ")";
				int result = stmt.executeUpdate(sql);
				if(result>0)
				{
					errDialog("��� �Ϸ�", "ġ���� ��ϵǾ����ϴ�.", tf);
					tf.setText("");
					
				}
				if(table.getRowCount()>0)
				{ 
					//DefaultTableModel�� Row�� �ʱ�ȭ ���� ���̺��� �ڷḦ ���� �����Ѵ�. (���ΰ�ħ�� ���� ȿ��)
					dtm.setDataVector(new Vector(), columns);
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
				makingTable();

			} catch(SQLException se)
			{
				System.err.println("SQLException: " + se.getMessage());
				errDialog("��� ����", "������ �̹� ����ϼ̽��ϴ�.", tf);
			}
		}
		
	}
	
	class ThighModify extends JPanel
	{
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton cancel;
		
		ThighModify()
		{		
			setLayout(null);
			
			label = new JLabel("ġ���� �ٽ� �Է����ּ���.");
			label.setFont(new Font("Serif", Font.BOLD, 20));
			label.setSize(300, 25);
			label.setLocation(230, 120);
			
			tf = new JTextField(3);
			tf.setSize(100,25);
			tf.setLocation(290, 170);
			
			ok = new JButton("����");
			ok.setLocation(420,358);
			ok.setSize(100,40);

			cancel = new JButton("���");
			cancel.setLocation(530,358);
			cancel.setSize(100,40);
			
			add(label);
			add(tf);
			add(ok);
			add(cancel);
			
			ok.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							int thigh = 0;
							String tmp = tf.getText();
							
							if(tmp.length()>3 || tmp.equals(""))
							{
								errDialog("��Ͻ���", "ġ���� ��Ȯ�� �Է����ּ���", tf);
								return;
							}
							for(int i=0; i<(tf.getText()).length(); i++)
							{
								if(tmp.charAt(i)<'0' || tmp.charAt(i)>'9')
								{
									errDialog("��Ͻ���", "���ڸ� �Է��� �����մϴ�.", tf);
									return;
								}
							}
							
							thigh = Integer.parseInt(tf.getText());
							
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
								
								sql = "update thigh set thigh = " + thigh
										+ "where day = to_char(sysdate, 'yy-mm-dd')";
								int result = stmt.executeUpdate(sql);
								
								if(result>0)
								{
									stmt.close();
									conn.close();
									
									tab.setComponentAt(4, new Thigh());
								}
								
							} catch(SQLException se)
							{
								System.err.println("SQLException: " + se.getMessage());
							}
						}
					});
			
			cancel.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							tab.setComponentAt(4, new Thigh());
						}
					});
		}
	}

}
