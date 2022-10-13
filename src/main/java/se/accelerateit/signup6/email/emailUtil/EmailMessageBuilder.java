package se.accelerateit.signup6.email.emailUtil;

import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.Group;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

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
                getLink(user, ParticipationStatus.On) +
                newLine +
                getLink(user, ParticipationStatus.Maybe) +
                newLine +
                getLink(user, ParticipationStatus.Off);
    }

    public static String getLink(User user, ParticipationStatus status){
        return "info + api link participationcontroller, post/update participation, + userId as path variable + yes, maybe, no as path variable";
    }
}
