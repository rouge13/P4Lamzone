package julien.hammer.p4lamzone.service;

import java.util.Date;
import java.util.List;

import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public interface MareuApiService {
    /**
     * Get all my Users
     * @return {@link List}
     */
    // Get all the users
    List<User> getUsers();

    /**
     * Get all my Rooms
     * @return {@link List}
     */
    // Get all the rooms
    List<Room> getRooms();

    /**
     * Get all my Meetings
     * @return {@link List}
     */
    // Get all the meetings
    List<Meeting> getMeetings();

    // Get all the meetings sorted by date
    List<Meeting> getMeetingsByDate(Date date);
    // Get all the meetings sorted by room
    List<Meeting> getMeetingsByRoom(Room room);

    // Delete a specific meeting
    void deleteMeeting(Meeting meetingToDelete);

    // Add a new meeting
    void createMeeting(Meeting meeting);

    // Check if the room is already booked
    boolean checkRoomBookedOff(int roomId, Date startDate, Date endDate) ;


}
