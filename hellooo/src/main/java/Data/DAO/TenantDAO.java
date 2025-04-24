package Data.DAO;

import Data.Model.Tenant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;

public class TenantDAO implements DAO<Tenant> {
    private final Connection connection;

    public TenantDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Tenant entity) {
        String query = "INSERT INTO \"Tenant\" (username, \"Password\", fullname, gender, BOD, \"phone no\", email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getFullname());
            stmt.setString(4, entity.getGender());
            stmt.setDate(5, new Date(entity.getDoB().getTime()));
            stmt.setInt(6, entity.getPhone_number());
            stmt.setString(7, entity.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Tenant record inserted successfully.");
            } else {
                System.out.println("No Tenant record was inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting Tenant record: " + e.getMessage(), e);
        }
    }

    public void delete(Tenant entity) {
        String query = "DELETE FROM \"Tenant\" WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.executeUpdate();
            System.out.println("Tenant record deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Tenant record", e);
        }
    }

    public Tenant logIn(String username, String password) {
        String query = "SELECT * FROM \"Tenant\" WHERE username = ? AND \"Password\" = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Tenant Tenant = new Tenant();
                    Tenant.setUsername(rs.getString("username"));
                    Tenant.setPassword(rs.getString("Password"));
                    Tenant.setFullname(rs.getString("fullname"));
                    Tenant.setGender(rs.getString("gender"));
                    Tenant.setDoB(new java.util.Date(rs.getDate("bod").getTime()));
                    Tenant.setPhone_number(rs.getInt("phone no"));
                    Tenant.setEmail(rs.getString("email"));
                    return Tenant;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving Tenant record: " + e.getMessage(), e);
        }
        return null;
    }
}
