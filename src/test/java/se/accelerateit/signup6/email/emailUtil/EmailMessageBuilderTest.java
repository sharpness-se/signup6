package se.accelerateit.signup6.email.emailUtil;

import org.junit.jupiter.api.Test;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

import static org.junit.jupiter.api.Assertions.*;

class EmailMessageBuilderTest {

    private User createMockUser(long id, String firstName) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        return user;
    }

    private Event createMockEvent() {
        Event event = new Event();
        event.setId(-9L);
        event.setName("Test Name");
        return event;
    }

    @Test
    void reminderMailTest() {
        User userOne = createMockUser(-9L, "TestFName1");
        Event event = createMockEvent();
        User userTwo = createMockUser(-10L, "TestFName2");

        String userOneString = "Hello <b>TestFName1</b>, don't forget responding to your invitation for <b> Test Name</b><br></br>" +
          "<a href=\"http://localhost:8080/api/participations/registration?userId=-9&eventId=-9&pStatus=On\">Click here to say yes</a><br></br>" +
          "<a href=\"http://localhost:8080/api/participations/registration?userId=-9&eventId=-9&pStatus=Maybe\">Click here to say maybe</a><br></br>" +
          "<a href=\"http://localhost:8080/api/participations/registration?userId=-9&eventId=-9&pStatus=Off\">Click here to say no</a>";

        String userTwoString = "Hello <b>TestFName2</b>, don't forget responding to your invitation for <b> Test Name</b><br></br>" +
          "<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=On\">Click here to say yes</a><br></br>" +
          "<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=Maybe\">Click here to say maybe</a><br></br>" +
          "<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=Off\">Click here to say no</a>";

        EmailMessageBuilder emailMessageBuilder = new EmailMessageBuilder();
        emailMessageBuilder.setBaseUrl("http://localhost:8080");
        assertEquals(userOneString, emailMessageBuilder.reminderMail(userOne, event));
        assertEquals(userTwoString, emailMessageBuilder.reminderMail(userTwo, event));
    }

    @Test
    void getLinkTest() {
        User user = createMockUser(-10L, "TestFName1");
        Event event = createMockEvent();
        EmailMessageBuilder emailMessageBuilder = new EmailMessageBuilder();
        emailMessageBuilder.setBaseUrl("http://localhost:8080");

        assertEquals("<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=On\">Click here to say yes</a>", emailMessageBuilder.getLink(user, event, ParticipationStatus.On));

        assertEquals("<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=Maybe\">Click here to say maybe</a>", emailMessageBuilder.getLink(user, event, ParticipationStatus.Maybe));

        assertEquals("<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=Off\">Click here to say no</a>", emailMessageBuilder.getLink(user, event, ParticipationStatus.Off));
    }
}