package application.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import application.model.Score;

public class DatabaseHandler {

	private static DatabaseHandler instance = null;
	private Connection con = null;
	
	private DatabaseHandler() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:Scores.db");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DatabaseHandler getInstance() {
		if(instance == null)
			instance = new DatabaseHandler();
		return instance;
	}
	
	// Metodo che mi ritorna una lista degli scores e username che stanno nel database
	public synchronized List<Score> getScores() {
		List<Score> list = new ArrayList<Score>();
		String query = "SELECT * from Scores;";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = con.prepareStatement(query);
			rs = st.executeQuery();
			while(rs.next()) {
				Score score = new Score();
				score.setUsername(rs.getString("Username"));
				score.setScore(rs.getInt("Score"));
				list.add(score);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(st);
		}
		sortList(list);
		return list;
	}
	
	// Metodo che mi salva lo score a fine partita inserendo uno username
	public synchronized void loadScore(String username, int score) {
		if(username == null)
			return;
		String query = "INSERT INTO Scores (Username, Score) VALUES(?,?);";
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(query);
			st.setString(1, username);
			st.setInt(2, score);
			st.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			closeStatement(st);
		}
	}
	
	// Metodo che mi verifica se già esiste uno username
	public synchronized boolean userExist(String username) {
		String query = "SELECT * FROM Scores WHERE username=?;";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = con.prepareStatement(query);
			st.setString(1, username);
			rs = st.executeQuery();
			if(rs.next())
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(st);
		}
		return false;
	}
	
	// Metodo che dato uno username mi restituisce il suo score 
	public synchronized int getUsernameScore(String username) {
		int score = 0;
		String query = "SELECT Score FROM Scores WHERE username=?;";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = con.prepareStatement(query);
			st.setString(1, username);
			rs = st.executeQuery();
			if(rs.next())
				score = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(st);
		}
		return score;
	}
	
	// Metodo che mi aggiorna uno score 
	public synchronized void updateScore(String username, int score) {
		String query = "UPDATE Scores SET Score=? WHERE username=?;";
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(query);
			st.setInt(1, score);
			st.setString(2, username);
			st.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(st);
		}
	}
	
	private static void closeResultSet(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void closeStatement(PreparedStatement st) {
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void sortList(List<Score> list) {
		list.sort(new Comparator<Score>() {
			@Override
			public int compare(Score o1, Score o2) {
				Integer score1 = o1.getScore();
				Integer score2 = o2.getScore();
				return score1.compareTo(score2) *-1;
			}
		});
	}
}
