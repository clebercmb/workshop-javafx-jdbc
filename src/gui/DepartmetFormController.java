package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmetFormController implements Initializable{

	//Creating a dependency for Department
	Department entity;
	
	//# Declaration of the components´ Window
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	/**
	 * Setting up Department dependency
	 * 
	 * @param entity
	 */
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	
	//#Methods to handle the Buttons´ event
	@FXML
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBTCancelAction");
	}
	
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

	/**
	 * Method to fill up Department Form with the Department dependency
	 */
	public void updateFormData() {
		if (entity ==  null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
}
