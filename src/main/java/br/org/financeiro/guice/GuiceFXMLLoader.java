package br.org.financeiro.guice;

import javax.inject.Inject;

import com.google.inject.Injector;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

public final class GuiceFXMLLoader extends FXMLLoader {

	@Inject
	public GuiceFXMLLoader(final Injector injector) {
		super();
		this.setControllerFactory(new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> param) {
				return injector.getInstance(param);
			}
		});
	}

}