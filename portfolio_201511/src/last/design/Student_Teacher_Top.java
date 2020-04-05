package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import dao.table.*;

// 선생님과 학생 메인을 만드는 클래스
public class Student_Teacher_Top extends JPanel
{	
	// UI 변수
	private JLabel infoDataText;	// 테이블 타이틀을 표시하는 라벨
	private JLabel helloLabel;		// 환영 라벨
	private JLabel cancelLabel;	// 수강신청 취소 안내 라벨
	private JButton modifyButton;		// 수강신청 혹은 출석부 보는 버튼
	private JButton lessonButton;		// 회원 정보 수정 버튼
	private JButton logoutButton;		// 로그아웃 버튼
	private JLabel login_name;		// 로그인 시 회원의 이름을 나타낼 라벨
	private JTable table;
	
	// 회원정보를 가져오는 클래스
	private Student student;
	private Teacher teacher;
	private Lecture_TableDAO lt_dao;
	
	// 테이블 만드는 클래스
	private DefaultTableModel dtm;
	private ArrayList<Vector> list;
	private Vector<String> colName;
	
	// 이벤트 핸들러
	private Handler handler = new Handler();

	// 생성자
	public Student_Teacher_Top(Object who)
	{							
		if(who instanceof Student)		// 학생이 로그인 한 경우
		{
			student = (Student)who;
			infoDataText = new JLabel("내 수강정보");
			lessonButton = new JButton("수 강 신 청");
			modifyButton = new JButton("내 정보 수정");
			login_name = new JLabel(student.getName());
			
//			수강취소 안내 라벨	
			cancelLabel = new JLabel("수강신청 취소는 관리자에게 문의");
			cancelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			cancelLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			cancelLabel.setForeground(new Color(82, 55, 56));
			cancelLabel.setBounds(610, 130, 230, 40);
			add(cancelLabel);
		}
		else	// 강사가 로그인 한 경우
		{
			teacher = (Teacher)who;
			infoDataText = new JLabel("내 강의정보");
			lessonButton = new JButton("수강생 명단");	
			modifyButton = new JButton("내 정보 수정");
			login_name = new JLabel(teacher.getName());
		}
		
		lt_dao = Lecture_TableDAO.getInstance();
				
		makeTable(who);		// 테이블 만드는 메소드
		
//		내 수강 정보 스크롤판
		JScrollPane LessonDataScroll = new JScrollPane(table);
		LessonDataScroll.setBounds(40, 170, 810, 400);
		add(LessonDataScroll);
		
//		내 수강 정보 텍스트
		infoDataText.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		infoDataText.setForeground(new Color(82, 55, 56));
		infoDataText.setHorizontalAlignment(SwingConstants.CENTER);
		infoDataText.setBounds(40, 130, 120, 40);
		add(infoDataText);
		
//		회원정보수정 버튼
		modifyButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		modifyButton.setBounds(690, 40, 150, 60);
		modifyButton.setForeground(new Color(82, 55, 56));
		add(modifyButton);
		
//		수강신청 버튼
		lessonButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lessonButton.setBounds(516, 40, 150, 60);
		lessonButton.setForeground(new Color(82, 55, 56));
		add(lessonButton);

//		사용자 환영문구 텍스트
		helloLabel = new JLabel("님 환영합니다");
		helloLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		helloLabel.setForeground(new Color(82, 55, 56));
		helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		helloLabel.setBounds(140, 45, 140, 30);
		add(helloLabel);
		
//		로그아웃 버튼
		logoutButton = new JButton("로그아웃");
		logoutButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		logoutButton.setForeground(new Color(82, 55, 56));
		logoutButton.setBounds(270, 50, 75, 25);
		add(logoutButton);

//		로그인한 회원의 이름
		login_name.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		login_name.setHorizontalAlignment(SwingConstants.CENTER);
		login_name.setForeground(new Color(82, 55, 56));
		login_name.setBounds(50, 45, 140, 30);
		add(login_name);
		
		// 이벤트 구현하는 부분
		lessonButton.addActionListener(handler);
		modifyButton.addActionListener(handler);
		logoutButton.addActionListener(handler);
		table.addMouseListener(handler);
		
		setVisible(true);
	}
	
