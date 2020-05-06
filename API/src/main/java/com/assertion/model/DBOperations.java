package com.assertion.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class DBOperations {
	private final static String url = "jdbc:postgresql://localhost:5432/postgres";
	private final static String user = "postgres";
	private final static String password = "admin";
	JasyptEncryption encryption=new JasyptEncryption();
	public static Connection connect() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public HashMap<String, String> getPasswords() {
		HashMap<String, String> passwordList = new HashMap<>();
		try {
			Connection conn = connect();
			;
			try (PreparedStatement selectStmt = conn.prepareStatement("SELECT * from passwordmanager");
					ResultSet rs = selectStmt.executeQuery()) {
				if (!rs.isBeforeFirst()) {
					System.out.println("no rows found");
					passwordList.clear();
				} else {
					passwordList.clear();
					while (rs.next()) {
						passwordList.put(rs.getString(1), encryption.Decrypt(rs.getString(2)));
					}
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return passwordList;
	}

	public  boolean createPassword(Password password) {
		System.out.println("***********Connecting to Database************");
		String SQL = "INSERT INTO passwordmanager(website,password) VALUES(?,?)";

		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, password.getWebsite().replaceAll("\\s", ""));
			pstmt.setString(2, encryption.Encrypt(password.getPassword().replaceAll("\\s", "")));
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return true;
	}

	public boolean updatePassword(Password password) {
		String SQL = "UPDATE passwordmanager SET password = ? WHERE website = ?";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			pstmt.setString(1, encryption.Encrypt(password.getPassword().replaceAll("\\s", "")));
			pstmt.setString(2, password.getWebsite().replaceAll("\\s", ""));
			System.out.println("Updating Password");
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return true;
	}

	public  boolean deletePassword(String[] delete) {
		int length = delete.length;
		StringBuilder deletePasswordList = new StringBuilder("DELETE FROM passwordmanager WHERE website IN (");
		deletePasswordList.append("'"+delete[0].replaceAll("\\s", "")+"'");
		for (int i = 1; i < length; i++) {
			deletePasswordList.append(",'" + delete[i].replaceAll("\\s", "")+"'");
		}
		deletePasswordList.append(")");
		String SQL = deletePasswordList.toString();
		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			System.out.println("Deleting passwords");
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return true;
	}
}
