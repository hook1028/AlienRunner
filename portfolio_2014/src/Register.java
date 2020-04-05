import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

class Register extends JDialog
{
	JLabel essential;
	JLabel nEssential;
	JLabel nickname;
	JLabel nNickname;
	JLabel height;
	JLabel weight;
	JLabel goal;
	JLabel option;
	JLabel nOption;
	JLabel bust;
	JLabel waist;
	JLabel thigh;
	
	JTextField tfNickname;
	JTextField tfHeight;
	JTextField tfWeight;
	JTextField tfGoal;
	JTextField tfBust;
	JTextField tfWaist;
	JTextField tfThigh;
	
	JButton ok;
	JButton cancel;
	
//	Register(JFrame frame, String title, boolean modal)
	Register()
	{
//		super(frame, title, modal);
		super();
		
		setTitle("����ϱ�");
//		new Login(this, "Login", true).setVisible(true);
		
		essential = new JLabel("* �ʼ� �Է� ����");
		nEssential = new JLabel("(Ű�� �����Դ� ���ڸ� �Է����ּ���.)");
		nickname = new JLabel("�г��� / �̸� : ", JLabel.RIGHT);
		nNickname = new JLabel("10�� �̳��� �ۼ����ּ���.");
		height = new JLabel("Ű : ", JLabel.RIGHT);
		weight = new JLabel("ü�� : ", JLabel.RIGHT);
		goal = new JLabel("��ǥ ü��:", JLabel.RIGHT);
		option = new JLabel("* ���� �Է� ����");
		nOption = new JLabel("(���ڸ� �Է����ּ���.)");
		bust = new JLabel("�����ѷ� : ", JLabel.RIGHT);
		waist = new JLabel("�㸮�ѷ� : ", JLabel.RIGHT);
		thigh = new JLabel("������ѷ� : ", JLabel.RIGHT);
		
		tfNickname = new JTextField(10);
		tfHeight = new JTextField(10);
		tfWeight = new JTextField(10);
		tfGoal = new JTextField(10);
		tfBust = new JTextField(10);
		tfWaist = new JTextField(10);
		tfThigh = new JTextField(10);
		
		ok = new JButton("Ȯ��");
		cancel = new JButton("���");
		
		setLayout(null);	
		setBounds(MainFrame.center().width/2 - 350, MainFrame.center().height/2 - 250, 700, 500);
		
		Font subtitle = new Font("Serif", Font.BOLD, 20);
		Font item = new Font("Serif", Font.PLAIN, 17);
		Font notice = new Font("Serif", Font.PLAIN, 13);
		
		essential.setLocation(40,30);
		essential.setFont(subtitle);
		essential.setSize(300,20);
		
		nEssential.setLocation(200,30);
		nEssential.setFont(notice);
		nEssential.setSize(230,20);
		
		nickname.setLocation(115, 80);
		nickname.setFont(item);
		nickname.setSize(150,20);
		
		tfNickname.setLocation(270, 80);
		tfNickname.setSize(200, 25);
		
		nNickname.setLocation(475,80);
		nNickname.setFont(notice);
		nNickname.setSize(180,20);
		
		height.setLocation(115,110);
		height.setFont(item);
		height.setSize(150,20);
		
		tfHeight.setLocation(270, 110);
		tfHeight.setSize(200, 25);
		
		weight.setLocation(115, 140);
		weight.setFont(item);
		weight.setSize(150,20);
		
		tfWeight.setLocation(270, 140);
		tfWeight.setSize(200,25);
		
		goal.setLocation(115, 170);
		goal.setFont(item);
		goal.setSize(150,20);
		
		tfGoal.setLocation(270,170);
		tfGoal.setSize(200,25);
		
		option.setLocation(40, 230);
		option.setFont(subtitle);
		option.setSize(300,20);
		
		nOption.setLocation(200,230);
		nOption.setFont(notice);
		nOption.setSize(230,20);
		
		bust.setLocation(115,280);
		bust.setFont(item);
		bust.setSize(150,20);
		
		tfBust.setLocation(270,280);
		tfBust.setSize(200,25);
		
		waist.setLocation(115,310);
		waist.setFont(item);
		waist.setSize(150,20);
		
		tfWaist.setLocation(270,310);
		tfWaist.setSize(200,25);
		
		thigh.setLocation(115,340);
		thigh.setFont(item);
		thigh.setSize(150,20);
		
		tfThigh.setLocation(270,340);
		tfThigh.setSize(200,25);
		
		ok.setLocation(500,400);
		ok.setSize(70,40);
		
		cancel.setLocation(590,400);
		cancel.setSize(70,40);
		
		ok.addActionListener(new Handler());
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Register.this.dispose();
			}
		});
		
		add(essential);
		add(nEssential);
		add(nickname);
		add(tfNickname);
		add(nNickname);
		add(height);
		add(tfHeight);
		add(weight);
		add(tfWeight);
		add(goal);
		add(tfGoal);
		add(option);
		add(nOption);
		add(bust);
		add(tfBust);
		add(waist);
		add(tfWaist);
		add(thigh);
		add(tfThigh);
		add(ok);
		add(cancel);
			
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	void errdialog(int code)
	{
		JDialog errd = new JDialog(this, "�޽���", true);
		errd.setBounds(MainFrame.center().width/2 - 100, MainFrame.center().height/2 - 50, 200,100);
		errd.setLayout(new FlowLayout());
		
		JLabel msg = new JLabel();
		JButton ok2 = new JButton("Ȯ��");
		
		if(code==0)
		{
			msg.setText("�г����� �Է����ּ���.");
		}
		else if(code==1)
		{
			msg.setText("������ �Է��� �ּ���.");
		}
		else if(code==2)
		{
			msg.setText("ü���� �Է��� �ּ���.");
		}
		else if(code==3)
		{
			msg.setText("��ǥ ü���� �Է��� �ּ���.");
		}
		else if(code==4)
		{
			msg.setText("��Ͽ� �����߽��ϴ�..");
		}
		
		errd.add(msg);
		errd.add(ok2);
		
		ok2.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						errd.dispose();
					}
				});
		
		errd.setResizable(false);
		errd.setVisible(true);
	}
	
	class Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int height;
			int goal;
			int weight;
			int bust = 0;
			int waist = 0;
			int thigh = 0;
			
			try
			{
				height = Integer.parseInt(tfHeight.getText());
				goal = Integer.parseInt(tfGoal.getText());
				weight = Integer.parseInt(tfWeight.getText());
				
			}catch(NumberFormatException ne)
			{
				DietTab.popDialog("��� ����", "ü���� ���ڷ� �Է����ּ���.");
				return;
			}
			
			if(!((tfBust.getText()).equals("")))
			{
				try
				{
					bust = Integer.parseInt(tfBust.getText());
				}catch(NumberFormatException ne)
				{
					DietTab.popDialog("��� ����", "�ѷ��� ���ڷ� �Է����ּ���.");
					return;
				}
			}
			
			if(!((tfWaist.getText()).equals("")))
			{
				try
				{
					waist = Integer.parseInt(tfWaist.getText());
				}catch(NumberFormatException ne)
				{
					DietTab.popDialog("��� ����", "�ѷ��� ���ڷ� �Է����ּ���.");
					return;
				}
			}
			
			if(!((tfThigh.getText()).equals("")))
			{
				try
				{
					thigh = Integer.parseInt(tfThigh.getText());
				}catch(NumberFormatException ne)
				{
					DietTab.popDialog("��� ����", "�ѷ��� ���ڷ� �Է����ּ���.");
					return;
				}
			}
			
			if((tfNickname.getText()).equals(""))
			{
				errdialog(0);
				tfNickname.requestFocus();
				return;
			}
			else if((tfHeight.getText()).equals(""))
			{
				errdialog(1);
				tfHeight.requestFocus();
				return;
			}
			else if((tfWeight.getText()).equals(""))
			{
				errdialog(2);
				tfWeight.requestFocus();
				return;
			}
			else if((tfGoal.getText()).equals(""))
			{
				errdialog(3);
				tfGoal.requestFocus();
				return;
			}
			else
			{
				Connection conn;
				Statement stmt;
				
				try
				{
					Class.forName("oracle.jdbc.driver.OracleDriver");
				} catch(ClassNotFoundException ce)
				{
					System.err.println("ClassNotFoundException: " + ce.getMessage());
				}
				try
				{
					String sql;

					conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
					stmt = conn.createStatement();
					sql = "select password from userdata";
					ResultSet rs = stmt.executeQuery(sql);
					String password = "";
				
					if(rs.next()) 
					{
						password = rs.getString("password");
					}
					
					sql = "update userdata set nickname = '" + tfNickname.getText() 
							+ "', height = " + height 
							+ ", goal = " + goal 
							+ " where password = '" + password + "'";
					
					int result = stmt.executeUpdate(sql);
				
					sql = "insert into weight values (to_char(sysdate, 'yy-mm-dd')," 
							+ weight + ")"; 
				
					result = stmt.executeUpdate(sql);
					if(result<=0)
					{
						errdialog(4);
						return;
					}

					sql = "insert into bust values (to_char(sysdate, 'yy-mm-dd')," + bust + ")";
					
					result = stmt.executeUpdate(sql);
					if(result<=0)
					{
						errdialog(4);
						return;
					}
						
					sql = "insert into waist values (to_char(sysdate, 'yy-mm-dd')," + waist + ")";
						
					result = stmt.executeUpdate(sql);
					if(result<=0)
					{
						errdialog(4);
						return;
					}
					
					sql = "insert into thigh values (to_char(sysdate, 'yy-mm-dd')," + thigh + ")";
					
					result = stmt.executeUpdate(sql);
					if(result<=0)
					{
						errdialog(4);
						return;
					}
					
					JDialog dialog = new JDialog();

					JLabel label = new JLabel("����� �Ϸ�Ǿ����ϴ�.");
					JButton button = new JButton("Ȯ��");
					
					dialog.setModal(false);
					dialog.setTitle("��� �Ϸ�");
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
							Register.this.dispose();
							new MainFrame("Main");
						}
					});
				
					rs.close();
					stmt.close();
					conn.close();
				}catch(SQLException se)
				{
					System.err.println("SQLException: " + se.getMessage());
				}
			}
		}
	}
	
/*	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.add(new Register());
		f.setBounds(700,500, 700,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}	*/
}
