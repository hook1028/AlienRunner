package last.design;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import dao.table.*;

// ���� ���â
public class LectureInfo extends JDialog 
{	
	// ��� ����
	private JTextField title_tf;
	private JTextField price_tf;
	private JTextField allPeople_tf;
	private ButtonGroup language;
	private JRadioButton english;
	private JRadioButton chinese;
	private JRadioButton japanese;
	private JLabel classLanguage;
	private JLabel classNames;
	private JLabel teacherNum;
	private JLabel classDay;
	private JLabel classTime;
	private JLabel classRoom;
	private JLabel lessonPrice;
	private JLabel withMember;
	private Choice teacherNums;
	private Choice weekChoice;
	private Choice startTime;
	private Choice endTime;
	private JLabel wave;
	private Choice classRoomNum;
	private JButton classAdd;
	private JButton cancel;
	
	// �̺�Ʈ ����� Ŭ����
	private Handler handler = new Handler();
	
	// ���, ���� ��带 ��Ÿ���� ���
	final static int REGISTER = 1;
	final static int MODIFY = 2;
	
	// �Է� ���� ������ ����	
	private int mode;
	private int numVal;
	private int t_numVal;
	private String dayVal;
	private int classVal;
	private int priceVal = -1;
	private int allPeopleVal = -1;
	private String languageVal;
	private String titleVal;
	private String startTimeVal;
	private String endTimeVal;
	
	// �����ͺ��̽� ���� �� �����͸� üũ�� Ŭ����
	private TeacherDAO t_dao;
	private TeacherDTO t_check;
	private LectureDAO l_dao;
	private LectureDTO l_check;
	private Lecture lecture;
	private ArrayList<String> t_list;
		
	// ������
	public LectureInfo(int mode, Lecture lecture) 
	{		
		// Dialogâ �⺻ ����
		super(Main.mainFrame);
		setSize(650, 750);
		Dialog_Default.init(this, "");
		this.mode = mode;				// �Ķ���ͷ� ���� ��� ���� ��� ������ �ִ´�.
		
		// �����ͺ��̽� ������ ���õ� ��� ������ �ʱ�ȭ�Ѵ�.
		t_dao = TeacherDAO.getInstance();
		t_check = new TeacherDTO(t_dao);
		l_dao = LectureDAO.getInstance();
		this.lecture = lecture;			// �Ķ���ͷ� ���� Lecture �ν��Ͻ��� ��� ������ �ִ´�.

		if (mode == LectureInfo.REGISTER)		// ��� ����� ���
		{
			setTitle("�� �� �� ��");
			l_check = new LectureDTO(l_dao);
		}
		else if (mode == LectureInfo.MODIFY)	// ���� ����� ���
		{
			setTitle("�� �� �� ��");
			l_check = new LectureDTO(l_dao, lecture);		// ���� ����� �� ���� �޾ƿ� ���� ��ü�� ����
		}
		
		// ���� ��Ͻ� ������ �����ϴ� ������ư <����>
		english = new JRadioButton("����");
		english.setBounds(190, 53, 121, 25);
		english.setForeground(new Color(82, 55, 56));
		english.setFont(new Font("���� ���", Font.BOLD, 16));
		add(english);

		// ���� ���� �� <�߱���>
		chinese = new JRadioButton("�߱���");
		chinese.setBounds(340, 53, 121, 25);
		chinese.setForeground(new Color(82, 55, 56));
		chinese.setFont(new Font("���� ���", Font.BOLD, 16));
		add(chinese);

		// ���� ���� �� <�Ϻ���>
		japanese = new JRadioButton("�Ϻ���");
		japanese.setBounds(490, 53, 121, 25);
		japanese.setForeground(new Color(82, 55, 56));
		japanese.setFont(new Font("���� ���", Font.BOLD, 16));
		add(japanese);
		
		// ���� �� language ��ư �׷쿡 �ִ´�.
		language = new ButtonGroup();
		language.add(english);
		language.add(chinese);
		language.add(japanese);

		// ���� ��� ���̺�
		classLanguage = new JLabel("���� ��� :");
		classLanguage.setBounds(65, 55, 85, 19);
		classLanguage.setForeground(new Color(82, 55, 56));
		classLanguage.setFont(new Font("���� ���", Font.BOLD, 16));
		classLanguage.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classLanguage);

