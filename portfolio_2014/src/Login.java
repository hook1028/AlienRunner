import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Login extends JDialog
{
	static String jdbc_url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String sql;
	
	String password = "";
	static JLabel label;
	JPasswordField tf;
	JButton ok;
	JButton cancel;
	
//	Login(JDialog dialog, String title, boolean modal)
	Login()
	{
//		super(dialog, title, modal);
		super();
		
		setModal(false);
		setTitle("Login");
		setVisible(true);
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e)
		{
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		
		try
		{
			conn = DriverManager.getConnection(jdbc_url, "scott", "tiger");
			stmt = conn.createStatement();
			sql = "select password from userdata";
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				password = rs.getString("password");
			}
			
		} catch(SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
		
		label = new JLabel(password.equals("") ? "비밀번호를 등록해주세요." : "비밀번호를 입력해주세요.");
		tf = new JPasswordField(8);
		ok = new JButton("확인");
		cancel = new JButton("취소");
		
		setBounds(MainFrame.center().width/2 - 150, MainFrame.center().height/2 - 60, 300,120);
		setLayout(new FlowLayout());
		setResizable(false);
		
		Handler handler = new Handler();
		ok.addActionListener(handler);
		cancel.addActionListener(handler);
		tf.addActionListener(handler);
		
		addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				});
		
		add(label);
		add(tf);
		add(ok);
		add(cancel);
	}
	
	public static void main(String[] args)
	{
		Login login = new Login();
		Main main = new Main();
		main.getTime();
	}
	
	void errdialog(int code)
	{
		JDialog errd = new JDialog(this, "오류", true);
		errd.setBounds(MainFrame.center().width/2 - 125, MainFrame.center().height/2 - 55, 250,110);
		errd.setLayout(new FlowLayout());
		
		JLabel msg = new JLabel();
		JButton ok2 = new JButton("확인");
		
		if(code==0)
		{
			msg.setText("등록을 위해 비밀번호를 입력해주세요.");
		}
		else if(code==1)
		{
			msg.setText("비밀번호 등록에 실패하였습니다.");
		}
		else if(code==2)
		{
			msg.setText("비밀번호를 정확히 입력해주세요.");
		}
		else if(code==3)
		{
			msg.setText("비밀번호는 3~8자리로 입력해주세요.");
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
			if(e.getSource()==cancel)
			{
				System.exit(0);
			}
			if(e.getSource()==ok || e.getSource()==tf)
			{
				try
				{
					if(password.equals(""))
					{
						if((tf.getText()).equals(""))
						{
							errdialog(0);
							return;
						}
						else if(tf.getText().length()<3 || tf.getText().length()>8)
						{
							errdialog(3);
							return;
						}
						else
						{
							password = tf.getText();
							sql = "insert into userdata (password) values (" + password + ")";
							int result = stmt.executeUpdate(sql);
						
							if(result>0)
							{
								Login.this.dispose();
								new Register().setVisible(true);
							} else
							{
								errdialog(1);
								return;
							}
						}
						
					} else
					{
						password = tf.getText();
						sql = "select password from userdata";
						rs = stmt.executeQuery(sql);
						if(rs.next())
						{
							if(password.equals(rs.getString("password")))
							{
								Login.this.dispose();
								new MainFrame();
								
							} else
							{
								errdialog(2);
								return;
							}
						}
					}
					
					rs.close();
					stmt.close();
					conn.close();
				} catch(SQLException se)
				{
					System.err.println("SQLException: " + se.getMessage());
				}
			}
		}
	}
}
