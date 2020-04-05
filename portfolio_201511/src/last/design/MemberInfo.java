package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// 수강생 가입, 수강생 정보 수정, 강사 정보 수정 하는 클래스
public class MemberInfo extends JDialog
{
	// UI 관련 변수
	private JTextField nameField;
	private JPasswordField pwField;			// 비밀번호는 getPassword() 메소드로 가져온다.
	private JPasswordField pwReEnterField;
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField idField;
	private JLabel join;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel pwLabel;
	private JLabel phoneLabel;
	private JLabel transLabel;
	private JLabel emailLabel;
	private JLabel pwReEnterLabel;
	private JLabel passwordHint;
	private JLabel phoneHint;
	private JRadioButton man;
	private JRadioButton woman;
	private ButtonGroup gender;
	private JButton actionButton;
	private JButton cancelButton;
	private JButton overlapButton;
	private JButton dropOutButton;

	// 가입, 수정, 강사 수정 모드를 저장하는 변수
	private int mode;
	
	// 텍스트 필드에서 값을 얻어올 변수
	private String nameVal;
	private String pwVal;
	private String pw2Val;
	private String phoneVal;
	private String emailVal;
	private int genderVal = -1;
	
	// 데이터베이스 관련 클래스
	private StudentDAO s_dao;
	private StudentDTO s_check;
	private TeacherDAO t_dao;
	private TeacherDTO t_check;
	private Student student;
	private Teacher teacher;
	
	// 이벤트 관련 클래스
	private Handler handler = new Handler();
	
	// 창 모드를 설정하는 상수
	static final int JOIN = 1;				// 수강생 가입
	static final int MODIFY = 2;			// 수강생 수정
	static final int T_MODIFY = 3;		// 강사 수정
	static final int MODIFY_A = 4;		// 관리자의 수강생 수정
	
