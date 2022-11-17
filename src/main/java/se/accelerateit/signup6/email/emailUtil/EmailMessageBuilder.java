package se.accelerateit.signup6.email.emailUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

@Service
public class EmailMessageBuilder {

    @Value("${website.base.url}")
    private String baseUrl;

    public String reminderMail(User user, Event event){
        String newLine = "<br></br>";
        String body = "Hello <b>" + user.getFirstName() + "</b>, don't forget responding to your invitation for <b> " + event.getName() + "</b>";

        return body +
                newLine +
                getLink(user, event, ParticipationStatus.On) +
                newLine +
                getLink(user, event, ParticipationStatus.Maybe) +
                newLine +
                getLink(user, event, ParticipationStatus.Off);
    }

    //http://localhost:8080/api/participations/registration?userId=-5&eventId=-2&pStatus=On
    public String getLink(User user, Event event, ParticipationStatus status){
        String userPar = "userId=" + user.getId();
        String eventPar = "eventId=" + event.getId();
        String statusPar = "pStatus=" + status.toString();
        String toAppend = "?" + userPar + "&" + eventPar + "&" + statusPar;
        //String url = "http://localhost:8080/api/participations/registration" + toAppend;
        String url = baseUrl + "/api/participations/registration" + toAppend;
        return "<a href=\""+ url + "\">" + setLinkText(status) + "</a>";
    }
    //<a href="url">link text</a>

    private String setLinkText(ParticipationStatus status){
        if (status.equals(ParticipationStatus.On)){
            return "Click here to say yes";
        }
        if (status.equals(ParticipationStatus.Off)){
            return "Click here to say no";
        }
        return "Click here to say maybe";
    }
}
