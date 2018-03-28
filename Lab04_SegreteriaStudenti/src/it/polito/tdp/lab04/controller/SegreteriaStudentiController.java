/**
 /**
 * Sample Skeleton for 'SegreteriaStudenti.fxml' Controller Class
 */

package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class SegreteriaStudentiController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="comboBoxId"
	private ComboBox<Corso> comboBoxId; // Value injected by FXMLLoader

	@FXML // fx:id="btnCercaIscritti"
	private Button btnCercaIscritti; // Value injected by FXMLLoader

	@FXML // fx:id="txtMatricola"
	private TextField txtMatricola; // Value injected by FXMLLoader

	@FXML // fx:id="btnCompletamento"
	private Rectangle btnCompletamento; // Value injected by FXMLLoader

	@FXML // fx:id="txtNome"
	private TextField txtNome; // Value injected by FXMLLoader

	@FXML // fx:id="txtCognome"
	private TextField txtCognome; // Value injected by FXMLLoader

	@FXML // fx:id="btnCercaCorsi"
	private Button btnCercaCorsi; // Value injected by FXMLLoader

	@FXML // fx:id="btnIscrivi"
	private Button btnIscrivi; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="btnReset"
	private Button btnReset; // Value injected by FXMLLoader

	private Model model;

	@FXML
	void doCercaCorsi(ActionEvent event) {

	}

	@FXML
	void doCercaIscrittiCorso(ActionEvent event) {

	}

	@FXML
	void doChooseCorso(ActionEvent event) {

	}

	@FXML
	void doCompletamento(MouseEvent event) {

		String matricola = txtMatricola.getText();
		try {
			String nomeCognome = model.getNomeCognomeFromMatricola(Integer.parseInt(matricola));
			String array[] = nomeCognome.split(" ");
			txtNome.setText(array[0]);
			txtCognome.setText(array[1]);
			
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire solo caratteri numerici.");
		}

	}

	@FXML
	void doIscrivi(ActionEvent event) {

	}

	@FXML
	void doReset(ActionEvent event) {
		txtMatricola.clear();
		txtNome.clear();
		txtCognome.clear();
		txtResult.clear();
	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		assert comboBoxId != null : "fx:id=\"comboBoxId\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCompletamento != null : "fx:id=\"btnCompletamento\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		comboBoxId.setItems(FXCollections.observableArrayList(model.getTuttiICorsi()));

	}
}
