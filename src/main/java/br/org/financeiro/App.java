package br.org.financeiro;
import br.org.financeiro.model.Person;
import br.org.financeiro.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application{
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    
    public App() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
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

 }
