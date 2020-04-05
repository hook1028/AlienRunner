package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;
import java.util.*;

public class TeacherRegister extends JDialog
{
	// UI�� ���̴� ������Ʈ
	private JTextField nameField;
	private JPasswordField pwField;			// ��й�ȣ�� getPassword() �޼ҵ�� �����´�.
	private JPasswordField pwReEnterField;
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField idField;
	private JLabel registerLabel;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel pwLabel;
	private JLabel pwReEnter;
	private JLabel phoneLabel;
	private JLabel languageLabel;
	private JLabel transLabel;
	private JLabel emailLabel;
	private JLabel passwordHint;
	private JLabel phoneHint;
	private JRadioButton man;
	private JRadioButton woman;
	private ButtonGroup gender;
	private JRadioButton english;
	private JRadioButton chinese;
	private JRadioButton japanese;
	private ButtonGroup language;
	private JButton editButton;
	private JButton cancelButton;
	private JButton overlapButton;
	
	// �̺�Ʈ�� ����ϴ� Ŭ����
	private Handler handler = new Handler();
	
	// JDBC ���� ���� Ŭ����
	private TeacherDAO t_dao;
	private TeacherDTO t_check;
	private Teacher teacher;
	
	// ���� �޾ƿ� ����
	private int genderVal;
	private String nameVal;
	private String pwVal;
	private String pw2Val;
	private String phoneVal;
	private String emailVal;
	private String langVal;
	
	// ������
	public TeacherRegister() 
	{		
		// TeacherRegister Dialog �⺻ ����
		super(Main.mainFrame);
		setSize(520, 640);
		Dialog_Default.init(this, "�� �� �� ��");
		setLayout(null);
		
		// JDBC ������ ���õ� Ŭ���� �ʱ�ȭ
		t_dao = TeacherDAO.getInstance();
	    t_check = new TeacherDTO(t_dao);
	    teacher = new Teacher();

		// ������ ���̺�
		registerLabel = new JLabel("�� �� �� ��");
		registerLabel.setBounds(190, 30, 150, 50);
		registerLabel.setForeground(new Color(82, 55, 56));
		registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerLabel.setFont(new Font("���� ���", Font.BOLD, 20));
		add(registerLabel);
		
		// �̸� ���̺�
		nameLabel = new JLabel("�� ��");
		nameLabel.setBounds(15, 105, 130, 30);
		nameLabel.setForeground(new Color(82, 55, 56));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(nameLabel);

		// ��й�ȣ ���̺�
		pwLabel = new JLabel("��й�ȣ");
		pwLabel.setBounds(15, 160, 130, 30);
		pwLabel.setForeground(new Color(82, 55, 56));
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(pwLabel);
	
		// ��й�ȣ Ȯ�� ���̺�
		pwReEnter = new JLabel("��й�ȣ Ȯ��");
		pwReEnter.setBounds(15, 217, 130, 30);
		pwReEnter.setForeground(new Color(82, 55, 56));
		pwReEnter.setHorizontalAlignment(SwingConstants.RIGHT);
		pwReEnter.setFont(new Font("���� ���", Font.BOLD, 16));
		add(pwReEnter);

//		// �޴�����ȣ ���̺�
		phoneLabel = new JLabel("�޴�����ȣ");
		phoneLabel.setBounds(15, 270, 130, 30);
		phoneLabel.setForeground(new Color(82, 55, 56));
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(phoneLabel);
		
		// ��� ���̺�
		languageLabel = new JLabel("�� ��");
		languageLabel.setBounds(15, 333, 130, 30);
		languageLabel.setForeground(new Color(82, 55, 56));
		languageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(languageLabel);
		languageLabel.setFont(new Font("���� ���", Font.BOLD, 16));

		// ���� ���̺�
		transLabel = new JLabel("�� ��");
		transLabel.setBounds(15, 390, 130, 30);
		transLabel.setForeground(new Color(82, 55, 56));
		transLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		transLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		add(transLabel);

		// �̸��� ���̺�
		emailLabel = new JLabel("E - Mail");
		emailLabel.setBounds(15, 435, 130, 30);
		emailLabel.setForeground(new Color(82, 55, 56));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("���� ���", Font.PLAIN, 16));
		add(emailLabel);

		// �̸� �ʵ�
		nameField = new JTextField();
		nameField.setBounds(190, 105, 225, 30);
		nameField.setForeground(new Color(82, 55, 56));
		add(nameField);
		nameField.setColumns(10);

		// ��й�ȣ �ʵ�
		pwField = new JPasswordField();
		pwField.setBounds(190, 160, 225, 30);
		pwField.setForeground(new Color(82, 55, 56));
		add(pwField);
		pwField.setColumns(10);
		
		// ��й�ȣ ���� �ʵ�
		pwReEnterField = new JPasswordField();
		pwReEnterField.setBounds(190, 215, 225, 30);
		pwReEnterField.setForeground(new Color(82, 55, 56));
		add(pwReEnterField);
		pwReEnterField.setColumns(10);
		
