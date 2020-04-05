import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class ExerciseMain extends JPanel 
{
	JPanel cal;
	JPanel week;
	JButton record;
	JButton search;
	JButton reg;
	
	ExerciseMain()
	{
		super();
		setLayout(null);
		
		cal = new Cal();
		week = new JPanel();
		record = new JButton("운동 기록");
		search = new JButton("운동 검색");
		reg = new JButton("운동 등록");
		
		cal.setSize(500,400);
		cal.setLocation(10,25);
		cal.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		
		week.setSize(145,100);
		week.setLocation(520, 25);
		week.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		
		record.setSize(145, 80);
		record.setLocation(520, 145);
		
		search.setSize(145,80);
		search.setLocation(520, 245);
		
		reg.setSize(145,80);
		reg.setLocation(520, 345);
		
		add(cal);
		add(week);
		add(record);
		add(search);
		add(reg);
	}
	
	class Cal extends JPanel
	{
		JPanel up;
		JPanel down;
		JButton next;
		JButton prev;
		JLabel label;
		JLabel[] monthlb = new JLabel[42];
		
		Calendar curMon = Calendar.getInstance();
		
		Cal()
		{
			super();
			setLayout(new BorderLayout());
			
			up = new JPanel();
			down = new JPanel();
			next = new JButton("▶");
			prev = new JButton("◀");
			label = new JLabel();
			
			up.add(prev);
			up.add(label);
			up.add(next);
			
			setDays(curMon);
			
			down.setLayout(new GridLayout(6,7));			
			for (int i=0; i<monthlb.length; i++)
			{
				down.add(monthlb[i]);
			}
			
			add(up, "North");
			add(down, "Center");
			
			next.addActionListener(new Handler());
			prev.addActionListener(new Handler());
			
		}
		
		class Handler implements ActionListener
		{
			public void actionPerformed(ActionEvent ae)
			{			
				if((ae.getActionCommand()).equals("◀"))
				{
					curMon.add(Calendar.MONTH, -1);
				}
				else if((ae.getActionCommand()).equals("▶"))
				{
					curMon.add(Calendar.MONTH, 1);
				}
				
				setDays(curMon);
			}
		}
		
		void setDays(Calendar date)
		{
			int year = date.get(Calendar.YEAR);
			int month = date.get(Calendar.MONTH);
			
			label.setText(year + "년 " + (month+1) + "월");

			Calendar sDay = Calendar.getInstance();		//시작일
			sDay.set(year, month, 1);
			sDay.add(Calendar.DATE, -(sDay.DAY_OF_WEEK - 1));
			
			for(int i=0; i<monthlb.length; i++)
			{
				monthlb[i] = new JLabel(sDay.get(Calendar.DATE)+"", JLabel.CENTER);
				monthlb[i].setBorder(BorderFactory.createLineBorder(Color.blue, 1));
				
				if(sDay.get(Calendar.MONTH) == month)
				{
					monthlb[i].setFont(new Font("serif", Font.BOLD, 25));
					monthlb[i].setOpaque(true); 
				    monthlb[i].setBackground(Color.cyan);
				}
				else
				{
					monthlb[i].setFont(new Font("serif", Font.PLAIN, 15)); 
					monthlb[i].setOpaque(true); 
				    monthlb[i].setForeground(Color.gray);
				}
				
				sDay.add(Calendar.DATE, 1);
			}
		}
		
	}
	
	public static void main(String[] args) 
	{
		JFrame f = new JFrame();
		f.add(new ExerciseMain());
		f.setBounds(700,500, 700,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}

}
