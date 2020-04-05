package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// 수강생 회원 번호 및 비밀번호 찾기 다이얼로그 창
public class FindNumDialog extends JDialog
{	
	// UI를 만드는 변수
	private JTextField id_nameBlank;
	private JTextField id_phoneBlank;
	private JTextField pw_numberBlack;
	private JTextField pw_phoneBlack;
	private JLabel id_nameLabel;
	private JLabel id_phoneLabel;
	private JLabel pw_numberLabel;
	private JLabel pw_phoneLabel;
	private JButton findNumberButton;
	private JButton findPWButton;
	private JSeparator separator;
	private JLabel find_num;
	private JLabel find_pw;
	private JLabel phoneInfoLabel1;
	private JLabel phoneInfoLabel2;

//	이벤트 핸들러 클래스
	private Handler handler = new Handler();
	
//	TextField에서 가져온 값을 저장할 변수
	private String id_nameVal;
	private String id_phoneVal;
	private String pw_numVal;
	private String pw_phoneVal;

	// DB 연동과 관련된 변수
	private StudentDAO s_dao;
	private Student student;
	
	// 생성자
	public FindNumDialog()
	{
//		기본 설정을 설정해준다.
		super(Main.loginFrame);										// Dialog창의 Owner를 Login으로 설정한다.
		setSize(400, 320);												// Dialog창의 크기를 설정한다.
		Dialog_Default.init(this, "회원번호 / 비밀번호 찾기");		// Dialog창의 기본 설정을 설정해준다.
		
//		회원번호찾기 이름 텍스트
		id_nameLabel = new JLabel("이름");
		id_nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		id_nameLabel.setForeground(new Color(82, 55, 56));
		id_nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		id_nameLabel.setBounds(20, 50, 80, 20);
		add(id_nameLabel);
		
//		회원번호찾기 휴대폰이름 텍스트
		id_phoneLabel = new JLabel("휴대폰번호");
		id_phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		id_phoneLabel.setForeground(new Color(82, 55, 56));
		id_phoneLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		id_phoneLabel.setBounds(20, 90, 80, 20);
		add(id_phoneLabel);
		
//		회원번호찾기 이름입력 입력란
		id_nameBlank = new JTextField();
		id_nameBlank.setBounds(120, 50, 120, 23);
		id_nameBlank.setForeground(new Color(82, 55, 56));
		add(id_nameBlank);
		id_nameBlank.setColumns(10);
		
//		회원번호찾기 휴대폰번호입력 입력란
		id_phoneBlank = new JTextField();
		id_phoneBlank.setBounds(120, 90, 120, 23);
		id_phoneBlank.setForeground(new Color(82, 55, 56));
		add(id_phoneBlank);
		id_phoneBlank.setColumns(10);
		
		// 회원번호찾기 휴대폰번호 입력란 아래 안내문구 레이블
		phoneInfoLabel1 = new JLabel("'-'를 포함 띄어쓰기 없이 입력");
		phoneInfoLabel1.setBounds(105, 110, 155, 20);
		phoneInfoLabel1.setForeground(new Color(82, 55, 56));
		phoneInfoLabel1.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		add(phoneInfoLabel1);
		
//		비밀번호찾기 회원번호 텍스트
		pw_numberLabel = new JLabel("회원번호");
		pw_numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pw_numberLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		pw_numberLabel.setForeground(new Color(82, 55, 56));
		pw_numberLabel.setBounds(20, 190, 80, 20);
		add(pw_numberLabel);
		
//		비밀번호찾기 휴대폰번호 텍스트
		pw_phoneLabel = new JLabel("휴대폰번호");
		pw_phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pw_phoneLabel.setForeground(new Color(82, 55, 56));
		pw_phoneLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		pw_phoneLabel.setBounds(20, 230, 80, 20);
		add(pw_phoneLabel);
		
//		비밀번호찾기 이름입력 입력란
		pw_numberBlack = new JTextField();
		pw_numberBlack.setColumns(10);
		pw_numberBlack.setForeground(new Color(82, 55, 56));
		pw_numberBlack.setBounds(120, 190, 120, 23);
		add(pw_numberBlack);
		
//		비밀번호찾기 휴대폰번호입력 입력란
		pw_phoneBlack = new JTextField();
		pw_phoneBlack.setColumns(10);
		pw_phoneBlack.setForeground(new Color(82, 55, 56));
		pw_phoneBlack.setBounds(120, 230, 120, 23);
		add(pw_phoneBlack);
		
//		회원번호 찾기 버튼
		findNumberButton = new JButton("찾기");
		findNumberButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		findNumberButton.setForeground(new Color(82, 55, 56));
		findNumberButton.setBounds(270, 50, 100, 60);
		add(findNumberButton);
		
//		비밀번호 찾기 버튼
		findPWButton = new JButton("찾기");
		findPWButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		findPWButton.setForeground(new Color(82, 55, 56));
		findPWButton.setBounds(270, 190, 100, 60);
		add(findPWButton);
		
//		절취선ㅋㅋ
		separator = new JSeparator();
		separator.setBounds(-50, 140, 470, 10);
		add(separator);
		
//		비밀번호찾기 텍스트
		find_num = new JLabel("회원번호 찾기");
		find_num.setHorizontalAlignment(SwingConstants.CENTER);
		find_num.setForeground(new Color(82, 55, 56));
		find_num.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		find_num.setBounds(140, 20, 90, 20);
		add(find_num);
		
//		회원번호찾기 텍스트
		find_pw = new JLabel("비밀번호 찾기");
		find_pw.setHorizontalAlignment(SwingConstants.CENTER);
		find_pw.setForeground(new Color(82, 55, 56));
		find_pw.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		find_pw.setBounds(140, 160, 90, 20);
		add(find_pw);
		
		phoneInfoLabel2 = new JLabel("'-'를 포함 띄어쓰기 없이 입력");
		phoneInfoLabel2.setBounds(105, 250, 155, 20);
		phoneInfoLabel2.setForeground(new Color(82, 55, 56));
		phoneInfoLabel2.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		add(phoneInfoLabel2);
		
		// 이벤트를 추가하는 부분
		findNumberButton.addActionListener(handler);
		findPWButton.addActionListener(handler);
		
		setVisible(true);
	}
	
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// DB 연동과 관련된 변수 초기화
			s_dao = StudentDAO.getInstance();
			student = new Student();
		 
