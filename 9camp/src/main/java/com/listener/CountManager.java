package com.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// HttpSessionListener : 세션이 생성되거나 소멸될때 발생하는 이벤트를 처리하는 리스너
@WebListener
public class CountManager implements HttpSessionListener {
	private static int currentCount;
	private static long toDayCount;
	private static long yesterDayCount;
	private static long totalCount;
	
	public static void init(long toDay, long yesterDay, long total) {
		toDayCount = toDay;
		yesterDayCount = yesterDay;
		totalCount = total;
		
	} 
	
	public CountManager() {
		
	}
	
	public static int getCurrentCount() {
		return currentCount;
	}


	public static long getToDayCount() {
		return toDayCount;
	}



	public static long getYesterDayCount() {
		return yesterDayCount;
	}

	public static long getTotalCount() {
		return totalCount;
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// 세션이 만들어 질 때
		HttpSession session = se.getSession();
		ServletContext context = session.getServletContext();
		
		synchronized (se) {
			currentCount++;
			toDayCount++;
			totalCount++;
			
			context.setAttribute("currentCount", currentCount);
			context.setAttribute("toDayCount", toDayCount);
			context.setAttribute("totalCount", totalCount);
			context.setAttribute("yesterDayCount", yesterDayCount);
			
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// 세션이 소멸될 떄
		HttpSession session = se.getSession();
		ServletContext context = session.getServletContext();
		
		synchronized (se) {
			currentCount--;
			if(currentCount<0) {
				currentCount = 0;
			}
			
			context.setAttribute("currentCount", currentCount);
			context.setAttribute("toDayCount", toDayCount);
			context.setAttribute("totalCount", totalCount);
			context.setAttribute("yesterDayCount", yesterDayCount);
			
			
		}
		
		
		
	}

		

	
	

	
	
}
