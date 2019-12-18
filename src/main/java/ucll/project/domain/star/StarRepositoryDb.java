package ucll.project.domain.star;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.user.Role;
import ucll.project.domain.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarRepositoryDb implements StarRepository{

    @Override
    public void createStar(Star star) {
        try(Connection conn = ConnectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"award-team9\".star " +
                            "(sender_id) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS))
        {
            stmtSetStar(stmt, 1, star);
            stmt.execute();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Star get(int starId) {
        return null;
    }

    @Override
    public List<Star> getAll() {
        try(Connection conn = ConnectionPool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"award-team9\".star"))
        {
            List<Star> stars = new ArrayList<>();
            while (rs.next()) {
                stars.add(starFromResultSet(rs));
            }
            return stars;
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Star star) {

    }

    @Override
    public void delete(Star star) {

    }

    private static int stmtSetStar(PreparedStatement stmt, int i, Star star)throws SQLException{
        stmt.setInt(i++, star.getSender_id());
        return i;
    }

    private static Star starFromResultSet(ResultSet rs)throws SQLException{
        Star star = new Star();
        star.setReceiver_id(rs.getInt(1));
        star.setSender_id(rs.getInt(2));
        star.setComment(rs.getString(3));
        star.setTags(sqlArrayToArrayList(rs.getArray(4)));
        star.setTimestamp(rs.getTimestamp(5));
        star.setStar_id(rs.getInt(6));
        return star;
    }

    private static ArrayList<String> sqlArrayToArrayList(Array array)throws SQLException{
        ArrayList<String> tags = new ArrayList<>();
        String[] rsTags = (String[]) array.getArray();
        for (int i = 0 ; i < rsTags.length ; i++){
            tags.add(rsTags[i]);
        }
        return tags;
    }
}