	// 생성자
	public MemberInfo(int mode, Object who)
	{
//		JDialog 기본 설정
		super(Main.mainFrame);
		setSize(520, 570);
		Dialog_Default.init(this, "");
		this.mode = mode;		// 파라미터로 받은 mode 값을 멤버 변수에 넣는다.
		
		// 데이터 베이스 클래스 초기화
		s_dao = StudentDAO.getInstance();
		s_check = new StudentDTO(s_dao);
		t_dao = TeacherDAO.getInstance();
		t_check = new TeacherDTO(t_dao);
		
		// 강사 수정일 때는 라벨에 회원번호가 아닌 강사번호로 표시 
		if (mode == MemberInfo.T_MODIFY)	// 강사 수정일 시
		{
			idLabel = new JLabel("강사번호");			
		}
		else	// 강사 수정이 아닐 시
		{
			idLabel = new JLabel("회원번호");
		}
		
		// 회원 가입시 나타낼 회원가입 레이블
		join = new JLabel("회 원 가 입");
		join.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		join.setForeground(new Color(82, 55, 56));
		join.setHorizontalAlignment(SwingConstants.CENTER);
		join.setBounds(170, 35, 150, 30);
				
//		ID(회원번호) 레이블
		idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		idLabel.setForeground(new Color(82, 55, 56));
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setBounds(40, 50, 125, 30);

//		이름 레이블
		nameLabel = new JLabel("이 름");
		nameLabel.setBounds(40, 107, 125, 30);
		nameLabel.setForeground(new Color(82, 55, 56));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(nameLabel);
		
//		비밀번호 레이블
		pwLabel = new JLabel("비밀번호");
		pwLabel.setBounds(40, 160, 125, 30);
		pwLabel.setForeground(new Color(82, 55, 56));
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(pwLabel);
		
		//비밀번호 재입력 레이블
		pwReEnterLabel = new JLabel("비밀번호 확인");
		pwReEnterLabel.setBounds(40, 217, 125, 30);
		pwReEnterLabel.setForeground(new Color(82, 55, 56));
		pwReEnterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwReEnterLabel.setFont(new Font("맑은 고딕", Font.BOLD,16));
		add(pwReEnterLabel);
		
//		휴대폰번호 레이블
		phoneLabel = new JLabel("휴대폰번호");
		phoneLabel.setBounds(40, 270, 125, 30);
		phoneLabel.setForeground(new Color(82, 55, 56));
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(phoneLabel);
		
//		성별 레이블
		transLabel = new JLabel("성별");
		transLabel.setBounds(40, 326, 125, 30);
		transLabel.setForeground(new Color(82, 55, 56));
		transLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		transLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(transLabel);
		
//		이메일 레이블
		emailLabel = new JLabel("E - Mail");
		emailLabel.setBounds(40, 380, 125, 30);
		emailLabel.setForeground(new Color(82, 55, 56));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(emailLabel);
				
//		ID(회원번호) 필드
		idField = new JTextField();
		idField.setBounds(190, 50, 225, 30);
		idField.setForeground(new Color(82, 55, 56));
		idField.setColumns(10);

//		이름 필드
		nameField = new JTextField();
		nameField.setBounds(190, 105, 225, 30);
		nameField.setForeground(new Color(82, 55, 56));
		nameField.setColumns(10);
		add(nameField);
		
//		비밀번호 필드
		pwField = new JPasswordField();
		pwField.setBounds(190, 160, 225, 30);
		pwField.setForeground(new Color(82, 55, 56));
		pwField.setColumns(10);
		add(pwField);
		
//		비밀번호 확인 필드
		pwReEnterField = new JPasswordField();
		pwReEnterField.setBounds(190, 215, 225, 30);
		pwReEnterField.setForeground(new Color(82, 55, 56));
		pwReEnterField.setColumns(10);
		add(pwReEnterField);

//		휴대전화 필드
		phoneField = new JTextField();
		phoneField.setBounds(190, 270, 140, 30);
		phoneField.setForeground(new Color(82, 55, 56));
		phoneField.setColumns(10);
		add(phoneField);
		
//		이메일 필드
		emailField = new JTextField();
		emailField.setBounds(190, 380, 225, 30);
		emailField.setForeground(new Color(82, 55, 56));
		emailField.setColumns(10);
		add(emailField);
		
//		성별 라디오버튼 남
		man = new JRadioButton("남");
		man.setBounds(241, 326, 60, 30);
		man.setForeground(new Color(82, 55, 56));
		man.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(man);
	
//		성별 라디오버튼 여		
		woman = new JRadioButton("여");
		woman.setBounds(337, 326, 60, 30);
		woman.setForeground(new Color(82, 55, 56));
		woman.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(woman);
		
//		성별 라디오 그룹을 생성해 두 라디오 버튼을 담는다.		
		gender = new ButtonGroup();
		gender.add(man);
		gender.add(woman);
					
//		수정하기 버튼
		actionButton = new JButton("가입하기");
		actionButton.setBounds(81, 441, 150, 60);
		actionButton.setForeground(new Color(82, 55, 56));
		actionButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(actionButton);
		
//		취소하기 버튼
		cancelButton = new JButton("취소하기");
		cancelButton.setBounds(265, 441, 150, 60);
		cancelButton.setForeground(new Color(82, 55, 56));
		cancelButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(cancelButton);
		
//		휴대폰번호 중복확인 버튼
		overlapButton = new JButton("중복확인");
		overlapButton.setBounds(338, 269, 80, 30);
		overlapButton.setForeground(new Color(82, 55, 56));
		overlapButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		add(overlapButton);

//		비밀번호 안내 레이블
		passwordHint = new JLabel("8 ~ 20자 이내로 입력");
		passwordHint.setBounds(190, 190, 220, 20);
		passwordHint.setForeground(new Color(82, 55, 56));
		passwordHint.setHorizontalAlignment(SwingConstants.CENTER);
		passwordHint.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		add(passwordHint);

		// 핸드폰 안내 레이블
		phoneHint = new JLabel("'-'를 포함 띄어쓰기 없이 입력");
		phoneHint.setBounds(175, 300, 250, 20);
		phoneHint.setForeground(new Color(82, 55, 56));
		phoneHint.setHorizontalAlignment(SwingConstants.CENTER);
		phoneHint.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		add(phoneHint);
		
//		창 모드가 수강생 정보 수정 및 강사 정보 수정인 경우 몇몇 필드를 비활성화 시킴		
		if(mode != JOIN)	// 수강생 가입 모드가 아닐경우
		{
			setTitle("정 보 수 정");
			actionButton.setText("수정하기");
			add(idLabel);
			add(idField);
			
			idField.setEditable(false);
			nameField.setEditable(false);
			man.setEnabled(false);
			woman.setEnabled(false);
			
			// 모드가 학생수정, 관리자-학생수정인 경우
			if ((mode == MODIFY || mode == MODIFY_A) && who instanceof Student)
			{
				student = (Student)who;								// Object로 들어온 객체를 다운캐스팅 해준다.
				s_check = new StudentDTO(s_dao, student);
				setStudent(student);										// 해당 객체 정보에 맞춰 컴포넌트를 값을 설정한다.
			}
			// 모드가 강사 수정일 경우
			else if (mode == T_MODIFY && who instanceof Teacher)
			{
				teacher = (Teacher)who;								// Object로 들어온 객체를 다운캐스팅 해준다.
				t_check = new TeacherDTO(t_dao, teacher);
				setTeacher(teacher);										// 해당 객체 정보에 맞춰 컴포넌트 값을 설정한다.
			}
		}
		else	// 수강생 가입모드인 경우
		{
			setTitle("회 원 가 입");
			add(join);		// 회원가입 레이블 추가
		}
		
		// 이벤트가 추가될 부분
		overlapButton.addActionListener(handler);
		actionButton.addActionListener(handler);
		cancelButton.addActionListener(handler);

		setVisible(true);
	}
	
