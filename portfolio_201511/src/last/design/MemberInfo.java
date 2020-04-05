package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// ������ ����, ������ ���� ����, ���� ���� ���� �ϴ� Ŭ����
public class MemberInfo extends JDialog
{
	// UI ���� ����
	private JTextField nameField;
	private JPasswordField pwField;			// ��й�ȣ�� getPassword() �޼ҵ�� �����´�.
	private JPasswordField pwReEnterField;
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField idField;
	private JLabel join;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel pwLabel;
	private JLabel phoneLabel;
	private JLabel transLabel;
	private JLabel emailLabel;
	private JLabel pwReEnterLabel;
	private JLabel passwordHint;
	private JLabel phoneHint;
	private JRadioButton man;
	private JRadioButton woman;
	private ButtonGroup gender;
	private JButton actionButton;
	private JButton cancelButton;
	private JButton overlapButton;
	private JButton dropOutButton;

	// ����, ����, ���� ���� ��带 �����ϴ� ����
	private int mode;
	
	// �ؽ�Ʈ �ʵ忡�� ���� ���� ����
	private String nameVal;
	private String pwVal;
	private String pw2Val;
	private String phoneVal;
	private String emailVal;
	private int genderVal = -1;
	
	// �����ͺ��̽� ���� Ŭ����
	private StudentDAO s_dao;
	private StudentDTO s_check;
	private TeacherDAO t_dao;
	private TeacherDTO t_check;
	private Student student;
	private Teacher teacher;
	
	// �̺�Ʈ ���� Ŭ����
	private Handler handler = new Handler();
	
	// â ��带 �����ϴ� ���
	static final int JOIN = 1;				// ������ ����
	static final int MODIFY = 2;			// ������ ����
	static final int T_MODIFY = 3;		// ���� ����
	static final int MODIFY_A = 4;		// �������� ������ ����
	
