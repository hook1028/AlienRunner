package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import dao.table.*;

// 관리자 메인 화면 클래스
public class ManagerTop extends JPanel
{
	// 멤버 변수
	private JLabel hellolabel;
	private JLabel login_name;		// 로그인 시 회원의 이름을 나타낼 라벨
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
	private Choice choice1;					// 수강생 초이스
	private Choice choice2;				// 강사 초이스
	private Choice choice3;				// 강의 초이스
	private JTextField findField1;		// 수강생검색 텍스트
	private JTextField findField2;		// 강사검색 텍스트
	private JTextField findField3;		// 강의검색 텍스트
	private JButton searchButton1;		// 수강생 검색버튼
	private JButton searchButton2;		// 강사 검색버튼
	private JButton searchButton3;		// 강의 검색버튼
	
	//테이블 만드는 클래스
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
	
	// 이벤트 구현할 클래스
	private Handler handler = new Handler();
		
	// JDBC 연동을 위한 클래스
	private Admin admin;
	private Teacher teacher;
	private Student student;
	private Lecture lecture;
	private StudentDAO s_dao;
	private TeacherDAO t_dao;
	private LectureDAO l_dao;
	private Lecture_TableDAO lt_dao;
		
	// 탭팬이 어떤 관리 모드인지 구분하기 위한 상수
	final static int STUDENT = 0;
	final static int TEACHER = 1;
	final static int LECTURE = 2;
	
	// 값을 받아올 변수들
	private int tabVal = 0;
	
