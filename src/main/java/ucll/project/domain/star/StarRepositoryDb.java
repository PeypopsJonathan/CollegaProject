package ucll.project.domain.star;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.user.Role;
import ucll.project.domain.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StarRepositoryDb implements StarRepository {

    @Override
    public void createStar(Star star) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"award-team9\".star " +
                             "(receiver_id, sender_id, comment, tags, timestamp, star_id) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP , ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, star.getReceiver_id());
            stmt.setInt(2, star.getSender_id());
            stmt.setString(3, star.getComment());

            String[] data = star.getTags().toArray(new String[star.getTags().size()]);
            java.sql.Array sqlArray = conn.createArrayOf("varchar", data);
            stmt.setArray(4, sqlArray);
            stmt.setInt(5, star.getStar_id());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Star get(int starId) {
        return null;
    }

    @Override
    public List<Star> getAll() {
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM \"award-team9\".star")) {
            List<Star> stars = new ArrayList<>();
            while (rs.next()) {
                stars.add(starFromResultSet(rs));
            }
            return stars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Star star) {

    }

    @Override
    public void delete(Star star) {

    }

    /*private static int
    stmtSetStar(PreparedStatement stmt, int i, Star star) throws SQLException {
        stmt.setInt(i++, star.getSender_id());
        return i;
    }*/

    private static Star starFromResultSet(ResultSet rs) throws SQLException {
        Star star = new Star();
        star.setReceiver_id(rs.getInt(1));
        star.setSender_id(rs.getInt(2));
        star.setComment(rs.getString(3));
        star.setTags(sqlArrayToArrayList(rs.getArray(4)));
        star.setTimestamp(rs.getTimestamp(5));
        star.setStar_id(rs.getInt(6));
        return star;
    }

    private static ArrayList<String> sqlArrayToArrayList(Array array) throws SQLException {
        ArrayList<String> tags = new ArrayList<>();
        String[] rsTags = (String[]) array.getArray();
        for (int i = 0; i < rsTags.length; i++) {
            tags.add(rsTags[i]);
        }
        return tags;
    }


    @Override
    public List<Star> getUserInvolvedInStarExchanges(int userId){
        List<Star> exchanges = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team9\".star WHERE receiver_id = ? OR sender_id = ?")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    exchanges.add(starFromResultSet(rs));
                }
                return exchanges;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean starWasGivenBy(int userId, int starId){
        boolean result = false;
        for(Star star: getStarsSent(userId)){
            if(star.getStar_id()==starId){
                result = true;
            }
        }
        return result;
    }

    public boolean starWasReceivedBy(int userId, int starId){
        boolean result = false;
        for(Star star: getStarsReceived(userId)){
            if(star.getStar_id()==starId){
                result = true;
            }
        }
        return result;
    }

    @Override
    public List<Star> getStarsSent(int userId) {
        List<Star> received = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team9\".star WHERE sender_id = ?")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    received.add(starFromResultSet(rs));
                }
                return received;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Star> getStarsReceived(int userId) {
        List<Star> granted = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"award-team9\".star WHERE receiver_id = ?")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    granted.add(starFromResultSet(rs));
                }
                return granted;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}