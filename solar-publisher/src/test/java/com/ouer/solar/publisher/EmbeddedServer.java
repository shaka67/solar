package com.ouer.solar.publisher;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;

public class EmbeddedServer {

	public static void main(String[] args) throws Exception {
		final Tomcat tomcat = new Tomcat();
		tomcat.setPort(8888);
		tomcat.setBaseDir("target/tomcat");
		final Context ctx = tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());
		tomcat.getConnector().setURIEncoding("UTF-8");

//		final RedisSessionManager manager = new RedisSessionManager();
//		manager.setDatabase(0);
//		manager.setHost("10.8.100.2");
//		manager.setPort(6379);
//		manager.setMaxInactiveInterval(60);
//		ctx.setManager(manager);
//
//		ctx.getPipeline().addValve(new RedisSessionHandlerValve());

		// declare an alternate location for your "WEB-INF/classes" dir:
		final File additionWebInfClasses = new File("target/classes");
		final VirtualDirContext resources = new VirtualDirContext();
		resources.setExtraResourcePaths("/WEB-INF/classes="
				+ additionWebInfClasses);
		ctx.setResources(resources);

		tomcat.start();
		tomcat.getServer().await();
	}
}
