package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// �α��� Ŭ����
public class Login extends JFrame
{	
	// ��� ����
	private JPanel mainPane;
	private JTextField loginBlank;
	private JPasswordField passwordBlank;	// ��й�ȣ�� getPassword() �޼ҵ�� �����´�.
	private JButton loginButton;
	private JButton joinButton;
	private JButton findButton;
	private JButton adminButton;
	private JLabel numLabel;
	private JLabel pwLabel;
	private JLabel mainLabel;
	private ImageIcon logo;
	private JLabel logo_l;
	private ImageIcon img;
	
	// �̺�Ʈ �ٷ�� Ŭ����
	private Handler handler = new Handler();
	
	// JDBC �����ϴ� Ŭ����
	Admin admin;
	Teacher teacher;
	Student student;
	AdminDAO a_dao;
	StudentDAO s_dao;
	TeacherDAO t_dao;
	
	// TextField���� ���� �������� ����
	private String idVal;
	private String pwVal;

	// ������
	public Login()
	{
//		Frame �⺻ ����
		setFont(new Font("���� ���", Font.BOLD, 13));				// JFrame�� ��Ʈ�� �����Ѵ�.
		setResizable(false);												// JFrame�� ũ�⸦ �������� ���ϰ� �Ѵ�.
		setTitle("(��) �� �� �� �� �� ��");								// JFrame�� Ÿ��Ʋ�� �����Ѵ�.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// JFrame�� X ��ư ������ �����Ѵ�.
		setSize(900, 650);												// JFrame�� ũ�⸦ �����Ѵ�.
		setLocationRelativeTo(null);									// JFrame�� ��ġ�� ��ũ�� ����� �д�.
				        
        img = new ImageIcon("image/icon.png");	// �����ܿ� ���� �̹����� �����´�.
        setIconImage(img.getImage());				// ������ �̹����� ���������� �����Ѵ�.
				
        // �г��� ������ �װ��� Frame�� �⺻ ContentPane (������Ʈ�� �÷����� ��)���� �����Ѵ�.
		mainPane = new JPanel();
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		// JDBC ���� ���� Ŭ���� �ʱ�ȭ
		a_dao = AdminDAO.getInstance();
		admin = a_dao.createAdmin();
					
//		�α��� ��ư
		loginButton = new JButton("�� �� ��");
		loginButton.setForeground(new Color(82, 55, 56));
		loginButton.setFont(new Font("���� ���", Font.BOLD, 18));
		loginButton.setBounds(500, 310, 120, 90);
		mainPane.add(loginButton);
		
//		ȸ������ ��ư
	    joinButton = new JButton("ȸ �� �� ��");
	    joinButton.setForeground(new Color(82, 55, 56));
		joinButton.setFont(new Font("���� ���", Font.BOLD, 18));
		joinButton.setBounds(260, 410, 150, 60);
		mainPane.add(joinButton);
		
//		ȸ����ȣ/��й�ȣ ã�� ��ư
	    findButton = new JButton("ȸ����ȣ / ��й�ȣ ã��");
	    findButton.setForeground(new Color(82, 55, 56));
		findButton.setFont(new Font("���� ���", Font.BOLD, 15));
		findButton.setBounds(420, 410, 200, 60);
		mainPane.add(findButton);
		
//		ȸ����ȣ �Է¶�
		loginBlank = new JTextField();
		loginBlank.setForeground(new Color(82, 55, 56));
		loginBlank.setBounds(340, 310, 150, 40);
		loginBlank.setFont(new Font("���� ���", Font.PLAIN, 18));
		mainPane.add(loginBlank);
		loginBlank.setColumns(10);
		
//		��й�ȣ �Է¶�
		passwordBlank = new JPasswordField();
		passwordBlank.setForeground(new Color(82, 55, 56));
		passwordBlank.setColumns(10);
		passwordBlank.setFont(new Font("���� ���", Font.PLAIN, 18));
		passwordBlank.setBounds(340, 360, 150, 40);
		mainPane.add(passwordBlank);
		
//		ȸ����ȣ �ؽ�Ʈ
		numLabel = new JLabel("ȸ����ȣ");
		numLabel.setForeground(new Color(82, 55, 56));
		numLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		numLabel.setBounds(260, 310, 80, 40);
		mainPane.add(numLabel);
		
//		��й�ȣ �ؽ�Ʈ
		pwLabel = new JLabel("��й�ȣ");
		pwLabel.setForeground(new Color(82, 55, 56));
		pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pwLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		pwLabel.setBounds(260, 360, 80, 40);
		mainPane.add(pwLabel);
		
		// �ΰ� �̹���
		logo = new ImageIcon("image/logo.png");		// �ΰ� �̹����� �����´�.
		logo_l = new JLabel(logo);							// ������ �̹����� JLabel�� �ִ´�.
		logo_l.setBounds(350, 40, 200, 200);				// JLabel�� ũ��� ��ġ ����
		add(logo_l);
		
//		�߾� ���� �ؽ�Ʈ
	    mainLabel = new JLabel("�� �� ��   �� �� ��");
	    mainLabel.setForeground(new Color(82, 55, 56));
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setFont(new Font("���� ���", Font.BOLD, 40));
		mainLabel.setBounds(270, 200, 350, 80);
		mainPane.add(mainLabel);
		
		if(admin.getId() == null)		// ������ ��ü�� ��������� = ������ ����� �ȵǾ�������
		{
			adminButton = new JButton("������ ���");
			adminButton.setForeground(new Color(82, 55, 56));
			adminButton.setFont(new Font("���� ���", Font.BOLD, 14));
			adminButton.setBounds(400, 480, 100, 40);
			mainPane.add(adminButton);
			adminButton.addActionListener(handler);
		}
		
		// �̺�Ʈ ������ �κ�
		passwordBlank.addActionListener(handler);
		loginButton.addActionListener(handler);
		joinButton.addActionListener(handler);
		findButton.addActionListener(handler);
					
		setVisible(true);
	}
	
