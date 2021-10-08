package julien.hammer.p4lamzone.service;

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

}
