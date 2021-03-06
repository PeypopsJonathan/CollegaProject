package ucll.project.domain.star;

import java.util.ArrayList;
import java.util.List;

public interface StarRepository {

    // CREATE
    void createStar(Star star);

    // READ ONE
    Star get(int starId);

    // READ ALL
    List<Star> getAll();

    // UPDATE
    void update(Star star);

    // DELETE
    void delete(Star star);

    List<Star> getUserInvolvedInStarExchanges(int userId);

    List<ArrayList<String>> getAllTagsDb();

    List<Star> getStarsSent(int userId);

    List<Star> getStarsReceived(int userId);

    int countGivenStars(int userId);
    int countReceivedStars(int userId);

}
