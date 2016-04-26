package br.edu.ite.financeiro.util;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public interface ControllerParameter extends Initializable{

	Map<String, Object>getParametros();
	void setParametros(Map<String, Object> parametros);
	void initialize(URL location, ResourceBundle resources);
}
