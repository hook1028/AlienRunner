package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// 관리자 등록 다이얼로그 클래스
public class AdminRegister extends JDialog
{	
	// UI를 만드는 변수
	private JTextField id_tf;
	private JPasswordField pw_tf;
	private JPasswordField pw2_tf;
	private JLabel id_lb;
	private JLabel pw_lb;
	private JLabel pw2_lb;
	private JButton reg_btn;
	private JLabel pwHint_lb;
	
//	이벤트 핸들러 클래스
	private Handler handler = new Handler();
	
//	TextField에서 가져온 값을 저장할 변수
	private String idVal;
	private String pwVal;
	private String pw2Val;
	
//	DB 연동에 사용될 클래스	
	private AdminDAO a_dao;
	private Admin admin;
	
	// 생성자
	public AdminRegister()
	{
//		JDailog 초기화
		super(Main.loginFrame);					// Owner 프레임 설정
		setSize(400, 200);							// Dialog의 크기 설정
		Dialog_Default.init(this, "관리자 등록");	// Dialog의 제목 및 기본 설정을 지정한다.
		
//		아이디 텍스트
		id_lb = new JLabel("ID");
		id_lb.setHorizontalAlignment(SwingConstants.RIGHT);		// JLabel의 수평정렬을 설정한다.
		id_lb.setForeground(new Color(82, 55, 56));					// JLabel의 글자색을 설정한다.
		id_lb.setFont(new Font("맑은 고딕", Font.BOLD, 14));		// JLabel의 폰트를 설정한다.
		id_lb.setBounds(20, 30, 90, 20);								// JLabel의 위치와 크기를 설정한다.
		add(id_lb);														// Dialog에 라벨을 넣는다.
		
//		비밀번호 텍스트
		pw_lb = new JLabel("비밀 번호");
		pw_lb.setHorizontalAlignment(SwingConstants.RIGHT);
		pw_lb.setForeground(new Color(82, 55, 56));
		pw_lb.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		pw_lb.setBounds(20, 60, 90, 20);
		add(pw_lb);
		
//		아이디를 입력받을 JTextField
		id_tf = new JTextField();
		id_tf.setBounds(120, 30, 120, 23);
		id_tf.setForeground(new Color(82, 55, 56));
		add(id_tf);
		id_tf.setColumns(10);
		
//		비밀번호를 입력받을 JPasswordField
		pw_tf = new JPasswordField();
		pw_tf.setBounds(120, 60, 120, 23);
		pw_tf.setForeground(new Color(82, 55, 56));
		add(pw_tf);
		pw_tf.setColumns(10);
		
		// 비밀번호 안내 JLabel
		pwHint_lb = new JLabel("8 ~ 20 자 이내로 입력");
		pwHint_lb.setBounds(123, 80, 155, 20);
		pwHint_lb.setForeground(new Color(82, 55, 56));
		pwHint_lb.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		add(pwHint_lb);
		
//		비밀번호 확인 JLabel
		pw2_lb = new JLabel("비밀번호 확인");
		pw2_lb.setHorizontalAlignment(SwingConstants.RIGHT);
		pw2_lb.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		pw2_lb.setForeground(new Color(82, 55, 56));
		pw2_lb.setBounds(20, 105, 90, 20);
		add(pw2_lb);
				
//		비밀번호 확인 JPasswordField
		pw2_tf = new JPasswordField();
		pw2_tf.setColumns(10);
		pw2_tf.setForeground(new Color(82, 55, 56));
		pw2_tf.setBounds(120, 105, 120, 23);
		add(pw2_tf);
		
//		등록 버튼
		reg_btn = new JButton("등록");
		reg_btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		reg_btn.setForeground(new Color(82, 55, 56));
		reg_btn.setBounds(270, 48, 100, 60);
		add(reg_btn);		
		
		// 이벤트를 추가하는 부분 (다이얼로그는 setVisible 이후의 작업은 실행하지 않기 때문에 setVisible 이전에 작성)
		reg_btn.addActionListener(handler);	
		
		setVisible(true);
	}
	
	// 이벤트를 담당하는 내부 클래스
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// DB연동과 관련된 클래스 초기화
			a_dao = AdminDAO.getInstance();
			admin = new Admin(); 
		 	   
			// 등록버튼을 눌렀을 때
			if (e.getSource() == reg_btn)
			{
				idVal = id_tf.getText();								// 사용자가 입력한 id 값을 가져온다.
				pwVal = new String(pw_tf.getPassword());		// 사용자가 입력한 pw 값을 가져온다.
				pw2Val = new String(pw2_tf.getPassword());	// 사용자가 입력한 pw 확인 값을 가져온다.
				
				if (pwVal.equals(pw2Val))		// 패스워드와 패스워드 확인에 입력한 값이 같다면
				{
					if(pwVal.length() >= 8 && pwVal.length() <= 20)		// 패스워드의 길이가 8자 이상 20자 이하인지 확인해 맞다면
					{
						admin.setId(idVal);					// Admin 객체에 id 값을 넣는다.
						admin.setPassword(pwVal);		// Admin 객체에 pw 값을 넣는다.
						
						if (a_dao.insertAdmin(admin) == 1)		// DB에 정보가 들어갔으면
						{
							new Dialog_Default("등록 성공", "관리자 등록에 성공하였습니다.");
							Main.loginFrame.setAdmin(admin);	// Login 페이지에 등록한 관리자 정보를 보낸다.
							AdminRegister.this.dispose();			// 다이얼로그가 닫힌 뒤 현재 창을 닫는다.
						}
						else	// DB에 정보가 들어가지 못했다면
						{
							new Dialog_Default("등록 실패", "관리자 등록에 실패하였습니다.");
						}
					}
					else		// 비밀번호의 길이가 8 ~ 20자가 아니라면
					{
						new Dialog_Default("오류", "비밀번호는 8~20자 이내로 입력해주세요.");
						pw_tf.requestFocus();		// 패스워드 JTextField에 포커스를 맞춰준다.
					}
				}
				else			// 비밀번호, 비밀번호 확인의 값이 다르다면
				{
					new Dialog_Default("오류", "비밀번호를 다르게 입력하셨습니다.");
					pw_tf.requestFocus();			// 패스워드 JTextField에 포커스를 맞춰준다.
				}
			}	
		}	
	}
}