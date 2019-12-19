package ucll.project.domain.user;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.star.Star;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepositoryDb implements UserRepository {

    @Override
    public void createUser(User user, String password) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"award-team9\".user " +
                             "(username, firstname, lastname, email, gender, role, password) VALUES (?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
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
             ResultSet rs = stmt.executeQuery("SELECT * FROM \"award-team9\".user")) {
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
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team9\".user WHERE username = ?")) {
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
                     "WHERE id = ? ")) {
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
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM \"award-team9\".user WHERE id = ?")) {
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
        user.setManager(rs.getBoolean("manager"));
        return user;
    }

    private static List<User> usersFromResult(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("id"));
            user.setFirstName(rs.getString("firstname"));
            user.setLastName(rs.getString("lastname"));
            user.setEmail(rs.getString("email"));
            user.setRole(Role.valueOf(rs.getString("role")));
            user.setHashedPassword(rs.getString("password"));
            user.setManager(rs.getBoolean("manager"));
            users.add(user);
        }
        return users;
    }

    private static Star starFromResult(ResultSet rs) throws SQLException {
        Star star = new Star();
        star.setStar_id(rs.getInt("star_id"));
        star.setComment(rs.getString("comment"));
        star.setReceiver_id(rs.getInt("receiver_id"));
        star.setSender_id(rs.getInt("sender_id"));
        Array array = rs.getArray("tags");
        String[] arrayString = (String[]) array.getArray();
        List<String> list = Arrays.asList(arrayString);
        star.setTags(list);
        star.setTimestamp(rs.getTimestamp("timestamp"));
        return star;
    }

    private static int stmtSetUser(PreparedStatement stmt, int i, User user) throws SQLException {
        stmt.setString(i++, user.getFirstName());
        stmt.setString(i++, user.getLastName());
        stmt.setString(i++, user.getEmail());
        stmt.setString(i++, user.getRole().toString());
        stmt.setString(i++, user.getHashedPassword());
        return i;
    }

    @Override
    public int verify(String email, String password) {
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id from \"award-team9\".user where email = ? and password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Star> getStar(int receiver_id) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("select * from \"award-team9\".star where receiver_id = ?")) {
            ArrayList<Star> starList = new ArrayList<>();
            stmt.setInt(1,receiver_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    starList.add(starFromResult(rs));
                }
                return starList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public int UserIdByName(String firstname, String lastname) {
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id from \"award-team9\".user where firstname = ? and lastname = ?");
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public String userMailById(int id){
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT email from \"award-team9\".user where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public String userNameById(int id){
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT firstname, lastname from \"award-team9\".user where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            String name = "";
            if (rs.next()) {
                name  += rs.getString("firstname");
                name += " " + rs.getString("lastname");
                return name;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public List<User> getAllMAnagers(){
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * from \"award-team9\".user where manager = ?");
            stmt.setBoolean(1, true);
            ResultSet rs = stmt.executeQuery();
            List<User> managers = usersFromResult(rs);
            return managers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAvailableStars(int id) {
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT available_stars from \"award-team9\".user where id = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("available_stars");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public void setAvailableStar(int id, int aantal){
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("update \"award-team9\".user set available_stars = ? where id = ? ");
            stmt.setInt(1, aantal);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public void reassignStars(){
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("update \"award-team9\".user\n" +
                    "set available_stars = 3");
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public void addUserWithoutPassword(User user){
        try (Connection conn = ConnectionPool.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("insert into \"award-team9\".user\n" +
                    "(id,firstname,lastname,email,role,password,available_stars,superuser,manager)\n" +
                    "values (\n" +
                    "?, ?, ?,?, ?, '', 3, ?, ?\n" +
                    ")");
            stmt.setInt(1,getHighestID() + 1);
            stmt.setString(2,user.getFirstName());
            stmt.setString(3,user.getLastName());
            stmt.setString(4,user.getEmail());
            stmt.setString(5,user.getRole().toString());
            stmt.setBoolean(6,user.isSuperuser());
            stmt.setBoolean(7,user.isManager());
            stmt.executeUpdate();

        } catch ( SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public int getHighestID(){
        try (Connection conn = ConnectionPool.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select id from \"award-team9\".user order by id desc");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }
            throw new RuntimeException("could not find id in database");
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public void setPassword(int id,String password){
        try (Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("update \"award-team9\".user set password = ? where id = ?");
            stmt.setString(1,password);
            stmt.setInt(2,id);
            stmt.executeUpdate();
        } catch ( SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public boolean isSuperUser(int id){
        try (Connection conn = ConnectionPool.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select superuser from \"award-team9\".user where id = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getBoolean(1);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}