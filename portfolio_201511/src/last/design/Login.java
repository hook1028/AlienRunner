package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// 로그인 클래스
public class Login extends JFrame
{	
	// 멤버 변수
	private JPanel mainPane;
	private JTextField loginBlank;
	private JPasswordField passwordBlank;	// 비밀번호는 getPassword() 메소드로 가져온다.
	private JButton loginButton;
	private JButton joinButton;
	private JButton findButton;
	private JButton adminButton;
	private JLabel numLabel;
	private JLabel pwLabel;
	private JLabel mainLabel;
	private ImageIcon logo;
	private JLabel logo_l;
	private ImageIcon img;
	
	// 이벤트 다루는 클래스
	private Handler handler = new Handler();
	
	// JDBC 연동하는 클래스
	Admin admin;
	Teacher teacher;
	Student student;
	AdminDAO a_dao;
	StudentDAO s_dao;
	TeacherDAO t_dao;
	
	// TextField에서 값을 가져오는 변수
	private String idVal;
	private String pwVal;

	// 생성자
	public Login()
	{
//		Frame 기본 설정
		setFont(new Font("맑은 고딕", Font.BOLD, 13));				// JFrame의 폰트를 설정한다.
		setResizable(false);												// JFrame의 크기를 조절하지 못하게 한다.
		setTitle("(주) 하 리 보 어 학 원");								// JFrame의 타이틀을 설정한다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// JFrame의 X 버튼 동작을 설정한다.
		setSize(900, 650);												// JFrame의 크기를 설정한다.
		setLocationRelativeTo(null);									// JFrame의 위치를 스크린 가운데에 둔다.
				        
        img = new ImageIcon("image/icon.png");	// 아이콘에 쓰일 이미지를 가져온다.
        setIconImage(img.getImage());				// 가져온 이미지를 아이콘으로 설정한다.
				
        // 패널을 생성해 그것을 Frame의 기본 ContentPane (컴포넌트를 올려놓는 판)으로 설정한다.
		mainPane = new JPanel();
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		// JDBC 연동 관련 클래스 초기화
		a_dao = AdminDAO.getInstance();
		admin = a_dao.createAdmin();
					
//		로그인 버튼
		loginButton = new JButton("로 그 인");
		loginButton.setForeground(new Color(82, 55, 56));
		loginButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		loginButton.setBounds(500, 310, 120, 90);
		mainPane.add(loginButton);
		
//		회원가입 버튼
	    joinButton = new JButton("회 원 가 입");
	    joinButton.setForeground(new Color(82, 55, 56));
		joinButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		joinButton.setBounds(260, 410, 150, 60);
		mainPane.add(joinButton);
		
//		회원번호/비밀번호 찾기 버튼
	    findButton = new JButton("회원번호 / 비밀번호 찾기");
	    findButton.setForeground(new Color(82, 55, 56));
		findButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		findButton.setBounds(420, 410, 200, 60);
		mainPane.add(findButton);
		
//		회원번호 입력란
		loginBlank = new JTextField();
		loginBlank.setForeground(new Color(82, 55, 56));
		loginBlank.setBounds(340, 310, 150, 40);
		loginBlank.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		mainPane.add(loginBlank);
		loginBlank.setColumns(10);
		
//		비밀번호 입력란
		passwordBlank = new JPasswordField();
		passwordBlank.setForeground(new Color(82, 55, 56));
		passwordBlank.setColumns(10);
		passwordBlank.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		passwordBlank.setBounds(340, 360, 150, 40);
		mainPane.add(passwordBlank);
		
//		회원번호 텍스트
		numLabel = new JLabel("회원번호");
		numLabel.setForeground(new Color(82, 55, 56));
		numLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		numLabel.setBounds(260, 310, 80, 40);
		mainPane.add(numLabel);
		
//		비밀번호 텍스트
		pwLabel = new JLabel("비밀번호");
		pwLabel.setForeground(new Color(82, 55, 56));
		pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pwLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		pwLabel.setBounds(260, 360, 80, 40);
		mainPane.add(pwLabel);
		
		// 로고 이미지
		logo = new ImageIcon("image/logo.png");		// 로고 이미지를 가져온다.
		logo_l = new JLabel(logo);							// 가져온 이미지를 JLabel에 넣는다.
		logo_l.setBounds(350, 40, 200, 200);				// JLabel의 크기와 위치 설정
		add(logo_l);
		
//		중앙 메인 텍스트
	    mainLabel = new JLabel("하 리 보   어 학 원");
	    mainLabel.setForeground(new Color(82, 55, 56));
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		mainLabel.setBounds(270, 200, 350, 80);
		mainPane.add(mainLabel);
		
		if(admin.getId() == null)		// 관리자 객체가 비어있으면 = 관리자 등록이 안되어있으면
		{
			adminButton = new JButton("관리자 등록");
			adminButton.setForeground(new Color(82, 55, 56));
			adminButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			adminButton.setBounds(400, 480, 100, 40);
			mainPane.add(adminButton);
			adminButton.addActionListener(handler);
		}
		
		// 이벤트 구현할 부분
		passwordBlank.addActionListener(handler);
		loginButton.addActionListener(handler);
		joinButton.addActionListener(handler);
		findButton.addActionListener(handler);
					
		setVisible(true);
	}
	
	public void setAdmin(Admin admin)		// 관리자 정보를 Admin 객체로 받아오는 메소드
	{
		this.admin = admin;		// 파라미터로 받아온 Admin 인스턴스를 멤버 변수에 넣는다.
	}
	
