package dao.table;

// 강의테이블에 값을 넣기 전에 올바른 값이 들어왔는지 확인하는 클래스
public class LectureDTO
{
	// 값을 확인하고 넘기기 위해 필요한 변수
	private LectureDAO lectureDAO;
	private Lecture lecture;
	
	// LectureDAO만 필요로 하는 생성자
	public LectureDTO(LectureDAO lectureDAO)
	{
		this.lectureDAO = lectureDAO;
		lecture = new Lecture();
	}
	
	// LectureDAO와 Lecture를 모두 필요로 하는 생성자
	public LectureDTO(LectureDAO lectureDAO, Lecture lecture)
	{
		this.lectureDAO = lectureDAO;
		this.lecture = lecture;
	}
	
	// 강의 번호를 생성해주는 메소드
	public boolean checkL_num(String language)
	{
		boolean result = false;		// 강의번호 생성여부를 반환할 변수
		
		switch (language)
		{
		case "영어":
			lecture.setL_num(lectureDAO.createL_num(1000));	// 1. 언어번호를 넘겨 강의 번호를 생성한다.
			result = true;											// 2. 생성된 강의번호를 강의 객체에 넣는다.
			break;														// 3. 생성여부를 true로 바꾼다.
			
		case "일본어":
			lecture.setL_num(lectureDAO.createL_num(2000));
			result = true;
			break;
			
		case "중국어":
			lecture.setL_num(lectureDAO.createL_num(3000));
			result = true;
			break;	
		}
		
		return result;
	}
	
	// 강의명을 검사한 뒤 넣어주는 메소드
	public boolean checkTitle(String title)
	{
		boolean result = false;		// 강의명이 올바르게 입력됐는지를 알려줄 변수
		title = title.trim();				// 문자열 앞, 뒤의 공백을 제거한다.
		// 강의명은 5글자 이상 25자 이하로 한다. 조건에 맞을시 값을 넣고 true, 아닐 시 false
		if (title.length() >= 5 && title.length() <= 25)
		{
			lecture.setTitle(title);		// 해당 조건이 맞을 시 lecture 객체에 넣는다.
			result = true;			// 입력 여부를 true로 바꾼다.
		}
		
		return result;
	}
	
	// 강의 요일을 검사한 뒤 넣어주는 메소드
	public boolean checkDay(String day)
	{
		boolean result = false;		// 강의 요일이 잘 설정됐는지 여부를 알려줄 변수
		
		// 강의 요일에 맞춰 lecture에 숫자 값을 넣는다.
		// 1 = 월, 수, 금, 2 = 화, 목, 토, 3 = 토, 일
		switch(day)
		{
		case "월, 수, 금" :
			lecture.setDay(1);		// Lecture 객체에 요일 값을 넣는다.
			result = true;			// 설정 여부를 true로 바꾼다.
			break;
			
		case "화, 목, 토" :
			lecture.setDay(2);
			result = true;
			break;
			
		case "토, 일" :
			lecture.setDay(3);
			result = true;
			break;
		}
		
		return result;
	}
	
	// 강사번호를 넣어주는 메소드
	public boolean checkT_num(int t_num)
	{
		boolean result = false;		// 강의번호가 잘 입력됐는지 알려줄 변수
		
		lecture.setT_num(t_num);	// Lecture 객체에 강사번호를 넣는다.
		result = true;				// 입력여부를 true로 바꾼다.
		
		return result;
	}
	
	// 강의 시간을 넣어주는 메소드
	public boolean checkTime(String startTime, String endTime)
	{
		boolean result = false;		// 강의 시간이 잘 입력됐는지 알려줄 변수
		String time = "";
		
		// 강의 시간을 하나의 스트링 형식으로 만든 후 true를 반환
		time = startTime + "-" + endTime;
		lecture.setTime(time);		// Lecture 객체에 강의 시간을 넣는다.
		result = true;				// 입력여부를 true로 바꾼다.
		
		return result;
	}
	
	// 강의실 값을 넣어주는 메소드
	public boolean checkClass(int class_n)
	{
		boolean result = false;		// 강의실 값이 잘 입력됐는지 알려줄 변수
		
		// 1부터 5 사이의 값이 잘 들어왔는지 확인 후, lecture에 넣고 true 반환
		if (class_n >= 1 && class_n <= 5)
		{
			lecture.setClass_n(class_n);		// Lecture 객체에 강의실 값을 넣는다.
			result = true;					// 입력여부를 true로 바꾼다.
		}
		
		return result;
	}
	
	// 강의료를 넣어주는 메소드
	public boolean checkCost(int cost)
	{
		boolean result = false;		// 강의료 값이 잘 입력됐는지 알려줄 변수
		
		// 강의료는 300000 이내로 한다. 값을 확인한 후 제대로 들어왔으면 lecture에 넣고 true 반환
		if (cost >= 0 && cost <= 300000)
		{
			lecture.setCost(cost);	// Lecture 객체에 강의료 값을 넣는다.
			result = true;			// 입력 여부를 true로 바꾼다.
		}
		
		return result;
	}
	
	// 총 인원을 넣어주는 메소드
	public boolean checkAll_people(int all_people)
	{
		boolean result = false;		// 총 인원 값이 잘 입력됐는지 알려줄 변수
		
		// 10 ~ 50 사이의 숫자인지 확인 후 제대로 들어왔으면 lecture에 넣고 true 반환
		if (all_people >= 10 && all_people <= 50)
		{
			lecture.setAll_people(all_people);		// Lecture 객체에 총 인원 값을 넣는다.
			result = true;							// 입력 여부를 true로 바꾼다.
		}
		
		return result;
	}
	
	// 값이 모두 등록된 lecture를 객체로 반환
	public Lecture getLecture()
	{
		return lecture;
	}

}
