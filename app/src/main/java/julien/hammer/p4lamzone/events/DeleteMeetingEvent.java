package julien.hammer.p4lamzone.events;

import julien.hammer.p4lamzone.model.Meeting;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public class DeleteMeetingEvent {
    /**
     * Meeting to delete
     */
    public Meeting meeting;

    /**
     * Constructor.
     * @param meeting
     */
    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
