package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import utils.DBConnection;

import model.UserModel;

public class UserDAO {
	
    public static int addUser(UserModel user) {
        String query = "INSERT INTO users (fullName, email, password, profilePicture, role, created_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(5, user.getRole());
            ps.setDate(6, new java.sql.Date(user.getCreatedDate().getTime()));

            if (user.getProfilePicture() != null) {
                ps.setBytes(4, user.getProfilePicture());
            } else {
                ps.setNull(4, Types.BLOB);
            }
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        return -1;
    }
    
    public static UserModel getUserByEmail(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UserModel(
                        rs.getInt("id"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBytes("profilePicture"),
                        rs.getInt("role"),
                        rs.getDate("created_date")
                        
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
