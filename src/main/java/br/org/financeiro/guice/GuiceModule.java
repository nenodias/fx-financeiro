package br.org.financeiro.guice;

import java.util.Properties;
import java.util.Set;

import javax.persistence.Entity;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.reflections.Reflections;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mchange.v2.c3p0.DriverManagerDataSource;

import br.org.financeiro.App;
import br.org.financeiro.service.PersonService;
import br.org.financeiro.service.impl.PersonServiceImpl;

public class GuiceModule extends AbstractModule {
	
	private App app;
	
	private SessionFactory factory;
	
	public GuiceModule(App app) {
		this.app = app;
	}

	@Override
	protected void configure() {
		bind(App.class).toInstance(app);
		bind(PersonService.class).to(PersonServiceImpl.class);
	}

	@Provides
	@Singleton
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(true);
		dataSource.setDriverClass("org.sqlite.JDBC");
		dataSource.setJdbcUrl("jdbc:sqlite:banco.db");
		dataSource.setUser("");
		dataSource.setPassword("");
		return dataSource;
	}
	
	@Provides
	@Singleton
	public SessionFactory sessionFactory(DataSource dataSource) {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto","update");
		
		properties.setProperty("hibernate.connection.characterEncoding","ISO-8859-1");
		
		properties.setProperty("hibernate.c3p0.min_size","50");
		properties.setProperty("hibernate.c3p0.max_size","200");
		properties.setProperty("hibernate.c3p0.timeout","300");
		properties.setProperty("hibernate.c3p0.max_statements","50");
		properties.setProperty("hibernate.c3p0.idle_test_period","3000");
		
		Configuration hibernateConfig = new Configuration();
		hibernateConfig.addProperties(properties);
		Reflections reflections = new Reflections(App.class.getPackage().getName());
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
		for(Class<?> clazz : classes) {
			hibernateConfig.addAnnotatedClass(clazz);
		}
		
		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySetting(Environment.DATASOURCE, dataSource);
        serviceRegistryBuilder.applySettings(hibernateConfig.getProperties());
        StandardServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        SessionFactory sessionFactory = hibernateConfig.buildSessionFactory(serviceRegistry);
        
        factory = sessionFactory;
		return sessionFactory;
	}

	@Provides
	@Singleton
	public Session provideEntityManager(SessionFactory sessionFactory) {
		return sessionFactory.openSession();
	}

	@Override
	protected void finalize() throws Throwable {
		app = null;
		if(factory != null && !factory.isClosed()) {
			factory.close();
		}
		super.finalize();
	}

	public void stop() {
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}