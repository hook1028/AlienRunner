package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;
import java.util.*;

public class TeacherRegister extends JDialog
{
	// UI에 쓰이는 컴포넌트
	private JTextField nameField;
	private JPasswordField pwField;			// 비밀번호는 getPassword() 메소드로 가져온다.
	private JPasswordField pwReEnterField;
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField idField;
	private JLabel registerLabel;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel pwLabel;
	private JLabel pwReEnter;
	private JLabel phoneLabel;
	private JLabel languageLabel;
	private JLabel transLabel;
	private JLabel emailLabel;
	private JLabel passwordHint;
	private JLabel phoneHint;
	private JRadioButton man;
	private JRadioButton woman;
	private ButtonGroup gender;
	private JRadioButton english;
	private JRadioButton chinese;
	private JRadioButton japanese;
	private ButtonGroup language;
	private JButton editButton;
	private JButton cancelButton;
	private JButton overlapButton;
	
	// 이벤트를 담당하는 클래스
	private Handler handler = new Handler();
	
	// JDBC 연동 관련 클래스
	private TeacherDAO t_dao;
	private TeacherDTO t_check;
	private Teacher teacher;
	
	// 값을 받아올 변수
	private int genderVal;
	private String nameVal;
	private String pwVal;
	private String pw2Val;
	private String phoneVal;
	private String emailVal;
	private String langVal;
	
