package com.listener;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// ServletContextListener : 웹 컨테이너가 시작되거나 종료될때 발생하는 이벤트를 처리하는 리스너

@WebListener
public class WebAppinit implements ServletContextListener {
	private String pathname = "/WEB-INF/userCount.properties";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 서버가 시작되는 시점에 호출
		
		// 실제 경로
		pathname = sce.getServletContext().getRealPath(pathname);
		
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 서버가 종료되는 시점에 호출
		
	}
	
	protected void loadFile() {
		// 서버에 저장된 접속자수 불러오기 
	
	}
	
	protected void saveFile() {
		// 서버에 접속자수를 프로퍼티로 저장
		long toDay, yesterDay, total;
		Properties p = new Properties();
		
		try( FileOutputStream fos = new FileOutputStream(pathname) ) {
			toDay = CountManager.getToDayCount();
			yesterDay = CountManager.getYesterDayCount();
			total = CountManager.getTotalCount();
			
			p.setProperty("toDay", Long.toString(toDay));
			p.setProperty("yesterDay", Long.toString(yesterDay));
			p.setProperty("total", Long.toString(total));
			
			p.store(fos, "count");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
