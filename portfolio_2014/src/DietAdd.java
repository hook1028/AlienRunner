import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;


public class DietAdd extends JPanel
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
		ok.setLocation(500,380);
		ok.setSize(70,40);

		cancel = new JButton("취소");
		cancel.setLocation(590,380);
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
						System.out.println("등록 성공");
						JDialog dialog = new JDialog();

						JLabel label = new JLabel("식단이 등록되었습니다.");
						JButton button = new JButton("확인");
					
						dialog.setModal(false);
						dialog.setTitle("등록 성공");
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
				
					stmt.close();
					conn.close();
				}catch(SQLException se)
				{
					System.err.println("SQLException: " + se.getMessage());
				}
			}
		}
	}

	public static void main(String[] args) 
	{
		JFrame f = new JFrame();
		f.add(new DietAdd());
		f.setBounds(700,500, 700,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
