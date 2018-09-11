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

//������,ʵ�ֵĵ�һ���ӿ��Ǽ���session�Ĵ��������٣��ڶ����ӿ��Ǽ���application�Ĵ��������٣�ServletContext����application����
//�����Ļ�ÿ�����µĻỰ�ͻ��¼��������վ�������У�Ȼ���ڹر�tomcat����application��ʱ������洢�����ݿ���
//����һ�δ�Tomcat�ִ���һ���µ�application��ʱ�����ó������
public class CountListener implements HttpSessionListener,ServletContextListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("session����");
		//������session�����ʵ�ʱ�򣬾ͻ���������������Ҫ��web������
		//application������java����ֱ�ӵ��ã���Ҫ��ô�õ�
		ServletContext application=event.getSession().getServletContext();
		int num=0;
		//ÿ�����˷��ʶ�������һ��num����num�ŵ�������Ψһ��application�м�¼
		if(application.getAttribute("num")!=null) {
			num=(Integer)application.getAttribute("num");
		}
		num++;
		application.setAttribute("num", num);
		//����websocket��������ҳ����ʵʱ��ʾ����
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
		System.out.println("session����");
		
	}

	

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}


}
