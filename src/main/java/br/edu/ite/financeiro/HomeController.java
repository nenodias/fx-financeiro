package br.edu.ite.financeiro;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import br.edu.ite.financeiro.util.ControllerParameterImpl;

public class HomeController extends ControllerParameterImpl{
	
	private static final long serialVersionUID = -3562405081728036960L;

	@FXML
	private Label messageLabel;
	
	@Override
	protected void initialize(URL location, ResourceBundle resources, Map<String, Object> parametros) {
		if(messageLabel != null){
			messageLabel.setText((String)parametros.get("mensagem"));
		}
	}
}
