package last.design;

import javax.swing.*;

public class Main
{
	// Frame�� static ������ ������ �������� �� �� �ְ� ��
	static OverrallFrame mainFrame;
	static Login loginFrame;

	public static void main(String[] args) throws Exception
	{
		// ����� ����
		UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		loginFrame = new Login();
	}
}
