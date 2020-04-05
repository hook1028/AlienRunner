package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import dao.table.*;

// 강의 등록창
public class LectureInfo extends JDialog 
{	
	// 멤버 변수
	private JTextField title_tf;
	private JTextField price_tf;
	private JTextField allPeople_tf;
	private ButtonGroup language;
	private JRadioButton english;
	private JRadioButton chinese;
	private JRadioButton japanese;
	private JLabel classLanguage;
	private JLabel classNames;
	private JLabel teacherNum;
	private JLabel classDay;
	private JLabel classTime;
	private JLabel classRoom;
	private JLabel lessonPrice;
	private JLabel withMember;
	private Choice teacherNums;
	private Choice weekChoice;
	private Choice startTime;
	private Choice endTime;
	private JLabel wave;
	private Choice classRoomNum;
	private JButton classAdd;
	private JButton cancel;
	
	// 이벤트 담당할 클래스
	private Handler handler = new Handler();
	
	// 등록, 수정 모드를 나타내는 상수
	final static int REGISTER = 1;
	final static int MODIFY = 2;
	
	// 입력 값을 가져올 변수	
	private int mode;
	private int numVal;
	private int t_numVal;
	private String dayVal;
	private int classVal;
	private int priceVal = -1;
	private int allPeopleVal = -1;
	private String languageVal;
	private String titleVal;
	private String startTimeVal;
	private String endTimeVal;
	
	// 데이터베이스 연동 및 데이터를 체크할 클래스
	private TeacherDAO t_dao;
	private TeacherDTO t_check;
	private LectureDAO l_dao;
	private LectureDTO l_check;
	private Lecture lecture;
	private ArrayList<String> t_list;
		
	// 생성자
	public LectureInfo(int mode, Lecture lecture) 
	{		
		// Dialog창 기본 설정
		super(Main.mainFrame);
		setSize(650, 750);
		Dialog_Default.init(this, "");
		this.mode = mode;				// 파라미터로 받은 모드 값을 멤버 변수에 넣는다.
		
		// 데이터베이스 연동과 관련된 멤버 변수를 초기화한다.
		t_dao = TeacherDAO.getInstance();
		t_check = new TeacherDTO(t_dao);
		l_dao = LectureDAO.getInstance();
		this.lecture = lecture;			// 파라미터로 받은 Lecture 인스턴스를 멤버 변수에 넣는다.

		if (mode == LectureInfo.REGISTER)		// 등록 모드일 경우
		{
			setTitle("강 의 등 록");
			l_check = new LectureDTO(l_dao);
		}
		else if (mode == LectureInfo.MODIFY)	// 수정 모드일 경우
		{
			setTitle("강 의 수 정");
			l_check = new LectureDTO(l_dao, lecture);		// 수정 모드일 시 현재 받아온 강의 객체를 넣음
		}
		
		// 강의 등록시 과목을 선택하는 라디오버튼 <영어>
		english = new JRadioButton("영어");
		english.setBounds(190, 53, 121, 25);
		english.setForeground(new Color(82, 55, 56));
		english.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(english);

		// 강의 과목 중 <중국어>
		chinese = new JRadioButton("중국어");
		chinese.setBounds(340, 53, 121, 25);
		chinese.setForeground(new Color(82, 55, 56));
		chinese.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(chinese);

		// 강의 과목 중 <일본어>
		japanese = new JRadioButton("일본어");
		japanese.setBounds(490, 53, 121, 25);
		japanese.setForeground(new Color(82, 55, 56));
		japanese.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(japanese);
		
		// 강의 언어를 language 버튼 그룹에 넣는다.
		language = new ButtonGroup();
		language.add(english);
		language.add(chinese);
		language.add(japanese);

		// 강의 언어 레이블
		classLanguage = new JLabel("강의 언어 :");
		classLanguage.setBounds(65, 55, 85, 19);
		classLanguage.setForeground(new Color(82, 55, 56));
		classLanguage.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		classLanguage.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classLanguage);