	// 강사 수정 시 수정하려는 Teacher 객체의 정보를 각 컴포넌트에 설정시켜주는 메소드
	private void setTeacher(Teacher teacher)
	{
		idField.setText("" + teacher.getT_num());					// 강사번호
		nameField.setText(teacher.getName());					// 이름
		pwField.setText(teacher.getPassword());					// 비밀번호
		pwReEnterField.setText(teacher.getPassword());			// 비밀번호 확인
		phoneField.setText(teacher.getPhone());					// 휴대폰
		emailField.setText(teacher.getEmail());						// 이메일
		
		if (teacher.getGender() == 0)								// 성별
		{
			woman.setSelected(true); // 0 = woman
		}
		else
		{
			man.setSelected(true); // 1 = man
		}
	}
	
	// 학생 수정 시 수정하려는 Student 객체의 정보를 각 컴포넌트에 설정시켜주는 메소드
	private void setStudent(Student student)
	{
		idField.setText("" + student.getSt_num());				// 학생번호
		nameField.setText(student.getName());					// 이름
		pwField.setText(student.getPassword());					// 비밀번호
		pwReEnterField.setText(student.getPassword());			// 비밀번호 확인
		phoneField.setText(student.getPhone());					// 휴대폰
		emailField.setText(student.getEmail());						// 이메일
		
		if (student.getGender() == 0)								// 성별
		{
			woman.setSelected(true); // 0 = woman
		}
		else
		{
			man.setSelected(true); // 1 = man
		}
		
		if (mode == MODIFY)		// 수강생 수정인 경우
		{
			actionButton.setBounds(40, 441, 120, 60);
			cancelButton.setBounds(200, 441, 120, 60);
			dropOutButton = new JButton("탈퇴하기");		// 수강생 정보수정 내에서 탈퇴가 가능한 버튼 구현
			dropOutButton.setBounds(357, 441, 120, 60);
			dropOutButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			add(dropOutButton);
			dropOutButton.addActionListener(handler);
		}
		else if (mode == MODIFY_A)	// 관리자 - 수강생 수정인 경우
		{
			pwField.setEditable(false);				// 비밀번호 칸을 비활성화 시킨다.
			pwReEnterField.setEditable(false);
		}
	}
	
	// ActionListener를 구현한 Handler 클래스로 이벤트 내용을 담당한다.
	class Handler implements ActionListener
	{
		boolean checkOverlap = false;		// 중복확인 버튼을 눌렀나 확인하기 위한 변수
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == cancelButton)	// 취소 버튼을 눌렀을 경우
			{
				MemberInfo.this.dispose();		// 현재 다이얼로그 창을 닫는다.
			}
			