		// �޴�����ȣ �ʵ�
		phoneField = new JTextField();
		phoneField.setBounds(190, 270, 140, 30);
		phoneField.setForeground(new Color(82, 55, 56));
		add(phoneField);
		phoneField.setColumns(10);

		// �̸��� �ʵ�
		emailField = new JTextField();
		emailField.setBounds(190, 435, 225, 30);
		emailField.setForeground(new Color(82, 55, 56));
		add(emailField);
		emailField.setColumns(10);
	
		// ��� ���� ��ư<����>
		english = new JRadioButton("����");
		english.setBounds(166, 335, 75, 20);
		english.setForeground(new Color(82, 55, 56));
		english.setFont(new Font("���� ���", Font.BOLD, 16));
		add(english);

		// ��� ���� ��ư<�߱���>
		chinese = new JRadioButton("�߱���");
		chinese.setBounds(265, 335, 75, 19);
		chinese.setForeground(new Color(82, 55, 56));
		chinese.setFont(new Font("���� ���", Font.BOLD, 16));
		add(chinese);

		// ��� ���� ��ư<�Ϻ���>
		japanese = new JRadioButton("�Ϻ���");
		japanese.setBounds(375, 335, 75, 19);
		japanese.setForeground(new Color(82, 55, 56));
		japanese.setFont(new Font("���� ���", Font.BOLD, 16));
		add(japanese);
	
		// ��� ���� �׷��� ������ �� ���� ��ư�� ��´�.
		language = new ButtonGroup();
		language.add(english);
		language.add(chinese);
		language.add(japanese);

		// ���� üũ�ڽ� ��
		man = new JRadioButton("��");
		man.setBounds(240, 380, 60, 30);
		man.setForeground(new Color(82, 55, 56));
		man.setFont(new Font("���� ���", Font.BOLD, 16));
		add(man);

		// ���� üũ�ڽ� ��
		woman = new JRadioButton("��");
		woman.setBounds(340, 380, 60, 30);
		woman.setForeground(new Color(82, 55, 56));
		woman.setFont(new Font("���� ���", Font.BOLD, 16));
		add(woman);

		// ���� ���� �׷��� ������ �� ���� ��ư�� ��´�.		
		gender = new ButtonGroup();
		gender.add(man);
		gender.add(woman);

		// ����ϱ� ��ư
		editButton = new JButton("����ϱ�");
		editButton.setBounds(82, 518, 150, 60);
		editButton.setForeground(new Color(82, 55, 56));
		editButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(editButton);

		// ����ϱ� ��ư
		cancelButton = new JButton("����ϱ�");
		cancelButton.setBounds(265, 518, 150, 60);
		cancelButton.setForeground(new Color(82, 55, 56));
		cancelButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(cancelButton);

		// �޴�����ȣ �ߺ�Ȯ�� ��ư
		overlapButton = new JButton("�ߺ�Ȯ��");
		overlapButton.setBounds(335, 268, 80, 30);
		overlapButton.setForeground(new Color(82, 55, 56));
		overlapButton.setFont(new Font("���� ���", Font.BOLD, 12));
		add(overlapButton);

		// Ư������ �����ϰ� �Է� ���̺�
		passwordHint = new JLabel("8 ~ 20�� �̳��� �Է�");
		passwordHint.setBounds(190, 190, 220, 20);
		passwordHint.setForeground(new Color(82, 55, 56));
		passwordHint.setHorizontalAlignment(SwingConstants.CENTER);
		passwordHint.setFont(new Font("���� ���", Font.BOLD, 12));
		add(passwordHint);
		
		// �ڵ��� ��ȣ �Է� ��Ʈ ���̺�
		phoneHint = new JLabel("'-'�� ���� ���� ���� �Է�");
		phoneHint.setBounds(175, 300, 250, 20);
		phoneHint.setForeground(new Color(82, 55, 56));
		phoneHint.setHorizontalAlignment(SwingConstants.CENTER);
		phoneHint.setFont(new Font("���� ���", Font.BOLD, 12));
		add(phoneHint);

		// �̺�Ʈ�� �����ϴ� �κ�
		overlapButton.addActionListener(handler);
		editButton.addActionListener(handler);
		cancelButton.addActionListener(handler);
		
