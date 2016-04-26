package br.edu.ite.financeiro;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.ite.financeiro.util.ControllerFactoryUtil;
import br.edu.ite.financeiro.util.ControllerParameterImpl;

public class HelloController extends ControllerParameterImpl{
	
	private static final long serialVersionUID = 8120582536070269377L;

	private static final Logger log = LoggerFactory.getLogger(HelloController.class);

	@FXML
	private TextField login;
	
	@FXML
	private TextField password;
	
	@FXML
	private Label messageLabel;
	
	@FXML
	private void login(javafx.event.ActionEvent event) throws IOException {
		try{
			if (login.getText().equals("admin") && password.getText().equals("123")) {
				String fxmlFile = File.separator+"fxml"+File.separator+"home.fxml";
		        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		        Map<String, Object> parametrosTela = new HashMap<String, Object>();
		        parametrosTela.put("mensagem", "Olá mundo");
		        loader.setControllerFactory(ControllerFactoryUtil.toController(parametrosTela));
		        Parent rootNode = (Parent) loader.load();
				Scene scene = new Scene(rootNode, 400, 200);
				Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Button botao = (Button)event.getSource();
				botao.getParent();
				appStage.setScene(scene);
				appStage.show();
			} else {
				messageLabel.setText("Password is incorrect. Please Try Again");
			}
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