	// 생성자
	public TeacherRegister() 
	{		
		// TeacherRegister Dialog 기본 설정
		super(Main.mainFrame);
		setSize(520, 640);
		Dialog_Default.init(this, "강 사 등 록");
		setLayout(null);
		
		// JDBC 연동과 관련된 클래스 초기화
		t_dao = TeacherDAO.getInstance();
	    t_check = new TeacherDTO(t_dao);
	    teacher = new Teacher();

		// 강사등록 레이블
		registerLabel = new JLabel("강 사 등 록");
		registerLabel.setBounds(190, 30, 150, 50);
		registerLabel.setForeground(new Color(82, 55, 56));
		registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		add(registerLabel);
		
		// 이름 레이블
		nameLabel = new JLabel("이 름");
		nameLabel.setBounds(15, 105, 130, 30);
		nameLabel.setForeground(new Color(82, 55, 56));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(nameLabel);

		// 비밀번호 레이블
		pwLabel = new JLabel("비밀번호");
		pwLabel.setBounds(15, 160, 130, 30);
		pwLabel.setForeground(new Color(82, 55, 56));
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(pwLabel);
	
		// 비밀번호 확인 레이블
		pwReEnter = new JLabel("비밀번호 확인");
		pwReEnter.setBounds(15, 217, 130, 30);
		pwReEnter.setForeground(new Color(82, 55, 56));
		pwReEnter.setHorizontalAlignment(SwingConstants.RIGHT);
		pwReEnter.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(pwReEnter);

//		// 휴대폰번호 레이블
		phoneLabel = new JLabel("휴대폰번호");
		phoneLabel.setBounds(15, 270, 130, 30);
		phoneLabel.setForeground(new Color(82, 55, 56));
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(phoneLabel);
		
		// 언어 레이블
		languageLabel = new JLabel("언 어");
		languageLabel.setBounds(15, 333, 130, 30);
		languageLabel.setForeground(new Color(82, 55, 56));
		languageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(languageLabel);
		languageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

		// 성별 레이블
		transLabel = new JLabel("성 별");
		transLabel.setBounds(15, 390, 130, 30);
		transLabel.setForeground(new Color(82, 55, 56));
		transLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		transLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(transLabel);

		// 이메일 레이블
		emailLabel = new JLabel("E - Mail");
		emailLabel.setBounds(15, 435, 130, 30);
		emailLabel.setForeground(new Color(82, 55, 56));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		add(emailLabel);

		// 이름 필드
		nameField = new JTextField();
		nameField.setBounds(190, 105, 225, 30);
		nameField.setForeground(new Color(82, 55, 56));
		add(nameField);
		nameField.setColumns(10);

		// 비밀번호 필드
		pwField = new JPasswordField();
		pwField.setBounds(190, 160, 225, 30);
		pwField.setForeground(new Color(82, 55, 56));
		add(pwField);
		pwField.setColumns(10);
		
		// 비밀번호 수정 필드
		pwReEnterField = new JPasswordField();
		pwReEnterField.setBounds(190, 215, 225, 30);
		pwReEnterField.setForeground(new Color(82, 55, 56));
		add(pwReEnterField);
		pwReEnterField.setColumns(10);
		
		// 휴대폰번호 필드
		phoneField = new JTextField();
		phoneField.setBounds(190, 270, 140, 30);
		phoneField.setForeground(new Color(82, 55, 56));
		add(phoneField);
		phoneField.setColumns(10);

		// 이메일 필드
		emailField = new JTextField();
		emailField.setBounds(190, 435, 225, 30);
		emailField.setForeground(new Color(82, 55, 56));
		add(emailField);
		emailField.setColumns(10);
	
		// 언어 라디오 버튼<영어>
		english = new JRadioButton("영어");
		english.setBounds(166, 335, 75, 20);
		english.setForeground(new Color(82, 55, 56));
		english.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(english);

		// 언어 라디오 버튼<중국어>
		chinese = new JRadioButton("중국어");
		chinese.setBounds(265, 335, 75, 19);
		chinese.setForeground(new Color(82, 55, 56));
		chinese.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(chinese);

		// 언어 라디오 버튼<일본어>
		japanese = new JRadioButton("일본어");
		japanese.setBounds(375, 335, 75, 19);
		japanese.setForeground(new Color(82, 55, 56));
		japanese.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(japanese);
	
		// 언어 라디오 그룹을 생성해 세 라디오 버튼을 담는다.
		language = new ButtonGroup();
		language.add(english);
		language.add(chinese);
		language.add(japanese);

		// 성별 체크박스 남
		man = new JRadioButton("남");
		man.setBounds(240, 380, 60, 30);
		man.setForeground(new Color(82, 55, 56));
		man.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(man);

		// 성별 체크박스 여
		woman = new JRadioButton("여");
		woman.setBounds(340, 380, 60, 30);
		woman.setForeground(new Color(82, 55, 56));
		woman.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		add(woman);

		// 성별 라디오 그룹을 생성해 두 라디오 버튼을 담는다.		
		gender = new ButtonGroup();
		gender.add(man);
		gender.add(woman);

		// 등록하기 버튼
		editButton = new JButton("등록하기");
		editButton.setBounds(82, 518, 150, 60);
		editButton.setForeground(new Color(82, 55, 56));
		editButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(editButton);

		// 취소하기 버튼
		cancelButton = new JButton("취소하기");
		cancelButton.setBounds(265, 518, 150, 60);
		cancelButton.setForeground(new Color(82, 55, 56));
		cancelButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(cancelButton);

		// 휴대폰번호 중복확인 버튼
		overlapButton = new JButton("중복확인");
		overlapButton.setBounds(335, 268, 80, 30);
		overlapButton.setForeground(new Color(82, 55, 56));
		overlapButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		add(overlapButton);

		// 특수문자 제외하고 입력 레이블
		passwordHint = new JLabel("8 ~ 20자 이내로 입력");
		passwordHint.setBounds(190, 190, 220, 20);
		passwordHint.setForeground(new Color(82, 55, 56));
		passwordHint.setHorizontalAlignment(SwingConstants.CENTER);
		passwordHint.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		add(passwordHint);
		
		// 핸드폰 번호 입력 힌트 레이블
		phoneHint = new JLabel("'-'를 포함 띄어쓰기 없이 입력");
		phoneHint.setBounds(175, 300, 250, 20);
		phoneHint.setForeground(new Color(82, 55, 56));
		phoneHint.setHorizontalAlignment(SwingConstants.CENTER);
		phoneHint.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		add(phoneHint);

		// 이벤트를 구현하는 부분
		overlapButton.addActionListener(handler);
		editButton.addActionListener(handler);
		cancelButton.addActionListener(handler);
		
		setVisible(true);
	}
	
	class Handler implements ActionListener 
	{
		boolean checkOverlap = false;		// 휴대폰 중복확인 버튼을 눌렀나 확인할 변수

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == cancelButton)		// 취소버튼을 눌렀을 때 
			{	
				TeacherRegister.this.dispose();		// 창을 닫게 한다.
			}

