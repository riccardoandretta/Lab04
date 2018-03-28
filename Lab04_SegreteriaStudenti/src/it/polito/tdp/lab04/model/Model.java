package it.polito.tdp.lab04.model;

import java.util.List;
import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private List<Corso> corsi;
	private CorsoDAO corsoDAO = null;
	private StudenteDAO studenteDAO = null;

	public Model() {
		corsoDAO = new CorsoDAO();
		studenteDAO = new StudenteDAO();
	}

	public List<Corso> getTuttiICorsi() {
		// Carico una sola volta tutti i corsi
		// sfruttando un meccanismo di cache
		if (this.corsi == null)
			this.corsi = corsoDAO.getTuttiICorsi();
		return this.corsi;
	}

	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		// opzione 2: faccio lavorare il database
		return corsoDAO.getStudentiIscrittiAlCorso(corso);
	}

	public String getNomeCognomeFromMatricola(int matricola) {
		Studente s = studenteDAO.getStudenteByMatricola(matricola);
		if (s == null) {
			// la matricola non esiste
			String result = "Not found";
			return result;
		}
		return s.getNome() + " " + s.getCognome();
	}

	public List<Studente> getStudentiIscrittiAlCorso(String codins) {
		List<Studente> studenti = studenteDAO.getStudentiByCodins(codins);
		return studenti;
	}


}