		// 강의명 레이블
		classNames = new JLabel("강의명 :");
		classNames.setBounds(65, 120, 85, 25);
		classNames.setForeground(new Color(82, 55, 56));
		classNames.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		classNames.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classNames);

		// 강사 번호 레이블
		teacherNum = new JLabel("강사 번호 :");
		teacherNum.setBounds(65, 195, 85, 25);
		teacherNum.setForeground(new Color(82, 55, 56));
		teacherNum.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		teacherNum.setHorizontalAlignment(SwingConstants.RIGHT);
		add(teacherNum);

		// 강의 요일 레이블
		classDay = new JLabel("강의 요일 :");
		classDay.setBounds(65, 270, 85, 25);
		classDay.setForeground(new Color(82, 55, 56));
		classDay.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		classDay.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classDay);

		// 강의 시간 레이블
		classTime = new JLabel("강의 시간 :");
		classTime.setBounds(65, 345, 85, 25);
		classTime.setForeground(new Color(82, 55, 56));
		classTime.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		classTime.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classTime);

		// 강의실 레이블
		classRoom = new JLabel("강의실 :");
		classRoom.setBounds(65, 415, 85, 25);
		classRoom.setForeground(new Color(82, 55, 56));
		classRoom.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		classRoom.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classRoom);

		// 강의료 레이블입니당
		lessonPrice = new JLabel("강의료 :");
		lessonPrice.setBounds(65, 490, 85, 25);
		lessonPrice.setForeground(new Color(82, 55, 56));
		lessonPrice.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lessonPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lessonPrice);

		// 총 인원 레이블
		withMember = new JLabel("총인원 :");
		withMember.setBounds(65, 567, 85, 25);
		withMember.setForeground(new Color(82, 55, 56));
		withMember.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		withMember.setHorizontalAlignment(SwingConstants.RIGHT);
		add(withMember);

		// 입력을 요하는 TextField 중 '강의명'
		title_tf = new JTextField();
		title_tf.setBounds(205, 122, 200, 25);
		title_tf.setForeground(new Color(82, 55, 56));
		title_tf.setColumns(10);
		add(title_tf);

		// 입력을 요하는 TextField 중 '강의료'
		price_tf = new JTextField();
		price_tf.setBounds(205, 493, 120, 25);
		price_tf.setForeground(new Color(82, 55, 56));
		price_tf.setColumns(10);
		add(price_tf);
		
		// 입력을 요하는 TextField 중 '총인원'
		allPeople_tf = new JTextField();
		allPeople_tf.setBounds(205, 570, 120, 25);
		allPeople_tf.setForeground(new Color(82, 55, 56));
		allPeople_tf.setColumns(10);
		add(allPeople_tf);

		// 강사 번호 선택하기
		teacherNums = new Choice();
		teacherNums.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		teacherNums.setBackground(new Color(255, 254, 220));
		teacherNums.setForeground(new Color(82, 55, 56));
		teacherNums.setBounds(205, 197, 120, 25);
		
		// JDBC 연동으로 강사번호와 이름을 불러와 Choice에 넣는다.
		t_list = t_dao.selectT_numAndName();
		for(int i = 0; i < t_list.size(); i++)
		{
			teacherNums.add(t_list.get(i));
		}
		add(teacherNums);

		// 요일 고르기
		weekChoice = new Choice();
		weekChoice.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		weekChoice.setBounds(205, 273, 120, 25);
		weekChoice.setBackground(new Color(255, 254, 220));
		weekChoice.setForeground(new Color(82, 55, 56));
		weekChoice.add("월, 수, 금");
		weekChoice.add("화, 목, 토");
		weekChoice.add("토, 일");
		add(weekChoice);

		// 시작 시간
		startTime = new Choice();
		startTime.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		startTime.setBounds(205, 350, 120, 25);
		startTime.setBackground(new Color(255, 254, 220));
		startTime.setForeground(new Color(82, 55, 56));
		startTime.add("09:00");
		startTime.add("10:00");
		startTime.add("11:00");
		startTime.add("12:00");
		startTime.add("13:00");
		startTime.add("14:00");
		startTime.add("15:00");
		startTime.add("16:00");
		startTime.add("17:00");
		startTime.add("18:00");
		startTime.add("19:00");
		startTime.add("20:00");
		startTime.add("21:00");
		add(startTime);

		// 끝나는 시간
		endTime = new Choice();
		endTime.setBounds(363, 350, 120, 25);
		endTime.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		endTime.setBackground(new Color(255, 254, 220));
		endTime.setForeground(new Color(82, 55, 56));
		endTime.add("10:00");
		endTime.add("11:00");
		endTime.add("12:00");
		endTime.add("13:00");
		endTime.add("14:00");
		endTime.add("15:00");
		endTime.add("16:00");
		endTime.add("17:00");
		endTime.add("18:00");
		endTime.add("19:00");
		endTime.add("20:00");
		endTime.add("21:00");
		endTime.add("22:00");
		add(endTime);
		
		// 강의 시작시간과 종료시간 사이의 물결표시
		wave = new JLabel("~");
		wave.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		wave.setForeground(new Color(82, 55, 56));
		wave.setBounds(340, 344, 24, 28);
		add(wave);

		// 강의실
		classRoomNum = new Choice();
		classRoomNum.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		classRoomNum.setBackground(new Color(255, 254, 220));
		classRoomNum.setForeground(new Color(82, 55, 56));
		classRoomNum.setBounds(205, 420, 125, 25);
		classRoomNum.add("1 강의실");
		classRoomNum.add("2 강의실");
		classRoomNum.add("3 강의실");
		classRoomNum.add("4 강의실");
		classRoomNum.add("5 강의실");
		add(classRoomNum);

		// 강의 등록시 등록하기 버튼
		classAdd = new JButton("등록하기");
		classAdd.setBounds(180, 635, 120, 50);
		classAdd.setForeground(new Color(82, 55, 56));
		classAdd.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(classAdd);

		// 강의 등록하지 않고 취소하는 버튼
		cancel = new JButton("취소");
		cancel.setBounds(350, 635, 120, 50);
		cancel.setForeground(new Color(82, 55, 56));
		cancel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(cancel);
		
		if (mode == LectureInfo.MODIFY)		// 수정모드일 경우 몇몇 컴포넌트를 비활성화 시켜 수정하지 못하게 한다.
		{
			setTitle("강 의 수 정");
			classAdd.setText("수정하기");
			english.setEnabled(false);
			chinese.setEnabled(false);
			japanese.setEnabled(false);
			title_tf.setEditable(false);
			price_tf.setEditable(false);
			teacherNums.setEnabled(false);
			weekChoice.setEnabled(false);
			startTime.setEnabled(false);
			endTime.setEnabled(false);
			
			// 선택된 강의의 정보를 가져온다.
			// 강의 번호를 가져와 앞자리에 따라 해당 언어를 선택되게 한다.
			switch (lecture.getL_num() / 1000)
			{
			case 1:
				english.setSelected(true);
				break;
			case 2:
				japanese.setSelected(true);
				break;
			case 3:
				chinese.setSelected(true);
				break;
			}
			
			title_tf.setText(lecture.getTitle());		// 타이틀을 가져와 설정한다.
			
			for(int i = 0; i < teacherNums.getItemCount(); i++)	// 강사번호 Choice 설정
			{
				if (teacherNums.getItem(i).substring(0, 3).equals(lecture.getT_num()+""))	// 앞 숫자가 해당 강사번호와 일치할 시
				{
					teacherNums.select(i);	// 해당 아이템으로 설정한다.
				}
			}
			
			weekChoice.select(lecture.getDay() - 1);				// 요일 값 설정
			
			for(int i = 0; i < startTime.getItemCount(); i++)	// 시작 시간 설정
			{
				if (startTime.getItem(i).equals(lecture.getTime().substring(0, 5)))	// 전체 시간 값에서 시작 시간 부분만 가져옴
				{
					startTime.select(i);	// 해당 아이템으로 설정한다.
				}
			}
			
			for(int i = 0; i < endTime.getItemCount(); i++)		// 끝나는 시간 설정
			{
				if (endTime.getItem(i).equals(lecture.getTime().substring(6)))	// 전체 시간 값에서 끝나는 시간 부분만 가져옴
				{
					endTime.select(i);	// 해당 아이템으로 설정한다.
				}
			}
			
			for(int i = 0; i < classRoomNum.getItemCount(); i++)	// 강의실 설정
			{
				if (classRoomNum.getItem(i).substring(0, 1).equals(lecture.getClass_n()+""))	// 강의실 값에서 숫자만 가져옴
				{
					classRoomNum.select(i);	// 해당 아이템으로 설정한다.
				}
			}
			
			price_tf.setText(lecture.getCost() + "");					// 강의료 설정
			allPeople_tf.setText(lecture.getAll_people() + "");		// 총 인원 설정
		}
				
		// 이벤트를 구현할 부분
		classAdd.addActionListener(handler);
		cancel.addActionListener(handler);
		
		setVisible(true);
	}
	
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{	
			// 강의 등록하기 모드
			if (mode == LectureInfo.REGISTER)
			{
				// 등록하기 버튼을 눌렀을 때
				if (e.getSource() == classAdd)
				{
					// 입력한 값을 받아 검사 후 등록한 뒤 등록이 완료되었다는 팝업창
					// 입력한 값이 제대로 된 값이 아닐 경우 팝업창 띄움
					titleVal = title_tf.getText().trim();
					t_numVal = Integer.parseInt(teacherNums.getSelectedItem().trim().substring(0, 3));
					dayVal = weekChoice.getSelectedItem();
					startTimeVal = startTime.getSelectedItem();
					endTimeVal = endTime.getSelectedItem();
					classVal = classRoomNum.getSelectedIndex() + 1;
					
					// 라디오버튼에서 선택된 값 가져옴
					if (english.isSelected())
					{
						languageVal = english.getText();
					}
					else if (japanese.isSelected())
					{
						languageVal = japanese.getText();
					}
					else if (chinese.isSelected())
					{
						languageVal = chinese.getText();
					}
					else	// 선택된 값이 없을 경우
					{
						new Dialog_Default("언어 선택", "언어를 선택해주세요.");
						english.requestFocus();
						return;
					}
					
					// 예외처리를 통해 숫자인지 문자인지 검사 후 숫자로 넣기					
					try
					{
						priceVal = Integer.parseInt(price_tf.getText());
						allPeopleVal = Integer.parseInt(allPeople_tf.getText());
						
					} catch (NumberFormatException ne)	// 숫자가 아닐 시 Exception 발생
					{
						if (priceVal == -1)				// 가격이 입력되지 않았을 시
						{
							new Dialog_Default("금액 입력", "금액은 숫자로만 입력해주세요.");
							price_tf.requestFocus();
							return;
						}
						else if (allPeopleVal == -1)	// 총 인원이 입력되지 않았을 시
						{
							new Dialog_Default("총 인원 입력", "총 인원은 숫자로만 입력해주세요.");
							allPeople_tf.requestFocus();
							return;
						}
					}

					if (l_check.checkL_num(languageVal) == false)					// 언어 값을 넣지 못했을 시
					{
						new Dialog_Default("언어 선택", "언어를 선택해주세요.");
						english.requestFocus();
					}
					else if (l_check.checkTitle(titleVal) == false)							// 제목이 5 ~ 20자가 아닐 시
					{
						new Dialog_Default("강의명 입력", "강의명은 5~20 자로 입력해주세요.");
						title_tf.requestFocus();
					}
					else if (l_check.checkT_num(t_numVal) == false)					// 강사번호를 넣지 못했을 시
					{
						new Dialog_Default("강사 선택", "강사를 선택해 주세요.");
						teacherNums.requestFocus();
					}
					else if (l_check.checkDay(dayVal) == false)							// 요일 값을 넣지 못했을 시
					{
						new Dialog_Default("요일 선택", "요일을 선택해 주세요.");
						weekChoice.requestFocus();
					}
					else if (l_check.checkTime(startTimeVal, endTimeVal) == false)	// 시간 값을 넣지 못했을 시
					{
						new Dialog_Default("시간 선택", "시간을 선택해 주세요.");
						startTime.requestFocus();
					}
					else if (l_check.checkClass(classVal) == false)						// 강의실 값을 넣지 못했을 시
					{
						new Dialog_Default("강의실 선택", "강의실을 선택해 주세요.");
						classRoomNum.requestFocus();
					}
					else if (l_check.checkCost(priceVal) == false)						// 강의료 값을 넣지 못했을 시
					{
						new Dialog_Default("강의료 입력", "강의료를 다시 입력해주세요.");
						price_tf.requestFocus();
					}
					else if (l_check.checkAll_people(allPeopleVal) == false)			// 총 인원 값을 넣지 못했을 시
					{
						new Dialog_Default("총 인원 입력", "인원은 10~50명 사이로 입력해주세요.");
						allPeople_tf.requestFocus();
					}
					else	// 모든 값을 넣었을 시
					{
						lecture = l_check.getLecture();			// 값을 모두 입력받은 Lecture 인스턴스를 돌려받는다.
						
						if (l_dao.insertLecture(lecture) == 1)		// 강의 등록에 성공했을 시
						{
							new Dialog_Default("등록 성공", "강의 등록에 성공하였습니다.");
							LectureInfo.this.dispose();
						}
						else											// 강의 등록에 실패했을 시
						{
							new Dialog_Default("등록 실패", "강의 등록에 실패하였습니다.");
						}
					}						
				}
				
				// 닫기 버튼을 눌렀을 때
				else if (e.getSource() == cancel)
				{
					// 강의 등록창을 닫음
					LectureInfo.this.dispose();
				}		
			}
			
			else if (mode == LectureInfo.MODIFY)
			{
				// 수정하기 버튼을 눌렀을 때
				if (e.getSource() == classAdd)
				{
					classVal = classRoomNum.getSelectedIndex() + 1;		// 수정된 강의실 값을 가져온다.
					
					try		// try-catch 문으로 총 인원에 숫자만 입력됐는지 확인한다.
					{
						allPeopleVal = Integer.parseInt(allPeople_tf.getText());
						
					} catch (NumberFormatException ne)	// 문자가 입력됐을 시 Exception 발생
					{
						new Dialog_Default("총 인원 입력", "총 인원은 숫자로만 입력해주세요.");
						allPeople_tf.requestFocus();	// 총 인원 JTextField에 포커스를 맞춘다.
					}
					
					if (l_check.checkClass(classVal) == false)	// 강의실 수정에 실패했을 시
					{
						new Dialog_Default("강의실 선택", "강의실을 선택해 주세요.");
						classRoomNum.requestFocus();
					}
					else if (l_check.checkAll_people(allPeopleVal) == false)	// 총 인원 수정에 실패했을 시
					{
						new Dialog_Default("총 인원 입력", "인원은 10~50명 사이로 입력해주세요.");
						allPeople_tf.requestFocus();
					}
					else	// 모두 수정에 성공했을 시
					{
						lecture = l_check.getLecture();		// 수정된 Lecture 객체를 돌려 받는다.
						
						if (l_dao.updateLecture(lecture) == 1)	// 수정에 성공했을 시
						{						
							new Dialog_Default("수정 성공", "강의 수정에 성공하였습니다.");
							LectureInfo.this.dispose();
						}
						else	// 수정에 실패했을 시
						{
							new Dialog_Default("수정 실패", "강의 수정에 실패하였습니다.");
						}
					}					
				}
				
				// 닫기 버튼을 눌렀을 때
				else if (e.getSource() == cancel)
				{
					LectureInfo.this.dispose();		// 현재 다이얼로그 창을 닫는다.
				}		
			}
		}
	}
}
