package last.design;

import java.awt.*;
import javax.swing.*;

// ���α׷� �������� ����ϴ� Ŭ����
public class OverrallFrame extends JFrame
{
	// Frame���� ������Ʈ�� ��� ������ �Ѵ�.
	static Container contentPane;
	
	public OverrallFrame(JPanel panel)	// �г��� �Ű������� ������ ��ǻ� ù �������� �޴� ������
	{	
		// JFrame �⺻ ����
		setResizable(false);
		setTitle("(��) �� �� �� �� �� ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 650);
		setLocationRelativeTo(null);
		init(panel);
		
		// ������ ����
        ImageIcon img = new ImageIcon("image/icon.png");		// ���������� �� �̹����� �޾ƿ�
        setIconImage(img.getImage());						// ���������� ����
		
		contentPane = getContentPane();	// JFrame �� �������� ��� �����̳� ������ �ϴ� �����̳���
		contentPane.setLayout(null);
		contentPane.add(panel);
		
		setVisible(true);
	}

	// JPanel ���� �����ϴ� �޼ҵ�
	static void init(JPanel panel)
	{
		panel.setFont(new Font("���� ���", Font.BOLD, 14));
		panel.setSize(900, 650);
		panel.setLayout(null);
	}
			
	// �������� �ٲٴ� �޼ҵ�
	static void changePanel(JPanel panel)
	{
		init(panel);
		contentPane.removeAll();
		contentPane.add(panel);
		contentPane.repaint();
	}
}
