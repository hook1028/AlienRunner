package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import dao.table.*;

// 수강신청 패널 클래스
public class AddLesson extends JPanel
{
	// UI 구성
	private JTextField findField;
	private JScrollPane lessonDataScroll;
	private JTable table;
	private JLabel findLabel;
	private JLabel lessonDataText;
	private JButton applyButton;
	private JButton backButton;
	private Choice lessonChoice;
	private JButton searchButton;
	
	// 이벤트 핸들러 클래스
	private Handler handler = new Handler();
	
	// 데이터 연동에 필요한 클래스
	private Lecture_TableDAO lt_dao;
	private Student student;
	
	// 테이블 만드는 클래스
	private DefaultTableModel dtm;
	private Vector<String> colName;
	private ArrayList<Vector> list;
	
	// Choice와 TextField에서 값을 가져올 변수
	private String searchVal;
	private String choiceVal;
	
//	생성자	
	public AddLesson(Student student)
	{
		this.student = student;							// 수강신청할 학생의 정보를 담은 Student 객체를 받는다. 
		lt_dao = Lecture_TableDAO.getInstance();	// 수강신청 테이블을 만들 때 쓰일 Lecture_TableDAO 인스턴스 생성
		list = lt_dao.selectLecture_table();				// Lecture_TableDAO에서 모든 강의 정보를 ArrayList로 받아온다.
	    
		// 테이블 생성
		colName = new Vector<String>();		// 테이블 헤더로 쓰일 Vector
		colName.add("강의번호");
		colName.add("강의언어");
		colName.add("강의명");
		colName.add("강사명");
		colName.add("요일");
		colName.add("강의시간");
		colName.add("강의실");
		colName.add("비용");
		colName.add("신청현황");
		
        // 익명중첩 클래스로 테이블 편집 여부를 설정한다.
		// DefaultTableModel(Vector ColumnNames, int rowCount)
        dtm = new DefaultTableModel(colName, 0)
        	{ 
        		// 테이블의 편집 가능 여부를 알려주는 메소드
        		@Override
        		public boolean isCellEditable(int row, int column)      
        		{
            	   return false;       // 편집이 안되도록 한다.
        		}
        	};
        
        makeTable(list);		// ArrayList를 받아 dtm을 만들어주는 메소드
        
	    table = new JTable(dtm);		// dtm을 통해 테이블을 생성한다.
	    table.getTableHeader().setForeground(new Color(82, 55, 56));	// 테이블 헤더의 글자색 지정
        table.getTableHeader().setReorderingAllowed(false);		// 테이블의 헤더를 움직이지 못하게 고정
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 15));		// 테이블 헤더의 폰트 설정
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));	// 테이블의 폰트  설정
	    table.setForeground(new Color(82, 55, 56));				// 테이블의 글자색 지정
        table.setRowSorter(new TableRowSorter(dtm));			// 테이블을 정렬하게 해주는 RowSorter 추가
                
        sortTable();		// 테이블 컬럼의 간격 지정 및 정렬하는 메소드
        
//		모든 수강 정보 스크롤판
		lessonDataScroll = new JScrollPane(table);		// 테이블을 넣어 생성한다.
		lessonDataScroll.setBounds(40, 170, 810, 400);
		add(lessonDataScroll);
		
//		강의검색 레이블
		findLabel = new JLabel("강 의 검 색");
		findLabel.setBounds(38, 55, 80, 30);
		findLabel.setForeground(new Color(82, 55, 56));
		findLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		findLabel.setHorizontalAlignment(SwingConstants.CENTER);		// JLabel의 수평 정렬을 설정한다.
		add(findLabel);
		
//		모든 수강 정보 레이블
		lessonDataText = new JLabel("신청 가능한 모든 수강");
		lessonDataText.setBounds(40, 130, 200, 40);
		lessonDataText.setForeground(new Color(82, 55, 56));
		lessonDataText.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lessonDataText.setHorizontalAlignment(SwingConstants.CENTER);
		add(lessonDataText);

//		신청하기 버튼
		applyButton = new JButton("신 청 하 기");
		applyButton.setBounds(516, 40, 150, 60);
		applyButton.setForeground(new Color(82, 55, 56));
		applyButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(applyButton);
		
//		돌아가기 버튼
		backButton = new JButton("돌 아 가 기");
		backButton.setBounds(690, 40, 150, 60);
		backButton.setForeground(new Color(82, 55, 56));
		backButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(backButton);

//		강의검색 필드
		findField = new JTextField();
		findField.setForeground(new Color(82, 55, 56));
		findField.setBounds(240, 58, 120, 30);
		findField.setColumns(10);
		add(findField);
		
