package last.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.table.*;

// 삭제 창 및 수강신청창
public class Dialog_Select extends JDialog
{	
	// UI 관련 변수
	private JLabel label1;
	private JLabel label2;
	private JButton btn1;
	private JButton btn2;
	
	// 이벤트 핸들러
	private Handler handler = new Handler();
	
	// JDBC 연동에 필요한 클래스
	RegisterDAO r_dao;
	LectureDAO l_dao;
	TeacherDAO t_dao;
	StudentDAO s_dao;
	
	// JDBC 연동시 사용할 상수
	int mode;
	int l_num, st_num, num;
	final static int MEMBER_DELETE = 1;
	final static int LECTURE_DELETE = 2;
	final static int TEACHER_DELETE = 3;
	final static int LECTURE_ADD = 4;
	final static int REGISTER_DELETE = 5;
	
	// 문구가 한 줄, 버튼이 두 개인 dialog
	public Dialog_Select(String title, String l_str1, int mode, int num)
	{
		// Dialog 창 설정
		super(Main.mainFrame);			// Dialog의 Owner를 OverrallFrame으로 설정한다.
		setSize(300, 150);					// Dialog의 크기를 설정한다.
		Dialog_Default.init(this, title);		// Dialog의 기본 설정을 설정해주는 메소드
		this.mode = mode;					// 파라미터로 받은 mode를 mode로 설정해준다.
		this.num = num;					// 파라미터로 받은 num을 num로 설정해준다.
				
//		라벨1
		label1 = new JLabel(l_str1);
		label1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label1.setForeground(new Color(82, 55, 56));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(25, 35, 250, 20);

//		왼쪽 버튼
		btn1 = new JButton("확인");
		btn1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(40, 76, 100, 30);
			
//		오른쪽 버튼
		btn2 = new JButton("취소");
		btn2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn2.setForeground(new Color(82, 55, 56));
		btn2.setBounds(155, 76, 100, 30);
					
		add(btn1);
		add(btn2);
		add(label1);
	
//		버튼 이벤트 구현
		btn1.addActionListener(handler);
		btn2.addActionListener(handler);
		
		setVisible(true);
	}
	
	// 수강신청 확인창
	Dialog_Select(String name, int l_num, int st_num)
	{
		super(Main.mainFrame);
		setSize(300, 150);
		Dialog_Default.init(this, "수강신청 확인");
		mode = this.LECTURE_ADD;					// 모드를 수강신청 모드로 한다.
		this.l_num = l_num;							// 강의 번호를 받아 멤버 변수에 넣는다.
		this.st_num = st_num;							// 학생 번호를 받아 멤버 변수에 넣는다.
		
		// JDBC 클래스 초기화
		r_dao = RegisterDAO.getInstance();

		// 라벨에 넣을 문구 설정
		String l_str1 = "[" + l_num + "] " + name;
		String l_str2 = "위 강의를 수강 신청 하시겠습니까?";

		// 라벨1
		label1 = new JLabel(l_str1);
		label1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label1.setForeground(new Color(82, 55, 56));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(25, 25, 250, 20);

		// 라벨2
		label2 = new JLabel(l_str2);
		label2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label2.setForeground(new Color(82, 55, 56));
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setBounds(25, 45, 250, 20);

		// 왼쪽 버튼
		btn1 = new JButton("확인");
		btn1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(40, 76, 100, 30);

		// 오른쪽 버튼
		btn2 = new JButton("취소");
		btn2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn2.setForeground(new Color(82, 55, 56));
		btn2.setBounds(155, 76, 100, 30);

		add(label1);
		add(label2);
		add(btn1);
		add(btn2);
		
//		버튼 이벤트 구현
		btn1.addActionListener(handler);
		btn2.addActionListener(handler);
		
		setVisible(true);
	}
	
	// 이벤트를 담당할 클래스	
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// 회원 삭제일 때 이벤트
			if (mode == Dialog_Select.MEMBER_DELETE)
			{
				if (e.getSource() == btn1)		// 왼쪽 버튼일 경우
				{
					s_dao = StudentDAO.getInstance();
					if (s_dao.deleteStudent(num) == 1)		// 학생 번호로 해당 학생을 삭제하는 데 성공했을 시
					{
						new Dialog_Default("탈퇴 완료", "탈퇴가 완료되었습니다.");
					}
					else
					{
						new Dialog_Default("탈퇴 실패", "탈퇴에 실패하였습니다.");
					}
										
					if(Dialog_Select.this.getTitle().equals("탈퇴 확인"))		// 회원 삭제가 학생 모드에서 이루어졌을 경우
					{
						Main.mainFrame.dispose();				// 학생 메인 창을 닫는다.
						Main.loginFrame = new Login();		// 로그인 창을 새롭게 띄운다.
					}
					
					Dialog_Select.this.dispose();		// 탈퇴 성공 여부 다이얼로그가 닫힌 뒤, 현재 다이얼로그도 닫는다.
				}
				else if (e.getSource() == btn2)	// 오른쪽 버튼일 경우
				{
					Dialog_Select.this.dispose();	// 다이얼로그 창을 닫는다.
				}
			}
			