	// 이벤트와 관련된 클래스
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// 로그인 버튼을 눌렀을 때
			if (e.getSource() == loginButton || e.getSource() == passwordBlank)
			{
				idVal = loginBlank.getText();								// JTextField에서 id 값을 가져온다.
				pwVal = new String(passwordBlank.getPassword());	// JPasswordField에서 pw 값을 가져온다.
				
				if (idVal.equals(""))				// id 입력란에 입력 값이 없을 시
				{
					new Dialog_Default("로그인 실패", "회원번호를 입력해주세요.");
					loginBlank.requestFocus();		// id JTextField에 포커스를 맞춘다.
				}
				else if (pwVal.equals(""))		// pw 입력란에 입력 값이 없을 시
				{
					new Dialog_Default("로그인 실패", "비밀번호를 입력해주세요.");
					passwordBlank.requestFocus();	// pw JPasswordField에 포커스를 맞춘다.
				}
				else if (idVal.equals(admin.getId()))		// id 입력값이 관리자 아이디인 경우
				{
					// 관리자 비밀번호와 입력한 비밀번호가 맞는지 확인한 후에 관리자 패널 띄우기
					if (pwVal.equals(admin.getPassword()))	// id, pw가 모두 맞을 경우
					{
						Login.this.dispose();		// 로그인 창을 닫는다.
						Main.mainFrame = new OverrallFrame(new ManagerTop(admin));	// 관리자모드 창 실행
					}
					else	// 비밀번호가 맞지 않을 경우
					{
						new Dialog_Default("로그인 실패", "회원번호, 비밀번호를 확인해 주세요.");
						loginBlank.requestFocus();
					}
				}
				else	// 관리자 아이디가 아닐 경우
				{
					int int_id = 0;	// 회원 번호 혹은 강사 번호를 받을 변수
					
					try		// try-catch로 문자열을 숫자로 바꾸는 처리를 한다.
					{
						int_id = Integer.parseInt(idVal);
					}
					catch (NumberFormatException ne)	// 문자열일 시 Exception 발생
					{
						new Dialog_Default("회원번호 입력", "회원번호를 바르게 입력해주세요");
						loginBlank.requestFocus();			// id 입력란에 포커스를 맞춘다.
						return;
					}
					
					if ((int_id / 100) > 99)			// 학생인 경우
					{
						// DB 연동과 관련된 멤버 변수 초기화
						s_dao = StudentDAO.getInstance();
						student = s_dao.createStudent(int_id);
						
						if (student.getSt_num() == 0)	// 없는 회원의 아이디는 0으로 출력됨 = 해당 회원이 없는 경우
						{
							new Dialog_Default("로그인 실패", "회원번호나 비밀번호를 확인해주세요.");
							loginBlank.requestFocus();	// id 입력란에 포커스를 맞춘다.
						}
						else	// 해당 회원이 있는 경우
						{
							if (pwVal.equals(student.getPassword()))	// 패스워드가 맞는 지 확인해, 맞을 경우
							{
								Login.this.dispose();		// 로그인 창을 닫는다.
								Main.mainFrame = new OverrallFrame(new Student_Teacher_Top(student));	// 학생 모드 메인 창 띄움
							}
							else	// 패스워드가 맞지 않을 경우
							{
								new Dialog_Default("로그인 실패", "회원번호나 비밀번호를 확인해주세요.");
								loginBlank.requestFocus();
							}
						}
					}
					else if ((int_id / 100) < 10)	// 강사인 경우
					{
						// DB와 관련된 변수 초기화
						t_dao = TeacherDAO.getInstance();
						teacher = t_dao.createTeacher(int_id);
						
						if (teacher.getT_num() == 0)	// 없는 회원의 아이디는 0으로 출력됨 = 해당 강사가 없을 경우
						{
							new Dialog_Default("로그인 실패", "회원번호나 비밀번호를 확인해주세요.");
							loginBlank.requestFocus();		// id 입력란에 포커스를 맞춘다.
						}
						else	// 해당 강사가 있을 경우
						{
							if (pwVal.equals(teacher.getPassword()))		// 비밀번호가 맞을 경우
							{
								Login.this.dispose();		// 로그인 창을 닫는다.
								Main.mainFrame = new OverrallFrame(new Student_Teacher_Top(teacher));	// 강사 메인창을 띄운다.
							}
							else	// 비밀번호가 틀릴 경우
							{
								new Dialog_Default("로그인 실패", "회원번호나 비밀번호를 확인해주세요.");
								loginBlank.requestFocus();
							}
						}
					}
				}	
			}
			
			// 회원 가입 버튼을 눌렀을 때 
			else if (e.getSource() == joinButton)
			{
				new MemberInfo(MemberInfo.JOIN, null);		// 회원 가입창을 띄운다.
			}
			
			// 아이디 비밀번호 찾기 버튼을 눌렀을 때
			else if (e.getSource() == findButton)
			{
				new FindNumDialog();		// 아이디 비밀번호 찾기 창을 띄운다.
			}
			
			// 관리자 등록 버튼을 눌렀을 때
			else if (e.getSource() == adminButton)
			{
				new AdminRegister();		// 관리자 등록 창을 띄운다.
				
				if (admin.getId() != null)	// 관리자 등록이 완료된 뒤
				{
					Login.this.adminButton.setVisible(false);		// 관리자 버튼을 안보이게 한다.
				}				
			}
		}
	}
}