package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	// Ritorna uno studente data una matricola
	public Studente getStudenteByMatricola(int matricola) {

		String sql = "SELECT matricola, nome, cognome, cds  FROM studente WHERE matricola = ?";
		Studente studente = null;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			
			if (res.next()) {
				studente = new Studente(matricola, res.getString("nome"), res.getString("cognome"), res.getString("cds"));
			}
//			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return studente;
	}

	// Ritorna la lista di corsi cui lo studente è iscritto
	public List<Corso> getCorsiByStudente(int matricola) {

		String sql = "SELECT c.codins, crediti, nome, pd  FROM corso as c, iscrizione as i WHERE c.codins = i.codins and i.matricola = ? ";
		
		List<Corso> corsi = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Corso corso = new Corso(res.getString("codins"), res.getInt("crediti"),
						res.getString("nome"), res.getInt("pd"));
				corsi.add(corso);
			}

//			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return corsi;
	}

	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {
		
		String sql = "SELECT matricola, codins FROM iscrizione WHERE codins = ? and matricola = ? ";
		
		boolean trovato = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodIns());
			st.setInt(2, studente.getMatricola());
			ResultSet res = st.executeQuery();

			if (res.next()) {
				trovato = true;
			}

//			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return trovato;
	}
}
