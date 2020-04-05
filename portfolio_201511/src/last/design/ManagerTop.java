package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import dao.table.*;

// ������ ���� ȭ�� Ŭ����
public class ManagerTop extends JPanel
{
	// ��� ����
	private JLabel hellolabel;
	private JLabel login_name;		// �α��� �� ȸ���� �̸��� ��Ÿ�� ��
	private JButton registerButton;
	private JButton modifyButton;
	private JButton deleteButton;
	private JButton logoutButton;
	private JTabbedPane managerTab;	
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;	
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private Choice choice1;					// ������ ���̽�
	private Choice choice2;				// ���� ���̽�
	private Choice choice3;				// ���� ���̽�
	private JTextField findField1;		// �������˻� �ؽ�Ʈ
	private JTextField findField2;		// ����˻� �ؽ�Ʈ
	private JTextField findField3;		// ���ǰ˻� �ؽ�Ʈ
	private JButton searchButton1;		// ������ �˻���ư
	private JButton searchButton2;		// ���� �˻���ư
	private JButton searchButton3;		// ���� �˻���ư
	
	//���̺� ����� Ŭ����
	private JTable table1;
	private JTable table2;
	private JTable table3;
	private Vector<String> colName1;
	private Vector<String> colName2;
	private Vector<String> colName3;
	private DefaultTableModel dtm1;
	private DefaultTableModel dtm2;
	private DefaultTableModel dtm3;
	private ArrayList<Vector> list;
	
	// �̺�Ʈ ������ Ŭ����
	private Handler handler = new Handler();
		
	// JDBC ������ ���� Ŭ����
	private Admin admin;
	private Teacher teacher;
	private Student student;
	private Lecture lecture;
	private StudentDAO s_dao;
	private TeacherDAO t_dao;
	private LectureDAO l_dao;
	private Lecture_TableDAO lt_dao;
		
	// ������ � ���� ������� �����ϱ� ���� ���
	final static int STUDENT = 0;
	final static int TEACHER = 1;
	final static int LECTURE = 2;
	
	// ���� �޾ƿ� ������
	private int tabVal = 0;
	