		// ���Ǹ� ���̺�
		classNames = new JLabel("���Ǹ� :");
		classNames.setBounds(65, 120, 85, 25);
		classNames.setForeground(new Color(82, 55, 56));
		classNames.setFont(new Font("���� ���", Font.BOLD, 16));
		classNames.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classNames);

		// ���� ��ȣ ���̺�
		teacherNum = new JLabel("���� ��ȣ :");
		teacherNum.setBounds(65, 195, 85, 25);
		teacherNum.setForeground(new Color(82, 55, 56));
		teacherNum.setFont(new Font("���� ���", Font.BOLD, 16));
		teacherNum.setHorizontalAlignment(SwingConstants.RIGHT);
		add(teacherNum);

		// ���� ���� ���̺�
		classDay = new JLabel("���� ���� :");
		classDay.setBounds(65, 270, 85, 25);
		classDay.setForeground(new Color(82, 55, 56));
		classDay.setFont(new Font("���� ���", Font.BOLD, 16));
		classDay.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classDay);

		// ���� �ð� ���̺�
		classTime = new JLabel("���� �ð� :");
		classTime.setBounds(65, 345, 85, 25);
		classTime.setForeground(new Color(82, 55, 56));
		classTime.setFont(new Font("���� ���", Font.BOLD, 16));
		classTime.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classTime);

		// ���ǽ� ���̺�
		classRoom = new JLabel("���ǽ� :");
		classRoom.setBounds(65, 415, 85, 25);
		classRoom.setForeground(new Color(82, 55, 56));
		classRoom.setFont(new Font("���� ���", Font.BOLD, 16));
		classRoom.setHorizontalAlignment(SwingConstants.RIGHT);
		add(classRoom);

		// ���Ƿ� ���̺��Դϴ�
		lessonPrice = new JLabel("���Ƿ� :");
		lessonPrice.setBounds(65, 490, 85, 25);
		lessonPrice.setForeground(new Color(82, 55, 56));
		lessonPrice.setFont(new Font("���� ���", Font.BOLD, 16));
		lessonPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lessonPrice);

		// �� �ο� ���̺�
		withMember = new JLabel("���ο� :");
		withMember.setBounds(65, 567, 85, 25);
		withMember.setForeground(new Color(82, 55, 56));
		withMember.setFont(new Font("���� ���", Font.BOLD, 16));
		withMember.setHorizontalAlignment(SwingConstants.RIGHT);
		add(withMember);

		// �Է��� ���ϴ� TextField �� '���Ǹ�'
		title_tf = new JTextField();
		title_tf.setBounds(205, 122, 200, 25);
		title_tf.setForeground(new Color(82, 55, 56));
		title_tf.setColumns(10);
		add(title_tf);

		// �Է��� ���ϴ� TextField �� '���Ƿ�'
		price_tf = new JTextField();
		price_tf.setBounds(205, 493, 120, 25);
		price_tf.setForeground(new Color(82, 55, 56));
		price_tf.setColumns(10);
		add(price_tf);
		
		// �Է��� ���ϴ� TextField �� '���ο�'
		allPeople_tf = new JTextField();
		allPeople_tf.setBounds(205, 570, 120, 25);
		allPeople_tf.setForeground(new Color(82, 55, 56));
		allPeople_tf.setColumns(10);
		add(allPeople_tf);

		// ���� ��ȣ �����ϱ�
		teacherNums = new Choice();
		teacherNums.setFont(new Font("���� ���", Font.PLAIN, 16));
		teacherNums.setBackground(new Color(255, 254, 220));
		teacherNums.setForeground(new Color(82, 55, 56));
		teacherNums.setBounds(205, 197, 120, 25);
		
		// JDBC �������� �����ȣ�� �̸��� �ҷ��� Choice�� �ִ´�.
		t_list = t_dao.selectT_numAndName();
		for(int i = 0; i < t_list.size(); i++)
		{
			teacherNums.add(t_list.get(i));
		}
		add(teacherNums);

		// ���� ����
		weekChoice = new Choice();
		weekChoice.setFont(new Font("���� ���", Font.PLAIN, 16));
		weekChoice.setBounds(205, 273, 120, 25);
		weekChoice.setBackground(new Color(255, 254, 220));
		weekChoice.setForeground(new Color(82, 55, 56));
		weekChoice.add("��, ��, ��");
		weekChoice.add("ȭ, ��, ��");
		weekChoice.add("��, ��");
		add(weekChoice);

		// ���� �ð�
		startTime = new Choice();
		startTime.setFont(new Font("���� ���", Font.PLAIN, 16));
		startTime.setBounds(205, 350, 120, 25);
		startTime.setBackground(new Color(255, 254, 220));
		startTime.setForeground(new Color(82, 55, 56));
		startTime.add("09:00");
		startTime.add("10:00");
		startTime.add("11:00");
		startTime.add("12:00");
		startTime.add("13:00");
		startTime.add("14:00");
		startTime.add("15:00");
		startTime.add("16:00");
		startTime.add("17:00");
		startTime.add("18:00");
		startTime.add("19:00");
		startTime.add("20:00");
		startTime.add("21:00");
		add(startTime);

		// ������ �ð�
		endTime = new Choice();
		endTime.setBounds(363, 350, 120, 25);
		endTime.setFont(new Font("���� ���", Font.PLAIN, 16));
		endTime.setBackground(new Color(255, 254, 220));
		endTime.setForeground(new Color(82, 55, 56));
		endTime.add("10:00");
		endTime.add("11:00");
		endTime.add("12:00");
		endTime.add("13:00");
		endTime.add("14:00");
		endTime.add("15:00");
		endTime.add("16:00");
		endTime.add("17:00");
		endTime.add("18:00");
		endTime.add("19:00");
		endTime.add("20:00");
		endTime.add("21:00");
		endTime.add("22:00");
		add(endTime);
		
		// ���� ���۽ð��� ����ð� ������ ����ǥ��
		wave = new JLabel("~");
		wave.setFont(new Font("���� ���", Font.BOLD, 16));
		wave.setForeground(new Color(82, 55, 56));
		wave.setBounds(340, 344, 24, 28);
		add(wave);

		// ���ǽ�
		classRoomNum = new Choice();
		classRoomNum.setFont(new Font("���� ���", Font.PLAIN, 16));
		classRoomNum.setBackground(new Color(255, 254, 220));
		classRoomNum.setForeground(new Color(82, 55, 56));
		classRoomNum.setBounds(205, 420, 125, 25);
		classRoomNum.add("1 ���ǽ�");
		classRoomNum.add("2 ���ǽ�");
		classRoomNum.add("3 ���ǽ�");
		classRoomNum.add("4 ���ǽ�");
		classRoomNum.add("5 ���ǽ�");
		add(classRoomNum);

		// ���� ��Ͻ� ����ϱ� ��ư
		classAdd = new JButton("����ϱ�");
		classAdd.setBounds(180, 635, 120, 50);
		classAdd.setForeground(new Color(82, 55, 56));
		classAdd.setFont(new Font("���� ���", Font.BOLD, 18));
		add(classAdd);

		// ���� ������� �ʰ� ����ϴ� ��ư
		cancel = new JButton("���");
		cancel.setBounds(350, 635, 120, 50);
		cancel.setForeground(new Color(82, 55, 56));
		cancel.setFont(new Font("���� ���", Font.BOLD, 18));
		add(cancel);
		
		if (mode == LectureInfo.MODIFY)		// ��������� ��� ��� ������Ʈ�� ��Ȱ��ȭ ���� �������� ���ϰ� �Ѵ�.
		{
			setTitle("�� �� �� ��");
			classAdd.setText("�����ϱ�");
			english.setEnabled(false);
			chinese.setEnabled(false);
			japanese.setEnabled(false);
			title_tf.setEditable(false);
			price_tf.setEditable(false);
			teacherNums.setEnabled(false);
			weekChoice.setEnabled(false);
			startTime.setEnabled(false);
			endTime.setEnabled(false);
			
			// ���õ� ������ ������ �����´�.
			// ���� ��ȣ�� ������ ���ڸ��� ���� �ش� �� ���õǰ� �Ѵ�.
			switch (lecture.getL_num() / 1000)
			{
			case 1:
				english.setSelected(true);
				break;
			case 2:
				japanese.setSelected(true);
				break;
			case 3:
				chinese.setSelected(true);
				break;
			}
			
			title_tf.setText(lecture.getTitle());		// Ÿ��Ʋ�� ������ �����Ѵ�.
			
			for(int i = 0; i < teacherNums.getItemCount(); i++)	// �����ȣ Choice ����
			{
				if (teacherNums.getItem(i).substring(0, 3).equals(lecture.getT_num()+""))	// �� ���ڰ� �ش� �����ȣ�� ��ġ�� ��
				{
					teacherNums.select(i);	// �ش� ���������� �����Ѵ�.
				}
			}
			
			weekChoice.select(lecture.getDay() - 1);				// ���� �� ����
			
			for(int i = 0; i < startTime.getItemCount(); i++)	// ���� �ð� ����
			{
				if (startTime.getItem(i).equals(lecture.getTime().substring(0, 5)))	// ��ü �ð� ������ ���� �ð� �κи� ������
				{
					startTime.select(i);	// �ش� ���������� �����Ѵ�.
				}
			}
			
			for(int i = 0; i < endTime.getItemCount(); i++)		// ������ �ð� ����
			{
				if (endTime.getItem(i).equals(lecture.getTime().substring(6)))	// ��ü �ð� ������ ������ �ð� �κи� ������
				{
					endTime.select(i);	// �ش� ���������� �����Ѵ�.
				}
			}
			
			for(int i = 0; i < classRoomNum.getItemCount(); i++)	// ���ǽ� ����
			{
				if (classRoomNum.getItem(i).substring(0, 1).equals(lecture.getClass_n()+""))	// ���ǽ� ������ ���ڸ� ������
				{
					classRoomNum.select(i);	// �ش� ���������� �����Ѵ�.
				}
			}
			
			price_tf.setText(lecture.getCost() + "");					// ���Ƿ� ����
			allPeople_tf.setText(lecture.getAll_people() + "");		// �� �ο� ����
		}
				
		// �̺�Ʈ�� ������ �κ�
		classAdd.addActionListener(handler);
		cancel.addActionListener(handler);
		
		setVisible(true);
	}
	
	class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{	
			// ���� ����ϱ� ���
			if (mode == LectureInfo.REGISTER)
			{
				// ����ϱ� ��ư�� ������ ��
				if (e.getSource() == classAdd)
				{
					// �Է��� ���� �޾� �˻� �� ����� �� ����� �Ϸ�Ǿ��ٴ� �˾�â
					// �Է��� ���� ����� �� ���� �ƴ� ��� �˾�â ���
					titleVal = title_tf.getText().trim();
					t_numVal = Integer.parseInt(teacherNums.getSelectedItem().trim().substring(0, 3));
					dayVal = weekChoice.getSelectedItem();
					startTimeVal = startTime.getSelectedItem();
					endTimeVal = endTime.getSelectedItem();
					classVal = classRoomNum.getSelectedIndex() + 1;
					
					// ������ư���� ���õ� �� ������
					if (english.isSelected())
					{
						languageVal = english.getText();
					}
					else if (japanese.isSelected())
					{
						languageVal = japanese.getText();
					}
					else if (chinese.isSelected())
					{
						languageVal = chinese.getText();
					}
					else	// ���õ� ���� ���� ���
					{
						new Dialog_Default("��� ����", "�� �������ּ���.");
						english.requestFocus();
						return;
					}
					
					// ����ó���� ���� �������� �������� �˻� �� ���ڷ� �ֱ�					
					try
					{
						priceVal = Integer.parseInt(price_tf.getText());
						allPeopleVal = Integer.parseInt(allPeople_tf.getText());
						
					} catch (NumberFormatException ne)	// ���ڰ� �ƴ� �� Exception �߻�
					{
						if (priceVal == -1)				// ������ �Էµ��� �ʾ��� ��
						{
							new Dialog_Default("�ݾ� �Է�", "�ݾ��� ���ڷθ� �Է����ּ���.");
							price_tf.requestFocus();
							return;
						}
						else if (allPeopleVal == -1)	// �� �ο��� �Էµ��� �ʾ��� ��
						{
							new Dialog_Default("�� �ο� �Է�", "�� �ο��� ���ڷθ� �Է����ּ���.");
							allPeople_tf.requestFocus();
							return;
						}
					}

					if (l_check.checkL_num(languageVal) == false)					// ��� ���� ���� ������ ��
					{
						new Dialog_Default("��� ����", "�� �������ּ���.");
						english.requestFocus();
					}
					else if (l_check.checkTitle(titleVal) == false)							// ������ 5 ~ 20�ڰ� �ƴ� ��
					{
						new Dialog_Default("���Ǹ� �Է�", "���Ǹ��� 5~20 �ڷ� �Է����ּ���.");
						title_tf.requestFocus();
					}
					else if (l_check.checkT_num(t_numVal) == false)					// �����ȣ�� ���� ������ ��
					{
						new Dialog_Default("���� ����", "���縦 ������ �ּ���.");
						teacherNums.requestFocus();
					}
					else if (l_check.checkDay(dayVal) == false)							// ���� ���� ���� ������ ��
					{
						new Dialog_Default("���� ����", "������ ������ �ּ���.");
						weekChoice.requestFocus();
					}
					else if (l_check.checkTime(startTimeVal, endTimeVal) == false)	// �ð� ���� ���� ������ ��
					{
						new Dialog_Default("�ð� ����", "�ð��� ������ �ּ���.");
						startTime.requestFocus();
					}
					else if (l_check.checkClass(classVal) == false)						// ���ǽ� ���� ���� ������ ��
					{
						new Dialog_Default("���ǽ� ����", "���ǽ��� ������ �ּ���.");
						classRoomNum.requestFocus();
					}
					else if (l_check.checkCost(priceVal) == false)						// ���Ƿ� ���� ���� ������ ��
					{
						new Dialog_Default("���Ƿ� �Է�", "���ǷḦ �ٽ� �Է����ּ���.");
						price_tf.requestFocus();
					}
					else if (l_check.checkAll_people(allPeopleVal) == false)			// �� �ο� ���� ���� ������ ��
					{
						new Dialog_Default("�� �ο� �Է�", "�ο��� 10~50�� ���̷� �Է����ּ���.");
						allPeople_tf.requestFocus();
					}
					else	// ��� ���� �־��� ��
					{
						lecture = l_check.getLecture();			// ���� ��� �Է¹��� Lecture �ν��Ͻ��� �����޴´�.
						
						if (l_dao.insertLecture(lecture) == 1)		// ���� ��Ͽ� �������� ��
						{
							new Dialog_Default("��� ����", "���� ��Ͽ� �����Ͽ����ϴ�.");
							LectureInfo.this.dispose();
						}
						else											// ���� ��Ͽ� �������� ��
						{
							new Dialog_Default("��� ����", "���� ��Ͽ� �����Ͽ����ϴ�.");
						}
					}						
				}
				
				// �ݱ� ��ư�� ������ ��
				else if (e.getSource() == cancel)
				{
					// ���� ���â�� ����
					LectureInfo.this.dispose();
				}		
			}
			
			else if (mode == LectureInfo.MODIFY)
			{
				// �����ϱ� ��ư�� ������ ��
				if (e.getSource() == classAdd)
				{
					classVal = classRoomNum.getSelectedIndex() + 1;		// ������ ���ǽ� ���� �����´�.
					
					try		// try-catch ������ �� �ο��� ���ڸ� �Էµƴ��� Ȯ���Ѵ�.
					{
						allPeopleVal = Integer.parseInt(allPeople_tf.getText());
						
					} catch (NumberFormatException ne)	// ���ڰ� �Էµ��� �� Exception �߻�
					{
						new Dialog_Default("�� �ο� �Է�", "�� �ο��� ���ڷθ� �Է����ּ���.");
						allPeople_tf.requestFocus();	// �� �ο� JTextField�� ��Ŀ���� �����.
					}
					
					if (l_check.checkClass(classVal) == false)	// ���ǽ� ������ �������� ��
					{
						new Dialog_Default("���ǽ� ����", "���ǽ��� ������ �ּ���.");
						classRoomNum.requestFocus();
					}
					else if (l_check.checkAll_people(allPeopleVal) == false)	// �� �ο� ������ �������� ��
					{
						new Dialog_Default("�� �ο� �Է�", "�ο��� 10~50�� ���̷� �Է����ּ���.");
						allPeople_tf.requestFocus();
					}
					else	// ��� ������ �������� ��
					{
						lecture = l_check.getLecture();		// ������ Lecture ��ü�� ���� �޴´�.
						
						if (l_dao.updateLecture(lecture) == 1)	// ������ �������� ��
						{						
							new Dialog_Default("���� ����", "���� ������ �����Ͽ����ϴ�.");
							LectureInfo.this.dispose();
						}
						else	// ������ �������� ��
						{
							new Dialog_Default("���� ����", "���� ������ �����Ͽ����ϴ�.");
						}
					}					
				}
				
				// �ݱ� ��ư�� ������ ��
				else if (e.getSource() == cancel)
				{
					LectureInfo.this.dispose();		// ���� ���̾�α� â�� �ݴ´�.
				}		
			}
		}
	}
}
