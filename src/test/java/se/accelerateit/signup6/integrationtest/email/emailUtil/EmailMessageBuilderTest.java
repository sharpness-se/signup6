package se.accelerateit.signup6.integrationtest.email.emailUtil;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.EventMapper;
import se.accelerateit.signup6.dao.UserMapper;
import se.accelerateit.signup6.email.emailUtil.EmailMessageBuilder;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
import se.accelerateit.signup6.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class EmailMessageBuilderTest extends SignupDbTest{

    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    EmailMessageBuilder emailMessageBuilder = new EmailMessageBuilder();

    static final Logger logger = LoggerFactory.getLogger(EmailMessageBuilderTest.class);

    @Autowired
    public EmailMessageBuilderTest(UserMapper userMapper, EventMapper eventMapper) {
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
    }



    @Test
    void reminderMailTest() {
        Optional<User> userOneResponse = userMapper.findById(-9L);
        assertTrue(userOneResponse.isPresent(), "could not find the user in db");
        User userOne = userOneResponse.get();
        logger.info("user = {}", userOne);

        Optional<Event> eventResponse = eventMapper.findById(-9L);
        assertTrue(eventResponse.isPresent(), "could not find the event in db");
        Event event = eventResponse.get();
        logger.info("event = {}", event);

        Optional<User> userTwoResponse = userMapper.findById(-10L);
        assertTrue(userTwoResponse.isPresent(), "could not find the user in db");
        User userTwo = userTwoResponse.get();
        logger.info("user = {}", userTwo);

        String userOneString = "Hello <b>TestFName1</b>, don't forget responding to your invitation for <b> Test Name</b><br></br>" +
                "<a href=\"http://localhost:8080/api/participations/registration?userId=-9&eventId=-9&pStatus=On\">Click here to say yes</a><br></br>" +
                "<a href=\"http://localhost:8080/api/participations/registration?userId=-9&eventId=-9&pStatus=Maybe\">Click here to say maybe</a><br></br>" +
                "<a href=\"http://localhost:8080/api/participations/registration?userId=-9&eventId=-9&pStatus=Off\">Click here to say no</a>";

        String userTwoString = "Hello <b>TestFName2</b>, don't forget responding to your invitation for <b> Test Name</b><br></br>" +
                "<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=On\">Click here to say yes</a><br></br>" +
                "<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=Maybe\">Click here to say maybe</a><br></br>" +
                "<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=Off\">Click here to say no</a>";

        assertEquals(userOneString, emailMessageBuilder.reminderMail(userOne, event));
        assertEquals(userTwoString, emailMessageBuilder.reminderMail(userTwo, event));
    }

    @Test
    void getLinkTest() {
        Optional<User> databaseResponse = userMapper.findById(-10L);
        assertTrue(databaseResponse.isPresent(), "could not find the user in db");
        User user = databaseResponse.get();
        logger.info("user = {}", user);

        Optional<Event> eventResponse = eventMapper.findById(-9L);
        assertTrue(eventResponse.isPresent(), "could not find the event in db");
        Event event = eventResponse.get();
        logger.info("event = {}", event);

        assertEquals("<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=On\">Click here to say yes</a>", emailMessageBuilder.getLink(user, event, ParticipationStatus.On));

        assertEquals("<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=Maybe\">Click here to say maybe</a>", emailMessageBuilder.getLink(user, event, ParticipationStatus.Maybe));

        assertEquals("<a href=\"http://localhost:8080/api/participations/registration?userId=-10&eventId=-9&pStatus=Off\">Click here to say no</a>", emailMessageBuilder.getLink(user, event, ParticipationStatus.Off));
    }
}