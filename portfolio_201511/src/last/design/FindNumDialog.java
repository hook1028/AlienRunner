package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// ������ ȸ�� ��ȣ �� ��й�ȣ ã�� ���̾�α� â
public class FindNumDialog extends JDialog
{	
	// UI�� ����� ����
	private JTextField id_nameBlank;
	private JTextField id_phoneBlank;
	private JTextField pw_numberBlack;
	private JTextField pw_phoneBlack;
	private JLabel id_nameLabel;
	private JLabel id_phoneLabel;
	private JLabel pw_numberLabel;
	private JLabel pw_phoneLabel;
	private JButton findNumberButton;
	private JButton findPWButton;
	private JSeparator separator;
	private JLabel find_num;
	private JLabel find_pw;
	private JLabel phoneInfoLabel1;
	private JLabel phoneInfoLabel2;

//	�̺�Ʈ �ڵ鷯 Ŭ����
	private Handler handler = new Handler();
	
//	TextField���� ������ ���� ������ ����
	private String id_nameVal;
	private String id_phoneVal;
	private String pw_numVal;
	private String pw_phoneVal;

	// DB ������ ���õ� ����
	private StudentDAO s_dao;
	private Student student;
	
	// ������
	public FindNumDialog()
	{
//		�⺻ ������ �������ش�.
		super(Main.loginFrame);										// Dialogâ�� Owner�� Login���� �����Ѵ�.
		setSize(400, 320);												// Dialogâ�� ũ�⸦ �����Ѵ�.
		Dialog_Default.init(this, "ȸ����ȣ / ��й�ȣ ã��");		// Dialogâ�� �⺻ ������ �������ش�.
		
//		ȸ����ȣã�� �̸� �ؽ�Ʈ
		id_nameLabel = new JLabel("�̸�");
		id_nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		id_nameLabel.setForeground(new Color(82, 55, 56));
		id_nameLabel.setFont(new Font("���� ���", Font.BOLD, 14));
		id_nameLabel.setBounds(20, 50, 80, 20);
		add(id_nameLabel);
		
//		ȸ����ȣã�� �޴����̸� �ؽ�Ʈ
		id_phoneLabel = new JLabel("�޴�����ȣ");
		id_phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		id_phoneLabel.setForeground(new Color(82, 55, 56));
		id_phoneLabel.setFont(new Font("���� ���", Font.BOLD, 14));
		id_phoneLabel.setBounds(20, 90, 80, 20);
		add(id_phoneLabel);
		
//		ȸ����ȣã�� �̸��Է� �Է¶�
		id_nameBlank = new JTextField();
		id_nameBlank.setBounds(120, 50, 120, 23);
		id_nameBlank.setForeground(new Color(82, 55, 56));
		add(id_nameBlank);
		id_nameBlank.setColumns(10);
		
//		ȸ����ȣã�� �޴�����ȣ�Է� �Է¶�
		id_phoneBlank = new JTextField();
		id_phoneBlank.setBounds(120, 90, 120, 23);
		id_phoneBlank.setForeground(new Color(82, 55, 56));
		add(id_phoneBlank);
		id_phoneBlank.setColumns(10);
		
		// ȸ����ȣã�� �޴�����ȣ �Է¶� �Ʒ� �ȳ����� ���̺�
		phoneInfoLabel1 = new JLabel("'-'�� ���� ���� ���� �Է�");
		phoneInfoLabel1.setBounds(105, 110, 155, 20);
		phoneInfoLabel1.setForeground(new Color(82, 55, 56));
		phoneInfoLabel1.setFont(new Font("���� ���", Font.BOLD, 11));
		add(phoneInfoLabel1);
		
//		��й�ȣã�� ȸ����ȣ �ؽ�Ʈ
		pw_numberLabel = new JLabel("ȸ����ȣ");
		pw_numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pw_numberLabel.setFont(new Font("���� ���", Font.BOLD, 14));
		pw_numberLabel.setForeground(new Color(82, 55, 56));
		pw_numberLabel.setBounds(20, 190, 80, 20);
		add(pw_numberLabel);
		
//		��й�ȣã�� �޴�����ȣ �ؽ�Ʈ
		pw_phoneLabel = new JLabel("�޴�����ȣ");
		pw_phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pw_phoneLabel.setForeground(new Color(82, 55, 56));
		pw_phoneLabel.setFont(new Font("���� ���", Font.BOLD, 14));
		pw_phoneLabel.setBounds(20, 230, 80, 20);
		add(pw_phoneLabel);
		
//		��й�ȣã�� �̸��Է� �Է¶�
		pw_numberBlack = new JTextField();
		pw_numberBlack.setColumns(10);
		pw_numberBlack.setForeground(new Color(82, 55, 56));
		pw_numberBlack.setBounds(120, 190, 120, 23);
		add(pw_numberBlack);
		
//		��й�ȣã�� �޴�����ȣ�Է� �Է¶�
		pw_phoneBlack = new JTextField();
		pw_phoneBlack.setColumns(10);
		pw_phoneBlack.setForeground(new Color(82, 55, 56));
		pw_phoneBlack.setBounds(120, 230, 120, 23);
		add(pw_phoneBlack);
		
//		ȸ����ȣ ã�� ��ư
		findNumberButton = new JButton("ã��");
		findNumberButton.setFont(new Font("���� ���", Font.BOLD, 14));
		findNumberButton.setForeground(new Color(82, 55, 56));
		findNumberButton.setBounds(270, 50, 100, 60);
		add(findNumberButton);
		
//		��й�ȣ ã�� ��ư
		findPWButton = new JButton("ã��");
		findPWButton.setFont(new Font("���� ���", Font.BOLD, 14));
		findPWButton.setForeground(new Color(82, 55, 56));
		findPWButton.setBounds(270, 190, 100, 60);
		add(findPWButton);
		
//		���뼱����
		separator = new JSeparator();
		separator.setBounds(-50, 140, 470, 10);
		add(separator);
		
//		��й�ȣã�� �ؽ�Ʈ
		find_num = new JLabel("ȸ����ȣ ã��");
		find_num.setHorizontalAlignment(SwingConstants.CENTER);
		find_num.setForeground(new Color(82, 55, 56));
		find_num.setFont(new Font("���� ���", Font.BOLD, 14));
		find_num.setBounds(140, 20, 90, 20);
		add(find_num);
		
//		ȸ����ȣã�� �ؽ�Ʈ
		find_pw = new JLabel("��й�ȣ ã��");
		find_pw.setHorizontalAlignment(SwingConstants.CENTER);
		find_pw.setForeground(new Color(82, 55, 56));
		find_pw.setFont(new Font("���� ���", Font.BOLD, 14));
		find_pw.setBounds(140, 160, 90, 20);
		add(find_pw);
		
		phoneInfoLabel2 = new JLabel("'-'�� ���� ���� ���� �Է�");
		phoneInfoLabel2.setBounds(105, 250, 155, 20);
		phoneInfoLabel2.setForeground(new Color(82, 55, 56));
		phoneInfoLabel2.setFont(new Font("���� ���", Font.BOLD, 11));
		add(phoneInfoLabel2);
		
		// �̺�Ʈ�� �߰��ϴ� �κ�
		findNumberButton.addActionListener(handler);
		findPWButton.addActionListener(handler);
		
		setVisible(true);
	}
	
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// DB ������ ���õ� ���� �ʱ�ȭ
			s_dao = StudentDAO.getInstance();
			student = new Student();
		 