	// ������
	public MemberInfo(int mode, Object who)
	{
//		JDialog �⺻ ����
		super(Main.mainFrame);
		setSize(520, 570);
		Dialog_Default.init(this, "");
		this.mode = mode;		// �Ķ���ͷ� ���� mode ���� ��� ������ �ִ´�.
		
		// ������ ���̽� Ŭ���� �ʱ�ȭ
		s_dao = StudentDAO.getInstance();
		s_check = new StudentDTO(s_dao);
		t_dao = TeacherDAO.getInstance();
		t_check = new TeacherDTO(t_dao);
		
		// ���� ������ ���� �󺧿� ȸ����ȣ�� �ƴ� �����ȣ�� ǥ�� 
		if (mode == MemberInfo.T_MODIFY)	// ���� ������ ��
		{
			idLabel = new JLabel("�����ȣ");			
		}
		else	// ���� ������ �ƴ� ��
		{
			idLabel = new JLabel("ȸ����ȣ");
		}
		
		// ȸ�� ���Խ� ��Ÿ�� ȸ������ ���̺�
		join = new JLabel("ȸ �� �� ��");
		join.setFont(new Font("���� ���", Font.BOLD, 20));
		join.setForeground(new Color(82, 55, 56));
		join.setHorizontalAlignment(SwingConstants.CENTER);
		join.setBounds(170, 35, 150, 30);
				
//		ID(ȸ����ȣ) ���̺�
		idLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		idLabel.setForeground(new Color(82, 55, 56));
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setBounds(40, 50, 125, 30);

//		�̸� ���̺�
		nameLabel = new JLabel("�� ��");
		nameLabel.setBounds(40, 107, 125, 30);
		nameLabel.setForeground(new Color(82, 55, 56));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(nameLabel);
		
//		��й�ȣ ���̺�
		pwLabel = new JLabel("��й�ȣ");
		pwLabel.setBounds(40, 160, 125, 30);
		pwLabel.setForeground(new Color(82, 55, 56));
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(pwLabel);
		
		//��й�ȣ ���Է� ���̺�
		pwReEnterLabel = new JLabel("��й�ȣ Ȯ��");
		pwReEnterLabel.setBounds(40, 217, 125, 30);
		pwReEnterLabel.setForeground(new Color(82, 55, 56));
		pwReEnterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwReEnterLabel.setFont(new Font("���� ���", Font.BOLD,16));
		add(pwReEnterLabel);
		
//		�޴�����ȣ ���̺�
		phoneLabel = new JLabel("�޴�����ȣ");
		phoneLabel.setBounds(40, 270, 125, 30);
		phoneLabel.setForeground(new Color(82, 55, 56));
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(phoneLabel);
		
//		���� ���̺�
		transLabel = new JLabel("����");
		transLabel.setBounds(40, 326, 125, 30);
		transLabel.setForeground(new Color(82, 55, 56));
		transLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		transLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(transLabel);
		
//		�̸��� ���̺�
		emailLabel = new JLabel("E - Mail");
		emailLabel.setBounds(40, 380, 125, 30);
		emailLabel.setForeground(new Color(82, 55, 56));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(emailLabel);
				
//		ID(ȸ����ȣ) �ʵ�
		idField = new JTextField();
		idField.setBounds(190, 50, 225, 30);
		idField.setForeground(new Color(82, 55, 56));
		idField.setColumns(10);

//		�̸� �ʵ�
		nameField = new JTextField();
		nameField.setBounds(190, 105, 225, 30);
		nameField.setForeground(new Color(82, 55, 56));
		nameField.setColumns(10);
		add(nameField);
		
//		��й�ȣ �ʵ�
		pwField = new JPasswordField();
		pwField.setBounds(190, 160, 225, 30);
		pwField.setForeground(new Color(82, 55, 56));
		pwField.setColumns(10);
		add(pwField);
		
//		��й�ȣ Ȯ�� �ʵ�
		pwReEnterField = new JPasswordField();
		pwReEnterField.setBounds(190, 215, 225, 30);
		pwReEnterField.setForeground(new Color(82, 55, 56));
		pwReEnterField.setColumns(10);
		add(pwReEnterField);

//		�޴���ȭ �ʵ�
		phoneField = new JTextField();
		phoneField.setBounds(190, 270, 140, 30);
		phoneField.setForeground(new Color(82, 55, 56));
		phoneField.setColumns(10);
		add(phoneField);
		
//		�̸��� �ʵ�
		emailField = new JTextField();
		emailField.setBounds(190, 380, 225, 30);
		emailField.setForeground(new Color(82, 55, 56));
		emailField.setColumns(10);
		add(emailField);
		
//		���� ������ư ��
		man = new JRadioButton("��");
		man.setBounds(241, 326, 60, 30);
		man.setForeground(new Color(82, 55, 56));
		man.setFont(new Font("���� ���", Font.BOLD, 16));
		add(man);
	
//		���� ������ư ��		
		woman = new JRadioButton("��");
		woman.setBounds(337, 326, 60, 30);
		woman.setForeground(new Color(82, 55, 56));
		woman.setFont(new Font("���� ���", Font.BOLD, 16));
		add(woman);
		
//		���� ���� �׷��� ������ �� ���� ��ư�� ��´�.		
		gender = new ButtonGroup();
		gender.add(man);
		gender.add(woman);
					
//		�����ϱ� ��ư
		actionButton = new JButton("�����ϱ�");
		actionButton.setBounds(81, 441, 150, 60);
		actionButton.setForeground(new Color(82, 55, 56));
		actionButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(actionButton);
		
//		����ϱ� ��ư
		cancelButton = new JButton("����ϱ�");
		cancelButton.setBounds(265, 441, 150, 60);
		cancelButton.setForeground(new Color(82, 55, 56));
		cancelButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(cancelButton);
		
//		�޴�����ȣ �ߺ�Ȯ�� ��ư
		overlapButton = new JButton("�ߺ�Ȯ��");
		overlapButton.setBounds(338, 269, 80, 30);
		overlapButton.setForeground(new Color(82, 55, 56));
		overlapButton.setFont(new Font("���� ���", Font.BOLD, 12));
		add(overlapButton);

//		��й�ȣ �ȳ� ���̺�
		passwordHint = new JLabel("8 ~ 20�� �̳��� �Է�");
		passwordHint.setBounds(190, 190, 220, 20);
		passwordHint.setForeground(new Color(82, 55, 56));
		passwordHint.setHorizontalAlignment(SwingConstants.CENTER);
		passwordHint.setFont(new Font("���� ���", Font.BOLD, 12));
		add(passwordHint);

		// �ڵ��� �ȳ� ���̺�
		phoneHint = new JLabel("'-'�� ���� ���� ���� �Է�");
		phoneHint.setBounds(175, 300, 250, 20);
		phoneHint.setForeground(new Color(82, 55, 56));
		phoneHint.setHorizontalAlignment(SwingConstants.CENTER);
		phoneHint.setFont(new Font("���� ���", Font.BOLD, 12));
		add(phoneHint);
		
//		â ��尡 ������ ���� ���� �� ���� ���� ������ ��� ��� �ʵ带 ��Ȱ��ȭ ��Ŵ		
		if(mode != JOIN)	// ������ ���� ��尡 �ƴҰ��
		{
			setTitle("�� �� �� ��");
			actionButton.setText("�����ϱ�");
			add(idLabel);
			add(idField);
			
			idField.setEditable(false);
			nameField.setEditable(false);
			man.setEnabled(false);
			woman.setEnabled(false);
			
			// ��尡 �л�����, ������-�л������� ���
			if ((mode == MODIFY || mode == MODIFY_A) && who instanceof Student)
			{
				student = (Student)who;								// Object�� ���� ��ü�� �ٿ�ĳ���� ���ش�.
				s_check = new StudentDTO(s_dao, student);
				setStudent(student);										// �ش� ��ü ������ ���� ������Ʈ�� ���� �����Ѵ�.
			}
			// ��尡 ���� ������ ���
			else if (mode == T_MODIFY && who instanceof Teacher)
			{
				teacher = (Teacher)who;								// Object�� ���� ��ü�� �ٿ�ĳ���� ���ش�.
				t_check = new TeacherDTO(t_dao, teacher);
				setTeacher(teacher);										// �ش� ��ü ������ ���� ������Ʈ ���� �����Ѵ�.
			}
		}
		else	// ������ ���Ը���� ���
		{
			setTitle("ȸ �� �� ��");
			add(join);		// ȸ������ ���̺� �߰�
		}
		
		// �̺�Ʈ�� �߰��� �κ�
		overlapButton.addActionListener(handler);
		actionButton.addActionListener(handler);
		cancelButton.addActionListener(handler);

		setVisible(true);
	}
	
