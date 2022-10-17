package se.accelerateit.signup6.email.emailUtil;

import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.Group;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

import java.net.URI;

public class EmailMessageBuilder {

    public String newEventInviteMessage(User user, Group group, Event event) {
        return "Hello " + user.getFirstName() + "\n" + group.getName() + " has invited you to a new event - " + event.getName()
                + "that takes place " + event.getStartTime();
    }

    public String eventReminderMessage(User user, Event event) {
        return "Hello " + user.getFirstName() + "\nYou haven't notified if you are able to attend " + event.getName() + "."
                + "\nThe date for the event is" + event.getStartTime() + ", please respond before " + event.getLastSignUpDate();
    }

    public static String exmplMessage() {
        String newLine = "<br></br>";
        return "Hello " + "<b><em>User,</b></em>" + newLine + "<b><em>Sharpness</b></em>" + " has invited you to a new event - " + "<b><em>AW</b></em>"
                + newLine + "that takes place at " + "<b><em>16:00</b></em>";
    }

    public static String reminderMail(User user, Event event){
        String newLine = "<br></br>";
        String body = "Hello " + user.getFirstName() +  ", don't forget responding to your invitation for " + event.getName();

        return body +
                newLine +
                getLink(user, event, ParticipationStatus.On) +
                newLine +
                getLink(user, event, ParticipationStatus.Maybe) +
                newLine +
                getLink(user, event, ParticipationStatus.Off);
    }

    //http://localhost:8080/api/participations/registration?userId=-5&eventId=-2&pStatus=1
    public static String getLink(User user, Event event, ParticipationStatus status){
        String userPar = "userId=" + user.getId();
        String eventPar = "eventId=" + event.getId();
        String statusPar = "pStatus=" + setStatus(status);
        String toAppend = "?" + userPar + "&" + eventPar + "&" + statusPar;
        String url = "http://localhost:8080/api/participations/registration" + toAppend;
        return "<a href=\""+ url + "\">" + setLinkText(status) + "</a>";
    }
    //<a href="url">link text</a>

    private static String setLinkText(ParticipationStatus status){
        if (status.equals(ParticipationStatus.On)){
            return "Click here to say yes";
        }
        if (status.equals(ParticipationStatus.Off)){
            return "Click here to say no";
        }
        return "Click here to say maybe";
    }

    private static String setStatus(ParticipationStatus status){
        if (status.equals(ParticipationStatus.On)){
            return "1";
        }
        if (status.equals(ParticipationStatus.Off)){
            return "3";
        }
        return "2";
    }
}
