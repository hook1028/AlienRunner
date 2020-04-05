package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import dao.table.*;

// �⼮�� Dialog Ŭ����
public class StudentReading extends JDialog 
{
	// ��� ����
	private JPanel studentRdPane;
	private JTable table;
	private JButton closeButton;
	private JButton cancelButton;
	private JScrollPane studentScrollPane;

	// ������, ���� ��带 ������ ���
	public static final int TEACHER = 1;
	public static final int ADMIN = 2;
	
	// JDBC ������ ���� Ŭ����
	private StudentDAO s_dao;

	// ���̺� ���� �� ���� Ŭ����
	private DefaultTableModel dtm;
	ArrayList<Vector> list;
	Vector<String> colName;
	
	// �̺�Ʈ �����ϴ� Ŭ����
	private Handler handler = new Handler();
	
	// ���� �޾ƿ� ����
	private int l_num;
	private int st_num;

	public StudentReading(int l_num, int mode)
	{
		super(Main.mainFrame);
		setSize(700, 600);
		Dialog_Default.init(this, "������ ���");
		this.l_num = l_num;		// ������ �Ķ���ͷ� ���� ��� ���� ��������� �����Ѵ�.

		s_dao = StudentDAO.getInstance();

		// �г� ����
		studentRdPane = new JPanel();
		setContentPane(studentRdPane);
		studentRdPane.setLayout(null);

		// �ݱ� ��ư
		closeButton = new JButton("�� ��");
		closeButton.setForeground(new Color(82, 55, 56));
		closeButton.setForeground(new Color(82, 55, 56));
		closeButton.setFont(new Font("���� ���", Font.BOLD, 18));
		studentRdPane.add(closeButton);

		// ȸ������ ���̺�
		colName = new Vector<String>();
		colName.add("�̸�");
		colName.add("����");
		colName.add("�޴��� ��ȣ");
		colName.add("E-Mail");

		list = s_dao.selectStudentByLNum(l_num);		// �Ķ���ͷ� ���� ���� ��ȣ�� �������� �˻��Ѵ�.

		// ���̺��� ������ �� ������ DefaultTableModel�� �����Ѵ�.
		dtm = new DefaultTableModel(colName, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		makeTable(list);

		table = new JTable(dtm);
		table.getTableHeader().setReorderingAllowed(false);				// ����� ������ �ٲ��� ���ϰ� �Ѵ�.
		table.getTableHeader().setForeground(new Color(82, 55, 56));
		table.setForeground(new Color(82, 55, 56));
		table.setRowSorter(new TableRowSorter(dtm));						// ����� ������ �� ������ �ǰ� �Ѵ�.
		table.setFont(new Font("���� ���", Font.PLAIN, 15));
		table.setRowHeight(30);													// ���̺� ���� ���̸� �����Ѵ�.
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		if (mode == TEACHER)	// ���� ����� ���
		{
			closeButton.setBounds(260, 480, 150, 60);
		}
		else	// �����ڸ���� ���
		{
			// ������� ��ư�� �����.
			cancelButton = new JButton("���� ���");
			cancelButton.setForeground(new Color(82, 55, 56));
			cancelButton.setFont(new Font("���� ���", Font.BOLD, 18));
			cancelButton.setBounds(180, 480, 150, 60);
			studentRdPane.add(cancelButton);
			cancelButton.addActionListener(handler);		
			closeButton.setBounds(350, 480, 150, 60);
		}
		
		sortTable();

		studentScrollPane = new JScrollPane(table);		// table�� ���� ��ũ������ �����Ѵ�.
		studentScrollPane.setBounds(55, 40, 580, 410);
		studentRdPane.add(studentScrollPane);

		closeButton.addActionListener(handler);	
		
		setVisible(true);
	}
	
	// dtm�� ���� �ִ� �޼ҵ�
	private void makeTable(ArrayList<Vector> list)
	{
		if(dtm.getRowCount()>0)
		{ 
			//DefaultTableModel�� Row�� �ʱ�ȭ ���� ���̺��� �ڷḦ ���� �����Ѵ�. (���ΰ�ħ�� ���� ȿ��)
			dtm.setDataVector(null, colName);
		}
        for (int i = 0; i < list.size(); i++)
        {
        	dtm.addRow(list.get(i));
        }
	}
	
	// ���̺� �÷� ���� ���� �� ���� �޼ҵ�
	private void sortTable()
	{
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(180);
		table.setRowHeight(30);
		
		DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();	// ���̺� �� ������(display)�� ���õ� Ŭ����
		dtcr1.setHorizontalAlignment(SwingConstants.CENTER);				// ���̺��� ���������� ����� �Ѵ�. 
		TableColumnModel tcm1 = table.getColumnModel();					// table�� ColumnModel�� �����´�.

		for (int i = 0; i < tcm1.getColumnCount(); i++)		// �÷� ������ŭ �÷��� ������ ������
		{
			if (i % 5 == 4)	// �̸����� ��� ��� ������ ���� �ʴ´�.
				continue;
			else
				tcm1.getColumn(i).setCellRenderer(dtcr1);
		}
	}
	
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == closeButton)		// �ݱ� ��ư�� ������ ��
			{
				StudentReading.this.dispose();	// ���� ���̾�α�â�� ������.
			}
			
			else if (e.getSource() == cancelButton)	// ������� ��ư�� ������ ��
			{
				// ���� dtm�� ����� ����(index)�� �����´�.
				int index = table.convertRowIndexToModel(table.getSelectedRow());
				
				String name = (String) table.getValueAt(index, 0);		// ���õ� ���� 0��° �� = �̸�
				String phone = (String) table.getValueAt(index, 2);	// ���õ� ���� 2��° �� = ��ȭ��ȣ
				st_num = s_dao.findSt_num(name, phone);				// �̸��� ��ȭ��ȣ�� �л� ��ȣ�� ã�´�.
				
				new Dialog_Select("������û ���", "������û�� ����Ͻðڽ��ϱ�?", l_num, st_num);
				// ���̾�α� â�� ���� �� ���̺��� ���ΰ�ħ�Ѵ�.
				list = s_dao.selectStudentByLNum(l_num);
				makeTable(list);
				table.updateUI();
				sortTable();
			}			
		}		
	}
}