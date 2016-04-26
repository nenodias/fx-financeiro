package br.edu.ite.financeiro.util;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class ControllerParameterImpl implements ControllerParameter, Serializable{

	private static final long serialVersionUID = -4526498121588496645L;
	
	private Map<String, Object> parametros;

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	protected void initialize(URL location, ResourceBundle resources, Map<String, Object> parametros){
		
	}

	public void initialize(URL location, ResourceBundle resources) {
		initialize(location, resources, parametros);
	}
	
}
