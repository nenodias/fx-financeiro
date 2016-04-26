package br.edu.ite.financeiro.util;

import java.util.Map;

import javafx.util.Callback;

public class ControllerFactoryUtil {
	
	public static Callback<Class<?>, Object> toController(final Map<String, Object> parametros){
		Callback<Class<?>, Object>controllerFactory = new Callback<Class<?>, Object>() {

			public Object call(Class<?> controllerClass) {
				if (ControllerParameter.class.isAssignableFrom(controllerClass) ) {
					ControllerParameter controller = null;
					try {
						controller = (ControllerParameter)controllerClass.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
		            controller.setParametros(parametros);
		            return controller ;
		        } else {
		            try {
		                return controllerClass.newInstance();
		            } catch (Exception exc) {
		                throw new RuntimeException(exc);
		            }
		        }
			}
            
        };
        return controllerFactory;
	}

}
