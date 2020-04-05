import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GraphTab extends JPanel
{
	JTabbedPane tab;
	
	GraphTab()
	{
		super();
		
		setLayout(new BorderLayout());
		
		tab = new JTabbedPane();
		tab.setFont(new Font("Serif",Font.BOLD,15));
		tab.addTab("BMI", new JLabel("TEst"));
		tab.addTab("ü��", new JPanel());
		tab.addTab("�����ѷ�", new JPanel());
		tab.addTab("�㸮�ѷ�", new JPanel());
		tab.addTab("������ѷ�", new JPanel());
		
		add(tab,"Center");
	}

	public static void main(String[] args) 
	{
		MainFrame main = new MainFrame("test");
		main.add(new GraphTab());
	}

}
