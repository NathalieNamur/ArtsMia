package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<?> boxLUN;

    @FXML
    private Button btnCalcolaComponenteConnessa;

    @FXML
    private Button btnCercaOggetti;

    @FXML
    private Button btnAnalizzaOggetti;

    @FXML
    private TextField txtObjectId;

    @FXML 
    private TextArea txtResult;

    
    
    @FXML
    void doAnalizzaOggetti(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	
    	model.creaGrafo();
    	
    	txtResult.appendText("Grafo creato.");
    	txtResult.appendText("\nNumero vertici grafo: "+model.numeroVertici());
    	txtResult.appendText("\nNumero archi grafo: "+model.numeroArchi());

    }

    
    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	
    	int objectId;
    	
    	try {
    		objectId = Integer.parseInt(txtObjectId.getText());
    	}
    	catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un id oggetto numerico.");
    		return;
    	}
    	
    	ArtObject vertice = model.getObject(objectId);
    	
    	if(vertice == null) {
    		txtResult.appendText("Oggetto inesistente!");
    		return;
    	}
    	
    	
    	txtResult.appendText("Dimensione componente connessa: "+model.getDimComponenteConnessa(vertice));

    }

    
    @FXML
    void doCercaOggetti(ActionEvent event) {

    }

    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
