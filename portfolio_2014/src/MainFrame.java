import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class MainFrame extends JFrame
{
	static Container contentPane;
	JMenuBar mb;
	
	JMenu main;
	JMenu record;
	JMenu diet;
	JMenu exercise;
	JMenu option;
	
	JMenuItem tomain;
	JMenuItem exit;
	JMenuItem bmi;
	JMenuItem weight;
	JMenu etc;
	JMenuItem bust;
	JMenuItem waist;
	JMenuItem thigh;
//	JMenuItem graph;
	JMenuItem dietList;
	JMenuItem breakfast;
	JMenuItem lunch;
	JMenuItem dinner;
	JMenuItem snack;
	JMenuItem dietAdd;
	JMenuItem exSee;
	JMenuItem exRecord;
	JMenuItem exSearch;
	JMenuItem exAdd;
	JMenuItem option1;
	
	static String password = "";
	
	MainFrame()
	{
		this("Main");
	}
	
	MainFrame(String title)
	{
		super(title);
		
		contentPane = getContentPane();
		menubar();
		
		add(new Main());
		
		setBounds(center().width/2 - 350, center().height/2 - 250, 700, 500);
		setResizable(false);
		setVisible(true);
		
		Handler handler = new Handler();
		exit.addActionListener(handler);
		tomain.addActionListener(handler);
		dietAdd.addActionListener(handler);
		bmi.addActionListener(handler);
		weight.addActionListener(handler);
		bust.addActionListener(handler);
		waist.addActionListener(handler);
		thigh.addActionListener(handler);
//		graph.addActionListener(handler);
		dietList.addActionListener(handler);
		breakfast.addActionListener(handler);
		lunch.addActionListener(handler);
		dinner.addActionListener(handler);
		snack.addActionListener(handler);
		exAdd.addActionListener(handler);
		exRecord.addActionListener(handler);
		exSearch.addActionListener(handler);
		exSee.addActionListener(handler);
		option1.addActionListener(handler);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	static Dimension center()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.getScreenSize();
	}
	
	void menubar()
	{
		mb = new JMenuBar();
		
		main = new JMenu("����");
		record = new JMenu("���");
		diet = new JMenu("�Ĵ�");
		exercise = new JMenu("�");
		option = new JMenu("����");
		
		tomain = new JMenuItem("��������");
		exit = new JMenuItem("����");
		
		bmi = new JMenuItem("BMI");
		weight = new JMenuItem("ü��");
		etc = new JMenu("��Ÿ");
		bust = new JMenuItem("�����ѷ�");
		waist = new JMenuItem("�㸮�ѷ�");
		thigh = new JMenuItem("������ѷ�");
//		graph = new JMenuItem("�׷���");
		
		dietList = new JMenuItem("�Ĵ� ���");
		breakfast = new JMenuItem("��ħ");
		lunch = new JMenuItem("����");
		dinner = new JMenuItem("����");
		snack = new JMenuItem("����");
		dietAdd = new JMenuItem("���� ���");
		
		exSee = new JMenuItem("��� ����");
		exRecord = new JMenuItem("���");
		exSearch = new JMenuItem("�˻�");
		exAdd = new JMenuItem("���");
		
		option1 = new JMenuItem("ȯ�� ����");
		
		etc.add(bust);
		etc.add(waist);
		etc.add(thigh);
		
		main.add(tomain);
		main.addSeparator();
		main.add(exit);
		
		record.add(bmi);
		record.add(weight);
		record.add(etc);
//		record.addSeparator();
//		record.add(graph);
		
		diet.add(dietList);
		diet.add(breakfast);
		diet.add(lunch);
		diet.add(dinner);
		diet.add(snack);
		diet.addSeparator();
		diet.add(dietAdd);
		
		exercise.add(exSee);
		exercise.addSeparator();
		exercise.add(exRecord);
		exercise.add(exSearch);
		exercise.add(exAdd);
		
		option.add(option1);
		
		mb.add(main);
		mb.add(record);
		mb.add(diet);
		mb.add(exercise);
		mb.add(option);
		
		setJMenuBar(mb);
	}
	
	class Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			String gac = ae.getActionCommand();
			Object gs = ae.getSource();
			
			RecordTab recordTab = new RecordTab();
			DietTab dietTab = new DietTab();
			ExerciseTab exerciseTab = new ExerciseTab();
			
			if(gac.equals("����") || gac.equals("���"))
			{ System.exit(0); }
			
			if(gs == tomain)
			{
				contentPane.removeAll();
				contentPane.add(new Main());
				revalidate();
			}
			
			if(gs == dietAdd)
			{
				contentPane.removeAll();
				dietTab.tab.setSelectedIndex(5);
				contentPane.add(dietTab);
				revalidate();
			}
			
			if(gs == bmi)
			{
				contentPane.removeAll();
				recordTab.tab.setSelectedIndex(0);
				contentPane.add(recordTab);
				revalidate();
			}
			if(gs == weight)
			{
				contentPane.removeAll();
				recordTab.tab.setSelectedIndex(1);
				contentPane.add(recordTab);
				revalidate();
			}
			if(gs == bust)
			{
				contentPane.removeAll();
				recordTab.tab.setSelectedIndex(2);
				contentPane.add(recordTab);
				revalidate();
			}
			if(gs == waist)
			{
				contentPane.removeAll();
				recordTab.tab.setSelectedIndex(3);
				contentPane.add(recordTab);
				revalidate();
			}
			if(gs == thigh)
			{
				contentPane.removeAll();
				recordTab.tab.setSelectedIndex(4);
				contentPane.add(recordTab);
				revalidate();
			}
			
//			if(gs == graph)
//			{
//				contentPane.removeAll();
//				contentPane.add(new GraphTab());
//				revalidate();
//			}
			
			if(gs == dietList)
			{
				contentPane.removeAll();
				dietTab.tab.setSelectedIndex(0);
				contentPane.add(dietTab);
				revalidate();
			}
			
			if(gs == breakfast)
			{
				contentPane.removeAll();
				dietTab.tab.setSelectedIndex(1);
				contentPane.add(dietTab);
				revalidate();
			}
			if(gs == lunch)
			{
				contentPane.removeAll();
				dietTab.tab.setSelectedIndex(2);
				contentPane.add(dietTab);
				revalidate();
			}
			if(gs == dinner)
			{
				contentPane.removeAll();
				dietTab.tab.setSelectedIndex(3);
				contentPane.add(dietTab);
				revalidate();
			}
			if(gs == snack)
			{
				contentPane.removeAll();
				dietTab.tab.setSelectedIndex(4);
				contentPane.add(dietTab);
				revalidate();
			}
			if(gs == exAdd)
			{
				contentPane.removeAll();
				exerciseTab.tab.setSelectedIndex(3);
				contentPane.add(exerciseTab);
				revalidate();
			}
			if(gs == exSearch)
			{
				contentPane.removeAll();
				exerciseTab.tab.setSelectedIndex(2);
				contentPane.add(exerciseTab);
				revalidate();
			}
			if(gs == exRecord)
			{
				contentPane.removeAll();
				exerciseTab.tab.setSelectedIndex(1);
				contentPane.add(exerciseTab);
				revalidate();
			}
			if(gs == exSee)
			{
				contentPane.removeAll();
				exerciseTab.tab.setSelectedIndex(0);
				contentPane.add(exerciseTab);
				revalidate();
			}
			if(gs == option1)
			{
				contentPane.removeAll();
				contentPane.add(new Option());
				revalidate();
			}
		}
	}
}
