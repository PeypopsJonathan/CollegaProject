package ucll.project.domain.user;

import org.junit.Test;
import ucll.project.domain.star.Star;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StarTest {

    @Test
    public void testCreateStar() {

        ArrayList<String> listTags = new ArrayList<>();
        listTags.add("Eerste tag");
        listTags.add("tweede tag");

        Star star = new Star(1,
                2,
                "je moeder haar ster",
                1,
                listTags);

        assertEquals(star.getReceiver_id(), 1);
        assertEquals(star.getSender_id(), 2);
        assertEquals(star.getComment(), "je moeder haar ster");
        assertEquals(star.getStar_id(), 1);
        assertEquals(star.getTags().get(0), "Eerste tag");
    }
}
