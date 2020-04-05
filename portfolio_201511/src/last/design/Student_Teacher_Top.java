package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import dao.table.*;

// �����԰� �л� ������ ����� Ŭ����
public class Student_Teacher_Top extends JPanel
{	
	// UI ����
	private JLabel infoDataText;	// ���̺� Ÿ��Ʋ�� ǥ���ϴ� ��
	private JLabel helloLabel;		// ȯ�� ��
	private JLabel cancelLabel;	// ������û ��� �ȳ� ��
	private JButton modifyButton;		// ������û Ȥ�� �⼮�� ���� ��ư
	private JButton lessonButton;		// ȸ�� ���� ���� ��ư
	private JButton logoutButton;		// �α׾ƿ� ��ư
	private JLabel login_name;		// �α��� �� ȸ���� �̸��� ��Ÿ�� ��
	private JTable table;
	
	// ȸ�������� �������� Ŭ����
	private Student student;
	private Teacher teacher;
	private Lecture_TableDAO lt_dao;
	
	// ���̺� ����� Ŭ����
	private DefaultTableModel dtm;
	private ArrayList<Vector> list;
	private Vector<String> colName;
	
	// �̺�Ʈ �ڵ鷯
	private Handler handler = new Handler();

	// ������
	public Student_Teacher_Top(Object who)
	{							
		if(who instanceof Student)		// �л��� �α��� �� ���
		{
			student = (Student)who;
			infoDataText = new JLabel("�� ��������");
			lessonButton = new JButton("�� �� �� û");
			modifyButton = new JButton("�� ���� ����");
			login_name = new JLabel(student.getName());
			
//			������� �ȳ� ��	
			cancelLabel = new JLabel("������û ��Ҵ� �����ڿ��� ����");
			cancelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			cancelLabel.setFont(new Font("���� ���", Font.BOLD, 14));
			cancelLabel.setForeground(new Color(82, 55, 56));
			cancelLabel.setBounds(610, 130, 230, 40);
			add(cancelLabel);
		}
		else	// ���簡 �α��� �� ���
		{
			teacher = (Teacher)who;
			infoDataText = new JLabel("�� ��������");
			lessonButton = new JButton("������ ���");	
			modifyButton = new JButton("�� ���� ����");
			login_name = new JLabel(teacher.getName());
		}
		
		lt_dao = Lecture_TableDAO.getInstance();
				
		makeTable(who);		// ���̺� ����� �޼ҵ�
		
//		�� ���� ���� ��ũ����
		JScrollPane LessonDataScroll = new JScrollPane(table);
		LessonDataScroll.setBounds(40, 170, 810, 400);
		add(LessonDataScroll);
		
//		�� ���� ���� �ؽ�Ʈ
		infoDataText.setFont(new Font("���� ���", Font.BOLD, 16));
		infoDataText.setForeground(new Color(82, 55, 56));
		infoDataText.setHorizontalAlignment(SwingConstants.CENTER);
		infoDataText.setBounds(40, 130, 120, 40);
		add(infoDataText);
		
//		ȸ���������� ��ư
		modifyButton.setFont(new Font("���� ���", Font.BOLD, 18));
		modifyButton.setBounds(690, 40, 150, 60);
		modifyButton.setForeground(new Color(82, 55, 56));
		add(modifyButton);
		
//		������û ��ư
		lessonButton.setFont(new Font("���� ���", Font.BOLD, 18));
		lessonButton.setBounds(516, 40, 150, 60);
		lessonButton.setForeground(new Color(82, 55, 56));
		add(lessonButton);

//		����� ȯ������ �ؽ�Ʈ
		helloLabel = new JLabel("�� ȯ���մϴ�");
		helloLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		helloLabel.setForeground(new Color(82, 55, 56));
		helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		helloLabel.setBounds(140, 45, 140, 30);
		add(helloLabel);
		
//		�α׾ƿ� ��ư
		logoutButton = new JButton("�α׾ƿ�");
		logoutButton.setFont(new Font("���� ���", Font.BOLD, 12));
		logoutButton.setForeground(new Color(82, 55, 56));
		logoutButton.setBounds(270, 50, 75, 25);
		add(logoutButton);

//		�α����� ȸ���� �̸�
		login_name.setFont(new Font("���� ���", Font.BOLD, 16));
		login_name.setHorizontalAlignment(SwingConstants.CENTER);
		login_name.setForeground(new Color(82, 55, 56));
		login_name.setBounds(50, 45, 140, 30);
		add(login_name);
		
		// �̺�Ʈ �����ϴ� �κ�
		lessonButton.addActionListener(handler);
		modifyButton.addActionListener(handler);
		logoutButton.addActionListener(handler);
		table.addMouseListener(handler);
		
		setVisible(true);
	}
	
