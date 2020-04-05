package last.design;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// 기본 다이얼로그 창
public class Dialog_Default extends JDialog
{	
	// UI를 담당하는 변수
	private JLabel label1;
	private JLabel label2;
	private JButton btn1;

	// 문구가 한 줄, 확인버튼만 있는 Dialog
	public Dialog_Default(String title, String l_str1)
	{
		// Dialog 창 설정
		super(Main.mainFrame);	// 다이얼로그의 Owner를 OverrallFrame으로 지정한다.
		setSize(300, 150);			// 다이얼로그의 크기를 설정한다.
		init(this, title);				// 다이얼로그의 기본 설정을 설정해주는 메소드
		
//		라벨1
		label1 = new JLabel(l_str1);
		label1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label1.setForeground(new Color(82, 55, 56));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(25, 35, 250, 20);

//		확인 버튼
		btn1 = new JButton("확인");
		btn1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(93, 76, 105, 27);

		// 컴포넌트 추가
		add(btn1);
		add(label1);
		
		// 버튼 이벤트 구현 (setVisible 메소드 이전에 입력한다)
		buttonEvent();
		
		setVisible(true);
	}
	
	// 문구가 두 줄, 확인버튼만 있는 Dialog
	public Dialog_Default(String title, String l_str1, String l_str2)
	{		
		// Dialog 창 설정
		super(Main.mainFrame);
		setSize(300, 150);
		init(this, title);
				
//		라벨1
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
		
//		확인 버튼
		btn1 = new JButton("확인");
		btn1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(93, 76, 105, 27);
		
		// 컴포넌트 추가
		add(btn1);
		add(label1);
		add(label2);
		
		// 버튼 이벤트 구현
		buttonEvent();
		
		setVisible(true);
	}
		
	// 수강생 회원 가입 성공창을 만드는 생성자
	Dialog_Default(String name, int value)
	{
		super(Main.mainFrame);
		setSize(300, 150);
		init(this, "완료");

		String l_str1 = name + "님의 회원번호는 " + value + " 입니다.";
		String l_str2 = "로그인 시 회원번호를 입력해주세요";

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

		// 확인 버튼
		btn1 = new JButton("확인");
		btn1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btn1.setForeground(new Color(82, 55, 56));
		btn1.setBounds(93, 76, 105, 27);

		// 컴포넌트 추가
		add(btn1);
		add(label1);
		add(label2);
		
		// 버튼 이벤트 구현
		buttonEvent();;
	
		setVisible(true);	
	}
	
	// Dialog의 기본 설정을 담당하는 메소드. Static으로 설정해 다른 Dialog 클래스에서도 사용할 수 있게 한다.
	static void init(JDialog dialog, String title)
	{
		dialog.setModal(true);													// Dialog의 필수응답을 true로 설정한다.
		dialog.setFont(new Font("맑은 고딕", Font.BOLD, 14));				// Dialog의 폰트를 설정한다.
		dialog.setForeground(new Color(82, 55, 56));						// Dialog의 글자색을 설정한다.
		dialog.setTitle(title);														// Dialog의 title을 설정한다.
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);	// Dialog의 X 버튼 값을 설정한다.
		dialog.setLayout(null);													// Dialog의 Layout을 설정한다.
		dialog.setResizable(false);												// Dialog의 크기를 재설정하지 못하게 한다.
		dialog.setLocationRelativeTo(Main.mainFrame);						// Dialog의 위치를 OverrallFrame 가운데로 한다.
	}
	
	// 확인 버튼에 넣을 버튼 이벤트
	void buttonEvent()
	{
		btn1.addActionListener(new ActionListener()
		{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// 이벤트가 일어난 컴포넌트가 btn1 이라면
					if(e.getSource() == btn1)
					{
						Dialog_Default.this.dispose();		// 해당 다이얼로그 창을 닫는다.	
					}			
				}
		});
	}
}