	// ������
	public ManagerTop(Admin admin)
	{	
		this.admin = admin;				// �Ķ���ͷ� ���� Admin ��ü�� ��������� ����
		
		// DB ���� Ŭ���� �ʱ�ȭ
		s_dao=StudentDAO.getInstance();
		t_dao=TeacherDAO.getInstance();
		l_dao=LectureDAO.getInstance();
		lt_dao=Lecture_TableDAO.getInstance();
		
//		����� ȯ������ ���̺�. ������ ����̱� ������ ȯ���մϴ� ��� ����Դϴ�. ǥ��
		hellolabel = new JLabel("����Դϴ�.");
		hellolabel.setFont(new Font("���� ���", Font.BOLD, 15));
		hellolabel.setForeground(new Color(82, 55, 56));
		hellolabel.setHorizontalAlignment(SwingConstants.CENTER);
		hellolabel.setBounds(140, 50, 140, 30);
		add(hellolabel);
		
//		�����ڴ� �̸��� ���� ������ ������ ǥ��		
		login_name = new JLabel("������");
		login_name.setFont(new Font("���� ���", Font.BOLD, 15));
		login_name.setForeground(new Color(82, 55, 56));
		login_name.setHorizontalAlignment(SwingConstants.CENTER);
		login_name.setBounds(70, 50, 140, 30);
		add(login_name);
		
//		��� ��ư
		registerButton = new JButton("�� ��");
		registerButton.setBounds(432, 40, 130, 60);
		registerButton.setForeground(new Color(82, 55, 56));
		registerButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(registerButton);
		
//		���� ��ư
		modifyButton = new JButton("�� ��");
		modifyButton.setBounds(576, 40, 130, 60);
		modifyButton.setForeground(new Color(82, 55, 56));
		modifyButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(modifyButton);

//		���� ��ư
		deleteButton = new JButton("�� ��");
		deleteButton.setBounds(720, 40, 130, 60);
		deleteButton.setForeground(new Color(82, 55, 56));
		deleteButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(deleteButton);
		
//		�α׾ƿ� ��ư
		logoutButton = new JButton("�α׾ƿ�");
		logoutButton.setForeground(new Color(82, 55, 56));
		logoutButton.setFont(new Font("���� ���", Font.BOLD, 12));
		logoutButton.setBounds(270, 50, 80, 30);
		add(logoutButton);

		// ��ü ���� ��
		managerTab = new JTabbedPane(JTabbedPane.TOP);
		managerTab.setFont(new Font("���� ���", Font.BOLD, 16));
		managerTab.setForeground(new Color(82, 55, 56));
		managerTab.setBounds(40, 130, 810, 440);
		add(managerTab);
		
//		������ �г�/��ũ����		
		colName1 = new Vector<String>();
		colName1.add("������ ��ȣ");
		colName1.add("�̸�");
		colName1.add("����");
		colName1.add("��ȭ��ȣ");
		colName1.add("�̸���");

		list = s_dao.selectStudent();		// ��� �л� ������ ArrayList�� ��ƿ´�.
		// dtm�� �������� ���ϰ� ����� ���� �͸� ��� Ŭ������ ����.
		dtm1 = new DefaultTableModel(colName1, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// dtm1�� ������ �ִ´�.
		makeTable(dtm1, colName1, list);

		table1 = new JTable(dtm1);											// ������ ���� dtm�� ���� ���̺� ����
		table1.getTableHeader().setReorderingAllowed(false);				// ���̺� ��� ������ �ٲ��� ���ϰ� �Ѵ�.
		table1.getTableHeader().setForeground(new Color(82, 55, 56));
		table1.setRowSorter(new TableRowSorter(dtm1));					// ���̺� ����� ������ �� ������ �����ϰ� �Ѵ�.
		table1.setFont(new Font("���� ���", Font.PLAIN, 15));
		table1.setForeground(new Color(82, 55, 56));
		
		sortTable(table1);		// ���̺� ���� �� �÷� ������ �������ִ� �޼ҵ�
		
		scrollPane1 = new JScrollPane(table1);		// ���̺��� ��ũ���ǿ� �ִ´�.
		
		panel1 = new JPanel();
		panel1.setLayout(null);
		scrollPane1.setBounds(30, 80, 735, 300);
		panel1.add(scrollPane1);
		managerTab.addTab("������ ����", panel1);
		
//		������ �г�/���̽�
		choice1 = new Choice();
		choice1.setBounds(440, 25, 110, 30);
		choice1.setBackground(new Color(255, 254, 220));
		choice1.setForeground(new Color(82, 55, 56));
		choice1.add("������ ��ȣ");
		choice1.add("������ �̸�");
		choice1.setFont(new Font("���� ���", Font.BOLD, 15));
		panel1.add(choice1);
		
//		������ �г�/�˻����̺�
		findField1 = new JTextField();
		findField1.setBounds(560, 25, 130, 27);
		findField1.setForeground(new Color(82, 55, 56));
		findField1.setColumns(10);
		panel1.add(findField1);
		
//		������ �г�/�˻���ư
		searchButton1 = new JButton("�˻�");
		searchButton1.setBounds(698, 25, 65, 27);
		searchButton1.setForeground(new Color(82, 55, 56));
		searchButton1.setFont(new Font("���� ���",Font.BOLD, 16));
		panel1.add(searchButton1);
		
//		���� �г�/��ũ����		
		colName2 = new Vector<String>();
		colName2.add("�����ȣ");
		colName2.add("�̸�");
		colName2.add("����");
		colName2.add("��ȭ��ȣ");
		colName2.add("�̸���");
		
		list = t_dao.selectTeacher();		// ��� ���� ������ ArrayList�� ��ƿ´�.
		dtm2 = new DefaultTableModel(colName2,0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		makeTable(dtm2, colName2, list);		// dtm2�� ������ �ִ´�.
		
		table2 = new JTable(dtm2);												// dtm2�� �־� ���̺��� �����.
		table2.getTableHeader().setReorderingAllowed(false);					// ���̺� ��� ������ �ٲ��� ���ϰ� �Ѵ�.
		table2.getTableHeader().setForeground(new Color(82, 55, 56));
		table2.setRowSorter(new TableRowSorter(dtm2));						// ���̺� ����� ������ �� ������ �ǵ��� �Ѵ�.
		table2.setFont(new Font("���� ���", Font.PLAIN, 15));
		table2.setForeground(new Color(82, 55, 56));
		
		sortTable(table2);			// ���̺� ���� �� �÷� ������ �����ϴ� �޼ҵ�
								
		scrollPane2 = new JScrollPane(table2);		// ���̺��� ��ũ���ǿ� �ִ´�.
		panel2 = new JPanel();
		panel2.setLayout(null);
		scrollPane2.setBounds(30, 80, 735, 300);
		panel2.add(scrollPane2);
		managerTab.addTab("���� ����", panel2);
		
//		���� �г�/���̽�
		choice2 = new Choice();
		choice2.setBounds(440, 25, 110, 30);
		choice2.setBackground(new Color(255, 254, 220));
		choice2.setForeground(new Color(82, 55, 56));
		choice2.add("���� ��ȣ");
		choice2.add("���� �̸�");
		choice2.setFont(new Font("���� ���", Font.BOLD, 15));
		panel2.add(choice2);
		
//		���� �г�/�˻����̺�
		findField2 = new JTextField();
		findField2.setForeground(new Color(82, 55, 56));
		findField2.setBounds(560, 25, 130, 27);
		findField2.setColumns(10);
		panel2.add(findField2);
		
//		���� �г�/�˻���ư
		searchButton2 = new JButton("�˻�");
		searchButton2.setBounds(698, 25, 65, 27);
		searchButton2.setForeground(new Color(82, 55, 56));
		searchButton2.setFont(new Font("���� ���",Font.BOLD, 16));
		panel2.add(searchButton2);
		
//		���� �г�/��ũ����	
		colName3 = new Vector<String>();
		colName3.add("���ǹ�ȣ");
		colName3.add("���Ǿ��");
		colName3.add("���Ǹ�");
		colName3.add("�����");
		colName3.add("����");
		colName3.add("���ǽð�");
		colName3.add("���ǽ�");
		colName3.add("���");
		colName3.add("��û��Ȳ");
		
		list = lt_dao.selectLecture_table();			// ��� ���� ������ ArrayList�� ��´�.
		
		dtm3 = new DefaultTableModel(colName3,0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		makeTable(dtm3, colName3, list);			// dtm3�� ������ �ִ´�.
		
		table3 = new JTable(dtm3);												// dtm3�� �־� ���̺��� �����.
		table3.getTableHeader().setReorderingAllowed(false);					// ���̺� ��� ������ �ٲ��� ���ϰ� �Ѵ�.
		table3.getTableHeader().setForeground(new Color(82, 55, 56));
		table3.setForeground(new Color(82, 55, 56));
		table3.setRowSorter(new TableRowSorter(dtm3));						// ���̺� �÷��� ������ �� ������ �ǰ� �Ѵ�. 
		table3.setFont(new Font("���� ���", Font.PLAIN, 15));
		
		sortTable(table3);		// ���̺� ���� �� Į�� ������ �������ִ� �޼ҵ�
		
		scrollPane3 = new JScrollPane(table3);		// ���̺��� ��ũ���ǿ� �ִ´�.
				
		panel3 = new JPanel();
		panel3.setLayout(null);
		scrollPane3.setBounds(30, 80, 735, 300);
		panel3.add(scrollPane3);
		managerTab.addTab("���� ����", panel3);
		
//		���� �г�/���̽�
		choice3 = new Choice();
		choice3.setBounds(440, 25, 110, 30);
		choice3.setBackground(new Color(255, 254, 220));
		choice3.setForeground(new Color(82, 55, 56));
		choice3.add("���� ��ȣ");
		choice3.add("���� �̸�");
		choice3.setFont(new Font("���� ���", Font.BOLD, 15));
		panel3.add(choice3);
		
//		���� �г�/�˻����̺�
		findField3 = new JTextField();
		findField3.setBounds(560, 25, 130, 27);
		findField3.setForeground(new Color(82, 55, 56));
		findField3.setColumns(10);
		panel3.add(findField3);
		
//		���� �г�/�˻���ư
		searchButton3 = new JButton("�˻�");
		searchButton3.setBounds(698, 25, 65, 27);
		searchButton3.setForeground(new Color(82, 55, 56));
		searchButton3.setFont(new Font("���� ���",Font.BOLD, 16));
		panel3.add(searchButton3);
		
		// �̺�Ʈ�� �����ϴ� �κ�
		findField1.addActionListener(handler);
		findField2.addActionListener(handler);
		findField3.addActionListener(handler);
		searchButton1.addActionListener(handler);
		searchButton2.addActionListener(handler);
		searchButton3.addActionListener(handler);
		registerButton.addActionListener(handler);
		modifyButton.addActionListener(handler);
		deleteButton.addActionListener(handler);
		logoutButton.addActionListener(handler);
		table1.addMouseListener(handler);
		table2.addMouseListener(handler);
		table3.addMouseListener(handler);
		
		setVisible(true);

	}	
		// �л� ���̺� �� ����� �޼ҵ�
		private void makeTable(DefaultTableModel dtm, Vector<String> colName, ArrayList<Vector> list) 
		{
			if (dtm.getRowCount() > 0)
			{
				// DefaultTableModel�� Row�� �ʱ�ȭ ���� ���̺��� �ڷḦ ���� �����Ѵ�. (���ΰ�ħ�� ���� ȿ��)
				dtm.setDataVector(null, colName);
			}

			for (int i = 0; i < list.size(); i++)
			{
				dtm.addRow(list.get(i));
			}
		}
		   	
		// ���̺� �����ϴ� �޼ҵ�
		private void sortTable (JTable table)
		{
			table.setRowHeight(30);															// ���̺� row�� ���̸� �����Ѵ�.
			DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();		// ���̺� �� ���÷��� ���� Ŭ����
			dtcr1.setHorizontalAlignment(SwingConstants.CENTER);					// ���� ������ ��� ���ķ� �����Ѵ�.
			TableColumnModel tcm1 = table.getColumnModel();						// ���̺��� �÷����� �����´�.
			
			for (int i = 0; i < tcm1.getColumnCount(); i++)		// �÷� ������ŭ �÷��� ������ ������
			{
				if (table != table3 && i % 5 == 4)
				{
					continue;
				}
				
				tcm1.getColumn(i).setCellRenderer(dtcr1);			// ���̺� �÷��𵨿��� ������ �÷��� ������ �����Ѵ�.
			}

			// �ǿ� �ִ� ���̺��� �÷��� �ٸ��� ������ ���̺��� ������ �÷� ���̸� �ٸ��� �����Ѵ�.
			if (table == table1)
			{
				table1.getColumnModel().getColumn(0).setPreferredWidth(25);		// ������ ��ȣ
				table1.getColumnModel().getColumn(1).setPreferredWidth(50);		// �̸�
				table1.getColumnModel().getColumn(2).setPreferredWidth(10);		// ����
				table1.getColumnModel().getColumn(3).setPreferredWidth(100);		// ��ȭ��ȣ
				table1.getColumnModel().getColumn(4).setPreferredWidth(150);		// �̸���
			}
			else if (table == table2)
			{	      
			     table2.getColumnModel().getColumn(0).setPreferredWidth(25);		// �����ȣ
			     table2.getColumnModel().getColumn(1).setPreferredWidth(50);		// �̸�
			     table2.getColumnModel().getColumn(2).setPreferredWidth(20);		// ����
			     table2.getColumnModel().getColumn(3).setPreferredWidth(55);		// ��ȭ��ȣ
			     table2.getColumnModel().getColumn(4).setPreferredWidth(120);	// �̸���
			}
			else if (table == table3)
			{
			     table3.getColumnModel().getColumn(0).setPreferredWidth(40);		// ���� ��ȣ
			     table3.getColumnModel().getColumn(1).setPreferredWidth(50);		// ���Ǿ��
			     table3.getColumnModel().getColumn(2).setPreferredWidth(130);	//���Ǹ�
			     table3.getColumnModel().getColumn(3).setPreferredWidth(55);		//�����
			     table3.getColumnModel().getColumn(4).setPreferredWidth(60);		//����
			     table3.getColumnModel().getColumn(5).setPreferredWidth(70);		//���ǽð�
			     table3.getColumnModel().getColumn(6).setPreferredWidth(45);		//���ǽ�
			     table3.getColumnModel().getColumn(7).setPreferredWidth(55);		//���
			     table3.getColumnModel().getColumn(8).setPreferredWidth(30);		//��û��Ȳ
			}			
		}
		
		// �̺�Ʈ ������ ����ϴ� ���� Ŭ����
		private class Handler extends MouseAdapter implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// ���� ���õ� ���� �� ������ �޾ƿ´�.
				tabVal = managerTab.getSelectedIndex();
	         
				// �α׾ƿ� ��ư�� ������ ��
				if (e.getSource() == logoutButton)
				{
					Main.mainFrame.dispose();		// ����ȭ���� �ִ� �������� �ݴ´�.
					new Login();						// �α��� â�� ����.
				}
	         
				// �л� ���� ���� ��
				if (tabVal == ManagerTop.STUDENT)
				{
					if (e.getSource() == registerButton)		// ��Ϲ�ư
					{
						new MemberInfo(MemberInfo.JOIN, null);		// ȸ�� ����â�� �߰� �Ѵ�.
						list = s_dao.selectStudent();						// ȸ�� ����â�� ���� �� �л� ���� ���̺��� �����Ѵ�.
						makeTable(dtm1, colName1, list);					// dtm1�� ����
						table1.updateUI();									// ���̺��� UI�� ������Ʈ �Ѵ�.
						sortTable(table1);									// ���̺� ���� �� �÷� ���� ����
					}
					else if (e.getSource() == modifyButton)		// ������ư
					{
						// ���̺��� ���õ� ���� �ִ� ���
						if (table1.getSelectedRow() != -1)
						{
							int index = table1.convertRowIndexToModel(table1.getSelectedRow()); // dtm1�� ����� index ��ȣ ��ȯ
							int st_num = (int) dtm1.getValueAt(index, 0);			// index���� 0��° �ڷ� = ȸ����ȣ
							student = s_dao.createStudent(st_num);					// ȸ����ȣ�� ������ �л��� ��ü�� �����Ѵ�.
							new MemberInfo(MemberInfo.MODIFY_A, student);	// �л����� ����â�� ����.
							list = s_dao.selectStudent();								// ����â�� ������ ���̺��� �����Ѵ�.
							makeTable(dtm1, colName1, list);							// dtm1�� ������ ���Ӱ� ��´�.
							table1.updateUI();											// ���̺��� UI�� ������Ʈ �Ѵ�.
							sortTable(table1);											// ���̺� ���� �� �÷� ���� ����
						}
					}
					else if (e.getSource() == deleteButton)		// ������ư
					{
						// ���̺��� ���õ� ���� �ִ� ��� 
						if(table1.getSelectedRow() != -1)
						{
							int index = table1.convertRowIndexToModel(table1.getSelectedRow());	// dtm1�� ����� index ��ȣ ��ȯ
							int st_num = (int) dtm1.getValueAt(index, 0);	// index���� 0��° �ڷ� = ȸ����ȣ	                  
							new Dialog_Select("������ Ż��", "���� Ż���Ͻðڽ��ϱ�?", Dialog_Select.MEMBER_DELETE, st_num);
							list = s_dao.selectStudent();			// ���̾�α� â�� ���� �� ���̺��� �����Ѵ�.
							makeTable(dtm1, colName1, list);		// dtm1�� ������ ���Ӱ� ��´�.
							table1.updateUI();						// ���̺��� UI�� ������Ʈ �Ѵ�.
							sortTable(table1);						// ���̺� ���� �� �÷� ���� ����
						}	               
					}
					// �˻� JTextField���� ���͸� ġ�ų� �˻� ��ư�� ������ ��
					else if (e.getSource() == findField1 || e.getSource() == searchButton1)
					{
						// �˻� ������ ������ ��ȣ�� ���
						if (choice1.getSelectedItem().equals("������ ��ȣ"))
						{
							int st_num = 0;			// �л���ȣ�� �޾ƿ� ����
							
							if (findField1.getText().equals(""))		// �˻� ���� �ƹ��͵� �Է����� �ʾ��� ���
							{
								list = s_dao.selectStudent();			// ��ü ������ �����´�.
								makeTable(dtm1, colName1, list);		// dtm1�� ����
								table1.updateUI();						// ���̺� UI�� ������Ʈ �Ѵ�.
								sortTable(table1);						// ���̺� ���� �� �÷� ���� ����
							}
							else	// JTextField�� �Էµ� ���� ���� ���
							{
								try		// try-catch������ �Էµ� ���� �������� ���ڿ����� �����Ѵ�.
								{
									st_num = Integer.parseInt(findField1.getText());
								}
								catch (NumberFormatException ne)		// ���ڿ� �Է� �� Exception �߻�
								{
									new Dialog_Default("����", "������ ��ȣ�� ���ڷθ� �Է����ּ���.");
									return;
								}
								
								list = s_dao.selectStudentBySt_num(st_num);		// �ش� �л���ȣ�� �˻��� ��� ���� ArrayList�� ����
								makeTable(dtm1, colName1, list);						// dtm1�� �˻������ ��´�.
								table1.updateUI();										// ���̺� UI�� ������Ʈ�Ѵ�.
								sortTable(table1);										// ���̺� ���� �� �÷� ���� ����
							}
						}
						
						// �˻� ������ ������ �̸��� ���
						else if (choice1.getSelectedItem().equals("������ �̸�"))
						{
							list = s_dao.selectStudentByName(findField1.getText());	// JTextField���� ���� ���� �˻��Ѵ�.
							makeTable(dtm1, colName1, list);								// dtm1�� �ش� �˻� ����� ��´�.
							table1.updateUI();												// ���̺� UI�� ������Ʈ�Ѵ�.
							sortTable(table1);												// ���̺� ���� �� �÷� ���� ����
						}
					}
				}
	         
			// ���� ���� ���� ��
			else if (tabVal == ManagerTop.TEACHER)
			{
				if (e.getSource() == registerButton)	 		// ��� ��ư
				{
					new TeacherRegister();					// ���� ���â�� ����.
					list = t_dao.selectTeacher();			// ���â�� ���� �� ���̺��� �����ϱ� ���� ArrayList�� �ҷ��´�.
					makeTable(dtm2, colName2, list);		// dtm2�� ������ ���� ��´�.
					table2.updateUI();						// ���̺��� UI�� ������Ʈ �Ѵ�.
					sortTable(table2);						// ���̺� ���� �� �÷� ���� ����
				}

				else if (e.getSource() == modifyButton) 	// ���� ��ư
				{
					if (table2.getSelectedRow() != -1)		// ���̺��� ���õ� ���� �ִٸ�
					{
						int index = table2.convertRowIndexToModel(table2.getSelectedRow());	// dtm2�� ����� ���� index ���� �����´�.
						int t_num = (int) dtm2.getValueAt(index, 0);	// ���õ� ���� 0��° �÷� ���� �����´� = �����ȣ
						teacher = t_dao.createTeacher(t_num);			// �ش� �����ȣ�� Teacher ��ü ����
						new MemberInfo(MemberInfo.T_MODIFY, teacher);	// ���� ����â�� ����.
						list = t_dao.selectTeacher();								// ����â�� ���� �� ���̺��� �����Ѵ�.
						makeTable(dtm2, colName2, list);							// dtm2�� ������ ���Ӱ� ��´�.
						table2.updateUI();											// ���̺��� UI�� ������Ʈ�Ѵ�.
						sortTable(table2);											// ���̺� ���� �� �÷� ���� ����
					}
				}
	            else if (e.getSource() == deleteButton)		// ���� ��ư
	            {
	            	// ���̺��� ���õ� 
	            	if(table2.getSelectedRow() != -1)
	            	{
	            		int index = table2.convertRowIndexToModel(table2.getSelectedRow());	// dtm2�� ����� ���� index ���� �����´�.
	            		int t_num = (int) dtm2.getValueAt(index, 0);		// ���õ� ���� 0��° �÷� ���� �����´� = �����ȣ
	            		new Dialog_Select("���� ����", "���縦 �����Ͻðڽ��ϱ�?", Dialog_Select.TEACHER_DELETE, t_num);
						list = t_dao.selectTeacher();			// ���̾�α� â�� ���� �� ���̺��� �����Ѵ�.
						makeTable(dtm2, colName2, list);		// dtm2�� ������ ���Ӱ� ��´�.
						table2.updateUI();						// ���̺��� UI�� ������Ʈ �Ѵ�.
						sortTable(table2);						// ���̺� ���� �� �÷� ���� ����
	            	}
	            }			
	            
				// JTextField���� ���͸� ġ�ų� �˻���ư�� ������ ��
	            else if (e.getSource() == findField2 || e.getSource() == searchButton2)
				{
	            	// �˻� ������ ���� ��ȣ�� ���
					if (choice2.getSelectedItem().equals("���� ��ȣ"))
					{
						int t_num = 0;		// ���� ��ȣ�� ������ ����
						
						if (findField2.getText().equals(""))			// �Է� ���� ���� ��� ��ü ������ �����´�.
						{
							list = t_dao.selectTeacher();			// ��� ���� ������ �����´�.
							makeTable(dtm2, colName2, list);		// dtm2�� ������ ��´�.
							table2.updateUI();						// ���̺� UI�� ������Ʈ �Ѵ�.
							sortTable(table2);						// ���̺� ���� �� �÷� ���� ����
						}
						else	// JTextField�� �Էµ� ���� ���� ���
						{
							try		// try-catch ������ �Է��� ���� �������� ���ڿ����� �����Ѵ�.
							{
								t_num = Integer.parseInt(findField2.getText());
							}
							catch (NumberFormatException ne)		// ���ڿ� �Է� �� Exception �߻�
							{
								new Dialog_Default("����", "���� ��ȣ�� ���ڷθ� �Է����ּ���.");
								return;
							}			
							
							list = t_dao.selectTeacherByT_num(t_num);		// �ش� �����ȣ�� �˻��� ����� ArrayList�� ����
							makeTable(dtm2, colName2, list);					// dtm2�� �˻���� ���� �ִ´�.
							table2.updateUI();									// ���̺� UI�� ������Ʈ �Ѵ�.
							sortTable(table2);									// ���̺� ���� �� �÷� ���� ����
						}
					}
					// �˻� ������ ���� �̸��� ��
					else if (choice2.getSelectedItem().equals("���� �̸�"))
					{
						list = t_dao.selectTeacherByName(findField2.getText());	// JTextField�� �Էµ� ������ �˻��Ѵ�.
						makeTable(dtm2, colName2, list);								// dtm2�� �˻������ �ִ´�.
						table2.updateUI();												// ���̺� UI�� ������Ʈ�Ѵ�.
						sortTable(table2);												// ���̺� ���� �� �÷� ���� ����
					}
				}				
			}
	         
	         // ���� ���� ���� ��
	         else if (tabVal == ManagerTop.LECTURE)
	         {
	        	 if (e.getSource() == registerButton)					// ��� ��ư
	        	 {
	        		new LectureInfo(LectureInfo.REGISTER, lecture);	// ���� ���â�� ����.
	        		list = lt_dao.selectLecture_table();						// ���â�� ���� �� ���̺��� �����Ѵ�.
					makeTable(dtm3, colName3, list);						// dtm3�� ���ŵ� ������ ��´�.
		        	table3.updateUI();										// ���̺� UI�� ������Ʈ �Ѵ�.
		        	sortTable(table3); 		 								// ���̺� ���� �� �÷� ���� ����
	        	 }
	        	 else if (e.getSource() == modifyButton)			// ���� ��ư
	        	 { 
	        		 if(table3.getSelectedRow() != -1)		// ���̺��� ���õ� ���� �ִٸ�
	        		 {
	        			// dtm3�� ����� ����(index) ���� �����´�.
	        			int index = table3.convertRowIndexToModel(table3.getSelectedRow());
	        			int l_num = (int) dtm3.getValueAt(index, 0);		// ������ ���� 0��° �÷� = ���ǹ�ȣ
	        			lecture = l_dao.createLecture(l_num);				// �ش� ���� ��ȣ�� Lecture �ν��Ͻ� ����
	        			new LectureInfo(LectureInfo.MODIFY, lecture);		// ���� ����â�� ����.
	        			list = lt_dao.selectLecture_table();						// ����â�� ���� �� ���̺��� �����Ѵ�.
						makeTable(dtm3, colName3, list);						// dtm3�� ���ŵ� ������ ��´�.
			        	table3.updateUI();										// ���̺� UI�� �����Ѵ�.
			        	sortTable(table3);										// ���̺� ���� �� �÷� ���� ����
	        		 }		              	   
	            }
	            else if (e.getSource() == deleteButton)				// ���� ��ư
	            {
	            	if(table3.getSelectedRow() != -1)		// ���̺��� ���õ� ���� �ִ� ���
	            	{
	            		// dtm3�� ���� ����� ����(index) ���� �����´�.
	            		int index = table3.convertRowIndexToModel(table3.getSelectedRow());
	            		int l_num = (int) dtm3.getValueAt(index, 0);	// ���õ� ���� 0��° �÷� ���� �����´� = ���ǹ�ȣ               
	            		new Dialog_Select("���� ����", "���Ǹ� �����Ͻðڽ��ϱ�?", Dialog_Select.LECTURE_DELETE, l_num);
	            		list = lt_dao.selectLecture_table();		// ���̾�α� â�� ���� �� ���̺��� �����Ѵ�.
						makeTable(dtm3, colName3, list);		// dtm3�� ���ŵ� ������ ��´�.
		        		table3.updateUI();						// ���̺� UI�� ������Ʈ�Ѵ�.
		        		sortTable(table3);	        			// ���̺� ���� �� �÷� ���� ����
	               }      	                 
	           }
	        	 // JTextField���� ���͸� ġ�ų� �˻���ư�� ������ ��
	           else if (e.getSource() == findField3 || e.getSource() == searchButton3)
	           {
					// �˻� ������ ���ǹ�ȣ�� ��
					if (choice3.getSelectedItem().equals("���� ��ȣ"))
					{
						int l_num = 0;			// ���ǹ�ȣ�� ������ ����
						
						if (findField3.getText().equals(""))			// JTextField�� �Էµ� ���� ���� ���
						{
							list = lt_dao.selectLecture_table();		// ��� ���� ������ �����´�.
							makeTable(dtm3, colName3, list);		// dtm3�� ������ ��´�.
			        		table3.updateUI();						// ���̺� UI�� ������Ʈ �Ѵ�.
			        		sortTable(table3);						// ���̺� ���� �� �÷� ���� ����
						}
						else	// JTextField�� �Էµ� ���� ���� ���
						{
							try		// try-catch ������ �Էµ� ���� �������� ���ڿ����� �����Ѵ�.
							{
								l_num = Integer.parseInt(findField3.getText());
							}
							catch (NumberFormatException ne)		// ���ڿ��� ��� Exception �߻�
							{
								new Dialog_Default("����", "���� ��ȣ�� ���ڷθ� �Է����ּ���.");
								return;
							}
							
							list = lt_dao.searchLecture_tableByL_num(l_num);		// �ش� ���� ��ȣ�� �˻��� ����� ArrayList�� �����´�.
							makeTable(dtm3, colName3, list);		// dtm3�� �˻������ ��´�.
			        		table3.updateUI();						// ���̺� UI�� ������Ʈ �Ѵ�.
			        		sortTable(table3);						// ���̺� ���� �� �÷� ���� ����
						}
					}
					// �˻� ������ ���Ǹ��� ���
					else if (choice3.getSelectedItem().equals("���� �̸�"))
					{
						// JTextField�� �Էµ� ������ �˻��� ����� ArrayList�� �����´�.
						list = lt_dao.searchLecture_tableByTitle(findField3.getText());
						makeTable(dtm3, colName3, list);		// dtm3�� �˻������ ��´�.
		        		table3.updateUI();						// ���̺��� UI�� ������Ʈ�Ѵ�.
		        		sortTable(table3);						// ���̺� ���� �� �÷� ���� ����
					}
				}
	        }
		}	         

		@Override
		public void mouseClicked(MouseEvent me)
		{
			if (me.getClickCount() == 2)		// ���콺 Ŭ�� Ƚ���� 2ȸ�� �� = ����Ŭ��
			{
				if (me.getSource() == table1)		// ������ ���̺��� ����Ŭ�� ���� ��
				{
					if (table1.getSelectedRow() != -1)		// ���̺� ���õ� ���� �ִٸ�
					{
						// dtm1�� ����� ���� ����(index)�� �����´�.
						int index = table1.convertRowIndexToModel(table1.getSelectedRow());
						int st_num = (int) dtm1.getValueAt(index, 0);	// ���õ� ���� 0��° �÷� �� = �л���ȣ
						student = s_dao.createStudent(st_num);			// �ش� �л���ȣ�� Student �ν��Ͻ��� �����Ѵ�.
						new MemberInfo(MemberInfo.MODIFY_A, student);	// ȸ������ ����â�� ����.
					}				
				}
				else if (me.getSource() == table2)		// ���� ���̺��� ����Ŭ�� ���� ��
				{
					if (table2.getSelectedRow() != -1) 	// ���̺� ���õ� ���� �ִٸ�
					{
						// dtm2�� ����� ���� ����(index)�� �����´�.
						int index = table2.convertRowIndexToModel(table2.getSelectedRow());
						int t_num = (int) dtm2.getValueAt(index, 0);	// ���õ� ���� 0��° �÷� �� = �����ȣ
						teacher = t_dao.createTeacher(t_num);			// �ش� �����ȣ�� Teacher �ν��Ͻ��� �����Ѵ�.
						new MemberInfo(MemberInfo.T_MODIFY, teacher);	// �������� ����â�� ����.
					}
				}
				else if (me.getSource() == table3)		// ���� ���̺��� ����Ŭ�� ���� ��
				{
					if (table3.getSelectedRow() != -1) 	// ���̺��� ���õ� ���� �ִٸ�
					{
						// dtm3�� ����� ���� ����(index)�� �����´�.
						int index = table3.convertRowIndexToModel(table3.getSelectedRow());
						int l_num = (int) dtm3.getValueAt(index, 0);	// ���õ� ���� 0��° �÷� �� = ���ǹ�ȣ
						new StudentReading(l_num, StudentReading.ADMIN);	// �ش� ���ǹ�ȣ�� ������ ��� â�� ����.
						list = lt_dao.selectLecture_table();		// ������ ��� â�� ���� �� ���̺���  �����Ѵ�.
						makeTable(dtm3, colName3, list);		// dtm3�� ���ŵ� ���� ��´�.
		        		table3.updateUI();						// ���̺� UI�� ������Ʈ�Ѵ�.
		        		sortTable(table3);						// ���̺� ���� �� �÷� ���� ����
					}
				}
			}
		}			
	}
}