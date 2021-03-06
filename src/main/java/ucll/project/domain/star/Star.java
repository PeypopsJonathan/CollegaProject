package ucll.project.domain.star;

import ucll.project.domain.DomainException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Star implements Comparable {

    private int receiver_id, sender_id, star_id;
    private String comment, receiver_name, sender_name;
    private List<String> tags;
    private Timestamp timestamp;


    public Star(){
        setTimestamp();
    }

    public Star(int receiver_id, int sender_id, String comment, int star_id, ArrayList<String> tags) {
        setReceiver_id(receiver_id);
        setSender_id(sender_id);
        setComment(comment);
        setTags(tags);
        setTimestamp();
        setStar_id(star_id);
        receiver_name = "";
        sender_name = "";
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
        if (comment.trim().isEmpty())
            throw new DomainException("Comment can't be empty");
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        if (tags == null || tags.isEmpty())
            throw new DomainException("Tags can't be empty.");
        this.tags = tags;
    }

    public String getTimestamp() {
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Timestamp getTimeStampNotFormatted(){
        return timestamp;
    }

    public void setTimestamp() {
        Date date= new Date();

        long time = date.getTime();

        this.timestamp = new Timestamp(time);
    }

    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }

    public void setReceiver_name(String name){
        this.receiver_name = name;
    }

    public void setSender_name(String name){
        this.sender_name = name;
    }

    public String getReceiver_name(){
        return this.receiver_name;
    }

    public String getSender_name(){
        return this.sender_name;
    }

    public boolean starWasReceivedBy(int userId){
        return this.receiver_id == userId;
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

    @Override
    public int compareTo(Object o) {
        return -1*this.timestamp.compareTo(((Star) o).getTimeStampNotFormatted());
    }
}