			// 수강생 가입할 때 이벤트
			if (mode == MemberInfo.JOIN)
			{				
				// 중복확인 버튼을 눌렀을 때 이벤트 내용
				if (e.getSource() == overlapButton)
				{
					phoneVal = phoneField.getText();				// 입력된 전화번호 값을 받아온다.
					
					if (phoneVal.equals(""))							// 전화번호를 입력하지 않았을 때
					{
						new Dialog_Default("전화번호 입력", "전화번호를 입력해주세요.");
						phoneField.requestFocus();					// 전화번호 JTextField에 포커스를 맞춘다.
					}
									
					else												// 전화번호를 입력했을 때
					{
						if(s_check.checkPhone(phoneVal))		// 전화번호를 맞게 입력 했을 때
						{
							if (s_check.overlapPhone(phoneVal) == true)	// 중복검사를 해 중복되지 않은 번호일 때
							{
								checkOverlap = true;				// 중복확인 여부를 true로 한다.
								new Dialog_Default("중복확인", "사용할 수 있는 전화번호입니다.");
							}								
							else	// 중복된 전화번호일 때
							{
								new Dialog_Default("중복확인", "이미 등록된 전화번호 입니다.");	
								phoneField.requestFocus();			// 전화번호 JTextField에 포커스를 맞춘다.
							}
						}
						else											// 전화번호를 맞게 입력하지 않았을 때
						{
							new Dialog_Default("전화번호 입력", "전화번호를 바르게 입력해주세요");
							phoneField.requestFocus();				// 전화번호 JTextField에 포커스를 맞춘다.
						}					
					}
				}
				// 가입버튼을 눌렀을 때
				else if (e.getSource() == actionButton)
				{
					// 모든 TextField의 값을 가져옴
					nameVal = nameField.getText();
					pwVal = new String(pwField.getPassword());
					pw2Val = new String(pwReEnterField.getPassword());
					emailVal = emailField.getText();
					
					// 라디오버튼은 선택 여부를 확인해 값을 저장한다.
					if (man.isSelected())
						genderVal = 1;
					else if (woman.isSelected())
						genderVal = 0;
					
					// 값을 확인해 넣는 작업
					if (checkOverlap == false)		// 휴대폰 중복확인을 하지 않았을 시
					{
						new Dialog_Default("중복 확인", "휴대폰 번호 중복확인을 해주세요");
						overlapButton.requestFocus();		// 중복확인 버튼에 포커스를 맞춘다.
					}
					else if (s_check.checkName(nameVal) == false)		// 이름을 맞게 입력하지 않았을 시
					{
						new Dialog_Default("이름 입력", "이름은 2자 이상 10자 이하입니다.");
						nameField.requestFocus();			// 이름 JTextField에 포커스를 맞춘다.
					}
					else if (!(pwVal.equals(pw2Val)))							// 패스워드, 패스워드 확인란의 값이 다를 시
					{
						new Dialog_Default("비밀번호 입력", "비밀번호를 다르게 입력하셨습니다.");
						pwField.requestFocus();				// 패스워드 JPasswordField에 포커스를 맞춘다.
					}
					else if (s_check.checkPassword(pwVal) == false)		// 비밀번호를 맞게 입력하지 않았을 시
					{
						new Dialog_Default("비밀번호 입력", "비밀번호는 8~20자로 입력해주세요.");
						pwField.requestFocus();				// 패스워드 JPasswordField에 포커스를 맞춘다. 
					}
					else if (s_check.checkGender(genderVal) == false)	// 성별을 선택하지 않았을 시
					{
						new Dialog_Default("성별 선택", "성별을 선택해주세요.");
						man.requestFocus();					// 성별 라디오버튼에 포커스를 맞춘다.
					}
					else if (s_check.checkEmail(emailVal) == false)			// 이메일을 맞게 입력하지 않았을 시
					{
						new Dialog_Default("E-mail 입력", "이메일을 바르게 입력해주세요.");
						emailField.requestFocus();			// 이메일 JTextField에 포커스를 맞춘다.
					}
					else	// 모든 값을 맞게 입력했을 시
					{
						student = s_check.getStudent();			// 값이 모두 입력된 학생 객체를 받아온다.
						
						if (s_dao.insertStudent(student) == 1)	// 학생 객체를 데이터베이스에 입력해 성공했을 시
						{
							int st_num = s_dao.findSt_num(student.getName(), student.getPhone());	// 회원 번호를 받아옴
							new Dialog_Default(student.getName(), st_num);		// 확인 다이얼로그창
							MemberInfo.this.dispose();		// 다이얼로그창이 닫히면 현재 다이얼로그창도 닫히게 한다.
						}
						else	// 데이터베이스에 입력되지 못했을 시
						{
							new Dialog_Default("등록 실패", "회원 등록에 실패하였습니다.");
						}	
					}
				}
			}
			