			// 각 JTextField에 입력된 값을 가져온다.
			id_nameVal = id_nameBlank.getText();
			id_phoneVal = id_phoneBlank.getText();
			pw_numVal = pw_numberBlack.getText();
			pw_phoneVal = pw_phoneBlack.getText();
		 	
			// 회원번호 찾기 버튼을 눌렀을 때
			if (e.getSource() == findNumberButton)
			{		
			   int st_num = s_dao.findSt_num(id_nameVal, id_phoneVal);	// 이름과 전화번호로 회원 번호를 찾는다.
				 
				try 
				{
					if (st_num == 0)		// 해당 회원 번호가 없을 시
					{
						new Dialog_Default("오류", "등록된 회원번호가 없습니다.");
					}
					else	// 해당 회원번호가 있을 시
					{
						new Dialog_Default(id_nameVal, st_num);	// 회원번호 안내 다이얼로그 창
						id_nameBlank.setText("");						// 관련 JTextField 창의 텍스트를 지운다.
						id_phoneBlank.setText("");
						id_nameBlank.requestFocus();					// 포커스를 이름 입력 칸에 맞춘다.
					}
				} catch (Exception oe) {
					oe.printStackTrace();
				}
			}
			
			// 비밀번호 찾기 버튼을 눌렀을 때
			else if (e.getSource() == findPWButton)
			{
				int st_num = 0;
				
				try		// 입력한 회원번호가 숫자가 아닌지를 확인한다.
				{
					st_num = Integer.parseInt(pw_numVal);
				}
				catch (NumberFormatException ne)	// 숫자가 아닐 시 Exception이 발생한다.
				{
					new Dialog_Default("오류", "회원번호는 숫자로 입력해주세요.");
					pw_numberBlack.requestFocus();		// 비밀번호 찾기 회원 번호 입력칸에 포커스를 맞춘다.
					return;
				}
				
				// 회원번호로 객체 생성
				student = s_dao.createStudent(st_num);				
				
				// 회원번호가 0일경우 ( 해당 회원 번호가 없어서 빈 객체가 생성된 경우)
				if (student.getSt_num() == 0)
				{
					new Dialog_Default ("오류", "회원번호 또는 휴대폰 번호를", "다시 입력해주세요.");
				}
				else	// 해당 회원번호로 객체를 생성한 경우
				{
					if (student.getPhone().equals(pw_phoneVal))		// 휴대폰 번호를 맞게 입력했는지 검사
					{
						new Dialog_Default("확인", "입력하신 핸드폰 번호로", "임시 비밀번호를 보내드렸습니다.");
					}
					else	// 휴대폰 번호가 틀린 경우
					{
						new Dialog_Default ("오류", "회원번호 또는 휴대폰 번호를", "다시 입력해주세요.");
					}
				}
			}	
		}	
	}
}