	// �������� ���������� Ȯ���� �� �׿� ���� ���̺��� ������ִ� �޼ҵ�
	void makeTable(Object who)
	{
		// �л��� ���
		if (who instanceof Student)
		{
			student = (Student)who;				// Object�� ���� Student �ν��Ͻ��� �ٿ�ĳ�����Ѵ�.
			
			colName = new Vector<String>();
			colName.add("���ǹ�ȣ");
			colName.add("���Ǿ��");
			colName.add("���Ǹ�");
			colName.add("�����");
			colName.add("����");
			colName.add("���ǽð�");
			colName.add("���ǽ�");
			colName.add("���");
			colName.add("��û��Ȳ");
			
			// �ش� �л��� ������û�� ���Ǹ� ArrayList�� ��ƿ´�.
			list = lt_dao.searchLecture_tableBySt_Num(student.getSt_num());
			
			// dtm�� ���� �������� ���ϵ��� �Ѵ�.
			dtm = new DefaultTableModel(colName, 0) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			for (int i = 0; i < list.size(); i++)
			{
				dtm.addRow(list.get(i));
			}
			
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);				// ���̺��� ��� ������ �ٲ��� ���ϰ� �Ѵ�.
			table.getTableHeader().setForeground(new Color(82, 55, 56));
			table.setRowSorter(new TableRowSorter(dtm));						// ���̺� ����� ������ �� ������ �ǰ� �Ѵ�.
			table.setFont(new Font("���� ���", Font.PLAIN, 15));
			table.setForeground(new Color(82, 55, 56));
			sortTable();	// ���̺� ���� �� �÷� ���� ����
		}
		
