package julien.hammer.p4lamzone;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import julien.hammer.p4lamzone.di.DI;
import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;
import julien.hammer.p4lamzone.service.MareuApiService;
import julien.hammer.p4lamzone.service.ProjectMareuGenerator;

import static org.junit.Assert.*;

/**
 * Unit test on Mareu Service
 *
 */
@RunWith(JUnit4.class)
public class MareuServiceTest {
    //current date format
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy hh:mm:ss", Locale.FRANCE);
    private MareuApiService service;
    @Before
    public void setUp(){
        service = DI.getNewInstanceMareuApiService();
    }
    @Test
    public void getUsersWithSuccess() {
        List<User> users = service.getUsers();
        List<User> expectedUsers = ProjectMareuGenerator.FAKE_USERS;
        assertThat(users, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedUsers.toArray()));
    }
    @Test
    public void getRoomsWithSuccess() {
        List<Room> rooms = service.getRooms();
        List<Room> expectedRooms = ProjectMareuGenerator.FAKE_ROOMS;
        assertThat(rooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedRooms.toArray()));
    }
    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = ProjectMareuGenerator.FAKE_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }
    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void createMeetingWithSuccess() throws ParseException {
        Meeting expectedMeeting = new Meeting(100,dateFormat.parse("11 10 2021 10:00:00"), dateFormat.parse("11 10 2021 14:00:00"), "",
                Arrays.asList(service.getUsers().get(1), service.getUsers().get(3)), service.getRooms().get(2));
        service.createMeeting(expectedMeeting);
        assertTrue(service.getMeetings().contains(expectedMeeting));
    }

    @Test
    public void getMeetingsByDateWithSuccess() throws ParseException {
        List<Meeting> meetings = service.getMeetingsByDate(dateFormat.parse("11 15 2021 10:00:00"));
        List<Meeting> expectedMeetingsByDate = ProjectMareuGenerator.MEETING_BY_DATE_LIST;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetingsByDate.toArray()));
    }
    @Test
    public void getMeetingsCheckRoomBookedOffWhenNotAvailable() throws ParseException {
        boolean meeting = service.checkRoomBookedOff(service.getRooms().get(1).getId(),dateFormat.parse("11 15 2021 09:00:00 "),dateFormat.parse("11 15 2021 13:00:00"));
        assertFalse(meeting);
    }
    @Test
    public void getMeetingsCheckRoomBookedOffWhenAvailable() throws ParseException {
        boolean meeting = service.checkRoomBookedOff(service.getRooms().get(2).getId(),dateFormat.parse("11 18 2021 10:00:00 "),dateFormat.parse("11 18 2021 11:00:00"));
        assertTrue(meeting);

    }
    @Test
    public void getMeetingsByRoomWithSuccess(){
        List<Meeting> meetings = service.getMeetingsByRoom(service.getRooms().get(1));
        List<Meeting> expectedMeetingsByRoom = ProjectMareuGenerator.MEETING_BY_ROOM_LIST;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetingsByRoom.toArray()));
    }
}