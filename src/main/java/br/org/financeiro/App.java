package br.org.financeiro;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.org.financeiro.model.Person;
import br.org.financeiro.persistence.model.PersonEntity;
import br.org.financeiro.view.PersonEditDialogController;
import br.org.financeiro.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application{
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    private EntityManagerFactory factory;
    
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    
    private EntityManagerFactory getEntityManagerFactory(){
    	return Persistence.createEntityManagerFactory("persistenciaPU");
    }
    	
    public EntityManager getEntityManager(){
    	if(factory != null && factory.isOpen()) {
    		return factory.createEntityManager();
    	}
    	return null;
    }
    
    public App() {
    	this.factory = getEntityManagerFactory();
        final EntityManager em = getEntityManager();
    	Query createQuery = em.createQuery("FROM PersonEntity");
    	List<PersonEntity> resultList = createQuery.getResultList();
    	resultList.forEach((person) -> personData.add( PersonEntity.to(person) ));
    	em.close();
    }
    
    @Override
    public void stop() throws Exception {
    	this.finalize();
    	super.stop();
    }
    
    public void finalize() {
    	if(this.factory != null) {
    		this.factory.close();
    	}
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

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
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
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();			
			
			rootLayout.setCenter(personOverview);
			
			PersonOverviewController controller = loader.getController();
	        controller.setMainApp(this);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean showPersonEditDialog(Person person) {
		try {
			FXMLLoader loader = new FXMLLoader();
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
			controller.setApp(this);
			controller.setPerson(person);
			
			dialogStage.showAndWait();
			return controller.isClicked();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

 }
