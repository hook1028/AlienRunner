package last.design;

import java.awt.*;
import javax.swing.*;

// 프로그램 프레임을 담당하는 클래스
public class OverrallFrame extends JFrame
{
	// Frame에서 컴포넌트를 담는 역할을 한다.
	static Container contentPane;
	
	public OverrallFrame(JPanel panel)	// 패널을 매개변수로 받지만 사실상 첫 페이지를 받는 생성자
	{	
		// JFrame 기본 설정
		setResizable(false);
		setTitle("(주) 하 리 보 어 학 원");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 650);
		setLocationRelativeTo(null);
		init(panel);
		
		// 아이콘 설정
        ImageIcon img = new ImageIcon("image/icon.png");		// 아이콘으로 쓸 이미지를 받아와
        setIconImage(img.getImage());						// 아이콘으로 설정
		
		contentPane = getContentPane();	// JFrame 내 컨텐츠를 담는 컨테이너 역할을 하는 컨테이너판
		contentPane.setLayout(null);
		contentPane.add(panel);
		
		setVisible(true);
	}

	// JPanel 기초 설정하는 메소드
	static void init(JPanel panel)
	{
		panel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		panel.setSize(900, 650);
		panel.setLayout(null);
	}
			
	// 페이지를 바꾸는 메소드
	static void changePanel(JPanel panel)
	{
		init(panel);
		contentPane.removeAll();
		contentPane.add(panel);
		contentPane.repaint();
	}
}
