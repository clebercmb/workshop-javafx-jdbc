package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener{

	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		
		Stage parentStage = Utils.currentStage(event);
		
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);  //<== Injecting Department into DepartmentFormController
		
		
		System.out.println("onBtNewAction");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	public void  updateTableView() {
		if (service == null)  {
			throw new IllegalStateException("Service was null");
		}
			
		List<Department> list = service.findAll();
			
		obsList = FXCollections.observableArrayList(list);
		
		tableViewDepartment.setItems(obsList);
		
	}
	
	
	/**
	 * Function to load a Form Window that allows you to register a new Department
	 * This function will have to be called from the new button  
	 * 
	 * @author Cleber Barbosa
	 * @param obj  			Injecting Department into DepartmentFormController
	 * @param abosoluteName	Form view to be open
	 * @param parentStage	Parent 
	 * @version 1.0 
	 */
	private void createDialogForm(Department obj, String abosoluteName, Stage parentStage) {
		try {
			//########  Logic to open a Form Window  ############ 
			FXMLLoader loader = new FXMLLoader(getClass().getResource(abosoluteName));
			Pane pane = loader.load();   // <== View loaded (Pane loaded) 
			
			//Injecting Department into DepartmetFormController
			DepartmetFormController controller = loader.getController(); //Reference for DepartmetFormController
			controller.setDepartment(obj); //Injecting Department into DepartmetFormController
			controller.setDepartmentService(service);
			controller.subscribDataChangeListener(this);
			controller.updateFormData(); //load Department into the Form Data 
			
 			
			// In order to open a Modal Dialog Window in front of the existing Window, you have to instantiate a new Stage.
			// It will be  a  Stage (Windows) in front of the other
			Stage dialogStage = new Stage();  //Create a new Window
			
			//Setting up the new Stage
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane));  //Because it is a new Stage, there will be a new Scene 
													//with a "pane/view" object as its root element
			dialogStage.setResizable(false);  
			dialogStage.initOwner(parentStage); //Setting up the parent Stage of this new window
			
			dialogStage.initModality(Modality.WINDOW_MODAL);  	//It says that this Windows will be a Modal, 
																//instead of having another behavior.
																//It means that the new Window will be stuck(blocked).
																//In other words, you will be able to access  the parent Window
																//only after you close this new Modal Window.
			dialogStage.showAndWait(); //Opens the new Window
			
		} catch (IOException e)  {
			Alerts.showAlert("IOException", "Error loadingview", e.getMessage(), AlertType.ERROR);
		}
	}


	@Override
	public void onDataChanged() {
		updateTableView();
		
	}
}
