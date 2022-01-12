package julien.hammer.p4lamzone.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public class Meeting {

    /**
     * Meeting Identifier
     */
    private Integer id;

    /**
     * Meeting Date de début
     */
    private Date startOfTheMeeting;

    /**
     * Meeting Date de fin
     */
    private Date endOfTheMeeting;

    /**
     * Meeting Subject
     */
    private String subject;

    /**
     * Meeting Users
     */
    private List<User> users;

    /**
     * Meeting Rooms
     */
    private Room room;

    public Meeting(Integer id, Date startOfTheMeeting, Date endOfTheMeeting, String subject, List<User> users, Room room) {
        this.id = id;
        this.startOfTheMeeting = startOfTheMeeting;
        this.endOfTheMeeting = endOfTheMeeting;
        this.subject = subject;
        this.room = room;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartOfTheMeeting() {
        return startOfTheMeeting;
    }

    public void setStartOfTheMeeting(Date startOfTheMeeting) {
        this.startOfTheMeeting = startOfTheMeeting;
    }

    public Date getEndOfTheMeeting() {
        return endOfTheMeeting;
    }

    public void setEndOfTheMeeting(Date endOfTheMeeting) {
        this.endOfTheMeeting = endOfTheMeeting;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<User> getUsers() { return users; }

    public void setUsers(List<User> users) { this.users = users; }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room= room; }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