		// ������ ���
		else if (who instanceof Teacher)
		{
			teacher = (Teacher)who;				// Object�� ���� Teacher �ν��Ͻ��� �ٿ�ĳ�����Ѵ�.
			
			colName = new Vector<String>();
			colName.add("���ǹ�ȣ");
			colName.add("���Ǿ��");
			colName.add("���Ǹ�");
			colName.add("�����");
			colName.add("����");
			colName.add("���ǽð�");
			colName.add("���ǽ�");
			colName.add("���");
			colName.add("��û��Ȳ");
			
			// �ش� ���簡 �����ϴ� ���ǵ��� �ҷ��´�.
			list = lt_dao.searchLecture_tableByT_num(teacher.getT_num());
			
			// dtm�� ������ �������� ���ϰ� �Ѵ�.
			dtm = new DefaultTableModel(colName, 0) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			for (int i = 0; i < list.size(); i++)
			{
				dtm.addRow(list.get(i));
			}
			table = new JTable(dtm);
			table.getTableHeader().setReorderingAllowed(false);				// ���̺� ����� ������ �ٲ��� ���ϰ� �Ѵ�.
			table.getTableHeader().setForeground(new Color(82, 55, 56));
			table.setRowSorter(new TableRowSorter(dtm));						// ���̺� ����� ������ �� ������ �ǰ� �Ѵ�.
			table.setFont(new Font("���� ���", Font.PLAIN, 15));
			table.setForeground(new Color(82, 55, 56));
			sortTable();		// ���̺� ���� �� �÷� ���� ����
		}
	}
	
	// ���̺� ���� �� ������ �������ִ� �޼ҵ�
	private void sortTable()
	{
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();	// ���̺� ���� display�� �����ϴ� Ŭ����
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);				// ���̺��� ���������� ����� �Ѵ�.
		TableColumnModel tcm = table.getColumnModel();					// ���̺��� �÷����� �����´�.

		for (int i = 0; i < tcm.getColumnCount(); i++)		// �÷� ������ŭ �÷��� ������ ������
		{
			tcm.getColumn(i).setCellRenderer(dtcr);			// �÷��𵨿��� �÷��� ������ ��� ���ķ� �����Ѵ�.
		}
		
		table.setRowHeight(30);			// ���̺� ���� ���̸� ���Ѵ�.
        table.getColumnModel().getColumn(0).setPreferredWidth(50);		// ���� ��ȣ
        table.getColumnModel().getColumn(1).setPreferredWidth(50);		// ���� ���
        table.getColumnModel().getColumn(2).setPreferredWidth(170);		// ���Ǹ�
        table.getColumnModel().getColumn(3).setPreferredWidth(45);		// �����
        table.getColumnModel().getColumn(4).setPreferredWidth(50);		// ����
        table.getColumnModel().getColumn(5).setPreferredWidth(65);		// �ð�
        table.getColumnModel().getColumn(6).setPreferredWidth(50);		// ���ǽ�
        table.getColumnModel().getColumn(7).setPreferredWidth(50);		// ���
        table.getColumnModel().getColumn(8).setPreferredWidth(50);		// ��û��Ȳ
	}
	
	// �̺�Ʈ�� ����ϴ� ���� Ŭ����
	class Handler extends MouseAdapter implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// �л� ����� ���
			if (student != null)
			{
				
				if (e.getSource() == lessonButton)				// ���� ��û ��ư�� ������ ��
				{
					OverrallFrame.changePanel(new AddLesson(student));
				}
				else if (e.getSource() == modifyButton)			// ���� ���� ��ư�� ������ ��
				{
					new MemberInfo(MemberInfo.MODIFY, student);
				}
				else if (e.getSource() == logoutButton)			// �α׾ƿ� ��ư�� ������ ��
				{
					Main.mainFrame.dispose();
					new Login();
				}
			}
			
			// ���� ����� ���
			else
			{
				if (e.getSource() == lessonButton)				// ������ ��� ��ư�� ������ ��
				{
					if (table.getSelectedColumn() != -1)
					{
						// ���� dtm�� ����� ����(index)�� �����´�.
						int index = table.convertRowIndexToModel(table.getSelectedRow());
		                int l_num = (int) dtm.getValueAt(index, 0);	  // ���õ� ���� 0��° �÷� = ���� ��ȣ       
						new StudentReading(l_num, StudentReading.TEACHER);		// �ش� ������ ������ ����� ����.
					}
				}
				else if (e.getSource() == modifyButton)			// ���� ���� ��ư�� ������ ��
				{
					new MemberInfo(MemberInfo.T_MODIFY, teacher);// ���� ���� ���� â�� ����.
				}
				else if (e.getSource() == logoutButton)			// �α׾ƿ� ��ư�� ������ ��
				{
					Main.mainFrame.dispose();
					new Login();
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent me) 
		{
			if (me.getClickCount() == 2 && teacher != null)	// ���� ��忡�� ���콺 ����Ŭ���� ���� ��
			{
				if (table.getSelectedColumn() != -1)				// ���̺� ���õ� ���� �ִٸ�
				{
					// ���� dtm�� ����� ����(index)�� �����´�.
					int index = table.convertRowIndexToModel(table.getSelectedRow());
	                int l_num = (int) dtm.getValueAt(index, 0);	  // ���õ� ���� 0��° �÷� = ���� ��ȣ       
					new StudentReading(l_num, StudentReading.TEACHER);		// �ش� ������ ������ ����� ����.
				}
			}
		}								
	}
}