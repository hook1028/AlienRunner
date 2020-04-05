package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// ������ ��� ���̾�α� Ŭ����
public class AdminRegister extends JDialog
{	
	// UI�� ����� ����
	private JTextField id_tf;
	private JPasswordField pw_tf;
	private JPasswordField pw2_tf;
	private JLabel id_lb;
	private JLabel pw_lb;
	private JLabel pw2_lb;
	private JButton reg_btn;
	private JLabel pwHint_lb;
	
//	�̺�Ʈ �ڵ鷯 Ŭ����
	private Handler handler = new Handler();
	
//	TextField���� ������ ���� ������ ����
	private String idVal;
	private String pwVal;
	private String pw2Val;
	
//	DB ������ ���� Ŭ����	
	private AdminDAO a_dao;
	private Admin admin;
	
	// ������
	public AdminRegister()
	{
//		JDailog �ʱ�ȭ
		super(Main.loginFrame);					// Owner ������ ����
		setSize(400, 200);							// Dialog�� ũ�� ����
		Dialog_Default.init(this, "������ ���");	// Dialog�� ���� �� �⺻ ������ �����Ѵ�.
		
//		���̵� �ؽ�Ʈ
		id_lb = new JLabel("ID");
		id_lb.setHorizontalAlignment(SwingConstants.RIGHT);		// JLabel�� ���������� �����Ѵ�.
		id_lb.setForeground(new Color(82, 55, 56));					// JLabel�� ���ڻ��� �����Ѵ�.
		id_lb.setFont(new Font("���� ���", Font.BOLD, 14));		// JLabel�� ��Ʈ�� �����Ѵ�.
		id_lb.setBounds(20, 30, 90, 20);								// JLabel�� ��ġ�� ũ�⸦ �����Ѵ�.
		add(id_lb);														// Dialog�� ���� �ִ´�.
		
//		��й�ȣ �ؽ�Ʈ
		pw_lb = new JLabel("��� ��ȣ");
		pw_lb.setHorizontalAlignment(SwingConstants.RIGHT);
		pw_lb.setForeground(new Color(82, 55, 56));
		pw_lb.setFont(new Font("���� ���", Font.BOLD, 14));
		pw_lb.setBounds(20, 60, 90, 20);
		add(pw_lb);
		
//		���̵� �Է¹��� JTextField
		id_tf = new JTextField();
		id_tf.setBounds(120, 30, 120, 23);
		id_tf.setForeground(new Color(82, 55, 56));
		add(id_tf);
		id_tf.setColumns(10);
		
//		��й�ȣ�� �Է¹��� JPasswordField
		pw_tf = new JPasswordField();
		pw_tf.setBounds(120, 60, 120, 23);
		pw_tf.setForeground(new Color(82, 55, 56));
		add(pw_tf);
		pw_tf.setColumns(10);
		
		// ��й�ȣ �ȳ� JLabel
		pwHint_lb = new JLabel("8 ~ 20 �� �̳��� �Է�");
		pwHint_lb.setBounds(123, 80, 155, 20);
		pwHint_lb.setForeground(new Color(82, 55, 56));
		pwHint_lb.setFont(new Font("���� ���", Font.BOLD, 11));
		add(pwHint_lb);
		
//		��й�ȣ Ȯ�� JLabel
		pw2_lb = new JLabel("��й�ȣ Ȯ��");
		pw2_lb.setHorizontalAlignment(SwingConstants.RIGHT);
		pw2_lb.setFont(new Font("���� ���", Font.BOLD, 14));
		pw2_lb.setForeground(new Color(82, 55, 56));
		pw2_lb.setBounds(20, 105, 90, 20);
		add(pw2_lb);
				
//		��й�ȣ Ȯ�� JPasswordField
		pw2_tf = new JPasswordField();
		pw2_tf.setColumns(10);
		pw2_tf.setForeground(new Color(82, 55, 56));
		pw2_tf.setBounds(120, 105, 120, 23);
		add(pw2_tf);
		
//		��� ��ư
		reg_btn = new JButton("���");
		reg_btn.setFont(new Font("���� ���", Font.BOLD, 14));
		reg_btn.setForeground(new Color(82, 55, 56));
		reg_btn.setBounds(270, 48, 100, 60);
		add(reg_btn);		
		
		// �̺�Ʈ�� �߰��ϴ� �κ� (���̾�α״� setVisible ������ �۾��� �������� �ʱ� ������ setVisible ������ �ۼ�)
		reg_btn.addActionListener(handler);	
		
		setVisible(true);
	}
	
	// �̺�Ʈ�� ����ϴ� ���� Ŭ����
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// DB������ ���õ� Ŭ���� �ʱ�ȭ
			a_dao = AdminDAO.getInstance();
			admin = new Admin(); 
		 	   
			// ��Ϲ�ư�� ������ ��
			if (e.getSource() == reg_btn)
			{
				idVal = id_tf.getText();								// ����ڰ� �Է��� id ���� �����´�.
				pwVal = new String(pw_tf.getPassword());		// ����ڰ� �Է��� pw ���� �����´�.
				pw2Val = new String(pw2_tf.getPassword());	// ����ڰ� �Է��� pw Ȯ�� ���� �����´�.
				
				if (pwVal.equals(pw2Val))		// �н������ �н����� Ȯ�ο� �Է��� ���� ���ٸ�
				{
					if(pwVal.length() >= 8 && pwVal.length() <= 20)		// �н������� ���̰� 8�� �̻� 20�� �������� Ȯ���� �´ٸ�
					{
						admin.setId(idVal);					// Admin ��ü�� id ���� �ִ´�.
						admin.setPassword(pwVal);		// Admin ��ü�� pw ���� �ִ´�.
						
						if (a_dao.insertAdmin(admin) == 1)		// DB�� ������ ������
						{
							new Dialog_Default("��� ����", "������ ��Ͽ� �����Ͽ����ϴ�.");
							Main.loginFrame.setAdmin(admin);	// Login �������� ����� ������ ������ ������.
							AdminRegister.this.dispose();			// ���̾�αװ� ���� �� ���� â�� �ݴ´�.
						}
						else	// DB�� ������ ���� ���ߴٸ�
						{
							new Dialog_Default("��� ����", "������ ��Ͽ� �����Ͽ����ϴ�.");
						}
					}
					else		// ��й�ȣ�� ���̰� 8 ~ 20�ڰ� �ƴ϶��
					{
						new Dialog_Default("����", "��й�ȣ�� 8~20�� �̳��� �Է����ּ���.");
						pw_tf.requestFocus();		// �н����� JTextField�� ��Ŀ���� �����ش�.
					}
				}
				else			// ��й�ȣ, ��й�ȣ Ȯ���� ���� �ٸ��ٸ�
				{
					new Dialog_Default("����", "��й�ȣ�� �ٸ��� �Է��ϼ̽��ϴ�.");
					pw_tf.requestFocus();			// �н����� JTextField�� ��Ŀ���� �����ش�.
				}
			}	
		}	
	}
}