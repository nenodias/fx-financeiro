package br.org.financeiro.guice;

import java.io.IOException;

import com.google.inject.Injector;

import javafx.fxml.FXMLLoader;

public class GuiceFXMLLoader extends FXMLLoader {

	private Injector injector;

	public GuiceFXMLLoader(Injector injector) {
		super();
		this.injector = injector;
	}
	
	@Override
	public <T> T load() throws IOException {
		T retorno = super.load();
		Object controller = super.getController();
		if(controller != null) {
			injector.injectMembers(controller);
			if(GuiceInit.class.isAssignableFrom(controller.getClass())) {
				((GuiceInit)controller).init();
			}
		}
		return retorno;
	}
	
}
