package br.org.financeiro.guice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import br.org.financeiro.App;
import br.org.financeiro.service.PersonService;
import br.org.financeiro.service.impl.PersonServiceImpl;

public class GuiceModule extends AbstractModule {
	
	private App app;
	
	private EntityManagerFactory factory;
	
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
	public EntityManagerFactory provideEntityManagerFactory() {
		factory = Persistence.createEntityManagerFactory("persistenciaPU");
		return factory;
	}

	@Provides
	public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	@Override
	protected void finalize() throws Throwable {
		app = null;
		factory.close();
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