package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			String db_host = AppConfig.getConfigValue("db_host");
			String db_port = AppConfig.getConfigValue("db_port");
			String db_name = AppConfig.getConfigValue("db_name");
			String connectionUrl = "jdbc:mysql://"+db_host+":"+db_port+"/"+db_name;
			
			configuration.setProperty("hibernate.connection.url", connectionUrl);
			configuration.setProperty("hibernate.connection.username", AppConfig.getConfigValue("db_username"));
			configuration.setProperty("hibernate.connection.password", AppConfig.getConfigValue("db_password"));
			
		    configuration.configure();
		    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
		            configuration.getProperties()).build();      
		    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		    return sessionFactory;
		    
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static SessionFactory getSessionFactory() {
		if (sessionFactory==null) buildSessionFactory();
			return sessionFactory;
	}
}