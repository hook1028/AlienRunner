package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import dao.table.*;

// ������û �г� Ŭ����
public class AddLesson extends JPanel
{
	// UI ����
	private JTextField findField;
	private JScrollPane lessonDataScroll;
	private JTable table;
	private JLabel findLabel;
	private JLabel lessonDataText;
	private JButton applyButton;
	private JButton backButton;
	private Choice lessonChoice;
	private JButton searchButton;
	
	// �̺�Ʈ �ڵ鷯 Ŭ����
	private Handler handler = new Handler();
	
	// ������ ������ �ʿ��� Ŭ����
	private Lecture_TableDAO lt_dao;
	private Student student;
	
	// ���̺� ����� Ŭ����
	private DefaultTableModel dtm;
	private Vector<String> colName;
	private ArrayList<Vector> list;
	
	// Choice�� TextField���� ���� ������ ����
	private String searchVal;
	private String choiceVal;
	
//	������	
	public AddLesson(Student student)
	{
		this.student = student;							// ������û�� �л��� ������ ���� Student ��ü�� �޴´�. 
		lt_dao = Lecture_TableDAO.getInstance();	// ������û ���̺��� ���� �� ���� Lecture_TableDAO �ν��Ͻ� ����
		list = lt_dao.selectLecture_table();				// Lecture_TableDAO���� ��� ���� ������ ArrayList�� �޾ƿ´�.
	    
		// ���̺� ����
		colName = new Vector<String>();		// ���̺� ����� ���� Vector
		colName.add("���ǹ�ȣ");
		colName.add("���Ǿ��");
		colName.add("���Ǹ�");
		colName.add("�����");
		colName.add("����");
		colName.add("���ǽð�");
		colName.add("���ǽ�");
		colName.add("���");
		colName.add("��û��Ȳ");
		
        // �͸���ø Ŭ������ ���̺� ���� ���θ� �����Ѵ�.
		// DefaultTableModel(Vector ColumnNames, int rowCount)
        dtm = new DefaultTableModel(colName, 0)
        	{ 
        		// ���̺��� ���� ���� ���θ� �˷��ִ� �޼ҵ�
        		@Override
        		public boolean isCellEditable(int row, int column)      
        		{
            	   return false;       // ������ �ȵǵ��� �Ѵ�.
        		}
        	};
        
        makeTable(list);		// ArrayList�� �޾� dtm�� ������ִ� �޼ҵ�
        
	    table = new JTable(dtm);		// dtm�� ���� ���̺��� �����Ѵ�.
	    table.getTableHeader().setForeground(new Color(82, 55, 56));	// ���̺� ����� ���ڻ� ����
        table.getTableHeader().setReorderingAllowed(false);		// ���̺��� ����� �������� ���ϰ� ����
        table.getTableHeader().setFont(new Font("���� ���", Font.BOLD, 15));		// ���̺� ����� ��Ʈ ����
        table.setFont(new Font("���� ���", Font.PLAIN, 15));	// ���̺��� ��Ʈ  ����
	    table.setForeground(new Color(82, 55, 56));				// ���̺��� ���ڻ� ����
        table.setRowSorter(new TableRowSorter(dtm));			// ���̺��� �����ϰ� ���ִ� RowSorter �߰�
                
        sortTable();		// ���̺� �÷��� ���� ���� �� �����ϴ� �޼ҵ�
        
//		��� ���� ���� ��ũ����
		lessonDataScroll = new JScrollPane(table);		// ���̺��� �־� �����Ѵ�.
		lessonDataScroll.setBounds(40, 170, 810, 400);
		add(lessonDataScroll);
		
//		���ǰ˻� ���̺�
		findLabel = new JLabel("�� �� �� ��");
		findLabel.setBounds(38, 55, 80, 30);
		findLabel.setForeground(new Color(82, 55, 56));
		findLabel.setFont(new Font("���� ���", Font.BOLD, 14));
		findLabel.setHorizontalAlignment(SwingConstants.CENTER);		// JLabel�� ���� ������ �����Ѵ�.
		add(findLabel);
		
//		��� ���� ���� ���̺�
		lessonDataText = new JLabel("��û ������ ��� ����");
		lessonDataText.setBounds(40, 130, 200, 40);
		lessonDataText.setForeground(new Color(82, 55, 56));
		lessonDataText.setFont(new Font("���� ���", Font.BOLD, 16));
		lessonDataText.setHorizontalAlignment(SwingConstants.CENTER);
		add(lessonDataText);

//		��û�ϱ� ��ư
		applyButton = new JButton("�� û �� ��");
		applyButton.setBounds(516, 40, 150, 60);
		applyButton.setForeground(new Color(82, 55, 56));
		applyButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(applyButton);
		
//		���ư��� ��ư
		backButton = new JButton("�� �� �� ��");
		backButton.setBounds(690, 40, 150, 60);
		backButton.setForeground(new Color(82, 55, 56));
		backButton.setFont(new Font("���� ���", Font.BOLD, 18));
		add(backButton);

//		���ǰ˻� �ʵ�
		findField = new JTextField();
		findField.setForeground(new Color(82, 55, 56));
		findField.setBounds(240, 58, 120, 30);
		findField.setColumns(10);
		add(findField);
		
//		���Ǹ�/����� ���̽�
		lessonChoice = new Choice();
		lessonChoice.setBounds(145, 59, 80, 26);
		lessonChoice.setBackground(new Color(255, 254, 220));
		lessonChoice.setForeground(new Color(82, 55, 56));
		lessonChoice.add("���Ǹ�");
		lessonChoice.add("�����");
		lessonChoice.setFont(new Font("���� ���", Font.BOLD, 16));
		add(lessonChoice);
		
		// �˻� ���� ��ư
		searchButton = new JButton("��  ��");
		searchButton.setBounds(378, 57, 85, 30);
		searchButton.setForeground(new Color(82, 55, 56));
		searchButton.setFont(new Font("���� ���",Font.BOLD, 16));
		add(searchButton);
		
		// �̺�Ʈ ���� �κ�
		applyButton.addActionListener(handler);
		backButton.addActionListener(handler);
		findField.addActionListener(handler);
		searchButton.addActionListener(handler);
		table.addMouseListener(handler);
		
		setVisible(true);
	}
	
