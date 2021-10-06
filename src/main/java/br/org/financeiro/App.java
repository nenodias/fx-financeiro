package br.org.financeiro;
import com.google.inject.Guice;
import com.google.inject.Injector;

import br.org.financeiro.guice.GuiceFXMLLoader;
import br.org.financeiro.guice.GuiceModule;
import br.org.financeiro.model.Person;
import br.org.financeiro.service.PersonService;
import br.org.financeiro.view.PersonEditDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application{
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    
    private Injector injector;
    
    private GuiceModule module;
    
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    public ObservableList<Person> getPersonData() {
        return personData;
    }
     
    public App() {
    	module = new GuiceModule(this);
    	injector = Guice.createInjector(module);
        final PersonService personService = injector.getInstance(PersonService.class);
    	personData.addAll(personService.list());
    }
    
	public static void main(String args[]){
		launch(args);
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage stage){
		 this.primaryStage = stage;
	     this.primaryStage.setTitle("AddressApp");
	     
	     initRootLayout();
	     showPersonOverview();
	}
	@Override
	public void stop() throws Exception {
		this.module.stop();
		this.module = null;
		this.injector = null;
		super.stop();
	}

	private void initRootLayout() {
		try {
			GuiceFXMLLoader loader = new GuiceFXMLLoader(injector);
			loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void showPersonOverview() {
		try {
			GuiceFXMLLoader loader = new GuiceFXMLLoader(injector);
			loader.setLocation(App.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();			
			
			rootLayout.setCenter(personOverview);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean showPersonEditDialog(Person person) {
		try {
			GuiceFXMLLoader loader = new GuiceFXMLLoader(injector);
			loader.setLocation(App.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane personEditDialog = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);	
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(personEditDialog);
			dialogStage.setScene(scene);
			
			
			PersonEditDialogController controller = (PersonEditDialogController)loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			
			dialogStage.showAndWait();
			return controller.isClicked();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

 }
