
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<Country> cmbState;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	String anno= this.txtAnno.getText();
    	if (anno.isEmpty()) {
    		this.txtResult.setText("Nessun anno inserito");
    		return;
    	}
    	int year;
    	try {
    		year=Integer.parseInt(anno);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un valore numerico come anno");
    		return;
    	}
    	if (year>2016 || year<1816) {
    		this.txtResult.setText("Errore. L'anno deve essere compreso tra 1816 e 2016");
    		return;
    	}
    	this.model.creaGrafo(year);
    	this.txtResult.setText(model.stampaStatiConGrado());
    }
    
    @FXML
    void doStatiRaggiungibili(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbState != null : "fx:id=\"cmbState\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbState.getItems().addAll(model.getAllStates());
    }
}