//		강의명/강사명 초이스
		lessonChoice = new Choice();
		lessonChoice.setBounds(145, 59, 80, 26);
		lessonChoice.setBackground(new Color(255, 254, 220));
		lessonChoice.setForeground(new Color(82, 55, 56));
		lessonChoice.add("강의명");
		lessonChoice.add("강사명");
		lessonChoice.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(lessonChoice);
		
		// 검색 실행 버튼
		searchButton = new JButton("검  색");
		searchButton.setBounds(378, 57, 85, 30);
		searchButton.setForeground(new Color(82, 55, 56));
		searchButton.setFont(new Font("맑은 고딕",Font.BOLD, 16));
		add(searchButton);
		
		// 이벤트 구현 부분
		applyButton.addActionListener(handler);
		backButton.addActionListener(handler);
		findField.addActionListener(handler);
		searchButton.addActionListener(handler);
		table.addMouseListener(handler);
		
		setVisible(true);
	}
	
	// ArrayList를 받아 dtm에 자료를 넣어주는 메소드
	private void makeTable(ArrayList<Vector> list)
	{
		if (dtm.getRowCount() > 0)		// dtm에 자료가 있는 경우
		{ 
			//DefaultTableModel의 테이블 자료를 전부 삭제한다. (새로고침과 같은 효과)
			dtm.setDataVector(null, colName);
		}
		
        for (int i = 0; i < list.size(); i++)
        {
        	dtm.addRow(list.get(i));		// ArrayList에 있는 자료가 담긴 Vector를 차례대로 dtm에 넣는다.
        }
	}
	
	// 테이블 컬럼 간격 및 정렬을 도와주는 메소드
	private void sortTable()
	{
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();	// 테이블 셀 디스플레이와 관련된 클래스
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);				// 가운데 정렬로 설정한다.
		TableColumnModel tcm = table.getColumnModel();					// 해당 테이블의 TableColumnModel을 가져온다.

		for (int i = 0; i < tcm.getColumnCount(); i++)		// 컬럼 개수만큼 컬럼을 가져와 정렬함
		{
			tcm.getColumn(i).setCellRenderer(dtcr);			// 테이블 컬럼모델에서 가져온 컬럼에 정렬을 적용한다.
		}
		
		// 컬럼 간격 설정
        table.getColumnModel().getColumn(0).setPreferredWidth(50);		// 강의 번호
        table.getColumnModel().getColumn(1).setPreferredWidth(50);		// 강의 언어
        table.getColumnModel().getColumn(2).setPreferredWidth(170);		// 강의명
        table.getColumnModel().getColumn(3).setPreferredWidth(45);		// 강사명
        table.getColumnModel().getColumn(4).setPreferredWidth(50);		// 요일
        table.getColumnModel().getColumn(5).setPreferredWidth(65);		// 시간
        table.getColumnModel().getColumn(6).setPreferredWidth(50);		// 강의실
        table.getColumnModel().getColumn(7).setPreferredWidth(50);		// 비용
        table.getColumnModel().getColumn(8).setPreferredWidth(50);		// 신청현황
        table.setRowHeight(30);		// Row의 높이를 설정한다.
	}
	
	// 이벤트 내용을 저장할 클래스
	class Handler extends MouseAdapter implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// 신청하기 버튼을 눌렀을 때
			if (e.getSource() == applyButton)
			{
				if(table.getSelectedColumn() != -1)	// 테이블에 선택한 값이 있다면
				{
					// 수강신청에 필요한 변수 값을 얻어온다.
					int index = table.convertRowIndexToModel(table.getSelectedRow());	// 정렬 시 선택된 row가 dtm에서는 실제로 몇 번째 row인지 알려준다.
					int l_num = (int) dtm.getValueAt(index, 0);		// dtm에서 index열, 0번째 행에 있는 값을 가져온다.
					int all_people = 0;
					int reg_people = 0;
					
					String register = ((String)dtm.getValueAt(index, 8)).trim();		// 가져온 값의 공백을 제거한다.
					StringTokenizer st = new StringTokenizer(register, "/");		// /를 기준으로 앞, 뒤 숫자를 자른다.
					while(st.hasMoreTokens())
					{
						reg_people = Integer.parseInt(st.nextToken());		// 앞 부분의 숫자를 int형으로 변환
						all_people = Integer.parseInt(st.nextToken());		// 뒷 부분의 숫자를 int형으로 변환
					}
					
					if (reg_people == all_people)		// 수강 가능한 인원과 수강 신청한 인원이 같을 경우
					{
						new Dialog_Default("신청 실패", "수강 인원이 모두 찼습니다.");
					}
					else
					{
						String title = (String) dtm.getValueAt(index, 2);		// 테이블에서 강의명을 가져옴
						
						// 수강신청 다이얼로그창을 띄운다.
						new Dialog_Select(title, l_num, student.getSt_num());
						// 수강신청 다이얼로그 창이 닫힌 뒤, 수강신청 테이블을 새로고침한다.
						list = lt_dao.selectLecture_table();		// 모든 강의정보를 ArrayList로 가져오는 메소드
						makeTable(list);							// ArrayList에 담긴 값을 통해 table을 만든다.
						table.updateUI();							// table의 UI를 갱신한다.
						sortTable();								// table의 컬럼 간격을 지정하고, 정렬한다.
					}
				}
			}
			
			// 돌아가기 버튼을 눌렀을 때
			else if (e.getSource() == backButton)
			{
				Main.mainFrame.changePanel(new Student_Teacher_Top(student));		// 학생 메인화면으로 돌아간다.
			}
			
			// 검색 버튼을 누르거나 검색 TextField에서 엔터를 눌렀을 때
			else if (e.getSource() == searchButton || e.getSource() == findField)
			{
				// 검색조건에 맞춰 검색하고 테이블이 갱신됨
				choiceVal = lessonChoice.getSelectedItem();		// 초이스에서 값을 가져옴
				searchVal = findField.getText();						// 검색할 값을 가져옴
				
				if (choiceVal.equals("강의명"))
				{
					list = lt_dao.searchLecture_tableByTitle(searchVal);		// 강의명으로 검색한 결과를 가져오는 메소드
					// 검색결과에 맞춰 dtm에 새로운 값을 넣는다.
					makeTable(list);			// ArrayList에 담긴 값을 통해 table을 만든다.
					table.updateUI();			// table의 UI를 갱신한다.
					sortTable();				// table의 컬럼 간격을 지정하고, 정렬한다.
				}
				else if (choiceVal.equals("강사명"))
				{
					list = lt_dao.searchLecture_tableByTeacher(searchVal);	// 강사명으로 검색한 결과를 가져오는 메소드
					// 검색결과에 맞춰 dtm에 새로운 값을 넣는다.
					makeTable(list);			// ArrayList에 담긴 값을 통해 table을 만든다.
					table.updateUI();			// table의 UI를 갱신한다.
					sortTable();				// table의 컬럼 간격을 지정하고, 정렬한다.
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			if (e.getClickCount() == 2)		// 클릭횟수가 2번이라면 = 더블클릭 했다면
			{
				if(table.getSelectedColumn() != -1)	// 테이블에 선택한 값이 있다면
				{
					// 수강신청에 필요한 변수 값을 얻어온다.
					int index = table.convertRowIndexToModel(table.getSelectedRow());	// 정렬 시 선택된 row가 실제 dtm에서는 몇 번째 row인지 알려준다.
					int l_num = (int) dtm.getValueAt(index, 0);		// dtm에서 index 번째 줄, 0번째 칼럼에 있는 값을 가져온다.
					int all_people = 0;		// 총 인원 값을 넣을 변수
					int reg_people = 0;		// 신청한 인원 수 값을 넣을 변수
					
					String register = ((String)dtm.getValueAt(index, 8)).trim();		// 신청현황 칼럼의 값을 가져와 공백을 제거한다.
					StringTokenizer st = new StringTokenizer(register, "/");		// "/" 를 통해 문자를 나눈다.
					while(st.hasMoreTokens())		// 토큰이 있다면
					{
						reg_people = Integer.parseInt(st.nextToken());		// 첫 번째 토큰은 신청한 인원
						all_people = Integer.parseInt(st.nextToken());		// 두 번째 토큰은 총 인원
					}
					
					if (reg_people == all_people)		// 만약 총 인원과 수강신청한 인원이 같다면 = 인원이 모두 찼다면
					{
						new Dialog_Default("신청 실패", "수강 인원이 모두 찼습니다.");
					}
					else
					{
						String title = (String) dtm.getValueAt(index, 2);		// 2번째 컬럼 즉, 강의명 컬럼에 있는 값을 가져온다.
						
						// 수강신청 다이얼로그창을 띄운다.
						new Dialog_Select(title, l_num, student.getSt_num());
						// 수강신청 다이얼로그 창이 닫힌 뒤 신청가능한 수강 테이블을 새로고침 한다.
						list = lt_dao.selectLecture_table();		// 모든 수강 정보를 가져온다.
						makeTable(list);							// ArrayList를 받아 table을 만든다.
						table.updateUI();							// table의 UI를 업데이트 한다.
						sortTable();								// table의 간격을 조정하고, 정렬한다.
					}
				}
			}
		}
	}
}