	// ���� ���� �� �����Ϸ��� Teacher ��ü�� ������ �� ������Ʈ�� ���������ִ� �޼ҵ�
	private void setTeacher(Teacher teacher)
	{
		idField.setText("" + teacher.getT_num());					// �����ȣ
		nameField.setText(teacher.getName());					// �̸�
		pwField.setText(teacher.getPassword());					// ��й�ȣ
		pwReEnterField.setText(teacher.getPassword());			// ��й�ȣ Ȯ��
		phoneField.setText(teacher.getPhone());					// �޴���
		emailField.setText(teacher.getEmail());						// �̸���
		
		if (teacher.getGender() == 0)								// ����
		{
			woman.setSelected(true); // 0 = woman
		}
		else
		{
			man.setSelected(true); // 1 = man
		}
	}
	
	// �л� ���� �� �����Ϸ��� Student ��ü�� ������ �� ������Ʈ�� ���������ִ� �޼ҵ�
	private void setStudent(Student student)
	{
		idField.setText("" + student.getSt_num());				// �л���ȣ
		nameField.setText(student.getName());					// �̸�
		pwField.setText(student.getPassword());					// ��й�ȣ
		pwReEnterField.setText(student.getPassword());			// ��й�ȣ Ȯ��
		phoneField.setText(student.getPhone());					// �޴���
		emailField.setText(student.getEmail());						// �̸���
		
		if (student.getGender() == 0)								// ����
		{
			woman.setSelected(true); // 0 = woman
		}
		else
		{
			man.setSelected(true); // 1 = man
		}
		
		if (mode == MODIFY)		// ������ ������ ���
		{
			actionButton.setBounds(40, 441, 120, 60);
			cancelButton.setBounds(200, 441, 120, 60);
			dropOutButton = new JButton("Ż���ϱ�");		// ������ �������� ������ Ż�� ������ ��ư ����
			dropOutButton.setBounds(357, 441, 120, 60);
			dropOutButton.setFont(new Font("���� ���", Font.BOLD, 18));
			add(dropOutButton);
			dropOutButton.addActionListener(handler);
		}
		else if (mode == MODIFY_A)	// ������ - ������ ������ ���
		{
			pwField.setEditable(false);				// ��й�ȣ ĭ�� ��Ȱ��ȭ ��Ų��.
			pwReEnterField.setEditable(false);
		}
	}
	