			// 수강생 강의 수정일 때 이벤트
			else if (mode == MemberInfo.MODIFY || mode == MemberInfo.MODIFY_A)
			{	
				// 중복확인 버튼을 눌렀을 때 이벤트 내용
				if (e.getSource() == overlapButton)
				{
					phoneVal = phoneField.getText();				// 입력된 전화번호 값을 가져옴
					
					if (phoneVal.equals(""))							// 전화번호를 입력하지 않았을 때
					{
						new Dialog_Default("전화번호 입력", "전화번호를 입력해주세요.");
						phoneField.requestFocus();					// 전화번호 JTextField에 포커스를 맞춘다.
					}
									
					else
					{
						if(s_check.checkPhone(phoneVal))		// 전화번호를 맞게 입력 했을 때
						{
							// 전화번호가 가입 때 썼던 번호이거나 중복검사에서 중복되지 않았을 때
							if (phoneVal.equals(student.getPhone()) || (s_check.overlapPhone(phoneVal) == true))
							{
								checkOverlap = true;		// 중복검사 여부를 true로 한다.
								new Dialog_Default("중복확인", "사용할 수 있는 전화번호입니다.");
							}
								
							else	// 중복된 전화번호 일 때
							{
								new Dialog_Default("중복확인", "이미 등록된 전화번호 입니다.");	
								phoneField.requestFocus();			// 전화번호 JTextField에 포커스를 맞춘다.
							}
						}
						else		// 전화번호를 바르게 입력하지 않았을 때
						{
							new Dialog_Default("전화번호 입력", "전화번호를 올바르게 입력해주세요");
							phoneField.requestFocus();				// 전화번호 JTextField에 포커스를 맞춘다.
						}					
					}
				}
				// 수정버튼을 눌렀을 때
				else if (e.getSource() == actionButton)
				{
					// 입력된 값을 가져온다.
					pwVal = new String(pwField.getPassword());
					pw2Val = new String(pwReEnterField.getPassword());
					emailVal = emailField.getText();
			
					// 값을 확인해 넣는 작업
					if (checkOverlap == false)				// 중복확인을 하지 않았을 때
					{
						new Dialog_Default("중복 확인", "휴대폰 번호 중복확인을 해주세요");
						overlapButton.requestFocus();		// 중복확인에 포커스를 맞춘다.
					}
					else if (!(pwVal.equals(pw2Val)))		// 패스워드를 다르게 입력했을 때
					{
						new Dialog_Default("비밀번호 입력", "비밀번호를 다르게 입력하셨습니다.");
						pwField.requestFocus();				// 패스워드 JPasswordField에 포커스를 맞춘다.
					}
					else if (s_check.checkPassword(pwVal) == false)	// 비밀번호를 바르게 입력하지 않았을 때
					{
						new Dialog_Default("비밀번호 입력", "비밀번호는 8 ~ 20자로 입력해주세요.");
						pwField.requestFocus();				// 패스워드 JPasswordField에 포커스를 맞춘다.
					}
					else if (s_check.checkEmail(emailVal) == false)		// 이메일을 바르게 입력하지 않았을 때
					{
						new Dialog_Default("E-mail 입력", "이메일을 입력해주세요.");
						emailField.requestFocus();			// 이메일 JTextField에 포커스를 맞춘다.
					}
					else		// 모든 값을 바르게 입력했을 때
					{
						student = s_check.getStudent();	// 정보가 모두 입력된 Student 인스턴스를 받아온다.
						
						if(s_dao.updateStudent(student) == 1)		// 정보 수정에 성공했을 시
						{
							new Dialog_Default("수정 성공", "수정에 성공하였습니다.");
						}
						else	// 정보 수정에 실패했을 시
						{
							new Dialog_Default("수정 실패", "수정에 실패하였습니다.");
						}
						MemberInfo.this.dispose();		// 다이얼로그창이 닫힌 뒤 해당 다이얼로그 창도 닫히게 한다.
					}
				}
				
				// 탈퇴 버튼을 눌렀을 시
				else if (e.getSource() == dropOutButton)
				{
					new Dialog_Select("탈퇴 확인", "정말로 탈퇴하시겠습니까?", Dialog_Select.MEMBER_DELETE, student.getSt_num());
				}
			}
			