		setVisible(true);
	}
	
	class Handler implements ActionListener 
	{
		boolean checkOverlap = false;		// �޴��� �ߺ�Ȯ�� ��ư�� ������ Ȯ���� ����

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == cancelButton)		// ��ҹ�ư�� ������ �� 
			{	
				TeacherRegister.this.dispose();		// â�� �ݰ� �Ѵ�.
			}

			// �ߺ���ư
			if (e.getSource() == overlapButton)		// �ߺ���ư�� ������ ��
			{
				phoneVal = phoneField.getText();		// �Է¹��� ���� �����´�.

				if (phoneVal.equals("")) // ��ȭ��ȣ�� �Է����� �ʾ��� ��
				{
					new Dialog_Default("��ȭ��ȣ �Է�", "��ȭ��ȣ�� �Է����ּ���.");
					phoneField.requestFocus();		// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
				}

				else
				{
					if (t_check.checkPhone(phoneVal)) // ��ȭ��ȣ�� �°� �Է� ���� ��
					{
						if (t_check.overlapPhone(phoneVal) == true) // ��ȭ��ȣ�� �ߺ����� �ʾ��� ��
						{
							checkOverlap = true;		// �ߺ�Ȯ�� ���θ� true�� �ٲ۴�.
							new Dialog_Default("�ߺ�Ȯ��", "����� �� �ִ� ��ȭ��ȣ�Դϴ�.");
						}

						else	// ��ȭ��ȣ�� �ߺ��Ǿ��� �� 
						{
							new Dialog_Default("�ߺ�Ȯ��", "�̹� ��ϵ� ��ȭ��ȣ �Դϴ�.");
							phoneField.requestFocus();		// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
						}
					} 
					else // ��ȭ��ȣ�� ���Ŀ� �°� �Է����� �ʾ��� ��
					{
						new Dialog_Default("��ȭ��ȣ �Է�", "��ȭ��ȣ�� �ùٸ��� �Է����ּ���");
						phoneField.requestFocus();		// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
					}
				}
			}

			// ����ϱ� ��ư�� ������ ��
			else if (e.getSource() == editButton)
			{
				// �Է� ���� �޾ƿ��� �κ�
				nameVal = nameField.getText();
				pwVal = new String(pwField.getPassword());
				pw2Val = new String(pwReEnterField.getPassword());
				emailVal = emailField.getText();

				if (english.isSelected())			// ��� ���õ��� ��
				{
					langVal = "����";
				}
				else if (chinese.isSelected())	// �߱�� ���õ��� ��
				{
					langVal = "�߱���";
				}
				else if (japanese.isSelected())	// �Ϻ�� ���õ��� ��
				{
					langVal = "�Ϻ���";
				}

				if (man.isSelected())				// ���ڰ� ���õ��� ��
					genderVal = 1;
				else if (woman.isSelected())	// ���ڰ� ���õ��� ��
					genderVal = 0;

				// ���� ����� ���� �� üũ�ϴ� �κ�
				if (checkOverlap == false) 							// �ߺ�Ȯ�� ��ư�� ������ �ʾ��� ��
				{
					new Dialog_Default("�ߺ� Ȯ��", "�޴��� ��ȣ �ߺ�Ȯ���� ���ּ���");
					overlapButton.requestFocus();		// ��ȭ��ȣ JTextField�� ��Ŀ���� �����.
				}
				else if (t_check.checkName(nameVal) == false) 	// �̸��� 2~10�ڷ� �Է����� �ʾ��� ��
				{
					new Dialog_Default("�̸� �Է�", "�̸��� 2�� �̻� 10�� �����Դϴ�.");
					nameField.requestFocus();			// �̸� JTextField�� ��Ŀ���� �����.
				}
				else if (!(pwVal.equals(pw2Val))) 						// ��й�ȣ�� �ٸ��� �Է����� ��
				{
					new Dialog_Default("��й�ȣ �Է�", "��й�ȣ�� �ٸ��� �Է��ϼ̽��ϴ�.");
					pwField.requestFocus();				// ��й�ȣ JPasswordField�� ��Ŀ���� �����.
				} 
				else if (t_check.checkPassword(pwVal) == false)	// ��й�ȣ�� 8~20�ڷ� �Է����� �ʾ��� ��
				{
					new Dialog_Default("��й�ȣ �Է�", "��й�ȣ�� 8 ~ 20�ڷ� �Է����ּ���.");
					pwField.requestFocus();				// ��й�ȣ JPasswordField�� ��Ŀ���� �����.
				} 
				else if (t_check.checkT_num(langVal) == false)	// �� �������� �ʾ��� ��
				{
					new Dialog_Default("��� ����", "��� �� �������ּ���.");
					english.requestFocus();				// ��� JRadioButton�� ��Ŀ���� �����.
				} 
				else if (t_check.checkGender(genderVal) == false) 	// ������ �������� �ʾ��� ��
				{
					new Dialog_Default("���� ����", "������ �������ּ���.");
					man.requestFocus();					// ���� JRadioButton�� ��Ŀ���� �����.
				} 
				else if (t_check.checkEmail(emailVal) == false)			// �̸����� �Է����� �ʾ��� ��
				{
					new Dialog_Default("E-mail �Է�", "�̸����� �Է����ּ���.");
					emailField.requestFocus();			// �̸��� JTextField�� ��Ŀ���� �����.
				} 
				else 	// ��� �°� �Է����� ��
				{
					teacher = t_check.getTeacher(); 		// ���� ��� �Է��� ���� ��ü�� ��������
					
					if (t_dao.insertTeacher(teacher) == 1)	// �����ͺ��̽��� ���縦 ����ϴµ� �������� ��
					{
						new Dialog_Default("��� �Ϸ�", "����� �Ϸ�Ǿ����ϴ�.");
						TeacherRegister.this.dispose();		// ����� �� ���̾�α� â�� �ݴ´�.
					}
					else
					{
						new Dialog_Default("��� ����", "����� �����߽��ϴ�.");
					}			
				}
			}
		}
	}
}
