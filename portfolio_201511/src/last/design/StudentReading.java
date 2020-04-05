package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import dao.table.*;

// 출석부 Dialog 클래스
public class StudentReading extends JDialog 
{
	// 멤버 변수
	private JPanel studentRdPane;
	private JTable table;
	private JButton closeButton;
	private JButton cancelButton;
	private JScrollPane studentScrollPane;

	// 관리자, 강사 모드를 구분할 상수
	public static final int TEACHER = 1;
	public static final int ADMIN = 2;
	
	// JDBC 연동에 쓰일 클래스
	private StudentDAO s_dao;

	// 테이블 만들 때 쓰일 클래스
	private DefaultTableModel dtm;
	ArrayList<Vector> list;
	Vector<String> colName;
	
	// 이벤트 구현하는 클래스
	private Handler handler = new Handler();
	
	// 값을 받아올 변수
	private int l_num;
	private int st_num;

	public StudentReading(int l_num, int mode)
	{
		super(Main.mainFrame);
		setSize(700, 600);
		Dialog_Default.init(this, "수강생 명단");
		this.l_num = l_num;		// 생성자 파라미터로 받은 모드 값을 멤버변수에 저장한다.

		s_dao = StudentDAO.getInstance();

		// 패널 설정
		studentRdPane = new JPanel();
		setContentPane(studentRdPane);
		studentRdPane.setLayout(null);

		// 닫기 버튼
		closeButton = new JButton("닫 기");
		closeButton.setForeground(new Color(82, 55, 56));
		closeButton.setForeground(new Color(82, 55, 56));
		closeButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		studentRdPane.add(closeButton);

		// 회원정보 테이블
		colName = new Vector<String>();
		colName.add("이름");
		colName.add("성별");
		colName.add("휴대폰 번호");
		colName.add("E-Mail");

		list = s_dao.selectStudentByLNum(l_num);		// 파라미터로 받은 강의 번호로 수강생을 검색한다.

		// 테이블을 수정할 수 없도록 DefaultTableModel을 생성한다.
		dtm = new DefaultTableModel(colName, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		makeTable(list);

		table = new JTable(dtm);
		table.getTableHeader().setReorderingAllowed(false);				// 헤더의 순서를 바꾸지 못하게 한다.
		table.getTableHeader().setForeground(new Color(82, 55, 56));
		table.setForeground(new Color(82, 55, 56));
		table.setRowSorter(new TableRowSorter(dtm));						// 헤더를 눌렀을 때 정렬이 되게 한다.
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.setRowHeight(30);													// 테이블 열의 높이를 지정한다.
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		if (mode == TEACHER)	// 강사 모드인 경우
		{
			closeButton.setBounds(260, 480, 150, 60);
		}
		else	// 관리자모드인 경우
		{
			// 수강취소 버튼을 만든다.
			cancelButton = new JButton("수강 취소");
			cancelButton.setForeground(new Color(82, 55, 56));
			cancelButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			cancelButton.setBounds(180, 480, 150, 60);
			studentRdPane.add(cancelButton);
			cancelButton.addActionListener(handler);		
			closeButton.setBounds(350, 480, 150, 60);
		}
		
		sortTable();

		studentScrollPane = new JScrollPane(table);		// table을 넣은 스크롤판을 생성한다.
		studentScrollPane.setBounds(55, 40, 580, 410);
		studentRdPane.add(studentScrollPane);

		closeButton.addActionListener(handler);	
		
		setVisible(true);
	}
	
	// dtm에 값을 넣는 메소드
	private void makeTable(ArrayList<Vector> list)
	{
		if(dtm.getRowCount()>0)
		{ 
			//DefaultTableModel의 Row를 초기화 시켜 테이블의 자료를 전부 삭제한다. (새로고침과 같은 효과)
			dtm.setDataVector(null, colName);
		}
        for (int i = 0; i < list.size(); i++)
        {
        	dtm.addRow(list.get(i));
        }
	}
	
	// 테이블 컬럼 간격 조정 및 정렬 메소드
	private void sortTable()
	{
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(180);
		table.setRowHeight(30);
		
		DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();	// 테이블 셀 렌더링(display)와 관련된 클래스
		dtcr1.setHorizontalAlignment(SwingConstants.CENTER);				// 테이블의 수평정렬을 가운데로 한다. 
		TableColumnModel tcm1 = table.getColumnModel();					// table의 ColumnModel을 가져온다.

		for (int i = 0; i < tcm1.getColumnCount(); i++)		// 컬럼 개수만큼 컬럼을 가져와 정렬함
		{
			if (i % 5 == 4)	// 이메일의 경우 가운데 정렬을 하지 않는다.
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
			if (e.getSource() == closeButton)		// 닫기 버튼을 눌렀을 때
			{
				StudentReading.this.dispose();	// 현재 다이얼로그창이 닫힌다.
			}
			
			else if (e.getSource() == cancelButton)	// 수강취소 버튼을 눌렀을 때
			{
				// 실제 dtm에 저장된 순서(index)를 가져온다.
				int index = table.convertRowIndexToModel(table.getSelectedRow());
				
				String name = (String) table.getValueAt(index, 0);		// 선택된 줄의 0번째 값 = 이름
				String phone = (String) table.getValueAt(index, 2);	// 선택된 줄의 2번째 값 = 전화번호
				st_num = s_dao.findSt_num(name, phone);				// 이름과 전화번호로 학생 번호를 찾는다.
				
				new Dialog_Select("수강신청 취소", "수강신청을 취소하시겠습니까?", l_num, st_num);
				// 다이얼로그 창이 닫힌 뒤 테이블을 새로고침한다.
				list = s_dao.selectStudentByLNum(l_num);
				makeTable(list);
				table.updateUI();
				sortTable();
			}			
		}		
	}
}