			// 강사 수정일 때
			else if (mode == MemberInfo.T_MODIFY)
			{
				// 중복확인 버튼을 눌렀을 때 이벤트 내용
				if (e.getSource() == overlapButton)
				{
					phoneVal = phoneField.getText();				// 입력된 전화번호 값을 가져온다.
					
					if (phoneVal.equals(""))							// 전화번호를 입력하지 않았을 때
					{
						new Dialog_Default("전화번호 입력", "전화번호를 입력해주세요.");
						phoneField.requestFocus();					// 전화번호 JTextField에 포커스를 맞춘다.
					}
									
					else		// 모든 값을 바르게 입력했을 때
					{
						if(t_check.checkPhone(phoneVal))		// 전화번호를 맞게 입력 했을 때
						{
							// 전화번호가 가입 때 썼던 번호이거나 중복검사에서 중복되지 않았을 때
							if (phoneVal.equals(teacher.getPhone()) || (t_check.overlapPhone(phoneVal) == true))
							{
								checkOverlap = true;			// 중복검사 여부를 true로 바꾼다.
								new Dialog_Default("중복확인", "사용할 수 있는 전화번호입니다.");
							}
								
							else	// 전화번호가 중복일 때
							{
								new Dialog_Default("중복확인", "이미 등록된 전화번호 입니다.");	
								phoneField.requestFocus();		// 전화번호 JTextField에 포커스를 맞춘다.
							}
						}
						else		// 전화번호를 입력하지 않았을 때
						{
							new Dialog_Default("전화번호 입력", "전화번호를 올바르게 입력해주세요");
							phoneField.requestFocus();			// 전화번호 JTextField에 포커스를 맞춘다.
						}					
					}
				}
				// 수정버튼을 눌렀을 시
				else if (e.getSource() == actionButton)
				{
					// 입력된 값을 가져온다.
					pwVal = new String(pwField.getPassword());
					pw2Val = new String(pwReEnterField.getPassword());
					emailVal = emailField.getText();
			
					// 값을 확인해 넣는 작업
					if (checkOverlap == false)		// 중복확인 버튼을 누르지 않았을 시
					{
						new Dialog_Default("중복 확인", "휴대폰 번호 중복확인을 해주세요");
						overlapButton.requestFocus();		// 중복확인 버튼에 포커스를 맞춘다.
					}
					else if (!(pwVal.equals(pw2Val)))		// 비밀번호를 다르게 입력했을 시
					{
						new Dialog_Default("비밀번호 입력", "비밀번호를 다르게 입력하셨습니다.");
						pwField.requestFocus();				// 패스워드 JPasswordField에 포커스를 맞춘다.
					}
					else if (t_check.checkPassword(pwVal) == false)	// 비밀번호를 바르게 입력하지 않았을 시
					{
						new Dialog_Default("비밀번호 입력", "비밀번호는 8 ~ 20자로 입력해주세요.");
						pwField.requestFocus();				// 패스워드 JPasswordField에 포커스를 맞춘다.
					}
					else if (t_check.checkEmail(emailVal) == false)		// 이메일을 바르게 입력하지 않았을 때
					{
						new Dialog_Default("E-mail 입력", "이메일을 입력해주세요.");
						emailField.requestFocus();			// 이메일 JTextField에 포커스를 맞춘다.
					}
					else		// 모든 값을 바르게 입력했을 때
					{
						teacher = t_check.getTeacher();		// 값이 모두 입력된 Teacher 인스턴스를 받아온다.
						
						if(t_dao.updateTeacher(teacher) == 1)		// 수정에 성공했을 시
						{
							new Dialog_Default("수정 성공", "수정에 성공하였습니다.");
						}
						else	// 수정에 실패했을 시
						{
							new Dialog_Default("수정 실패", "수정에 실패하였습니다.");
						}
						MemberInfo.this.dispose();		// 다이얼로그창이 닫힌 뒤 해당 다이얼로그 창도 닫히게 한다.
					}
				}
			}
		}
	}
}