	// 생성자
	public ManagerTop(Admin admin)
	{	
		this.admin = admin;				// 파라미터로 받은 Admin 객체를 멤버변수에 저장
		
		// DB 연동 클래스 초기화
		s_dao=StudentDAO.getInstance();
		t_dao=TeacherDAO.getInstance();
		l_dao=LectureDAO.getInstance();
		lt_dao=Lecture_TableDAO.getInstance();
		
//		사용자 환영문구 레이블. 관리자 모드이기 때문에 환영합니다 대신 모드입니다. 표시
		hellolabel = new JLabel("모드입니다.");
		hellolabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		hellolabel.setForeground(new Color(82, 55, 56));
		hellolabel.setHorizontalAlignment(SwingConstants.CENTER);
		hellolabel.setBounds(140, 50, 140, 30);
		add(hellolabel);
		
//		관리자는 이름이 없기 때문에 관리자 표시		
		login_name = new JLabel("관리자");
		login_name.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		login_name.setForeground(new Color(82, 55, 56));
		login_name.setHorizontalAlignment(SwingConstants.CENTER);
		login_name.setBounds(70, 50, 140, 30);
		add(login_name);
		
//		등록 버튼
		registerButton = new JButton("등 록");
		registerButton.setBounds(432, 40, 130, 60);
		registerButton.setForeground(new Color(82, 55, 56));
		registerButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(registerButton);
		
//		수정 버튼
		modifyButton = new JButton("수 정");
		modifyButton.setBounds(576, 40, 130, 60);
		modifyButton.setForeground(new Color(82, 55, 56));
		modifyButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(modifyButton);

//		삭제 버튼
		deleteButton = new JButton("삭 제");
		deleteButton.setBounds(720, 40, 130, 60);
		deleteButton.setForeground(new Color(82, 55, 56));
		deleteButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(deleteButton);
		
//		로그아웃 버튼
		logoutButton = new JButton("로그아웃");
		logoutButton.setForeground(new Color(82, 55, 56));
		logoutButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		logoutButton.setBounds(270, 50, 80, 30);
		add(logoutButton);

		// 전체 관리 탭
		managerTab = new JTabbedPane(JTabbedPane.TOP);
		managerTab.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		managerTab.setForeground(new Color(82, 55, 56));
		managerTab.setBounds(40, 130, 810, 440);
		add(managerTab);
		
//		수강생 패널/스크롤판		
		colName1 = new Vector<String>();
		colName1.add("수강생 번호");
		colName1.add("이름");
		colName1.add("성별");
		colName1.add("전화번호");
		colName1.add("이메일");

		list = s_dao.selectStudent();		// 모든 학생 정보를 ArrayList에 담아온다.
		// dtm을 편집되지 못하게 만들기 위해 익명 상속 클래스로 만듦.
		dtm1 = new DefaultTableModel(colName1, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// dtm1에 내용을 넣는다.
		makeTable(dtm1, colName1, list);

		table1 = new JTable(dtm1);											// 내용을 넣은 dtm을 통해 테이블 생성
		table1.getTableHeader().setReorderingAllowed(false);				// 테이블 헤더 순서를 바꾸지 못하게 한다.
		table1.getTableHeader().setForeground(new Color(82, 55, 56));
		table1.setRowSorter(new TableRowSorter(dtm1));					// 테이블 헤더를 눌렀을 때 정렬을 가능하게 한다.
		table1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table1.setForeground(new Color(82, 55, 56));
		
		sortTable(table1);		// 테이블 정렬 및 컬럼 간격을 지정해주는 메소드
		
		scrollPane1 = new JScrollPane(table1);		// 테이블을 스크롤판에 넣는다.
		
		panel1 = new JPanel();
		panel1.setLayout(null);
		scrollPane1.setBounds(30, 80, 735, 300);
		panel1.add(scrollPane1);
		managerTab.addTab("수강생 관리", panel1);
		
//		수강생 패널/초이스
		choice1 = new Choice();
		choice1.setBounds(440, 25, 110, 30);
		choice1.setBackground(new Color(255, 254, 220));
		choice1.setForeground(new Color(82, 55, 56));
		choice1.add("수강생 번호");
		choice1.add("수강생 이름");
		choice1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel1.add(choice1);
		
//		수강생 패널/검색레이블
		findField1 = new JTextField();
		findField1.setBounds(560, 25, 130, 27);
		findField1.setForeground(new Color(82, 55, 56));
		findField1.setColumns(10);
		panel1.add(findField1);
		
//		수강생 패널/검색버튼
		searchButton1 = new JButton("검색");
		searchButton1.setBounds(698, 25, 65, 27);
		searchButton1.setForeground(new Color(82, 55, 56));
		searchButton1.setFont(new Font("맑은 고딕",Font.BOLD, 16));
		panel1.add(searchButton1);
		
//		강사 패널/스크롤판		
		colName2 = new Vector<String>();
		colName2.add("강사번호");
		colName2.add("이름");
		colName2.add("성별");
		colName2.add("전화번호");
		colName2.add("이메일");
		
		list = t_dao.selectTeacher();		// 모든 강사 정보를 ArrayList에 담아온다.
		dtm2 = new DefaultTableModel(colName2,0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		makeTable(dtm2, colName2, list);		// dtm2에 내용을 넣는다.
		
		table2 = new JTable(dtm2);												// dtm2를 넣어 테이블을 만든다.
		table2.getTableHeader().setReorderingAllowed(false);					// 테이블 헤더 순서를 바꾸지 못하게 한다.
		table2.getTableHeader().setForeground(new Color(82, 55, 56));
		table2.setRowSorter(new TableRowSorter(dtm2));						// 테이블 헤더를 눌렀을 때 정렬이 되도록 한다.
		table2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table2.setForeground(new Color(82, 55, 56));
		
		sortTable(table2);			// 테이블 정렬 및 컬럼 간격을 지정하는 메소드
								
		scrollPane2 = new JScrollPane(table2);		// 테이블을 스크롤판에 넣는다.
		panel2 = new JPanel();
		panel2.setLayout(null);
		scrollPane2.setBounds(30, 80, 735, 300);
		panel2.add(scrollPane2);
		managerTab.addTab("강사 관리", panel2);
		
//		강사 패널/초이스
		choice2 = new Choice();
		choice2.setBounds(440, 25, 110, 30);
		choice2.setBackground(new Color(255, 254, 220));
		choice2.setForeground(new Color(82, 55, 56));
		choice2.add("강사 번호");
		choice2.add("강사 이름");
		choice2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel2.add(choice2);
		
//		강사 패널/검색레이블
		findField2 = new JTextField();
		findField2.setForeground(new Color(82, 55, 56));
		findField2.setBounds(560, 25, 130, 27);
		findField2.setColumns(10);
		panel2.add(findField2);
		
//		강사 패널/검색버튼
		searchButton2 = new JButton("검색");
		searchButton2.setBounds(698, 25, 65, 27);
		searchButton2.setForeground(new Color(82, 55, 56));
		searchButton2.setFont(new Font("맑은 고딕",Font.BOLD, 16));
		panel2.add(searchButton2);
		
//		강의 패널/스크롤판	
		colName3 = new Vector<String>();
		colName3.add("강의번호");
		colName3.add("강의언어");
		colName3.add("강의명");
		colName3.add("강사명");
		colName3.add("요일");
		colName3.add("강의시간");
		colName3.add("강의실");
		colName3.add("비용");
		colName3.add("신청현황");
		
		list = lt_dao.selectLecture_table();			// 모든 강의 정보를 ArrayList에 담는다.
		
		dtm3 = new DefaultTableModel(colName3,0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		makeTable(dtm3, colName3, list);			// dtm3에 내용을 넣는다.
		
		table3 = new JTable(dtm3);												// dtm3을 넣어 테이블을 만든다.
		table3.getTableHeader().setReorderingAllowed(false);					// 테이블 헤더 순서를 바꾸지 못하게 한다.
		table3.getTableHeader().setForeground(new Color(82, 55, 56));
		table3.setForeground(new Color(82, 55, 56));
		table3.setRowSorter(new TableRowSorter(dtm3));						// 테이블 컬럼을 눌렀을 때 정렬이 되게 한다. 
		table3.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		sortTable(table3);		// 테이블 정렬 및 칼럼 간격을 지정해주는 메소드
		
		scrollPane3 = new JScrollPane(table3);		// 테이블을 스크롤판에 넣는다.
				
		panel3 = new JPanel();
		panel3.setLayout(null);
		scrollPane3.setBounds(30, 80, 735, 300);
		panel3.add(scrollPane3);
		managerTab.addTab("강의 관리", panel3);
		
//		강의 패널/초이스
		choice3 = new Choice();
		choice3.setBounds(440, 25, 110, 30);
		choice3.setBackground(new Color(255, 254, 220));
		choice3.setForeground(new Color(82, 55, 56));
		choice3.add("강의 번호");
		choice3.add("강의 이름");
		choice3.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel3.add(choice3);
		
//		강의 패널/검색레이블
		findField3 = new JTextField();
		findField3.setBounds(560, 25, 130, 27);
		findField3.setForeground(new Color(82, 55, 56));
		findField3.setColumns(10);
		panel3.add(findField3);
		
//		강의 패널/검색버튼
		searchButton3 = new JButton("검색");
		searchButton3.setBounds(698, 25, 65, 27);
		searchButton3.setForeground(new Color(82, 55, 56));
		searchButton3.setFont(new Font("맑은 고딕",Font.BOLD, 16));
		panel3.add(searchButton3);
		
		// 이벤트를 구현하는 부분
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
		// 학생 테이블 모델 만드는 메소드
		private void makeTable(DefaultTableModel dtm, Vector<String> colName, ArrayList<Vector> list) 
		{
			if (dtm.getRowCount() > 0)
			{
				// DefaultTableModel의 Row를 초기화 시켜 테이블의 자료를 전부 삭제한다. (새로고침과 같은 효과)
				dtm.setDataVector(null, colName);
			}

			for (int i = 0; i < list.size(); i++)
			{
				dtm.addRow(list.get(i));
			}
		}
		   	
		// 테이블 정렬하는 메소드
		private void sortTable (JTable table)
		{
			table.setRowHeight(30);															// 테이블 row의 높이를 지정한다.
			DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();		// 테이블 셀 디스플레이 관련 클래스
			dtcr1.setHorizontalAlignment(SwingConstants.CENTER);					// 수평 정렬을 가운데 정렬로 설정한다.
			TableColumnModel tcm1 = table.getColumnModel();						// 테이블의 컬럼모델을 가져온다.
			
			for (int i = 0; i < tcm1.getColumnCount(); i++)		// 컬럼 개수만큼 컬럼을 가져와 정렬함
			{
				if (table != table3 && i % 5 == 4)
				{
					continue;
				}
				
				tcm1.getColumn(i).setCellRenderer(dtcr1);			// 테이블 컬럼모델에서 가져온 컬럼에 정렬을 적용한다.
			}

			// 탭에 있는 테이블별로 컬럼이 다르기 때문에 테이블을 구분해 컬럼 넓이를 다르게 설정한다.
			if (table == table1)
			{
				table1.getColumnModel().getColumn(0).setPreferredWidth(25);		// 수강생 번호
				table1.getColumnModel().getColumn(1).setPreferredWidth(50);		// 이름
				table1.getColumnModel().getColumn(2).setPreferredWidth(10);		// 성별
				table1.getColumnModel().getColumn(3).setPreferredWidth(100);		// 전화번호
				table1.getColumnModel().getColumn(4).setPreferredWidth(150);		// 이메일
			}
			else if (table == table2)
			{	      
			     table2.getColumnModel().getColumn(0).setPreferredWidth(25);		// 강사번호
			     table2.getColumnModel().getColumn(1).setPreferredWidth(50);		// 이름
			     table2.getColumnModel().getColumn(2).setPreferredWidth(20);		// 성별
			     table2.getColumnModel().getColumn(3).setPreferredWidth(55);		// 전화번호
			     table2.getColumnModel().getColumn(4).setPreferredWidth(120);	// 이메일
			}
			else if (table == table3)
			{
			     table3.getColumnModel().getColumn(0).setPreferredWidth(40);		// 강의 번호
			     table3.getColumnModel().getColumn(1).setPreferredWidth(50);		// 강의언어
			     table3.getColumnModel().getColumn(2).setPreferredWidth(130);	//강의명
			     table3.getColumnModel().getColumn(3).setPreferredWidth(55);		//강사명
			     table3.getColumnModel().getColumn(4).setPreferredWidth(60);		//요일
			     table3.getColumnModel().getColumn(5).setPreferredWidth(70);		//강의시간
			     table3.getColumnModel().getColumn(6).setPreferredWidth(45);		//강의실
			     table3.getColumnModel().getColumn(7).setPreferredWidth(55);		//비용
			     table3.getColumnModel().getColumn(8).setPreferredWidth(30);		//신청현황
			}			
		}
		
		// 이벤트 구현을 담당하는 내부 클래스
		private class Handler extends MouseAdapter implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// 현재 선택된 탭이 몇 번인지 받아온다.
				tabVal = managerTab.getSelectedIndex();
	         
				// 로그아웃 버튼을 눌렀을 시
				if (e.getSource() == logoutButton)
				{
					Main.mainFrame.dispose();		// 메인화면이 있는 프레임을 닫는다.
					new Login();						// 로그인 창을 연다.
				}
	         
				// 학생 관리 탭일 때
				if (tabVal == ManagerTop.STUDENT)
				{
					if (e.getSource() == registerButton)		// 등록버튼
					{
						new MemberInfo(MemberInfo.JOIN, null);		// 회원 가입창이 뜨게 한다.
						list = s_dao.selectStudent();						// 회원 가입창이 닫힌 뒤 학생 정보 테이블을 갱신한다.
						makeTable(dtm1, colName1, list);					// dtm1을 갱신
						table1.updateUI();									// 테이블의 UI를 업데이트 한다.
						sortTable(table1);									// 테이블 정렬 및 컬럼 간격 조정
					}
					else if (e.getSource() == modifyButton)		// 수정버튼
					{
						// 테이블에서 선택된 것이 있는 경우
						if (table1.getSelectedRow() != -1)
						{
							int index = table1.convertRowIndexToModel(table1.getSelectedRow()); // dtm1에 저장된 index 번호 반환
							int st_num = (int) dtm1.getValueAt(index, 0);			// index열의 0번째 자료 = 회원번호
							student = s_dao.createStudent(st_num);					// 회원번호로 수정할 학생의 객체를 생성한다.
							new MemberInfo(MemberInfo.MODIFY_A, student);	// 학생정보 수정창을 띄운다.
							list = s_dao.selectStudent();								// 수정창이 닫힌뒤 테이블을 갱신한다.
							makeTable(dtm1, colName1, list);							// dtm1의 내용을 새롭게 담는다.
							table1.updateUI();											// 테이블의 UI를 업데이트 한다.
							sortTable(table1);											// 테이블 정렬 및 컬럼 간격 조정
						}
					}
					else if (e.getSource() == deleteButton)		// 삭제버튼
					{
						// 테이블에서 선택된 것이 있는 경우 
						if(table1.getSelectedRow() != -1)
						{
							int index = table1.convertRowIndexToModel(table1.getSelectedRow());	// dtm1에 저장된 index 번호 반환
							int st_num = (int) dtm1.getValueAt(index, 0);	// index열의 0번째 자료 = 회원번호	                  
							new Dialog_Select("수강생 탈퇴", "정말 탈퇴하시겠습니까?", Dialog_Select.MEMBER_DELETE, st_num);
							list = s_dao.selectStudent();			// 다이얼로그 창이 닫힌 뒤 테이블을 갱신한다.
							makeTable(dtm1, colName1, list);		// dtm1의 내용을 새롭게 담는다.
							table1.updateUI();						// 테이블의 UI를 업데이트 한다.
							sortTable(table1);						// 테이블 정렬 및 컬럼 간격 조정
						}	               
					}
					// 검색 JTextField에서 엔터를 치거나 검색 버튼을 눌렀을 때
					else if (e.getSource() == findField1 || e.getSource() == searchButton1)
					{
						// 검색 조건이 수강생 번호일 경우
						if (choice1.getSelectedItem().equals("수강생 번호"))
						{
							int st_num = 0;			// 학생번호를 받아올 변수
							
							if (findField1.getText().equals(""))		// 검색 값에 아무것도 입력하지 않았을 경우
							{
								list = s_dao.selectStudent();			// 전체 정보를 가져온다.
								makeTable(dtm1, colName1, list);		// dtm1을 갱신
								table1.updateUI();						// 테이블 UI를 업데이트 한다.
								sortTable(table1);						// 테이블 정렬 및 컬럼 간격 조정
							}
							else	// JTextField에 입력된 값이 있을 경우
							{
								try		// try-catch문으로 입력된 값이 숫자인지 문자열인지 구분한다.
								{
									st_num = Integer.parseInt(findField1.getText());
								}
								catch (NumberFormatException ne)		// 문자열 입력 시 Exception 발생
								{
									new Dialog_Default("오류", "수강생 번호는 숫자로만 입력해주세요.");
									return;
								}
								
								list = s_dao.selectStudentBySt_num(st_num);		// 해당 학생번호로 검색한 결과 값을 ArrayList로 생성
								makeTable(dtm1, colName1, list);						// dtm1에 검색결과를 담는다.
								table1.updateUI();										// 테이블 UI를 업데이트한다.
								sortTable(table1);										// 테이블 정렬 및 컬럼 간격 조정
							}
						}
						
						// 검색 조건이 수강생 이름일 경우
						else if (choice1.getSelectedItem().equals("수강생 이름"))
						{
							list = s_dao.selectStudentByName(findField1.getText());	// JTextField에서 값을 얻어와 검색한다.
							makeTable(dtm1, colName1, list);								// dtm1에 해당 검색 결과를 담는다.
							table1.updateUI();												// 테이블 UI를 업데이트한다.
							sortTable(table1);												// 테이블 정렬 및 컬럼 간격 조정
						}
					}
				}
	         
			// 강사 관리 탭일 때
			else if (tabVal == ManagerTop.TEACHER)
			{
				if (e.getSource() == registerButton)	 		// 등록 버튼
				{
					new TeacherRegister();					// 강사 등록창을 띄운다.
					list = t_dao.selectTeacher();			// 등록창이 닫힌 뒤 테이블을 갱신하기 위해 ArrayList를 불러온다.
					makeTable(dtm2, colName2, list);		// dtm2에 내용을 새로 담는다.
					table2.updateUI();						// 테이블의 UI를 업데이트 한다.
					sortTable(table2);						// 테이블 정렬 및 컬럼 간격 조정
				}

				else if (e.getSource() == modifyButton) 	// 수정 버튼
				{
					if (table2.getSelectedRow() != -1)		// 테이블에서 선택된 열이 있다면
					{
						int index = table2.convertRowIndexToModel(table2.getSelectedRow());	// dtm2에 저장된 실제 index 값을 가져온다.
						int t_num = (int) dtm2.getValueAt(index, 0);	// 선택된 열의 0번째 컬럼 값을 가져온다 = 강사번호
						teacher = t_dao.createTeacher(t_num);			// 해당 강사번호로 Teacher 객체 생성
						new MemberInfo(MemberInfo.T_MODIFY, teacher);	// 강사 수정창을 띄운다.
						list = t_dao.selectTeacher();								// 수정창이 닫힌 뒤 테이블을 갱신한다.
						makeTable(dtm2, colName2, list);							// dtm2에 정보를 새롭게 담는다.
						table2.updateUI();											// 테이블의 UI를 업데이트한다.
						sortTable(table2);											// 테이블 정렬 및 컬럼 간격 지정
					}
				}
	            else if (e.getSource() == deleteButton)		// 삭제 버튼
	            {
	            	// 테이블에서 선택된 
	            	if(table2.getSelectedRow() != -1)
	            	{
	            		int index = table2.convertRowIndexToModel(table2.getSelectedRow());	// dtm2에 저장된 실제 index 값을 가져온다.
	            		int t_num = (int) dtm2.getValueAt(index, 0);		// 선택된 열의 0번째 컬럼 값을 가져온다 = 강사번호
	            		new Dialog_Select("강사 삭제", "강사를 삭제하시겠습니까?", Dialog_Select.TEACHER_DELETE, t_num);
						list = t_dao.selectTeacher();			// 다이얼로그 창이 닫힌 뒤 테이블을 갱신한다.
						makeTable(dtm2, colName2, list);		// dtm2에 정보를 새롭게 담는다.
						table2.updateUI();						// 테이블의 UI를 업데이트 한다.
						sortTable(table2);						// 테이블 정렬 및 컬럼 간격 지정
	            	}
	            }			
	            
				// JTextField에서 엔터를 치거나 검색버튼을 눌렀을 때
	            else if (e.getSource() == findField2 || e.getSource() == searchButton2)
				{
	            	// 검색 조건이 강사 번호인 경우
					if (choice2.getSelectedItem().equals("강사 번호"))
					{
						int t_num = 0;		// 강사 번호를 가져올 변수
						
						if (findField2.getText().equals(""))			// 입력 값이 없을 경우 전체 정보를 가져온다.
						{
							list = t_dao.selectTeacher();			// 모든 강사 정보를 가져온다.
							makeTable(dtm2, colName2, list);		// dtm2에 정보를 담는다.
							table2.updateUI();						// 테이블 UI를 업데이트 한다.
							sortTable(table2);						// 테이블 정렬 및 컬럼 간격 지정
						}
						else	// JTextField에 입력된 값이 있을 경우
						{
							try		// try-catch 문으로 입력한 값이 숫자인지 문자열인지 구분한다.
							{
								t_num = Integer.parseInt(findField2.getText());
							}
							catch (NumberFormatException ne)		// 문자열 입력 시 Exception 발생
							{
								new Dialog_Default("오류", "강사 번호는 숫자로만 입력해주세요.");
								return;
							}			
							
							list = t_dao.selectTeacherByT_num(t_num);		// 해당 강사번호로 검색한 결과를 ArrayList로 생성
							makeTable(dtm2, colName2, list);					// dtm2에 검색결과 값을 넣는다.
							table2.updateUI();									// 테이블 UI를 업데이트 한다.
							sortTable(table2);									// 테이블 정렬 및 컬럼 간격 지정
						}
					}
					// 검색 조건이 강사 이름일 시
					else if (choice2.getSelectedItem().equals("강사 이름"))
					{
						list = t_dao.selectTeacherByName(findField2.getText());	// JTextField에 입력된 값으로 검색한다.
						makeTable(dtm2, colName2, list);								// dtm2에 검색결과를 넣는다.
						table2.updateUI();												// 테이블 UI를 업데이트한다.
						sortTable(table2);												// 테이블 정렬 및 컬럼 간격 지정
					}
				}				
			}
	         
	         // 강의 관리 탭일 때
	         else if (tabVal == ManagerTop.LECTURE)
	         {
	        	 if (e.getSource() == registerButton)					// 등록 버튼
	        	 {
	        		new LectureInfo(LectureInfo.REGISTER, lecture);	// 강의 등록창을 띄운다.
	        		list = lt_dao.selectLecture_table();						// 등록창이 닫힌 뒤 테이블을 갱신한다.
					makeTable(dtm3, colName3, list);						// dtm3에 갱신된 정보를 담는다.
		        	table3.updateUI();										// 테이블 UI를 업데이트 한다.
		        	sortTable(table3); 		 								// 테이블 정렬 및 컬럼 간격 지정
	        	 }
	        	 else if (e.getSource() == modifyButton)			// 수정 버튼
	        	 { 
	        		 if(table3.getSelectedRow() != -1)		// 테이블에서 선택된 값이 있다면
	        		 {
	        			// dtm3에 저장된 순서(index) 값을 가져온다.
	        			int index = table3.convertRowIndexToModel(table3.getSelectedRow());
	        			int l_num = (int) dtm3.getValueAt(index, 0);		// 선택한 열의 0번째 컬럼 = 강의번호
	        			lecture = l_dao.createLecture(l_num);				// 해당 강의 번호로 Lecture 인스턴스 생성
	        			new LectureInfo(LectureInfo.MODIFY, lecture);		// 강의 수정창을 띄운다.
	        			list = lt_dao.selectLecture_table();						// 수정창이 닫힌 뒤 테이블을 갱신한다.
						makeTable(dtm3, colName3, list);						// dtm3에 갱신된 정보를 담는다.
			        	table3.updateUI();										// 테이블 UI를 갱신한다.
			        	sortTable(table3);										// 테이블 정렬 및 컬럼 간격 지정
	        		 }		              	   
	            }
	            else if (e.getSource() == deleteButton)				// 삭제 버튼
	            {
	            	if(table3.getSelectedRow() != -1)		// 테이블에서 선택된 것이 있는 경우
	            	{
	            		// dtm3에 실제 저장된 순서(index) 값을 가져온다.
	            		int index = table3.convertRowIndexToModel(table3.getSelectedRow());
	            		int l_num = (int) dtm3.getValueAt(index, 0);	// 선택된 열의 0번째 컬럼 값을 가져온다 = 강의번호               
	            		new Dialog_Select("강의 삭제", "강의를 삭제하시겠습니까?", Dialog_Select.LECTURE_DELETE, l_num);
	            		list = lt_dao.selectLecture_table();		// 다이얼로그 창이 닫힌 뒤 테이블을 갱신한다.
						makeTable(dtm3, colName3, list);		// dtm3에 갱신된 정보를 담는다.
		        		table3.updateUI();						// 테이블 UI를 업데이트한다.
		        		sortTable(table3);	        			// 테이블 정렬 및 컬럼 간격 지정
	               }      	                 
	           }
	        	 // JTextField에서 엔터를 치거나 검색버튼을 눌렀을 때
	           else if (e.getSource() == findField3 || e.getSource() == searchButton3)
	           {
					// 검색 조건이 강의번호일 때
					if (choice3.getSelectedItem().equals("강의 번호"))
					{
						int l_num = 0;			// 강의번호를 가져올 변수
						
						if (findField3.getText().equals(""))			// JTextField에 입력된 값이 없을 경우
						{
							list = lt_dao.selectLecture_table();		// 모든 강의 정보를 가져온다.
							makeTable(dtm3, colName3, list);		// dtm3에 정보를 담는다.
			        		table3.updateUI();						// 테이블 UI를 업데이트 한다.
			        		sortTable(table3);						// 테이블 정렬 및 컬럼 간격 지정
						}
						else	// JTextField에 입력된 값이 있을 경우
						{
							try		// try-catch 문으로 입력된 값이 숫자인지 문자열인지 구분한다.
							{
								l_num = Integer.parseInt(findField3.getText());
							}
							catch (NumberFormatException ne)		// 문자열인 경우 Exception 발생
							{
								new Dialog_Default("오류", "강의 번호는 숫자로만 입력해주세요.");
								return;
							}
							
							list = lt_dao.searchLecture_tableByL_num(l_num);		// 해당 강의 번호로 검색한 결과를 ArrayList로 가져온다.
							makeTable(dtm3, colName3, list);		// dtm3에 검색결과를 담는다.
			        		table3.updateUI();						// 테이블 UI를 업데이트 한다.
			        		sortTable(table3);						// 테이블 정렬 및 컬럼 간격 지정
						}
					}
					// 검색 조건이 강의명일 경우
					else if (choice3.getSelectedItem().equals("강의 이름"))
					{
						// JTextField에 입력된 값으로 검색한 결과를 ArrayList로 가져온다.
						list = lt_dao.searchLecture_tableByTitle(findField3.getText());
						makeTable(dtm3, colName3, list);		// dtm3에 검색결과를 담는다.
		        		table3.updateUI();						// 테이블의 UI를 업데이트한다.
		        		sortTable(table3);						// 테이블 정렬 및 컬럼 간격 지정
					}
				}
	        }
		}	         

		@Override
		public void mouseClicked(MouseEvent me)
		{
			if (me.getClickCount() == 2)		// 마우스 클릭 횟수가 2회일 때 = 더블클릭
			{
				if (me.getSource() == table1)		// 수강생 테이블에서 더블클릭 했을 시
				{
					if (table1.getSelectedRow() != -1)		// 테이블에 선택된 열이 있다면
					{
						// dtm1에 저장된 실제 순서(index)를 가져온다.
						int index = table1.convertRowIndexToModel(table1.getSelectedRow());
						int st_num = (int) dtm1.getValueAt(index, 0);	// 선택된 열의 0번째 컬럼 값 = 학생번호
						student = s_dao.createStudent(st_num);			// 해당 학생번호로 Student 인스턴스를 생성한다.
						new MemberInfo(MemberInfo.MODIFY_A, student);	// 회원정보 수정창을 띄운다.
					}				
				}
				else if (me.getSource() == table2)		// 강사 테이블에서 더블클릭 했을 시
				{
					if (table2.getSelectedRow() != -1) 	// 테이블에 선택된 열이 있다면
					{
						// dtm2에 저장된 실제 순서(index)를 가져온다.
						int index = table2.convertRowIndexToModel(table2.getSelectedRow());
						int t_num = (int) dtm2.getValueAt(index, 0);	// 선택된 열의 0번째 컬럼 값 = 강사번호
						teacher = t_dao.createTeacher(t_num);			// 해당 강사번호로 Teacher 인스턴스를 생성한다.
						new MemberInfo(MemberInfo.T_MODIFY, teacher);	// 강사정보 수정창을 띄운다.
					}
				}
				else if (me.getSource() == table3)		// 강의 테이블에서 더블클릭 했을 시
				{
					if (table3.getSelectedRow() != -1) 	// 테이블에서 선택된 열이 있다면
					{
						// dtm3에 저장된 실제 순서(index)를 가져온다.
						int index = table3.convertRowIndexToModel(table3.getSelectedRow());
						int l_num = (int) dtm3.getValueAt(index, 0);	// 선택된 열의 0번째 컬럼 값 = 강의번호
						new StudentReading(l_num, StudentReading.ADMIN);	// 해당 강의번호의 수강생 명단 창을 띄운다.
						list = lt_dao.selectLecture_table();		// 수강생 명단 창이 닫힌 뒤 테이블을  갱신한다.
						makeTable(dtm3, colName3, list);		// dtm3에 갱신된 값을 담는다.
		        		table3.updateUI();						// 테이블 UI를 업데이트한다.
		        		sortTable(table3);						// 테이블 정렬 및 컬럼 간격 지정
					}
				}
			}
		}			
	}
}