	public void setAdmin(Admin admin)		// ������ ������ Admin ��ü�� �޾ƿ��� �޼ҵ�
	{
		this.admin = admin;		// �Ķ���ͷ� �޾ƿ� Admin �ν��Ͻ��� ��� ������ �ִ´�.
	}
	
	// �̺�Ʈ�� ���õ� Ŭ����
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// �α��� ��ư�� ������ ��
			if (e.getSource() == loginButton || e.getSource() == passwordBlank)
			{
				idVal = loginBlank.getText();								// JTextField���� id ���� �����´�.
				pwVal = new String(passwordBlank.getPassword());	// JPasswordField���� pw ���� �����´�.
				
				if (idVal.equals(""))				// id �Է¶��� �Է� ���� ���� ��
				{
					new Dialog_Default("�α��� ����", "ȸ����ȣ�� �Է����ּ���.");
					loginBlank.requestFocus();		// id JTextField�� ��Ŀ���� �����.
				}
				else if (pwVal.equals(""))		// pw �Է¶��� �Է� ���� ���� ��
				{
					new Dialog_Default("�α��� ����", "��й�ȣ�� �Է����ּ���.");
					passwordBlank.requestFocus();	// pw JPasswordField�� ��Ŀ���� �����.
				}
				else if (idVal.equals(admin.getId()))		// id �Է°��� ������ ���̵��� ���
				{
					// ������ ��й�ȣ�� �Է��� ��й�ȣ�� �´��� Ȯ���� �Ŀ� ������ �г� ����
					if (pwVal.equals(admin.getPassword()))	// id, pw�� ��� ���� ���
					{
						Login.this.dispose();		// �α��� â�� �ݴ´�.
						Main.mainFrame = new OverrallFrame(new ManagerTop(admin));	// �����ڸ�� â ����
					}
					else	// ��й�ȣ�� ���� ���� ���
					{
						new Dialog_Default("�α��� ����", "ȸ����ȣ, ��й�ȣ�� Ȯ���� �ּ���.");
						loginBlank.requestFocus();
					}
				}
				else	// ������ ���̵� �ƴ� ���
				{
					int int_id = 0;	// ȸ�� ��ȣ Ȥ�� ���� ��ȣ�� ���� ����
					
					try		// try-catch�� ���ڿ��� ���ڷ� �ٲٴ� ó���� �Ѵ�.
					{
						int_id = Integer.parseInt(idVal);
					}
					catch (NumberFormatException ne)	// ���ڿ��� �� Exception �߻�
					{
						new Dialog_Default("ȸ����ȣ �Է�", "ȸ����ȣ�� �ٸ��� �Է����ּ���");
						loginBlank.requestFocus();			// id �Է¶��� ��Ŀ���� �����.
						return;
					}
					
					if ((int_id / 100) > 99)			// �л��� ���
					{
						// DB ������ ���õ� ��� ���� �ʱ�ȭ
						s_dao = StudentDAO.getInstance();
						student = s_dao.createStudent(int_id);
						
						if (student.getSt_num() == 0)	// ���� ȸ���� ���̵�� 0���� ��µ� = �ش� ȸ���� ���� ���
						{
							new Dialog_Default("�α��� ����", "ȸ����ȣ�� ��й�ȣ�� Ȯ�����ּ���.");
							loginBlank.requestFocus();	// id �Է¶��� ��Ŀ���� �����.
						}
						else	// �ش� ȸ���� �ִ� ���
						{
							if (pwVal.equals(student.getPassword()))	// �н����尡 �´� �� Ȯ����, ���� ���
							{
								Login.this.dispose();		// �α��� â�� �ݴ´�.
								Main.mainFrame = new OverrallFrame(new Student_Teacher_Top(student));	// �л� ��� ���� â ���
							}
							else	// �н����尡 ���� ���� ���
							{
								new Dialog_Default("�α��� ����", "ȸ����ȣ�� ��й�ȣ�� Ȯ�����ּ���.");
								loginBlank.requestFocus();
							}
						}
					}
					else if ((int_id / 100) < 10)	// ������ ���
					{
						// DB�� ���õ� ���� �ʱ�ȭ
						t_dao = TeacherDAO.getInstance();
						teacher = t_dao.createTeacher(int_id);
						
						if (teacher.getT_num() == 0)	// ���� ȸ���� ���̵�� 0���� ��µ� = �ش� ���簡 ���� ���
						{
							new Dialog_Default("�α��� ����", "ȸ����ȣ�� ��й�ȣ�� Ȯ�����ּ���.");
							loginBlank.requestFocus();		// id �Է¶��� ��Ŀ���� �����.
						}
						else	// �ش� ���簡 ���� ���
						{
							if (pwVal.equals(teacher.getPassword()))		// ��й�ȣ�� ���� ���
							{
								Login.this.dispose();		// �α��� â�� �ݴ´�.
								Main.mainFrame = new OverrallFrame(new Student_Teacher_Top(teacher));	// ���� ����â�� ����.
							}
							else	// ��й�ȣ�� Ʋ�� ���
							{
								new Dialog_Default("�α��� ����", "ȸ����ȣ�� ��й�ȣ�� Ȯ�����ּ���.");
								loginBlank.requestFocus();
							}
						}
					}
				}	
			}
			
			// ȸ�� ���� ��ư�� ������ �� 
			else if (e.getSource() == joinButton)
			{
				new MemberInfo(MemberInfo.JOIN, null);		// ȸ�� ����â�� ����.
			}
			
			// ���̵� ��й�ȣ ã�� ��ư�� ������ ��
			else if (e.getSource() == findButton)
			{
				new FindNumDialog();		// ���̵� ��й�ȣ ã�� â�� ����.
			}
			
			// ������ ��� ��ư�� ������ ��
			else if (e.getSource() == adminButton)
			{
				new AdminRegister();		// ������ ��� â�� ����.
				
				if (admin.getId() != null)	// ������ ����� �Ϸ�� ��
				{
					Login.this.adminButton.setVisible(false);		// ������ ��ư�� �Ⱥ��̰� �Ѵ�.
				}				
			}
		}
	}
}