	// ArrayList�� �޾� dtm�� �ڷḦ �־��ִ� �޼ҵ�
	private void makeTable(ArrayList<Vector> list)
	{
		if (dtm.getRowCount() > 0)		// dtm�� �ڷᰡ �ִ� ���
		{ 
			//DefaultTableModel�� ���̺� �ڷḦ ���� �����Ѵ�. (���ΰ�ħ�� ���� ȿ��)
			dtm.setDataVector(null, colName);
		}
		
        for (int i = 0; i < list.size(); i++)
        {
        	dtm.addRow(list.get(i));		// ArrayList�� �ִ� �ڷᰡ ��� Vector�� ���ʴ�� dtm�� �ִ´�.
        }
	}
	
	// ���̺� �÷� ���� �� ������ �����ִ� �޼ҵ�
	private void sortTable()
	{
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();	// ���̺� �� ���÷��̿� ���õ� Ŭ����
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);				// ��� ���ķ� �����Ѵ�.
		TableColumnModel tcm = table.getColumnModel();					// �ش� ���̺��� TableColumnModel�� �����´�.

		for (int i = 0; i < tcm.getColumnCount(); i++)		// �÷� ������ŭ �÷��� ������ ������
		{
			tcm.getColumn(i).setCellRenderer(dtcr);			// ���̺� �÷��𵨿��� ������ �÷��� ������ �����Ѵ�.
		}
		