			// 중복버튼
			if (e.getSource() == overlapButton)		// 중복버튼을 눌렀을 때
			{
				phoneVal = phoneField.getText();		// 입력받은 값을 가져온다.

				if (phoneVal.equals("")) // 전화번호를 입력하지 않았을 때
				{
					new Dialog_Default("전화번호 입력", "전화번호를 입력해주세요.");
					phoneField.requestFocus();		// 전화번호 JTextField에 포커스를 맞춘다.
				}

				else
				{
					if (t_check.checkPhone(phoneVal)) // 전화번호를 맞게 입력 했을 때
					{
						if (t_check.overlapPhone(phoneVal) == true) // 전화번호가 중복되지 않았을 시
						{
							checkOverlap = true;		// 중복확인 여부를 true로 바꾼다.
							new Dialog_Default("중복확인", "사용할 수 있는 전화번호입니다.");
						}

						else	// 전화번호가 중복되었을 시 
						{
							new Dialog_Default("중복확인", "이미 등록된 전화번호 입니다.");
							phoneField.requestFocus();		// 전화번호 JTextField에 포커스를 맞춘다.
						}
					} 
					else // 전화번호를 형식에 맞게 입력하지 않았을 시
					{
						new Dialog_Default("전화번호 입력", "전화번호를 올바르게 입력해주세요");
						phoneField.requestFocus();		// 전화번호 JTextField에 포커스를 맞춘다.
					}
				}
			}

			// 등록하기 버튼을 눌렀을 때
			else if (e.getSource() == editButton)
			{
				// 입력 값을 받아오는 부분
				nameVal = nameField.getText();
				pwVal = new String(pwField.getPassword());
				pw2Val = new String(pwReEnterField.getPassword());
				emailVal = emailField.getText();

				if (english.isSelected())			// 영어가 선택됐을 시
				{
					langVal = "영어";
				}
				else if (chinese.isSelected())	// 중국어가 선택됐을 시
				{
					langVal = "중국어";
				}
				else if (japanese.isSelected())	// 일본어가 선택됐을 시
				{
					langVal = "일본어";
				}

				if (man.isSelected())				// 남자가 선택됐을 시
					genderVal = 1;
				else if (woman.isSelected())	// 여자가 선택됐을 시
					genderVal = 0;

				// 값이 제대로 들어갔는 지 체크하는 부분
				if (checkOverlap == false) 							// 중복확인 버튼을 누르지 않았을 시
				{
					new Dialog_Default("중복 확인", "휴대폰 번호 중복확인을 해주세요");
					overlapButton.requestFocus();		// 전화번호 JTextField에 포커스를 맞춘다.
				}
				else if (t_check.checkName(nameVal) == false) 	// 이름을 2~10자로 입력하지 않았을 시
				{
					new Dialog_Default("이름 입력", "이름은 2자 이상 10자 이하입니다.");
					nameField.requestFocus();			// 이름 JTextField에 포커스를 맞춘다.
				}
				else if (!(pwVal.equals(pw2Val))) 						// 비밀번호를 다르게 입력했을 시
				{
					new Dialog_Default("비밀번호 입력", "비밀번호를 다르게 입력하셨습니다.");
					pwField.requestFocus();				// 비밀번호 JPasswordField에 포커스를 맞춘다.
				} 
				else if (t_check.checkPassword(pwVal) == false)	// 비밀번호를 8~20자로 입력하지 않았을 시
				{
					new Dialog_Default("비밀번호 입력", "비밀번호는 8 ~ 20자로 입력해주세요.");
					pwField.requestFocus();				// 비밀번호 JPasswordField에 포커스를 맞춘다.
				} 
				else if (t_check.checkT_num(langVal) == false)	// 언어를 선택하지 않았을 시
				{
					new Dialog_Default("언어 선택", "담당 언어를 선택해주세요.");
					english.requestFocus();				// 언어 JRadioButton에 포커스를 맞춘다.
				} 
				else if (t_check.checkGender(genderVal) == false) 	// 성별을 선택하지 않았을 시
				{
					new Dialog_Default("성별 선택", "성별을 선택해주세요.");
					man.requestFocus();					// 성별 JRadioButton에 포커스를 맞춘다.
				} 
				else if (t_check.checkEmail(emailVal) == false)			// 이메일을 입력하지 않았을 시
				{
					new Dialog_Default("E-mail 입력", "이메일을 입력해주세요.");
					emailField.requestFocus();			// 이메일 JTextField에 포커스를 맞춘다.
				} 
				else 	// 모두 맞게 입력했을 시
				{
					teacher = t_check.getTeacher(); 		// 값을 모두 입력한 강사 객체를 돌려받음
					
					if (t_dao.insertTeacher(teacher) == 1)	// 데이터베이스에 강사를 등록하는데 성공했을 시
					{
						new Dialog_Default("등록 완료", "등록이 완료되었습니다.");
						TeacherRegister.this.dispose();		// 등록한 후 다이얼로그 창을 닫는다.
					}
					else
					{
						new Dialog_Default("등록 실패", "등록이 실패했습니다.");
					}			
				}
			}
		}
	}
}