			// 강의 삭제일 때 이벤트
			else if (mode == Dialog_Select.LECTURE_DELETE)
			{
				if (e.getSource() == btn1)		// 왼쪽 버튼일 경우
				{
					l_dao = LectureDAO.getInstance();
					if (l_dao.deleteLecture(num) == 1)	// 강의 번호로 해당 강의를 삭제하는 데 성공했을 시
					{
						new Dialog_Default("삭제 성공", "강의가 삭제되었습니다.");	
					}
					else
					{
						new Dialog_Default("삭제 실패", "강의 삭제에 실패하였습니다.");
					}
					
					Dialog_Select.this.dispose();		// 삭제 성공 여부 다이얼로그가 닫힌 뒤, 현재 다이얼로그도 닫는다.
				}
				else if (e.getSource() == btn2)	// 오른쪽 버튼일 경우
				{
					Dialog_Select.this.dispose();	// 다이얼로그 창을 닫는다.
				}
			}
			
			// 강사 삭제일 때 이벤트
			else if (mode == Dialog_Select.TEACHER_DELETE)
			{
				if (e.getSource() == btn1)		// 왼쪽 버튼일 시
				{
					t_dao = TeacherDAO.getInstance();
					if (t_dao.deleteTeacher(num) == 1)		// 강사 번호로 해당 강사를 삭제하는 데 성공했을 시
					{
						new Dialog_Default("삭제 성공", "강사가 삭제되었습니다.");	
					}
					else
					{
						new Dialog_Default("삭제 실패", "강사 삭제에 실패하였습니다.");
					}
					
					Dialog_Select.this.dispose();		// 삭제 성공 여부 다이얼로그가 닫힌 뒤, 현재 다이얼로그도 닫는다.
				}
				else if (e.getSource() == btn2)	// 오른쪽 버튼일 시
				{
					Dialog_Select.this.dispose();	// 다이얼로그 창을 닫는다.
				}
			}
								
			// 수강신청일 때 이벤트
			else if (mode == Dialog_Select.LECTURE_ADD)
			{
				if (e.getSource() == btn1)			// 왼쪽 버튼일 때
				{
					if(r_dao.searchRegister(l_num, st_num))		// 선택한 강의에 해당 학생이 등록되지 않았을 경우
					{
						if( r_dao.insertRegister(l_num, st_num) == 1)	// DB에 등록 정보를 넣는데 성공했을 시
						{
							new Dialog_Default("신청성공", "수강신청이 완료되었습니다.");
							Dialog_Select.this.dispose();	// 수강신청 성공 다이얼로그 창이 닫힌 뒤, 현재 다이얼로그를 닫는다.
						}	
					}
					else	// 선택한 강의에 해당 학생이 이미 등록된 경우
					{
						new Dialog_Default("수강 신청 오류", "이미 수강 신청한 강의입니다.");
						Dialog_Select.this.dispose();
					}				
				}
				else if (e.getSource() == btn2)		// 오른쪽 버튼일 시
				{
					Dialog_Select.this.dispose();		// 해당 다이얼로그 창을 닫는다.
				}
			}
			
			// 수강 신청 취소일 때 모드
			else if (mode > 999) // 모드에 강의번호가 들어있을 경우 = 수강신청 취소
			{
				r_dao = RegisterDAO.getInstance();		// 등록 테이블 정보를 가져오는 r_dao 초기화
				
				if (e.getSource() == btn1)		// 왼쪽 버튼일 시
				{
					if (r_dao.deleteRegister(mode, num) == 1)		// 등록 테이블에서 해당 정보를 삭제하는 데 성공하면
					{
						new Dialog_Default("수강신청 취소 완료", "수강신청이 취소되었습니다.");
						Dialog_Select.this.dispose();	// 수강취소 성공 다이얼로그 창이 닫힌 뒤, 현재 다이얼로그도 닫음.
					}
					else	// 삭제에 실패한 경우
					{
						new Dialog_Default("오류", "수강신청 취소에 실패했습니다.");
						Dialog_Select.this.dispose();	// 수강취소 실패 다이얼로그 창이 닫힌 뒤, 현재 다이얼로그도 닫음
					}
				}
				
				else if (e.getSource() == btn2)	// 오른쪽 버튼일 시
				{
					Dialog_Select.this.dispose();	// 현재 다이얼로그 창을 닫는다.
				}
			}
		}	
	}
}