			// �� JTextField�� �Էµ� ���� �����´�.
			id_nameVal = id_nameBlank.getText();
			id_phoneVal = id_phoneBlank.getText();
			pw_numVal = pw_numberBlack.getText();
			pw_phoneVal = pw_phoneBlack.getText();
		 	
			// ȸ����ȣ ã�� ��ư�� ������ ��
			if (e.getSource() == findNumberButton)
			{		
			   int st_num = s_dao.findSt_num(id_nameVal, id_phoneVal);	// �̸��� ��ȭ��ȣ�� ȸ�� ��ȣ�� ã�´�.
				 
				try 
				{
					if (st_num == 0)		// �ش� ȸ�� ��ȣ�� ���� ��
					{
						new Dialog_Default("����", "��ϵ� ȸ����ȣ�� �����ϴ�.");
					}
					else	// �ش� ȸ����ȣ�� ���� ��
					{
						new Dialog_Default(id_nameVal, st_num);	// ȸ����ȣ �ȳ� ���̾�α� â
						id_nameBlank.setText("");						// ���� JTextField â�� �ؽ�Ʈ�� �����.
						id_phoneBlank.setText("");
						id_nameBlank.requestFocus();					// ��Ŀ���� �̸� �Է� ĭ�� �����.
					}
				} catch (Exception oe) {
					oe.printStackTrace();
				}
			}
			
			// ��й�ȣ ã�� ��ư�� ������ ��
			else if (e.getSource() == findPWButton)
			{
				int st_num = 0;
				
				try		// �Է��� ȸ����ȣ�� ���ڰ� �ƴ����� Ȯ���Ѵ�.
				{
					st_num = Integer.parseInt(pw_numVal);
				}
				catch (NumberFormatException ne)	// ���ڰ� �ƴ� �� Exception�� �߻��Ѵ�.
				{
					new Dialog_Default("����", "ȸ����ȣ�� ���ڷ� �Է����ּ���.");
					pw_numberBlack.requestFocus();		// ��й�ȣ ã�� ȸ�� ��ȣ �Է�ĭ�� ��Ŀ���� �����.
					return;
				}
				
				// ȸ����ȣ�� ��ü ����
				student = s_dao.createStudent(st_num);				
				
				// ȸ����ȣ�� 0�ϰ�� ( �ش� ȸ�� ��ȣ�� ��� �� ��ü�� ������ ���)
				if (student.getSt_num() == 0)
				{
					new Dialog_Default ("����", "ȸ����ȣ �Ǵ� �޴��� ��ȣ��", "�ٽ� �Է����ּ���.");
				}
				else	// �ش� ȸ����ȣ�� ��ü�� ������ ���
				{
					if (student.getPhone().equals(pw_phoneVal))		// �޴��� ��ȣ�� �°� �Է��ߴ��� �˻�
					{
						new Dialog_Default("Ȯ��", "�Է��Ͻ� �ڵ��� ��ȣ��", "�ӽ� ��й�ȣ�� ������Ƚ��ϴ�.");
					}
					else	// �޴��� ��ȣ�� Ʋ�� ���
					{
						new Dialog_Default ("����", "ȸ����ȣ �Ǵ� �޴��� ��ȣ��", "�ٽ� �Է����ּ���.");
					}
				}
			}	
		}	
	}
}