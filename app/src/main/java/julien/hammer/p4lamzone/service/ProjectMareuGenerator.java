package julien.hammer.p4lamzone.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public  abstract class ProjectMareuGenerator {

    //current date format
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy hh:mm:ss", Locale.FRANCE);

    public static List<User> FAKE_USERS = Arrays.asList(
            new User(1, "Julien", "Julien@lamzone.com"),
            new User(2, "Charles", "Charles@lamzone.com"),
            new User(3, "Nathalie", "Nathalie@lamzone.com"),
            new User(4, "Jean", "Jean@lamzone.com"),
            new User(5, "Thomas", "Thomas@lamzone.com"),
            new User(6, "Olivia", "Olivia@lamzone.com"),
            new User(7, "Sebastien", "Sebastien@lamzone.com"),
            new User(8, "Christelle", "Christelle@lamzone.com"),
            new User(9, "Alexandra", "Alexandra@lamzone.com"),
            new User(10, "Josy", "Josy@lamzone.com"),
            new User(11, "Francis", "Francis@lamzone.com")
    );

    public static final List<Room> FAKE_ROOMS = Arrays.asList(
            new Room(1, "Mario"),
            new Room(2, "Luigi"),
            new Room(3, "Peach"),
            new Room(4, "Toad"),
            new Room(5, "Yoshi"),
            new Room(6, "Daisy"),
            new Room(7, "Kong"),
            new Room(8, "Harmonie"),
            new Room(9, "Wario"),
            new Room(10, "Waluigi")
    );
    public static List<Meeting> FAKE_MEETINGS = null;

    static {
        try {
            FAKE_MEETINGS = Arrays.asList(
                                new Meeting(1, dateFormat.parse("11 15 2021 10:00:00"), dateFormat.parse("11 15 2021 12:00:00"), "reunion A", Arrays.asList(FAKE_USERS.get(1), FAKE_USERS.get(2)), FAKE_ROOMS.get(1)),
                                new Meeting(2, dateFormat.parse("11 15 2021 10:00:00"), dateFormat.parse("11 15 2021 12:00:00"), "reunion B", Arrays.asList(FAKE_USERS.get(3), FAKE_USERS.get(4)), FAKE_ROOMS.get(2)),
                                new Meeting(3, dateFormat.parse("11 14 2021 09:00:00"), dateFormat.parse("11 14 2021 11:00:00"), "reunion C", Arrays.asList(FAKE_USERS.get(5), FAKE_USERS.get(6)), FAKE_ROOMS.get(3))
                );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(FAKE_MEETINGS);
    }

    // Créer une nouvelle liste MEETING_BY_DATE_LIST pour stocker les Meetings qui sont classé par Date
    public static List<Meeting> MEETING_BY_DATE_LIST = Arrays.asList();
    // Générer la liste qui va nous retourner les MEETING_BY_DATE_LIST qui provient de ProjectMareuApiService de la fonction getMeetingsByDate
    static List<Meeting> generateMeetingsByDate() {
        return new ArrayList<>(MEETING_BY_DATE_LIST);
    }

    // Créer une nouvelle liste MEETING_BY_ROOM_LIST pour stocker les Meetings qui sont classé par Date
    public static List<Meeting> MEETING_BY_ROOM_LIST = Arrays.asList();
    // Générer la liste qui va nous retourner les MEETING_BY_DATE_LIST qui provient de ProjectMareuApiService de la fonction getMeetingsByDate
    static List<Meeting> generateMeetingsByRoom() {
        return new ArrayList<>(MEETING_BY_ROOM_LIST);
    }

    static List<Room> generateRooms() {
        return new ArrayList<>(FAKE_ROOMS);
    }

    static List<User> generateUsers() {
        return new ArrayList<>(FAKE_USERS);
    }

}