		// �÷� ���� ����
        table.getColumnModel().getColumn(0).setPreferredWidth(50);		// ���� ��ȣ
        table.getColumnModel().getColumn(1).setPreferredWidth(50);		// ���� ���
        table.getColumnModel().getColumn(2).setPreferredWidth(170);		// ���Ǹ�
        table.getColumnModel().getColumn(3).setPreferredWidth(45);		// �����
        table.getColumnModel().getColumn(4).setPreferredWidth(50);		// ����
        table.getColumnModel().getColumn(5).setPreferredWidth(65);		// �ð�
        table.getColumnModel().getColumn(6).setPreferredWidth(50);		// ���ǽ�
        table.getColumnModel().getColumn(7).setPreferredWidth(50);		// ���
        table.getColumnModel().getColumn(8).setPreferredWidth(50);		// ��û��Ȳ
        table.setRowHeight(30);		// Row�� ���̸� �����Ѵ�.
	}
	
	// �̺�Ʈ ������ ������ Ŭ����
	class Handler extends MouseAdapter implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// ��û�ϱ� ��ư�� ������ ��
			if (e.getSource() == applyButton)
			{
				if(table.getSelectedColumn() != -1)	// ���̺� ������ ���� �ִٸ�
				{
					// ������û�� �ʿ��� ���� ���� ���´�.
					int index = table.convertRowIndexToModel(table.getSelectedRow());	// ���� �� ���õ� row�� dtm������ ������ �� ��° row���� �˷��ش�.
					int l_num = (int) dtm.getValueAt(index, 0);		// dtm���� index��, 0��° �࿡ �ִ� ���� �����´�.
					int all_people = 0;
					int reg_people = 0;
					
					String register = ((String)dtm.getValueAt(index, 8)).trim();		// ������ ���� ������ �����Ѵ�.
					StringTokenizer st = new StringTokenizer(register, "/");		// /�� �������� ��, �� ���ڸ� �ڸ���.
					while(st.hasMoreTokens())
					{
						reg_people = Integer.parseInt(st.nextToken());		// �� �κ��� ���ڸ� int������ ��ȯ
						all_people = Integer.parseInt(st.nextToken());		// �� �κ��� ���ڸ� int������ ��ȯ
					}
					
					if (reg_people == all_people)		// ���� ������ �ο��� ���� ��û�� �ο��� ���� ���
					{
						new Dialog_Default("��û ����", "���� �ο��� ��� á���ϴ�.");
					}
					else
					{
						String title = (String) dtm.getValueAt(index, 2);		// ���̺��� ���Ǹ��� ������
						
						// ������û ���̾�α�â�� ����.
						new Dialog_Select(title, l_num, student.getSt_num());
						// ������û ���̾�α� â�� ���� ��, ������û ���̺��� ���ΰ�ħ�Ѵ�.
						list = lt_dao.selectLecture_table();		// ��� ���������� ArrayList�� �������� �޼ҵ�
						makeTable(list);							// ArrayList�� ��� ���� ���� table�� �����.
						table.updateUI();							// table�� UI�� �����Ѵ�.
						sortTable();								// table�� �÷� ������ �����ϰ�, �����Ѵ�.
					}
				}
			}
			
			// ���ư��� ��ư�� ������ ��
			else if (e.getSource() == backButton)
			{
				Main.mainFrame.changePanel(new Student_Teacher_Top(student));		// �л� ����ȭ������ ���ư���.
			}
			
			// �˻� ��ư�� �����ų� �˻� TextField���� ���͸� ������ ��
			else if (e.getSource() == searchButton || e.getSource() == findField)
			{
				// �˻����ǿ� ���� �˻��ϰ� ���̺��� ���ŵ�
				choiceVal = lessonChoice.getSelectedItem();		// ���̽����� ���� ������
				searchVal = findField.getText();						// �˻��� ���� ������
				
				if (choiceVal.equals("���Ǹ�"))
				{
					list = lt_dao.searchLecture_tableByTitle(searchVal);		// ���Ǹ����� �˻��� ����� �������� �޼ҵ�
					// �˻������ ���� dtm�� ���ο� ���� �ִ´�.
					makeTable(list);			// ArrayList�� ��� ���� ���� table�� �����.
					table.updateUI();			// table�� UI�� �����Ѵ�.
					sortTable();				// table�� �÷� ������ �����ϰ�, �����Ѵ�.
				}
				else if (choiceVal.equals("�����"))
				{
					list = lt_dao.searchLecture_tableByTeacher(searchVal);	// ��������� �˻��� ����� �������� �޼ҵ�
					// �˻������ ���� dtm�� ���ο� ���� �ִ´�.
					makeTable(list);			// ArrayList�� ��� ���� ���� table�� �����.
					table.updateUI();			// table�� UI�� �����Ѵ�.
					sortTable();				// table�� �÷� ������ �����ϰ�, �����Ѵ�.
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			if (e.getClickCount() == 2)		// Ŭ��Ƚ���� 2���̶�� = ����Ŭ�� �ߴٸ�
			{
				if(table.getSelectedColumn() != -1)	// ���̺� ������ ���� �ִٸ�
				{
					// ������û�� �ʿ��� ���� ���� ���´�.
					int index = table.convertRowIndexToModel(table.getSelectedRow());	// ���� �� ���õ� row�� ���� dtm������ �� ��° row���� �˷��ش�.
					int l_num = (int) dtm.getValueAt(index, 0);		// dtm���� index ��° ��, 0��° Į���� �ִ� ���� �����´�.
					int all_people = 0;		// �� �ο� ���� ���� ����
					int reg_people = 0;		// ��û�� �ο� �� ���� ���� ����
					
					String register = ((String)dtm.getValueAt(index, 8)).trim();		// ��û��Ȳ Į���� ���� ������ ������ �����Ѵ�.
					StringTokenizer st = new StringTokenizer(register, "/");		// "/" �� ���� ���ڸ� ������.
					while(st.hasMoreTokens())		// ��ū�� �ִٸ�
					{
						reg_people = Integer.parseInt(st.nextToken());		// ù ��° ��ū�� ��û�� �ο�
						all_people = Integer.parseInt(st.nextToken());		// �� ��° ��ū�� �� �ο�
					}
					
					if (reg_people == all_people)		// ���� �� �ο��� ������û�� �ο��� ���ٸ� = �ο��� ��� á�ٸ�
					{
						new Dialog_Default("��û ����", "���� �ο��� ��� á���ϴ�.");
					}
					else
					{
						String title = (String) dtm.getValueAt(index, 2);		// 2��° �÷� ��, ���Ǹ� �÷��� �ִ� ���� �����´�.
						
						// ������û ���̾�α�â�� ����.
						new Dialog_Select(title, l_num, student.getSt_num());
						// ������û ���̾�α� â�� ���� �� ��û������ ���� ���̺��� ���ΰ�ħ �Ѵ�.
						list = lt_dao.selectLecture_table();		// ��� ���� ������ �����´�.
						makeTable(list);							// ArrayList�� �޾� table�� �����.
						table.updateUI();							// table�� UI�� ������Ʈ �Ѵ�.
						sortTable();								// table�� ������ �����ϰ�, �����Ѵ�.
					}
				}
			}
		}
	}
}