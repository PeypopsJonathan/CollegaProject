package ucll.project.domain.user;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;

public class Star {

    private int receiver_id, sender_id, star_id;
    private String comment;
    private ArrayList<String> tags;
    private Timestamp timestamp;

    public Star(int receiver_id, int sender_id, String comment, int star_id, ArrayList<String> tags) {
        setReceiver_id(receiver_id);
        setSender_id(sender_id);
        setComment(comment);
        setTags(tags);
        setTimestamp();
        setStar_id(star_id);
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        if (receiver_id < 0)
            throw new DomainException("receiver id cannot be negative");
        this.receiver_id = receiver_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        if (sender_id < 0)
            throw new DomainException("sender id cannot be negative");
        this.sender_id = sender_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStar_id() {
        return star_id;
    }

    public void setStar_id(int star_id) {
        if (star_id < 0)
            throw new DomainException("star id cannot be negative");
        this.star_id = star_id;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        if (tags == null || tags.isEmpty())
            throw new DomainException("Tags can't be empty.");
        this.tags = tags;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Star{" +
                "receiver_id='" + receiver_id + '\'' +
                ", sender_id='" + sender_id + '\'' +
                ", comment='" + comment + '\'' +
                ", star_id='" + star_id + '\'' +
                ", tags=" + tags +
                ", timestamp=" + timestamp +
                '}';
    }
}
