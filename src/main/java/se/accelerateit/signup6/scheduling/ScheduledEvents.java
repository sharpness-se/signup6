package se.accelerateit.signup6.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.model.Event;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledEvents {

    private final EventMapper eventMapper;

    @Autowired
    public ScheduledEvents(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public void sendReminders(){
        List<Event> upcomingEvents = getUpcomingEvents();



    }

    private List<Event> getUpcomingEvents(){
        return eventMapper.findAllUpcomingEvents(LocalDate.now());
    }

}
