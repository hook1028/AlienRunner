package last.design;

import javax.swing.*;

public class Main
{
	// Frame을 static 변수로 지정해 공통으로 쓸 수 있게 함
	static OverrallFrame mainFrame;
	static Login loginFrame;

	public static void main(String[] args) throws Exception
	{
		// 룩앤필 적용
		UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		loginFrame = new Login();
	}
}
