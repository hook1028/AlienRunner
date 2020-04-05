package last.design;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// �⺻ ���̾�α� â
public class Dialog_Default extends JDialog
{	
	// UI�� ����ϴ� ����
	private JLabel label1;
	private JLabel label2;
	private JButton btn1;

	// ������ �� ��, Ȯ�ι�ư�� �ִ� Dialog
	public Dialog_Default(String title, String l_str1)
	{
		// Dialog â ����
		super(Main.mainFrame);	// ���̾�α��� Owner�� OverrallFrame���� �����Ѵ�.
		setSize(300, 150);			// ���̾�α��� ũ�⸦ �����Ѵ�.
		init(this, title);				// ���̾�α��� �⺻ ������ �������ִ� �޼ҵ�
		
//		��1
		label1 = new JLabel(l_str1);
		label1.setFont(new Font("���� ���", Font.BOLD, 14));
		label1.setForeground(new Color(82, 55, 56));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(25, 35, 250, 20);

//		Ȯ�� ��ư
		btn1 = new JButton("Ȯ��");
		btn1.setFont(new Font("���� ���", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(93, 76, 105, 27);

		// ������Ʈ �߰�
		add(btn1);
		add(label1);
		
		// ��ư �̺�Ʈ ���� (setVisible �޼ҵ� ������ �Է��Ѵ�)
		buttonEvent();
		
		setVisible(true);
	}
	
	// ������ �� ��, Ȯ�ι�ư�� �ִ� Dialog
	public Dialog_Default(String title, String l_str1, String l_str2)
	{		
		// Dialog â ����
		super(Main.mainFrame);
		setSize(300, 150);
		init(this, title);
				
//		��1
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
		
//		Ȯ�� ��ư
		btn1 = new JButton("Ȯ��");
		btn1.setFont(new Font("���� ���", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(93, 76, 105, 27);
		
		// ������Ʈ �߰�
		add(btn1);
		add(label1);
		add(label2);
		
		// ��ư �̺�Ʈ ����
		buttonEvent();
		
		setVisible(true);
	}
		
	// ������ ȸ�� ���� ����â�� ����� ������
	Dialog_Default(String name, int value)
	{
		super(Main.mainFrame);
		setSize(300, 150);
		init(this, "�Ϸ�");

		String l_str1 = name + "���� ȸ����ȣ�� " + value + " �Դϴ�.";
		String l_str2 = "�α��� �� ȸ����ȣ�� �Է����ּ���";

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

		// Ȯ�� ��ư
		btn1 = new JButton("Ȯ��");
		btn1.setFont(new Font("���� ���", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(93, 76, 105, 27);

		// ������Ʈ �߰�
		add(btn1);
		add(label1);
		add(label2);
		
		// ��ư �̺�Ʈ ����
		buttonEvent();;
	
		setVisible(true);	
	}
	
	// Dialog�� �⺻ ������ ����ϴ� �޼ҵ�. Static���� ������ �ٸ� Dialog Ŭ���������� ����� �� �ְ� �Ѵ�.
	static void init(JDialog dialog, String title)
	{
		dialog.setModal(true);													// Dialog�� �ʼ������� true�� �����Ѵ�.
		dialog.setFont(new Font("���� ���", Font.BOLD, 14));				// Dialog�� ��Ʈ�� �����Ѵ�.
		dialog.setForeground(new Color(82, 55, 56));						// Dialog�� ���ڻ��� �����Ѵ�.
		dialog.setTitle(title);														// Dialog�� title�� �����Ѵ�.
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);	// Dialog�� X ��ư ���� �����Ѵ�.
		dialog.setLayout(null);													// Dialog�� Layout�� �����Ѵ�.
		dialog.setResizable(false);												// Dialog�� ũ�⸦ �缳������ ���ϰ� �Ѵ�.
		dialog.setLocationRelativeTo(Main.mainFrame);						// Dialog�� ��ġ�� OverrallFrame ����� �Ѵ�.
	}
	
	// Ȯ�� ��ư�� ���� ��ư �̺�Ʈ
	void buttonEvent()
	{
		btn1.addActionListener(new ActionListener()
		{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// �̺�Ʈ�� �Ͼ ������Ʈ�� btn1 �̶��
					if(e.getSource() == btn1)
					{
						Dialog_Default.this.dispose();		// �ش� ���̾�α� â�� �ݴ´�.	
					}			
				}
		});
	}
}
