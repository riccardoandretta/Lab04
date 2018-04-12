package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
				
				// non chiudo la connessione perchè utilizzo un pattern particolare in ConnectDB che fa sì che non ci sia bisogno di chiuderla
			}

			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {

		String sql = "SELECT codins, crediti, nome, pd  FROM corso WHERE codins = ?";
		
		// salvo i dati nell oggetto ricevuto
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodIns());
			ResultSet res = st.executeQuery();

			corso.setCrediti(res.getInt("crediti"));
			corso.setNome(res.getString("nome"));
			corso.setPd(res.getInt("pd"));

//			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {

		String sql = "SELECT s.matricola, nome, cognome, cds  FROM studente as s, iscrizione as i WHERE s.matricola = i.matricola and i.codins = ?";
		List<Studente> studenti = new LinkedList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodIns());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Studente studente = new Studente(rs.getInt("matricola"), rs.getString("nome"),
						rs.getString("cognome"), rs.getString("cds"));
				studenti.add(studente);
			}

//			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return studenti;
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al
	 * corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {

		String sql = "INSERT INTO iscrizione (matricola, codins) VALUES (?, ?)";
		boolean returnValue = false;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodIns());

			int res = st.executeUpdate();	

			if (res == 1)
				returnValue = true;

//			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// ritorna true se l'iscrizione e' avvenuta con successo
		return returnValue;
	}
}
