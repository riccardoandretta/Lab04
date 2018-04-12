/**
 /**
 * Sample Skeleton for 'SegreteriaStudenti.fxml' Controller Class
 */

package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
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

		txtResult.clear();
		try {
			int matricola = Integer.parseInt(txtMatricola.getText());

			Studente studente = model.getStudente(matricola);
			if (studente == null) {
				txtResult.appendText("Nessun risultato: matricola inesistente");
				return;
			}

			List<Corso> corsi = model.getCorsiByStudente(studente);

			StringBuilder sb = new StringBuilder();

			for (Corso corso : corsi) {
				sb.append(String.format("%-8s ", corso.getCodIns()));
				sb.append(String.format("%-4s ", corso.getCrediti()));
				sb.append(String.format("%-45s ", corso.getNome()));
				sb.append(String.format("%-4s ", corso.getPd()));
				sb.append("\n");
			}
			txtResult.appendText(sb.toString());
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}

	}

	@FXML
	void doCercaIscrittiCorso(ActionEvent event) {
		txtResult.clear();
		txtNome.clear();
		txtCognome.clear();

		try {
			if (comboBoxId.getValue() == null) {
				txtResult.setText("Selezionare un corso");
				return;
			}
			List<Studente> studenti = model.getStudentiIscrittiAlCorso(comboBoxId.getValue());

			StringBuilder sb = new StringBuilder();

			for (Studente studente : studenti) {

				sb.append(String.format("%-10s ", studente.getMatricola()));
				sb.append(String.format("%-20s ", studente.getCognome()));
				sb.append(String.format("%-20s ", studente.getNome()));
				sb.append(String.format("%-10s ", studente.getCds()));
				sb.append("\n");
			}

			txtResult.appendText(sb.toString());
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}

	}

	@FXML
	void doChooseCorso(ActionEvent event) {
		// inutile
	}

	@FXML
	void doCompletamento(MouseEvent event) {

		txtResult.clear();

		try {
			String matricola = txtMatricola.getText();
			String nomeCognome = model.getNomeCognomeFromMatricola(Integer.parseInt(matricola));
			String array[] = nomeCognome.split(" ");
			txtNome.setText(array[0]);
			txtCognome.setText(array[1]);

		} catch (NumberFormatException e) {
			txtResult.setText("Inserire solo caratteri numerici.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}

	}

	@FXML
	void doIscrivi(ActionEvent event) {
		txtResult.clear();

		try {
			if (comboBoxId.getValue() == null) {
				txtResult.setText("Selezionare un corso");
				return;
			}
			int matricola = Integer.parseInt(txtMatricola.getText());

			Studente studente = model.getStudente(matricola);
			if (studente == null) {
				txtResult.setText("Nessun risultato: matricola inesistente");
				return;
			}
			// Punto 5: cerca se è iscritto

			// if (model.isStudenteIscrittoACorso(studente, comboBoxId.getValue())) {
			// txtResult.setText("Lo studente "+matricola+" è iscritto al corso
			// "+comboBoxId.getValue().getNome());
			// }
			// else {
			// txtResult.setText("Lo studente "+matricola+" NON è iscritto al corso
			// "+comboBoxId.getValue().getNome());
			// }

			// Punto 6: iscrivi studente

			if (!model.isStudenteIscrittoACorso(studente, comboBoxId.getValue())) {
				//controllo che l'iscrizione sia andata a buon fine
				if (model.inscriviStudenteACorso(studente, comboBoxId.getValue())) {
					txtResult.setText("Lo studente " + matricola + " è stato iscritto al corso "
							+ comboBoxId.getValue().getNome() + " con successo!");
				} else {
					txtResult.appendText("Errore durante l'iscrizione al corso");
				}
			} else {
				txtResult.setText(
						"Lo studente " + matricola + " è già iscritto al corso " + comboBoxId.getValue().getNome());
			}

		} catch (NumberFormatException e) {
			txtResult.setText("Inserire matricola valida (solo caratteri numerici).");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}

	}

	@FXML
	void doReset(ActionEvent event) {
		txtMatricola.clear();
		txtNome.clear();
		txtCognome.clear();
		txtResult.clear();
		comboBoxId.getSelectionModel().clearSelection(); // pulisco la selezione del corso
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
