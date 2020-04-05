package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// ���� â �� ������ûâ
public class Dialog_Select extends JDialog
{	
	// UI ���� ����
	private JLabel label1;
	private JLabel label2;
	private JButton btn1;
	private JButton btn2;
	
	// �̺�Ʈ �ڵ鷯
	private Handler handler = new Handler();
	
	// JDBC ������ �ʿ��� Ŭ����
	RegisterDAO r_dao;
	LectureDAO l_dao;
	TeacherDAO t_dao;
	StudentDAO s_dao;
	
	// JDBC ������ ����� ���
	int mode;
	int l_num, st_num, num;
	final static int MEMBER_DELETE = 1;
	final static int LECTURE_DELETE = 2;
	final static int TEACHER_DELETE = 3;
	final static int LECTURE_ADD = 4;
	final static int REGISTER_DELETE = 5;
	
	// ������ �� ��, ��ư�� �� ���� dialog
	public Dialog_Select(String title, String l_str1, int mode, int num)
	{
		// Dialog â ����
		super(Main.mainFrame);			// Dialog�� Owner�� OverrallFrame���� �����Ѵ�.
		setSize(300, 150);					// Dialog�� ũ�⸦ �����Ѵ�.
		Dialog_Default.init(this, title);		// Dialog�� �⺻ ������ �������ִ� �޼ҵ�
		this.mode = mode;					// �Ķ���ͷ� ���� mode�� mode�� �������ش�.
		this.num = num;					// �Ķ���ͷ� ���� num�� num�� �������ش�.
				
//		��1
		label1 = new JLabel(l_str1);
		label1.setFont(new Font("���� ���", Font.BOLD, 14));
		label1.setForeground(new Color(82, 55, 56));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(25, 35, 250, 20);

//		���� ��ư
		btn1 = new JButton("Ȯ��");
		btn1.setFont(new Font("���� ���", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(40, 76, 100, 30);
			
//		������ ��ư
		btn2 = new JButton("���");
		btn2.setFont(new Font("���� ���", Font.BOLD, 14));
		btn2.setForeground(new Color(82, 55, 56));
		btn2.setBounds(155, 76, 100, 30);
					
		add(btn1);
		add(btn2);
		add(label1);
	
//		��ư �̺�Ʈ ����
		btn1.addActionListener(handler);
		btn2.addActionListener(handler);
		
		setVisible(true);
	}
	
	// ������û Ȯ��â
	Dialog_Select(String name, int l_num, int st_num)
	{
		super(Main.mainFrame);
		setSize(300, 150);
		Dialog_Default.init(this, "������û Ȯ��");
		mode = this.LECTURE_ADD;					// ��带 ������û ���� �Ѵ�.
		this.l_num = l_num;							// ���� ��ȣ�� �޾� ��� ������ �ִ´�.
		this.st_num = st_num;							// �л� ��ȣ�� �޾� ��� ������ �ִ´�.
		
		// JDBC Ŭ���� �ʱ�ȭ
		r_dao = RegisterDAO.getInstance();

		// �󺧿� ���� ���� ����
		String l_str1 = "[" + l_num + "] " + name;
		String l_str2 = "�� ���Ǹ� ���� ��û �Ͻðڽ��ϱ�?";

		// ��1
		label1 = new JLabel(l_str1);
		label1.setFont(new Font("���� ���", Font.BOLD, 14));
		label1.setForeground(new Color(82, 55, 56));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(25, 25, 250, 20);

		// ��2
		label2 = new JLabel(l_str2);
		label2.setFont(new Font("���� ���", Font.BOLD, 14));
		label2.setForeground(new Color(82, 55, 56));
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setBounds(25, 45, 250, 20);

		// ���� ��ư
		btn1 = new JButton("Ȯ��");
		btn1.setFont(new Font("���� ���", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(40, 76, 100, 30);

		// ������ ��ư
		btn2 = new JButton("���");
		btn2.setFont(new Font("���� ���", Font.BOLD, 14));
		btn2.setForeground(new Color(82, 55, 56));
		btn2.setBounds(155, 76, 100, 30);

		add(label1);
		add(label2);
		add(btn1);
		add(btn2);
		
//		��ư �̺�Ʈ ����
		btn1.addActionListener(handler);
		btn2.addActionListener(handler);
		
		setVisible(true);
	}
	
	// �̺�Ʈ�� ����� Ŭ����	
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// ȸ�� ������ �� �̺�Ʈ
			if (mode == Dialog_Select.MEMBER_DELETE)
			{
				if (e.getSource() == btn1)		// ���� ��ư�� ���
				{
					s_dao = StudentDAO.getInstance();
					if (s_dao.deleteStudent(num) == 1)		// �л� ��ȣ�� �ش� �л��� �����ϴ� �� �������� ��
					{
						new Dialog_Default("Ż�� �Ϸ�", "Ż�� �Ϸ�Ǿ����ϴ�.");
					}
					else
					{
						new Dialog_Default("Ż�� ����", "Ż�� �����Ͽ����ϴ�.");
					}
										
					if(Dialog_Select.this.getTitle().equals("Ż�� Ȯ��"))		// ȸ�� ������ �л� ��忡�� �̷������ ���
					{
						Main.mainFrame.dispose();				// �л� ���� â�� �ݴ´�.
						Main.loginFrame = new Login();		// �α��� â�� ���Ӱ� ����.
					}
					
					Dialog_Select.this.dispose();		// Ż�� ���� ���� ���̾�αװ� ���� ��, ���� ���̾�α׵� �ݴ´�.
				}
				else if (e.getSource() == btn2)	// ������ ��ư�� ���
				{
					Dialog_Select.this.dispose();	// ���̾�α� â�� �ݴ´�.
				}
			}
			
			// ���� ������ �� �̺�Ʈ
			else if (mode == Dialog_Select.LECTURE_DELETE)
			{
				if (e.getSource() == btn1)		// ���� ��ư�� ���
				{
					l_dao = LectureDAO.getInstance();
					if (l_dao.deleteLecture(num) == 1)	// ���� ��ȣ�� �ش� ���Ǹ� �����ϴ� �� �������� ��
					{
						new Dialog_Default("���� ����", "���ǰ� �����Ǿ����ϴ�.");	
					}
					else
					{
						new Dialog_Default("���� ����", "���� ������ �����Ͽ����ϴ�.");
					}
					
					Dialog_Select.this.dispose();		// ���� ���� ���� ���̾�αװ� ���� ��, ���� ���̾�α׵� �ݴ´�.
				}
				else if (e.getSource() == btn2)	// ������ ��ư�� ���
				{
					Dialog_Select.this.dispose();	// ���̾�α� â�� �ݴ´�.
				}
			}
			
			// ���� ������ �� �̺�Ʈ
			else if (mode == Dialog_Select.TEACHER_DELETE)
			{
				if (e.getSource() == btn1)		// ���� ��ư�� ��
				{
					t_dao = TeacherDAO.getInstance();
					if (t_dao.deleteTeacher(num) == 1)		// ���� ��ȣ�� �ش� ���縦 �����ϴ� �� �������� ��
					{
						new Dialog_Default("���� ����", "���簡 �����Ǿ����ϴ�.");	
					}
					else
					{
						new Dialog_Default("���� ����", "���� ������ �����Ͽ����ϴ�.");
					}
					
					Dialog_Select.this.dispose();		// ���� ���� ���� ���̾�αװ� ���� ��, ���� ���̾�α׵� �ݴ´�.
				}
				else if (e.getSource() == btn2)	// ������ ��ư�� ��
				{
					Dialog_Select.this.dispose();	// ���̾�α� â�� �ݴ´�.
				}
			}
								
			// ������û�� �� �̺�Ʈ
			else if (mode == Dialog_Select.LECTURE_ADD)
			{
				if (e.getSource() == btn1)			// ���� ��ư�� ��
				{
					if(r_dao.searchRegister(l_num, st_num))		// ������ ���ǿ� �ش� �л��� ��ϵ��� �ʾ��� ���
					{
						if( r_dao.insertRegister(l_num, st_num) == 1)	// DB�� ��� ������ �ִµ� �������� ��
						{
							new Dialog_Default("��û����", "������û�� �Ϸ�Ǿ����ϴ�.");
							Dialog_Select.this.dispose();	// ������û ���� ���̾�α� â�� ���� ��, ���� ���̾�α׸� �ݴ´�.
						}	
					}
					else	// ������ ���ǿ� �ش� �л��� �̹� ��ϵ� ���
					{
						new Dialog_Default("���� ��û ����", "�̹� ���� ��û�� �����Դϴ�.");
						Dialog_Select.this.dispose();
					}				
				}
				else if (e.getSource() == btn2)		// ������ ��ư�� ��
				{
					Dialog_Select.this.dispose();		// �ش� ���̾�α� â�� �ݴ´�.
				}
			}
			
			// ���� ��û ����� �� ���
			else if (mode > 999) // ��忡 ���ǹ�ȣ�� ������� ��� = ������û ���
			{
				r_dao = RegisterDAO.getInstance();		// ��� ���̺� ������ �������� r_dao �ʱ�ȭ
				
				if (e.getSource() == btn1)		// ���� ��ư�� ��
				{
					if (r_dao.deleteRegister(mode, num) == 1)		// ��� ���̺��� �ش� ������ �����ϴ� �� �����ϸ�
					{
						new Dialog_Default("������û ��� �Ϸ�", "������û�� ��ҵǾ����ϴ�.");
						Dialog_Select.this.dispose();	// ������� ���� ���̾�α� â�� ���� ��, ���� ���̾�α׵� ����.
					}
					else	// ������ ������ ���
					{
						new Dialog_Default("����", "������û ��ҿ� �����߽��ϴ�.");
						Dialog_Select.this.dispose();	// ������� ���� ���̾�α� â�� ���� ��, ���� ���̾�α׵� ����
					}
				}
				
				else if (e.getSource() == btn2)	// ������ ��ư�� ��
				{
					Dialog_Select.this.dispose();	// ���� ���̾�α� â�� �ݴ´�.
				}
			}
		}	
	}
}
