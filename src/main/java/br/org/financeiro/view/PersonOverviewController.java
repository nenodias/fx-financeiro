package br.org.financeiro.view;

import org.apache.commons.lang3.StringUtils;

import br.org.financeiro.App;
import br.org.financeiro.model.Person;
import br.org.financeiro.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
	
	private App app;

    @FXML
    private TableView<Person> personTable;
    
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    
    @FXML
    private Label lastNameLabel;
    
    @FXML
    private Label streetLabel;
    
    @FXML
    private Label postalCodeLabel;
    
    @FXML
    private Label cityLabel;
    
    @FXML
    private Label birthdayLabel;
	
    public PersonOverviewController() {
    }
    
    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
        showPersonDetails(null);
        
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
    }
    
    @FXML
    private void handleDeletePerson() {
    	int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
        	Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhuma Pessoa Selecionada");
            alert.setContentText("Por favor, selecione uma pessoa na tabela.");

            alert.showAndWait();
        }
    }
    
    public void setMainApp(App app) {
        this.app = app;
        personTable.setItems(app.getPersonData());
    }
    
    public void showPersonDetails(Person person) {
    	if(person != null) {
    		firstNameLabel.setText(person.getFirstName());
		 	lastNameLabel.setText(person.getLastName());
		    streetLabel.setText(person.getStreet());
		    postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
		    cityLabel.setText(person.getCity());
	    	birthdayLabel.setText(DateUtil.format(person.getBirthday()));
    	}else {
    		firstNameLabel.setText(StringUtils.EMPTY);
		 	lastNameLabel.setText(StringUtils.EMPTY);
		    streetLabel.setText(StringUtils.EMPTY);
		    postalCodeLabel.setText(StringUtils.EMPTY);
		    cityLabel.setText(StringUtils.EMPTY);
	    	birthdayLabel.setText(StringUtils.EMPTY);
    	}
    }
}
