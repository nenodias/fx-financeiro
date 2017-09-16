package br.org.financeiro.view;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.org.financeiro.App;
import br.org.financeiro.model.Person;
import br.org.financeiro.persistence.model.PersonEntity;
import br.org.financeiro.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonEditDialogController {

	@FXML
	private TextField firstNameField;
	
	@FXML
	private TextField lastNameField;
	
	@FXML
	private TextField streetField;
	
	@FXML
	private TextField cityField;
	
	@FXML
	private TextField postalCodeField;
	
	@FXML
	private TextField birthdayField;
	
	private App app;
	private Stage dialogStage;
	private Person person;
	private boolean onClicked = false;
	
	@FXML
	public void initialize() {
		
	}
	
	public void setPerson(Person person) {
		this.person = person;
		
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		streetField.setText(person.getStreet());
		cityField.setText(person.getCity());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		birthdayField.setText(DateUtil.format(person.getBirthday()));
	}
	
	public void setApp(App app) {
		this.app = app;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isClicked() {
		return onClicked;
	}
	
	@FXML
	private void handleOk() {
		if(isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setCity(cityField.getText());
			person.setPostalCode(Integer.valueOf(postalCodeField.getText()));
			person.setBirthday(DateUtil.parse(birthdayField.getText()));
			
			final EntityManager em = this.app.getEntityManager();
			final EntityTransaction t = em.getTransaction();
			t.begin();
			try {
				PersonEntity entity = PersonEntity.from(person);
				if(entity.getId() != null) {
					em.merge(entity);
				}else {
					em.persist(entity);
				}
			}catch(Exception ex) {
				t.rollback();
				ex.printStackTrace();
			}finally {
				t.commit();
				em.close();
			}
			
			onClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		 String errorMessage = "";

	        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
	            errorMessage += "Nome inválido!\n"; 
	        }
	        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
	            errorMessage += "Sobrenome inválido!\n"; 
	        }
	        if (streetField.getText() == null || streetField.getText().length() == 0) {
	            errorMessage += "Rua inválida!\n"; 
	        }

	        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
	            errorMessage += "Código Postal inválido!\n"; 
	        } else {
	            // tenta converter o código postal em um int.
	            try {
	                Integer.parseInt(postalCodeField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "Código Postal inválido (deve ser um inteiro)!\n"; 
	            }
	        }

	        if (cityField.getText() == null || cityField.getText().length() == 0) {
	            errorMessage += "Cidade inválida!\n"; 
	        }

	        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
	            errorMessage += "Aniversário inválido!\n";
	        } else {
	            if (!DateUtil.validDate(birthdayField.getText())) {
	                errorMessage += "Aniversário inválido. Use o formato dd.mm.yyyy!\n";
	            }
	        }

	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Mostra a mensagem de erro.
	            Alert alert = new Alert(AlertType.ERROR);
	                      alert.setTitle("Campos Inválidos");
	                      alert.setHeaderText("Por favor, corrija os campos inválidos");
	                      alert.setContentText(errorMessage);
	                alert.showAndWait();

	            return false;
	        }
	}
}
