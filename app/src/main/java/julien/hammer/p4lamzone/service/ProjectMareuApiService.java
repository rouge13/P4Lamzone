package julien.hammer.p4lamzone.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
//    private final List<Meeting> meetingsByDate = ProjectMareuGenerator.generateMeetingsByDate();
//    private final List<Meeting> meetingsByRoom = ProjectMareuGenerator.generateMeetingsByRoom();
    @Override
    public List<User> getUsers() { return users; }
    @Override
    public List<Room> getRooms() { return rooms; }
    @Override
    public List<Meeting> getMeetings() { return meetings; }

    public void deleteMeeting(Meeting meetingToDelete) {
        meetings.remove(meetingToDelete);
    }
    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    // Check if the rooms is already booked
    public boolean checkRoomBookedOff(int roomId, Date startDate, Date endDate) {
        for (Meeting meeting : meetings) {
            if (roomId == meeting.getRoom().getId()) {
                if (meeting.getStartOfTheMeeting().after(startDate)
                        && meeting.getStartOfTheMeeting().before(endDate)
                        || meeting.getEndOfTheMeeting().after(startDate)
                        && meeting.getEndOfTheMeeting().before(endDate)
                        || meeting.getStartOfTheMeeting().before(startDate)
                        && meeting.getEndOfTheMeeting().after(endDate)) {

                    return false;
                }
            }
        }
        return true;
    }
    public List<Meeting> getMeetingsByDate(Date date) {
        List<Meeting> meetingsByDate = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        for (Meeting meeting : meetings) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(meeting.getStartOfTheMeeting());
                if (c.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && c.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) ) {
                    meetingsByDate.add(meeting);
                }
        }
        return meetingsByDate;
    }

    @Override
    public List<Meeting> getMeetingsByRoom(Room room) {
        List<Meeting> meetingsByRoom = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (meeting.getRoom()==room) {
                meetingsByRoom.add(meeting);
            }
        }
        return meetingsByRoom;
    }
}