	// ActionListener�� ������ Handler Ŭ������ �̺�Ʈ ������ ����Ѵ�.
	class Handler implements ActionListener
	{
		boolean checkOverlap = false;		// �ߺ�Ȯ�� ��ư�� ������ Ȯ���ϱ� ���� ����
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == cancelButton)	// ��� ��ư�� ������ ���
			{
				MemberInfo.this.dispose();		// ���� ���̾�α� â�� �ݴ´�.
			}
			
			// ������ ������ �� �̺�Ʈ
			if (mode == MemberInfo.JOIN)
			{				
				// �ߺ�Ȯ�� ��ư�� ������ �� �̺�Ʈ ����
				if (e.getSource() == overlapButton)
				{
					phoneVal = phoneField.getText();				// �Էµ� ��ȭ��ȣ ���� �޾ƿ´�.
					
					if (phoneVal.equals(""))							// ��ȭ��ȣ�� �Է����� �ʾ��� ��
					{
						new Dialog_Default("��ȭ��ȣ �Է�", "��ȭ��ȣ�� �Է����ּ���.");
						phoneField.requestFocus();					// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
					}
									
					else												// ��ȭ��ȣ�� �Է����� ��
					{
						if(s_check.checkPhone(phoneVal))		// ��ȭ��ȣ�� �°� �Է� ���� ��
						{
							if (s_check.overlapPhone(phoneVal) == true)	// �ߺ��˻縦 �� �ߺ����� ���� ��ȣ�� ��
							{
								checkOverlap = true;				// �ߺ�Ȯ�� ���θ� true�� �Ѵ�.
								new Dialog_Default("�ߺ�Ȯ��", "����� �� �ִ� ��ȭ��ȣ�Դϴ�.");
							}								
							else	// �ߺ��� ��ȭ��ȣ�� ��
							{
								new Dialog_Default("�ߺ�Ȯ��", "�̹� ��ϵ� ��ȭ��ȣ �Դϴ�.");	
								phoneField.requestFocus();			// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
							}
						}
						else											// ��ȭ��ȣ�� �°� �Է����� �ʾ��� ��
						{
							new Dialog_Default("��ȭ��ȣ �Է�", "��ȭ��ȣ�� �ٸ��� �Է����ּ���");
							phoneField.requestFocus();				// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
						}					
					}
				}
				// ���Թ�ư�� ������ ��
				else if (e.getSource() == actionButton)
				{
					// ��� TextField�� ���� ������
					nameVal = nameField.getText();
					pwVal = new String(pwField.getPassword());
					pw2Val = new String(pwReEnterField.getPassword());
					emailVal = emailField.getText();
					
					// ������ư�� ���� ���θ� Ȯ���� ���� �����Ѵ�.
					if (man.isSelected())
						genderVal = 1;
					else if (woman.isSelected())
						genderVal = 0;
					
					// ���� Ȯ���� �ִ� �۾�
					if (checkOverlap == false)		// �޴��� �ߺ�Ȯ���� ���� �ʾ��� ��
					{
						new Dialog_Default("�ߺ� Ȯ��", "�޴��� ��ȣ �ߺ�Ȯ���� ���ּ���");
						overlapButton.requestFocus();		// �ߺ�Ȯ�� ��ư�� ��Ŀ���� �����.
					}
					else if (s_check.checkName(nameVal) == false)		// �̸��� �°� �Է����� �ʾ��� ��
					{
						new Dialog_Default("�̸� �Է�", "�̸��� 2�� �̻� 10�� �����Դϴ�.");
						nameField.requestFocus();			// �̸� JTextField�� ��Ŀ���� �����.
					}
					else if (!(pwVal.equals(pw2Val)))							// �н�����, �н����� Ȯ�ζ��� ���� �ٸ� ��
					{
						new Dialog_Default("��й�ȣ �Է�", "��й�ȣ�� �ٸ��� �Է��ϼ̽��ϴ�.");
						pwField.requestFocus();				// �н����� JPasswordField�� ��Ŀ���� �����.
					}
					else if (s_check.checkPassword(pwVal) == false)		// ��й�ȣ�� �°� �Է����� �ʾ��� ��
					{
						new Dialog_Default("��й�ȣ �Է�", "��й�ȣ�� 8~20�ڷ� �Է����ּ���.");
						pwField.requestFocus();				// �н����� JPasswordField�� ��Ŀ���� �����. 
					}
					else if (s_check.checkGender(genderVal) == false)	// ������ �������� �ʾ��� ��
					{
						new Dialog_Default("���� ����", "������ �������ּ���.");
						man.requestFocus();					// ���� ������ư�� ��Ŀ���� �����.
					}
					else if (s_check.checkEmail(emailVal) == false)			// �̸����� �°� �Է����� �ʾ��� ��
					{
						new Dialog_Default("E-mail �Է�", "�̸����� �ٸ��� �Է����ּ���.");
						emailField.requestFocus();			// �̸��� JTextField�� ��Ŀ���� �����.
					}
					else	// ��� ���� �°� �Է����� ��
					{
						student = s_check.getStudent();			// ���� ��� �Էµ� �л� ��ü�� �޾ƿ´�.
						
						if (s_dao.insertStudent(student) == 1)	// �л� ��ü�� �����ͺ��̽��� �Է��� �������� ��
						{
							int st_num = s_dao.findSt_num(student.getName(), student.getPhone());	// ȸ�� ��ȣ�� �޾ƿ�
							new Dialog_Default(student.getName(), st_num);		// Ȯ�� ���̾�α�â
							MemberInfo.this.dispose();		// ���̾�α�â�� ������ ���� ���̾�α�â�� ������ �Ѵ�.
						}
						else	// �����ͺ��̽��� �Էµ��� ������ ��
						{
							new Dialog_Default("��� ����", "ȸ�� ��Ͽ� �����Ͽ����ϴ�.");
						}	
					}
				}
			}
			
