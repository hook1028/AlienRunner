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
		
		main = new JMenu("메인");
		record = new JMenu("기록");
		diet = new JMenu("식단");
		exercise = new JMenu("운동");
		option = new JMenu("설정");
		
		tomain = new JMenuItem("메인으로");
		exit = new JMenuItem("종료");
		
		bmi = new JMenuItem("BMI");
		weight = new JMenuItem("체중");
		etc = new JMenu("기타");
		bust = new JMenuItem("가슴둘레");
		waist = new JMenuItem("허리둘레");
		thigh = new JMenuItem("허벅지둘레");
//		graph = new JMenuItem("그래프");
		
		dietList = new JMenuItem("식단 기록");
		breakfast = new JMenuItem("아침");
		lunch = new JMenuItem("점심");
		dinner = new JMenuItem("저녁");
		snack = new JMenuItem("간식");
		dietAdd = new JMenuItem("음식 등록");
		
		exSee = new JMenuItem("기록 보기");
		exRecord = new JMenuItem("기록");
		exSearch = new JMenuItem("검색");
		exAdd = new JMenuItem("등록");
		
		option1 = new JMenuItem("환경 설정");
		
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
			
			if(gac.equals("종료") || gac.equals("취소"))
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
