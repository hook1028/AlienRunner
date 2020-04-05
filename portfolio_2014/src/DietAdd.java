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
		
		add = new JLabel("�Ĵ� ����ϱ�");
		add.setSize(200,32);
		add.setFont(new Font("Serif", Font.BOLD, 25));
		add.setLocation(40,40);
		
		name = new JLabel("�Ĵܸ� : ");
		name.setSize(200,20);
		name.setFont(new Font("Serif", Font.PLAIN, 20));
		name.setLocation(100, 120);
		
		tf = new JTextField(15);
		tf.setSize(200,30);
		tf.setLocation(180, 118);
		
		notice = new JLabel("(30�� �̳��� �Է����ּ���.)");
		notice.setSize(200,20);
		notice.setFont(new Font("Serif", Font.PLAIN, 12));
		notice.setLocation(385, 128);
		
		
		mainly = new JLabel("�ֿ� �����");
		mainly.setSize(200,20);
		mainly.setFont(new Font("Serif", Font.BOLD, 22));
		mainly.setLocation(60, 200);
		
		ok = new JButton("Ȯ��");
		ok.setLocation(500,380);
		ok.setSize(70,40);

		cancel = new JButton("���");
		cancel.setLocation(590,380);
		cancel.setSize(70,40);
		
		group = new ButtonGroup();
		choose = new JRadioButton[4];
		choose[0] = new JRadioButton("ź��ȭ��");
		choose[1] = new JRadioButton("�ܹ���");
		choose[2] =	new JRadioButton("����");
		choose[3] = new JRadioButton("��");
		
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
						System.out.println("��� ����");
						JDialog dialog = new JDialog();

						JLabel label = new JLabel("�Ĵ��� ��ϵǾ����ϴ�.");
						JButton button = new JButton("Ȯ��");
					
						dialog.setModal(false);
						dialog.setTitle("��� ����");
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
