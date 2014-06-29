package pl.ekosmiec.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

	public static Connection getDatabaseConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return null;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/ekosmiec", "ekosmiec",
					"jestemFilatelista");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
		return connection;
	}
	
	public void getRodzajeKontenerow(){
		Connection conn = getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, nazwa, pojemnosc, opis FROM ekosmiec.rodzaje_kontenerow");
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("nazwa");
				Integer volume = rs.getInt("pojemnosc");
				String desc = rs.getString("opis");
				System.out.println("Rodzaj kontenera: id:" + id + " nazwa:" + name + " pojemnosc:" + volume + " opis:" + desc);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
