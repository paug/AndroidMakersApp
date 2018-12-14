package fr.paug.androidmakers.model;

public class ScheduleSlot {

    public final int room;
    public final int sessionId;
    public final long startDate;
    public final long endDate;

    public ScheduleSlot(int room, int sessionId, long startDate, long endDate) {
        this.room = room;
        this.sessionId = sessionId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ScheduleSlot{" +
                "room=" + room +
                ", sessionId=" + sessionId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

}