	// 강사인지 수강생인지 확인한 뒤 그에 맞춰 테이블을 만들어주는 메소드
	void makeTable(Object who)
	{
		// 학생인 경우
		if (who instanceof Student)
		{
			student = (Student)who;				// Object로 들어온 Student 인스턴스를 다운캐스팅한다.
			
			colName = new Vector<String>();
			colName.add("강의번호");
			colName.add("강의언어");
			colName.add("강의명");
			colName.add("강사명");
			colName.add("요일");
			colName.add("강의시간");
			colName.add("강의실");
			colName.add("비용");
			colName.add("신청현황");
			
			// 해당 학생이 수강신청한 강의를 ArrayList로 담아온다.
			list = lt_dao.searchLecture_tableBySt_Num(student.getSt_num());
			
			// dtm의 셀을 수정하지 못하도록 한다.
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
			table.getTableHeader().setReorderingAllowed(false);				// 테이블의 헤더 순서를 바꾸지 못하게 한다.
			table.getTableHeader().setForeground(new Color(82, 55, 56));
			table.setRowSorter(new TableRowSorter(dtm));						// 테이블 헤더를 눌렀을 때 정렬이 되게 한다.
			table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			table.setForeground(new Color(82, 55, 56));
			sortTable();	// 테이블 정렬 및 컬럼 간격 지정
		}
		
		// 강사인 경우
		else if (who instanceof Teacher)
		{
			teacher = (Teacher)who;				// Object로 들어온 Teacher 인스턴스를 다운캐스팅한다.
			
			colName = new Vector<String>();
			colName.add("강의번호");
			colName.add("강의언어");
			colName.add("강의명");
			colName.add("강사명");
			colName.add("요일");
			colName.add("강의시간");
			colName.add("강의실");
			colName.add("비용");
			colName.add("신청현황");
			
			// 해당 강사가 강의하는 강의들을 불러온다.
			list = lt_dao.searchLecture_tableByT_num(teacher.getT_num());
			
			// dtm의 셀들을 수정하지 못하게 한다.
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
			table.getTableHeader().setReorderingAllowed(false);				// 테이블 헤더의 순서를 바꾸지 못하게 한다.
			table.getTableHeader().setForeground(new Color(82, 55, 56));
			table.setRowSorter(new TableRowSorter(dtm));						// 테이블 헤더를 눌렀을 시 정렬이 되게 한다.
			table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			table.setForeground(new Color(82, 55, 56));
			sortTable();		// 테이블 정렬 및 컬럼 간격 지정
		}
	}
	
	// 테이블 정렬 및 간격을 지정해주는 메소드
	private void sortTable()
	{
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();	// 테이블 셀의 display를 설정하는 클래스
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);				// 테이블의 수평정렬을 가운데로 한다.
		TableColumnModel tcm = table.getColumnModel();					// 테이블의 컬럼모델을 가져온다.

		for (int i = 0; i < tcm.getColumnCount(); i++)		// 컬럼 개수만큼 컬럼을 가져와 정렬함
		{
			tcm.getColumn(i).setCellRenderer(dtcr);			// 컬럼모델에서 컬럼을 가져와 가운데 정렬로 설정한다.
		}
		
		table.setRowHeight(30);			// 테이블 열의 높이를 정한다.
        table.getColumnModel().getColumn(0).setPreferredWidth(50);		// 강의 번호
        table.getColumnModel().getColumn(1).setPreferredWidth(50);		// 강의 언어
        table.getColumnModel().getColumn(2).setPreferredWidth(170);		// 강의명
        table.getColumnModel().getColumn(3).setPreferredWidth(45);		// 강사명
        table.getColumnModel().getColumn(4).setPreferredWidth(50);		// 요일
        table.getColumnModel().getColumn(5).setPreferredWidth(65);		// 시간
        table.getColumnModel().getColumn(6).setPreferredWidth(50);		// 강의실
        table.getColumnModel().getColumn(7).setPreferredWidth(50);		// 비용
        table.getColumnModel().getColumn(8).setPreferredWidth(50);		// 신청현황
	}
	
	// 이벤트를 담당하는 내부 클래스
	class Handler extends MouseAdapter implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// 학생 모드인 경우
			if (student != null)
			{
				
				if (e.getSource() == lessonButton)				// 수강 신청 버튼을 눌렀을 때
				{
					OverrallFrame.changePanel(new AddLesson(student));
				}
				else if (e.getSource() == modifyButton)			// 정보 수정 버튼을 눌렀을 때
				{
					new MemberInfo(MemberInfo.MODIFY, student);
				}
				else if (e.getSource() == logoutButton)			// 로그아웃 버튼을 눌렀을 때
				{
					Main.mainFrame.dispose();
					new Login();
				}
			}
			
			// 강사 모드인 경우
			else
			{
				if (e.getSource() == lessonButton)				// 수강자 명단 버튼을 눌렀을 때
				{
					if (table.getSelectedColumn() != -1)
					{
						// 실제 dtm에 저장된 순서(index)를 가져온다.
						int index = table.convertRowIndexToModel(table.getSelectedRow());
		                int l_num = (int) dtm.getValueAt(index, 0);	  // 선택된 열의 0번째 컬럼 = 강의 번호       
						new StudentReading(l_num, StudentReading.TEACHER);		// 해당 강의의 수강생 명단을 띄운다.
					}
				}
				else if (e.getSource() == modifyButton)			// 정보 수정 버튼을 눌렀을 때
				{
					new MemberInfo(MemberInfo.T_MODIFY, teacher);// 강사 정보 수정 창을 띄운다.
				}
				else if (e.getSource() == logoutButton)			// 로그아웃 버튼을 눌렀을 때
				{
					Main.mainFrame.dispose();
					new Login();
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent me) 
		{
			if (me.getClickCount() == 2 && teacher != null)	// 강사 모드에서 마우스 더블클릭을 했을 시
			{
				if (table.getSelectedColumn() != -1)				// 테이블에 선택된 값이 있다면
				{
					// 실제 dtm에 저장된 순서(index)를 가져온다.
					int index = table.convertRowIndexToModel(table.getSelectedRow());
	                int l_num = (int) dtm.getValueAt(index, 0);	  // 선택된 열의 0번째 컬럼 = 강의 번호       
					new StudentReading(l_num, StudentReading.TEACHER);		// 해당 강의의 수강생 명단을 띄운다.
				}
			}
		}								
	}
}