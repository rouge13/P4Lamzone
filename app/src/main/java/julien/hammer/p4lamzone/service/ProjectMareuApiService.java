package julien.hammer.p4lamzone.service;

import java.util.List;

import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public class ProjectMareuApiService implements MareuApiService {
    private final List<User> users = ProjectMareuGenerator.generateUsers();
    private final List<Room> rooms = ProjectMareuGenerator.generateRooms();
    private final List<Meeting> meetings = ProjectMareuGenerator.generateMeetings();

    @Override
    public List<User> getUsers() { return users; }

    @Override
    public List<Room> getRooms() { return rooms; }

    @Override
    public List<Meeting> getMeetings() { return meetings; }
}
