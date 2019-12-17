package ucll.project.domain.user;

import ucll.project.db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDb implements UserRepository {

    @Override
    public void createUser(User user, String password) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"award-team9\".user " +
                     "(username, firstname, lastname, email, gender, role, password) VALUES (?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS))
        {
            user.hashAndSetPassword(password);
            stmtSetUser(stmt, 1, user);
            if (stmt.executeUpdate() == 0) {
                throw new RuntimeException("Failed to create user");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                generatedKeys.next();
                user.setUserId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User get(int userId) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team9\".user WHERE id = ?")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return userFromResult(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM \"award-team9\".user"))
        {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(userFromResult(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User loginUser(String username, String password) throws InvalidLogin {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team9\".user WHERE username = ?"))
        {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new InvalidLogin("Invalid username");
                }

                User user = userFromResult(rs);
                if (!user.isValidPassword(password)) {
                    throw new InvalidLogin("Invalid password");
                }

                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE \"award-team9\".user SET " +
                     "username = ?, firstname = ?, lastname = ?, email = ?, gender = ?, role = ?, password = ? " +
                     "WHERE id = ? "))
        {
            int i = stmtSetUser(stmt, 1, user);
            stmt.setInt(i, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM \"award-team9\".user WHERE id = ?"))
        {
            stmt.setInt(1, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static User userFromResult(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("id"));
        user.setFirstName(rs.getString("firstname"));
        user.setLastName(rs.getString("lastname"));
        user.setEmail(rs.getString("email"));
        user.setRole(Role.valueOf(rs.getString("role")));
        user.setHashedPassword(rs.getString("password"));
        return user;
    }

    private static int stmtSetUser(PreparedStatement stmt, int i, User user) throws SQLException {
        stmt.setString(i++, user.getFirstName());
        stmt.setString(i++, user.getLastName());
        stmt.setString(i++, user.getEmail());
        stmt.setString(i++, user.getRole().toString());
        stmt.setString(i++, user.getHashedPassword());
        return i;
    }

    public int UserIdByName(String firstname,String lastname){
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id from \"award-team9\".user where firstname = ? and lastname = ?"))
        {
            stmt.setString(1,firstname);
            stmt.setString(2,lastname);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public static void main(String[] args) {
        UserRepositoryDb db = new UserRepositoryDb();
        int id = db.UserIdByName("Daan", "Heivers");
        System.out.println(id);
    }
}