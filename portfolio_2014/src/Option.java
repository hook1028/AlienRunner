import java.sql.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Option extends JPanel {

	JLabel title;
	JLabel chNickname;
	JLabel chPassword;
	JLabel chGoal;
	JLabel chogihwa;
	JLabel maden;
	
	JButton chNicknamebtn;
	JButton chPasswordbtn;
	JButton chGoalbtn;
	JButton chogihwabtn;
	JButton madenbtn;
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String sql;
	
	Option()
	{
		setLayout(null);
		
		title = new JLabel("<환경 설정>", JLabel.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 23));
		title.setSize(200,45);
		title.setLocation(30,40);
		
		chNicknamebtn = new JButton("닉네임 변경");
		chNicknamebtn.setSize(200,30);
		chNicknamebtn.setLocation(80,130);
		
		chPasswordbtn = new JButton("패스워드 변경");
		chPasswordbtn.setSize(200,30);
		chPasswordbtn.setLocation(80,180);

		chGoalbtn = new JButton("목표 몸무게 변경");
		chGoalbtn.setSize(200,30);
		chGoalbtn.setLocation(80,230);
		
		chogihwabtn = new JButton("프로그램 초기화");
		chogihwabtn.setSize(200,30);
		chogihwabtn.setLocation(80,280);
		
		madenbtn = new JButton("프로그램 정보");
		madenbtn.setSize(200,30);
		madenbtn.setLocation(80,330);
		
		chNickname = new JLabel("닉네임을 변경합니다.", JLabel.LEFT);
		chNickname.setSize(400,25);
		chNickname.setLocation(300, 133);
		
		chPassword = new JLabel("비밀번호를 변경합니다.", JLabel.LEFT);
		chPassword.setSize(400,25);
		chPassword.setLocation(300, 183);
		
		chGoal = new JLabel("목표 몸무게를 변경합니다.", JLabel.LEFT);
		chGoal.setSize(400,25);
		chGoal.setLocation(300,233);
		
		chogihwa = new JLabel("기록을 삭제하고, 프로그램을 초기화 시킵니다.", JLabel.LEFT);
		chogihwa.setSize(400,25);
		chogihwa.setLocation(300,283);
		
		maden = new JLabel("프로그램의 정보를 표시합니다.", JLabel.LEFT);
		maden.setSize(400,25);
		maden.setLocation(300,333);
		
		Handler handler = new Handler();
		chNicknamebtn.addActionListener(handler);
		chPasswordbtn.addActionListener(handler);
		chGoalbtn.addActionListener(handler);
		chogihwabtn.addActionListener(handler);
		madenbtn.addActionListener(handler);
		
		add(title);
		add(chNicknamebtn);
		add(chPasswordbtn);
		add(chGoalbtn);
		add(chogihwabtn);
		add(madenbtn);
		add(chNickname);
		add(chPassword);
		add(chGoal);
		add(chogihwa);
		add(maden);
	}
	
	class Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object gs = e.getSource();
			
			if(gs == chNicknamebtn)
			{
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(new ChangeNickname());
				MainFrame.contentPane.revalidate();
			}
			if(gs == chPasswordbtn)
			{
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(new ChangePassword());
				MainFrame.contentPane.revalidate();
			}
			if(gs == chGoalbtn)
			{
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(new ChangeGoal());
				MainFrame.contentPane.revalidate();
			}
			if(gs == chogihwabtn)
			{
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(new Chogihwa());
				MainFrame.contentPane.revalidate();
			}
			if(gs == madenbtn)
			{
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(new Maden());
				MainFrame.contentPane.revalidate();
			}
		}
	}	
	
	class ChangeNickname extends JPanel
	{
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton cancel;
		
		ChangeNickname()
		{
			setLayout(null);
			
			label = new JLabel("닉네임을 다시 입력해주세요.");
			label.setFont(new Font("Serif", Font.BOLD, 20));
			label.setSize(300, 25);
			label.setLocation(220, 120);
			
			tf = new JTextField(10);
			tf.setSize(120,25);
			tf.setLocation(290, 170);
			
			ok = new JButton("수정");
			ok.setLocation(420,358);
			ok.setSize(100,40);

			cancel = new JButton("취소");
			cancel.setLocation(530,358);
			cancel.setSize(100,40);
			
			Handler handler = new Handler();
			ok.addActionListener(handler);
			cancel.addActionListener(handler);
			
			add(label);
			add(tf);
			add(ok);
			add(cancel);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == ok)
				{
					String nickname = tf.getText();
					int height = 0;
					
					if(nickname.equals(""))
					{
						RecordTab.errDialog("수정 실패", "닉네임을 입력해주세요.", tf);
						return;
					}
					if(nickname.length()>10)
					{
						RecordTab.errDialog("수정 실패", "10자 이내로 입력해주세요.", tf);
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
						sql = "select height from userdata";
						ResultSet rs = stmt.executeQuery(sql);
						
						if(rs.next()) 
						{
							height = rs.getInt("height");
						}
					
						sql = "update userdata set nickname = '"
								+ nickname + "' where height = '" + height + "'";
						int result = stmt.executeUpdate(sql);
					
						if(result>0)
						{
							MainFrame.contentPane.removeAll();
							MainFrame.contentPane.add(new Option());
							MainFrame.contentPane.revalidate();
							DietTab.popDialog("수정 성공", "닉네임을 수정하였습니다.");
						}
					
						rs.close();
						stmt.close();
						conn.close();
					}catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
				if(gs == cancel)
				{
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new Option());
					MainFrame.contentPane.revalidate();
				}
			}
		}
	}
	
	class ChangePassword extends JPanel
	{
		JLabel title;
		JLabel label1;
		JLabel label2;
		JLabel label21;
		JLabel label3;
		JLabel label31;
		
		JPasswordField tf1;
		JPasswordField tf2;
		JPasswordField tf3;
		
		JButton ok;
		JButton cancel;
		
		ChangePassword()
		{
			setLayout(null);
			
			Font font1 = new Font("Serif", Font.BOLD, 17);
			Font font2 = new Font("Serif", Font.PLAIN, 13);
			
			title = new JLabel("＊비밀번호 변경");
			title.setFont(new Font("Serif", Font.BOLD, 20));
			title.setSize(200,50);
			title.setLocation(50,50);
			
			label1 = new JLabel("현재 비밀번호: ", JLabel.RIGHT);
			label1.setFont(font1);
			label1.setSize(150, 25);
			label1.setLocation(70, 150);
			
			tf1 = new JPasswordField(10);
			tf1.setSize(150,25);
			tf1.setLocation(235,152);
			
			label2 = new JLabel("새 비밀번호: ", JLabel.RIGHT);
			label2.setFont(font1);
			label2.setSize(150,25);
			label2.setLocation(70, 210);
			
			tf2 = new JPasswordField(10);
			tf2.setSize(150,25);
			tf2.setLocation(235, 212);
			
			label21 = new JLabel("(새 비밀번호를 입력해주세요.)");
			label21.setFont(font2);
			label21.setSize(200,25);
			label21.setLocation(390, 213);
			
			label3 = new JLabel("새 비밀번호 확인: ", JLabel.RIGHT);
			label3.setFont(font1);
			label3.setSize(150,25);
			label3.setLocation(70,260);
			
			tf3 = new JPasswordField(10);
			tf3.setSize(150,25);
			tf3.setLocation(235, 262);
			
			label31 = new JLabel("(한번 더 입력해주세요.)");
			label31.setFont(font2);
			label31.setSize(200,25);
			label31.setLocation(390, 263);
			
			ok = new JButton("수정");
			ok.setLocation(420,358);
			ok.setSize(100,40);

			cancel = new JButton("취소");
			cancel.setLocation(530,358);
			cancel.setSize(100,40);
			
			Handler handler = new Handler();
			ok.addActionListener(handler);
			cancel.addActionListener(handler);
			
			add(title);
			add(label1);
			add(tf1);
			add(label2);
			add(tf2);
			add(label21);
			add(label3);
			add(tf3);
			add(label31);
			add(ok);
			add(cancel);
		}	
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == ok)
				{					
					char[] tmp1 = tf1.getPassword();
					char[] tmp2 = tf2.getPassword();
					char[] tmp3 = tf3.getPassword();
					
					String oldpw = new String(tmp1);
					String newpw = new String(tmp2);
					String newpwr = new String(tmp3);
					
					if(oldpw.equals(""))
					{
						RecordTab.errDialog("수정 실패", "현재 비밀번호를 입력하세요", tf1);
						return;
					}
					
					if(newpw.equals(""))
					{
						RecordTab.errDialog("수정 실패", "새 비밀번호를 입력하세요.", tf2);
						return;
					}
					
					if(newpwr.equals(""))
					{
						RecordTab.errDialog("수정 실패", "새 비밀번호 확인을 입력하세요.", tf3);
						return;
					}
					
					if(!(newpw.equals(newpwr)))
					{
						RecordTab.errDialog("수정 실패", "새 비밀번호가 맞지 않습니다.", tf3);
						return;
					}
					
					//비밀번호 3~8자 이상 입력하게 만들기
					
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
						sql = "select password from userdata";
						ResultSet rs = stmt.executeQuery(sql);
						
						String sqlpw = "";
						
						if(rs.next()) 
						{
							sqlpw = rs.getString("password");
						}
					
						if(!(sqlpw.equals(oldpw)))
						{
							RecordTab.errDialog("수정 실패", "현재 비밀번호가 틀렸습니다.", tf1);
							return;
						}
						
						//수정 버튼 누를 때 프로그램 멈춤
						
						sql = "update userdata set password = '" + newpw 
								+ "' where password = '" + sqlpw + "'";
						int result = stmt.executeUpdate(sql);
					
						if(result>0)
						{
							MainFrame.contentPane.removeAll();
							MainFrame.contentPane.add(new Option());
							MainFrame.contentPane.revalidate();
							DietTab.popDialog("수정 성공", "비밀번호를 수정하였습니다.");
						}
					
						rs.close();
						stmt.close();
						conn.close();
						
					}catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
				if(gs == cancel)
				{
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new Option());
					MainFrame.contentPane.revalidate();
				}
			}
		}
	}
	
	class ChangeGoal extends JPanel
	{
		JLabel label;
		JTextField tf;
		JButton ok;
		JButton cancel;
		int nowGoal;
		
		ChangeGoal()
		{
			setLayout(null);
			
			label = new JLabel("목표 체중을 다시 입력해주세요.");
			label.setFont(new Font("Serif", Font.BOLD, 20));
			label.setSize(300, 25);
			label.setLocation(220, 120);
			
			tf = new JTextField(10);
			tf.setSize(120,25);
			tf.setLocation(290, 170);
			
			getGoal();
			
			ok = new JButton("수정");
			ok.setLocation(420,358);
			ok.setSize(100,40);

			cancel = new JButton("취소");
			cancel.setLocation(530,358);
			cancel.setSize(100,40);
			
			Handler handler = new Handler();
			ok.addActionListener(handler);
			cancel.addActionListener(handler);
			
			add(label);
			add(tf);
			add(ok);
			add(cancel);
		}
		
		void getGoal()
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
				sql = "select goal from userdata";
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()) 
				{
					nowGoal = rs.getInt("goal");
				}
			
				tf.setText(nowGoal+"");
			
				rs.close();
				stmt.close();
				conn.close();
			}catch(SQLException se)
			{
				System.err.println("SQLException: " + se.getMessage());
			}
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == ok)
				{
					int goal = 0;
					int height = 0;
					
					if(tf.getText().equals(""))
					{
						RecordTab.errDialog("수정 실패", "목표 체중을 입력해주세요.", tf);
						return;
					}
					
					try
					{
						goal = Integer.parseInt(tf.getText());
						
					} catch(NumberFormatException ne)
					{
						RecordTab.errDialog("수정 실패", "숫자만 입력 가능합니다.", tf);
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
						sql = "select height from userdata";
						ResultSet rs = stmt.executeQuery(sql);
						
						if(rs.next()) 
						{
							height = rs.getInt("height");
						}
					
						sql = "update userdata set goal = "
								+ goal + " where height = " + height;
						int result = stmt.executeUpdate(sql);
					
						if(result>0)
						{
							DietTab.popDialog("수정 성공", "목표 체중을 수정하였습니다.");
						}
					
						rs.close();
						stmt.close();
						conn.close();
					}catch(SQLException se)
					{
						System.err.println("SQLException: " + se.getMessage());
					}
				}
				if(gs == cancel)
				{
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new Option());
					MainFrame.contentPane.revalidate();
				}
			}
		}
	}
	
	class Chogihwa extends JPanel
	{
		JLabel title;
		JLabel caution1;
		JLabel caution2;
		JLabel caution3;
		JLabel caution4;
		
		JButton ok;
		JButton cancel;
		
		Chogihwa()
		{
			setLayout(null);
			
			title = new JLabel("※ 초기화 경고문", JLabel.CENTER);
			title.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 20));
			title.setSize(200,25);
			title.setLocation(30,60);
			
			Font font = new Font("Serif", Font.BOLD, 15);
			
			caution1 = new JLabel("초기화를 실행할 시 기록되어 있는 모든 치수, 식단, 운동 기록뿐만 아니라", JLabel.CENTER);
			caution1.setFont(font);
			caution1.setSize(650,20);
			caution1.setLocation(20,130);
			
			caution2 = new JLabel("닉네임, 목표 체중, 키, 비밀번호 등 모든 기록이 초기화됩니다.", JLabel.CENTER);
			caution2.setFont(font);
			caution2.setSize(650,20);
			caution2.setLocation(20,165);
			
			caution3 = new JLabel("또한 한 번 삭제된 기록은 복원되지 않습니다.", JLabel.CENTER);
			caution3.setFont(font);
			caution3.setSize(650,20);
			caution3.setLocation(20,200);
			
			caution4 = new JLabel("정말로 초기화를 실행하시겠습니까?", JLabel.CENTER);
			caution4.setFont(new Font("Serif", Font.BOLD, 18));
			caution4.setSize(650,20);
			caution4.setLocation(20,265);
			
			ok = new JButton("확인");
			ok.setLocation(235,320);
			ok.setSize(100,40);

			cancel = new JButton("취소");
			cancel.setLocation(355,320);
			cancel.setSize(100,40);
			
			Handler handler = new Handler();
			ok.addActionListener(handler);
			cancel.addActionListener(handler);
			
			add(title);
			add(caution1);
			add(caution2);
			add(caution3);
			add(caution4);
			add(ok);
			add(cancel);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == ok)
				{
					JDialog dialog = new JDialog();

					JLabel label = new JLabel("초기화 후 프로그램을 다시 실행해주세요.");
					JButton button = new JButton("확인");
					
					dialog.setTitle("초기화 실행");
					dialog.setModal(false);
					dialog.setBounds(MainFrame.center().width/2 - 150, MainFrame.center().height/2 - 50, 300,100);
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
							startChogihwa();
						}
					});
				}
				
				if(gs == cancel)
				{
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new Option());
					MainFrame.contentPane.revalidate();
				}
			}
			
			void startChogihwa()
			{
				//전체 테이블 삭제
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
					sql = "delete from weight";
					
					int result = stmt.executeUpdate(sql);
					
					if(result<0) 
					{
						DietTab.popDialog("초기화 실패", "체중기록 삭제에 실패했습니다.");
						return;
					}
				
					sql = "delete from bust";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "가슴둘레 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from waist";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "허리둘레 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from thigh";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "허벅지둘레 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from diet";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "식단목록 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from breakfast";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "아침식사 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from lunch";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "점심식사 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from dinner";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "저녁식사 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from snack";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "간식 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from exercise";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "운동목록 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from exerciserecord";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "운동기록 삭제에 실패했습니다.");
						return;
					}
					
					sql = "delete from userdata";
					result = stmt.executeUpdate(sql);
					
					if(result<0)
					{
						DietTab.popDialog("초기화 실패", "유저정보 삭제에 실패했습니다.");
						return;
					}
					
					System.exit(0);
				
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
	
	class Maden extends JPanel
	{
		
		JLabel version;
		JLabel maker;
		JLabel email;
		
		JButton toOption;
		JButton toMain;
		
		Maden()
		{
			setLayout(null);
			
			Font font = new Font("Serif", Font.BOLD, 20);
			
			version = new JLabel("버전: v1.0");
			version.setFont(font);
			version.setSize(400,20);
			version.setLocation(130,100);
			
			maker = new JLabel("만든 이: 최 소라");
			maker.setFont(font);
			maker.setSize(400,20);
			maker.setLocation(130,150);
			
			email = new JLabel("의견 및 문의: enfng@hanmail.net");
			email.setFont(font);
			email.setSize(400,25);
			email.setLocation(130,200);
			
			toOption = new JButton("환경 설정으로");
			toOption.setLocation(350,340);
			toOption.setSize(130,40);

			toMain = new JButton("메인으로");
			toMain.setLocation(500,340);
			toMain.setSize(130,40);
			
			Handler handler = new Handler();
			toOption.addActionListener(handler);
			toMain.addActionListener(handler);
			
			add(version);
			add(maker);
			add(email);
			add(toOption);
			add(toMain);
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Object gs = e.getSource();
				
				if(gs == toOption)
				{
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new Option());
					MainFrame.contentPane.revalidate();
				}
				
				if(gs == toMain)
				{
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new Main());
					MainFrame.contentPane.revalidate();
				}
			}
		}
	}
}
