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
		tab.addTab("체중", new JPanel());
		tab.addTab("가슴둘레", new JPanel());
		tab.addTab("허리둘레", new JPanel());
		tab.addTab("허벅지둘레", new JPanel());
		
		add(tab,"Center");
	}

	public static void main(String[] args) 
	{
		MainFrame main = new MainFrame("test");
		main.add(new GraphTab());
	}

}