			// ������ ���� ������ �� �̺�Ʈ
			else if (mode == MemberInfo.MODIFY || mode == MemberInfo.MODIFY_A)
			{	
				// �ߺ�Ȯ�� ��ư�� ������ �� �̺�Ʈ ����
				if (e.getSource() == overlapButton)
				{
					phoneVal = phoneField.getText();				// �Էµ� ��ȭ��ȣ ���� ������
					
					if (phoneVal.equals(""))							// ��ȭ��ȣ�� �Է����� �ʾ��� ��
					{
						new Dialog_Default("��ȭ��ȣ �Է�", "��ȭ��ȣ�� �Է����ּ���.");
						phoneField.requestFocus();					// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
					}
									
					else
					{
						if(s_check.checkPhone(phoneVal))		// ��ȭ��ȣ�� �°� �Է� ���� ��
						{
							// ��ȭ��ȣ�� ���� �� ��� ��ȣ�̰ų� �ߺ��˻翡�� �ߺ����� �ʾ��� ��
							if (phoneVal.equals(student.getPhone()) || (s_check.overlapPhone(phoneVal) == true))
							{
								checkOverlap = true;		// �ߺ��˻� ���θ� true�� �Ѵ�.
								new Dialog_Default("�ߺ�Ȯ��", "����� �� �ִ� ��ȭ��ȣ�Դϴ�.");
							}
								
							else	// �ߺ��� ��ȭ��ȣ �� ��
							{
								new Dialog_Default("�ߺ�Ȯ��", "�̹� ��ϵ� ��ȭ��ȣ �Դϴ�.");	
								phoneField.requestFocus();			// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
							}
						}
						else		// ��ȭ��ȣ�� �ٸ��� �Է����� �ʾ��� ��
						{
							new Dialog_Default("��ȭ��ȣ �Է�", "��ȭ��ȣ�� �ùٸ��� �Է����ּ���");
							phoneField.requestFocus();				// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
						}					
					}
				}
				// ������ư�� ������ ��
				else if (e.getSource() == actionButton)
				{
					// �Էµ� ���� �����´�.
					pwVal = new String(pwField.getPassword());
					pw2Val = new String(pwReEnterField.getPassword());
					emailVal = emailField.getText();
			
					// ���� Ȯ���� �ִ� �۾�
					if (checkOverlap == false)				// �ߺ�Ȯ���� ���� �ʾ��� ��
					{
						new Dialog_Default("�ߺ� Ȯ��", "�޴��� ��ȣ �ߺ�Ȯ���� ���ּ���");
						overlapButton.requestFocus();		// �ߺ�Ȯ�ο� ��Ŀ���� �����.
					}
					else if (!(pwVal.equals(pw2Val)))		// �н����带 �ٸ��� �Է����� ��
					{
						new Dialog_Default("��й�ȣ �Է�", "��й�ȣ�� �ٸ��� �Է��ϼ̽��ϴ�.");
						pwField.requestFocus();				// �н����� JPasswordField�� ��Ŀ���� �����.
					}
					else if (s_check.checkPassword(pwVal) == false)	// ��й�ȣ�� �ٸ��� �Է����� �ʾ��� ��
					{
						new Dialog_Default("��й�ȣ �Է�", "��й�ȣ�� 8 ~ 20�ڷ� �Է����ּ���.");
						pwField.requestFocus();				// �н����� JPasswordField�� ��Ŀ���� �����.
					}
					else if (s_check.checkEmail(emailVal) == false)		// �̸����� �ٸ��� �Է����� �ʾ��� ��
					{
						new Dialog_Default("E-mail �Է�", "�̸����� �Է����ּ���.");
						emailField.requestFocus();			// �̸��� JTextField�� ��Ŀ���� �����.
					}
					else		// ��� ���� �ٸ��� �Է����� ��
					{
						student = s_check.getStudent();	// ������ ��� �Էµ� Student �ν��Ͻ��� �޾ƿ´�.
						
						if(s_dao.updateStudent(student) == 1)		// ���� ������ �������� ��
						{
							new Dialog_Default("���� ����", "������ �����Ͽ����ϴ�.");
						}
						else	// ���� ������ �������� ��
						{
							new Dialog_Default("���� ����", "������ �����Ͽ����ϴ�.");
						}
						MemberInfo.this.dispose();		// ���̾�α�â�� ���� �� �ش� ���̾�α� â�� ������ �Ѵ�.
					}
				}
				
				// Ż�� ��ư�� ������ ��
				else if (e.getSource() == dropOutButton)
				{
					new Dialog_Select("Ż�� Ȯ��", "������ Ż���Ͻðڽ��ϱ�?", Dialog_Select.MEMBER_DELETE, student.getSt_num());
				}
			}
			
