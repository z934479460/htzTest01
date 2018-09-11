package listener;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import util.MyWebSocket;

//监听器,实现的第一个接口是监听session的创建和销毁，第二个接口是监听application的创建和销毁，ServletContext就是application的类
//这样的话每次有新的会话就会记录到访问网站的人数中，然后在关闭tomcat销毁application的时候把它存储到数据库中
//在下一次打开Tomcat又创建一个新的application的时候在拿出这个数
public class CountListener implements HttpSessionListener,ServletContextListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("session创建");
		//当有新session来访问的时候，就会进入这个方法，需要在web里配置
		//application不能在java类中直接调用，需要这么拿到
		ServletContext application=event.getSession().getServletContext();
		int num=0;
		//每次有人访问都会生成一个num，将num放到服务器唯一的application中记录
		if(application.getAttribute("num")!=null) {
			num=(Integer)application.getAttribute("num");
		}
		num++;
		application.setAttribute("num", num);
		//利用websocket技术，在页面上实时显示人数
	  for(MyWebSocket item:MyWebSocket.set) {
		  try {
			item.session.getBasicRemote().sendObject(num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("session销毁");
		
	}

	

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}


}
