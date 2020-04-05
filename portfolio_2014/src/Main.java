import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Main extends JPanel
{
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String sql;
	
	JPanel today;
	JLabel date;
	JLabel day;
	static JLabel time;
	
	JPanel notice;
	JLabel nickname;
	JLabel goal;
	JLabel fighting;
	
	JPanel record;
	JLabel rTitle;
	JLabel lWeight;
	JLabel rWeight;
	JLabel lBust;
	JLabel rBust;
	JLabel lWaist;
	JLabel rWaist;
	JLabel lThigh;
	JLabel rThigh;
	
	JPanel tip;
	JLabel tips;
	JLabel tiptitle;
	
	JPanel need;
	JLabel needsTitle;
	JLabel needs;
	
	JButton weight;
	JButton diet;
	JButton exercise;
	
	static Calendar cal = Calendar.getInstance();
	
	Main()
	{
		super();
		
		setLayout(null);
		
		today = new JPanel();
		today.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		today.setSize(150, 100);
		today.setLocation(10,10);
		today.setLayout(new BorderLayout());

		date = new JLabel(cal.get(Calendar.YEAR) + "�� " + cal.get(Calendar.MONTH) + "�� "
				+ cal.get(Calendar.DATE) + "��", JLabel.CENTER);
		date.setFont(new Font("Serif", Font.PLAIN, 20));
		
		day = new JLabel(getDay(), JLabel.CENTER);
		day.setFont(new Font("Serif", Font.BOLD, 30));
		
		time = new JLabel("", JLabel.CENTER);
		time.setFont(new Font("Serif", Font.PLAIN, 15));
	
		today.add(date, "North");
		today.add(day, "Center");
		today.add(time, "South");
		
		notice = new JPanel();
		notice.setSize(504, 100);
		notice.setLocation(170,10);
		notice.setLayout(null);
		notice.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		setFighting();
		
		record = new JPanel();
		record.setSize(250, 305);
		record.setLocation(10,120);
		record.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		record.setLayout(null);
		setRecord();
				
		tip = new JPanel();
		tip.setSize(404, 114);
		tip.setLocation(270, 187);
		tip.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		tip.setLayout(null);
		setTips();
	
		need = new JPanel();
		need.setSize(404, 114);
		need.setLocation(270, 311);
		need.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		need.setLayout(null);
		setNeed();
		
		weight = new JButton("ü�߱��");
		weight.setSize(128, 57);
		weight.setLocation(270, 120);
		
		diet = new JButton("�Ĵܱ��");
		diet.setSize(128, 57);
		diet.setLocation(408, 120);
		
		exercise = new JButton("����");
		exercise.setSize(128, 57);
		exercise.setLocation(546, 120);
		
		Handler handler = new Handler();
		weight.addActionListener(handler);
		diet.addActionListener(handler);
		exercise.addActionListener(handler);
	
		add(today);
		add(notice);
		add(record);
		add(tip);
		add(need);
		add(weight);
		add(diet);
		add(exercise);
	}
	
	class Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object gs = e.getSource();
			RecordTab recordTab = new RecordTab();
			ExerciseTab exerciseTab = new ExerciseTab();
			
			if(gs==diet)
			{
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(new DietTab());
				revalidate();
			}
			
			if(gs==weight)
			{
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(recordTab);
				recordTab.tab.setSelectedIndex(1);
				revalidate();
			}
			if(gs==exercise)
			{
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(exerciseTab);
				exerciseTab.tab.setSelectedIndex(1);
				revalidate();
			}
		}
	}
	
	String getDay()
	{
		int wday = cal.get(Calendar.DAY_OF_WEEK);
		String result = "";
		
		switch(wday)
		{
			case 1:
				result = "��";
				break;
			case 2:
				result = "ȭ";
				break;
			case 3:
				result = "��";
				break;
			case 4:
				result = "��";
				break;
			case 5:
				result = "��";
				break;
			case 6:
				result = "��";
				break;
			case 7:
				result = "��";
				break;	
		}
		
		return result;
	}
	
	static void getTime()
	{
		while(true)
		{
			int am_pm = cal.get(Calendar.AM_PM);
			int h = cal.get(Calendar.HOUR);
			int m = cal.get(Calendar.MINUTE);
			int s = cal.get(Calendar.SECOND);
				
			time.setText
			((am_pm==0? "AM":"PM") + " " + (h<10? "0"+h:h) + ":" 
					+ (m<10? "0"+m:m) + ":" + (s<10? "0"+s:s));
				
			try { Thread.sleep(1000); }
			catch(InterruptedException e){}
				
			cal.add(Calendar.SECOND, 1);
		}
	}
	
	void setTips()
	{
		tips = new JLabel("",JLabel.CENTER);
		tips.setSize(404,15);
		tips.setLocation(0,55);
		
		tiptitle = new JLabel("<������ ���̾�Ʈ ��>", JLabel.CENTER);
		tiptitle.setSize(404,15);
		tiptitle.setLocation(0,20);
		
		tip.add(tiptitle);
		tip.add(tips);
		
		ArrayList tiplist = new ArrayList();
		
		tiplist.add("� �Ŀ��� �ܹ����̳� �ܴ���� �����ϸ� �����ϴ�.");
		tiplist.add("������ Ű�� ���� �ܹ����� �� �����ϼ���.");
		tiplist.add("������� 30�� ���ܷ� �ϴ� ���� �����ϴ�.");
		tiplist.add("������ ���ĺ��� ������ �Դ� ���� �������� �����ݴϴ�.");
		tiplist.add("�亸�ٴ� ������ ���� �����ϼ���.");
		
		tips.setText((String)(tiplist.get((int)(Math.random()*(tiplist.size()-1)))));
	}
	
	void setFighting()
	{	
		String tmpnickname = "";
		int tmpgoal = 0;
		int nowweight = 0;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e)
		{
			System.err.println("ClassNotFoundException: " + e.getMessage());
		}
		
		try
		{
			conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
			stmt = conn.createStatement();
			
			//���� �ֱ� ������ ���ϱ�
			sql = "select weight from weight order by day desc";
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				nowweight = rs.getInt("weight");
			}
			
			sql = "select nickname, goal from userdata";
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{							
				tmpnickname = rs.getString("nickname");
				tmpgoal = rs.getInt("goal");
			}
			
			rs.close();
			stmt.close();
			conn.close();

		} catch(SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
		
		Font font = new Font("Serif", Font.BOLD, 20);
		
		nickname = new JLabel(tmpnickname + "��!!", JLabel.CENTER);
		goal = new JLabel("���� ��ǥ ü�߱��� " + (nowweight-tmpgoal) + "kg ���ҽ��ϴ�.", JLabel.CENTER);
		fighting = new JLabel("���õ� ȭ����~!!", JLabel.CENTER);
		
		nickname.setFont(font);
		nickname.setSize(504,25);
		nickname.setLocation(0,5);
		
		goal.setFont(font);
		goal.setSize(504,25);
		goal.setLocation(0,35);
		
		fighting.setFont(font);
		fighting.setSize(504,25);
		fighting.setLocation(0,65);
		
		notice.add(nickname);
		notice.add(goal);
		notice.add(fighting);
	}
	
	void setRecord()
	{
		int reWeight = 0;
		int reBust = 0;
		int reWaist = 0;
		int reThigh = 0;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e)
		{
			System.err.println("ClassNotFoundException: " + e.getMessage());
		}
		
		try
		{
			conn = DriverManager.getConnection(Login.jdbc_url, "scott", "tiger");
			stmt = conn.createStatement();
			
			sql = "select weight from weight order by day desc";
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				reWeight = rs.getInt("weight");
			}
			
			sql = "select bust from bust order by day desc";
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				reBust = rs.getInt("bust");
			}
			
			sql = "select waist from waist order by day desc";
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				reWaist = rs.getInt("waist");
			}

			sql = "select thigh from thigh order by day desc";
			rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				reThigh = rs.getInt("thigh");
			}
			
			rs.close();
			stmt.close();
			conn.close();

		} catch(SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
			
		Font fontl = new Font("Serif", Font.BOLD, 15);
		Font fontr = new Font("Serif", Font.PLAIN, 12);
		
		rTitle = new JLabel("<�ֱ� ����� ġ��>", JLabel.CENTER);
		rTitle.setFont(new Font("Serif", Font.BOLD, 18));
		rTitle.setSize(200,25);
		rTitle.setLocation(25,15);
		
		lWeight = new JLabel("[ü��]", JLabel.CENTER);
		lWeight.setFont(fontl);
		lWeight.setSize(200,20);
		lWeight.setLocation(25,60);
		
		rWeight = new JLabel(reWeight + "kg", JLabel.CENTER);
		rWeight.setFont(fontr);
		rWeight.setSize(200,20);
		rWeight.setLocation(25,85);
		
		lBust = new JLabel("[�����ѷ�]", JLabel.CENTER);
		lBust.setFont(fontl);
		lBust.setSize(200,25);
		lBust.setLocation(25,120);
		
		rBust = new JLabel(reBust + "cm", JLabel.CENTER);
		rBust.setFont(fontr);
		rBust.setSize(200,20);
		rBust.setLocation(25,145);
		
		lWaist = new JLabel("[�㸮�ѷ�]", JLabel.CENTER);
		lWaist.setFont(fontl);
		lWaist.setSize(200,25);
		lWaist.setLocation(25,180);
		
		rWaist = new JLabel(reWaist + "cm", JLabel.CENTER);
		rWaist.setFont(fontr);
		rWaist.setSize(200,20);
		rWaist.setLocation(25,205);
		
		lThigh = new JLabel("[������ѷ�]", JLabel.CENTER);
		lThigh.setFont(fontl);
		lThigh.setSize(200,25);
		lThigh.setLocation(25,240);
		
		rThigh = new JLabel(reThigh + "cm", JLabel.CENTER);
		rThigh.setFont(fontr);
		rThigh.setSize(200,20);
		rThigh.setLocation(25,265);
		
		record.add(rTitle);
		record.add(lWeight);
		record.add(rWeight);
		record.add(lBust);
		record.add(rBust);
		record.add(lWaist);
		record.add(rWaist);
		record.add(lThigh);
		record.add(rThigh);
	}
	
	void setNeed()
	{		
		needsTitle = new JLabel("<��õ �Ĵ�>", JLabel.CENTER);
		needs = new JLabel("", JLabel.CENTER);
		
		ArrayList needlist = new ArrayList();
		
		needlist.add("���÷���: ����� ź��ȭ���� ����, ���̼����� ���ƿ�.");
		needlist.add("�κ�: �ܹ����� ǳ���ϰ�, �������� ���ƿ�.");
		needlist.add("��� ����: �ܹ����� ǳ���� ������ ���� �� ���� ������ ���ƿ�.");
		needlist.add("��ȣ��: Į�θ��� ����, �������� ���ƿ�.");
		needlist.add("�޷�: �絵�� ������, Į�θ��� ���� �������� ���� �������� ���ƿ�.");
		
		needs.setText((String)(needlist.get((int)(Math.random()*(needlist.size()-1)))));
		
		needsTitle.setSize(404,15);
		needsTitle.setLocation(0,20);
		
		needs.setSize(404,15);
		needs.setLocation(0,55);
		
		need.add(needsTitle);
		need.add(needs);
	}
}
