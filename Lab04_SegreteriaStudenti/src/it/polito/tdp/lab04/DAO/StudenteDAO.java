package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	// Ritorna la lista di studenti iscritti al corso codins
	public List<Studente> getStudentiByCodins(String codins) {

		String sql = "SELECT s.matricola, nome, cognome, cds  FROM studente as s, iscrizione as i WHERE s.matricola = i.matricola and i.codins = ? ";
		List<Studente> studenti = new ArrayList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Studente studente = new Studente(res.getInt("matricola"), res.getString("nome"),
						res.getString("cognome"), res.getString("cds"));
				studenti.add(studente);
			}

//			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return studenti;
	}
}
