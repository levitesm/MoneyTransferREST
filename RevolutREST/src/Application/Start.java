package Application;

	import java.util.HashMap;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
	import org.eclipse.jetty.servlet.ServletHolder;
	import org.glassfish.jersey.servlet.ServletContainer;
	

import Services.*;

	public class Start {
	    
	    public static void main(String[] args) throws Exception {
	       
	    
	        //Host service on jetty
	        startService();

	    }

	    public static Server startService() throws Exception {

	    	
	    	Server server = new Server();
	    	ServerConnector pxy = new ServerConnector(server);
	    	pxy.setPort(8086);
	    	server.addConnector(pxy);

	    	ContextHandlerCollection contexts = new ContextHandlerCollection();
	    	server.setHandler(contexts);

	    	ServletContextHandler testApp = new ServletContextHandler(contexts, "/",
	    	    ServletContextHandler.NO_SESSIONS);

	    	ServletHolder servletHolder = new ServletHolder(ServletContainer.class);      
	    	
	    	 servletHolder.setInitParameter("jersey.config.server.provider.classnames",
		                UserServices.class.getCanonicalName()+","+
		                AccountServices.class.getCanonicalName()+","+
		                TransactionServices.class.getCanonicalName()
		                        );

	    	testApp.addServlet(servletHolder, "/Revolut/*");
	    	


	    	server.setHandler(testApp);
	    	server.start();
	    	server.join();
	    	return server;
	    }

	}