			// ���� ������ ��
			else if (mode == MemberInfo.T_MODIFY)
			{
				// �ߺ�Ȯ�� ��ư�� ������ �� �̺�Ʈ ����
				if (e.getSource() == overlapButton)
				{
					phoneVal = phoneField.getText();				// �Էµ� ��ȭ��ȣ ���� �����´�.
					
					if (phoneVal.equals(""))							// ��ȭ��ȣ�� �Է����� �ʾ��� ��
					{
						new Dialog_Default("��ȭ��ȣ �Է�", "��ȭ��ȣ�� �Է����ּ���.");
						phoneField.requestFocus();					// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
					}
									
					else		// ��� ���� �ٸ��� �Է����� ��
					{
						if(t_check.checkPhone(phoneVal))		// ��ȭ��ȣ�� �°� �Է� ���� ��
						{
							// ��ȭ��ȣ�� ���� �� ��� ��ȣ�̰ų� �ߺ��˻翡�� �ߺ����� �ʾ��� ��
							if (phoneVal.equals(teacher.getPhone()) || (t_check.overlapPhone(phoneVal) == true))
							{
								checkOverlap = true;			// �ߺ��˻� ���θ� true�� �ٲ۴�.
								new Dialog_Default("�ߺ�Ȯ��", "����� �� �ִ� ��ȭ��ȣ�Դϴ�.");
							}
								
							else	// ��ȭ��ȣ�� �ߺ��� ��
							{
								new Dialog_Default("�ߺ�Ȯ��", "�̹� ��ϵ� ��ȭ��ȣ �Դϴ�.");	
								phoneField.requestFocus();		// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
							}
						}
						else		// ��ȭ��ȣ�� �Է����� �ʾ��� ��
						{
							new Dialog_Default("��ȭ��ȣ �Է�", "��ȭ��ȣ�� �ùٸ��� �Է����ּ���");
							phoneField.requestFocus();			// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
						}					
					}
				}
				// ������ư�� ������ ��
				else if (e.getSource() == actionButton)
				{
					// �Էµ� ���� �����´�.
					pwVal = new String(pwField.getPassword());
					pw2Val = new String(pwReEnterField.getPassword());
					emailVal = emailField.getText();
			
					// ���� Ȯ���� �ִ� �۾�
					if (checkOverlap == false)		// �ߺ�Ȯ�� ��ư�� ������ �ʾ��� ��
					{
						new Dialog_Default("�ߺ� Ȯ��", "�޴��� ��ȣ �ߺ�Ȯ���� ���ּ���");
						overlapButton.requestFocus();		// �ߺ�Ȯ�� ��ư�� ��Ŀ���� �����.
					}
					else if (!(pwVal.equals(pw2Val)))		// ��й�ȣ�� �ٸ��� �Է����� ��
					{
						new Dialog_Default("��й�ȣ �Է�", "��й�ȣ�� �ٸ��� �Է��ϼ̽��ϴ�.");
						pwField.requestFocus();				// �н����� JPasswordField�� ��Ŀ���� �����.
					}
					else if (t_check.checkPassword(pwVal) == false)	// ��й�ȣ�� �ٸ��� �Է����� �ʾ��� ��
					{
						new Dialog_Default("��й�ȣ �Է�", "��й�ȣ�� 8 ~ 20�ڷ� �Է����ּ���.");
						pwField.requestFocus();				// �н����� JPasswordField�� ��Ŀ���� �����.
					}
					else if (t_check.checkEmail(emailVal) == false)		// �̸����� �ٸ��� �Է����� �ʾ��� ��
					{
						new Dialog_Default("E-mail �Է�", "�̸����� �Է����ּ���.");
						emailField.requestFocus();			// �̸��� JTextField�� ��Ŀ���� �����.
					}
					else		// ��� ���� �ٸ��� �Է����� ��
					{
						teacher = t_check.getTeacher();		// ���� ��� �Էµ� Teacher �ν��Ͻ��� �޾ƿ´�.
						
						if(t_dao.updateTeacher(teacher) == 1)		// ������ �������� ��
						{
							new Dialog_Default("���� ����", "������ �����Ͽ����ϴ�.");
						}
						else	// ������ �������� ��
						{
							new Dialog_Default("���� ����", "������ �����Ͽ����ϴ�.");
						}
						MemberInfo.this.dispose();		// ���̾�α�â�� ���� �� �ش� ���̾�α� â�� ������ �Ѵ�.
					}
				}
			}
		}
	}
}
