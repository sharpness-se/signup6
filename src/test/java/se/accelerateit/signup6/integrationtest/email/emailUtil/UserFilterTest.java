package se.accelerateit.signup6.integrationtest.email.emailUtil;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.email.emailUtil.UserFilter;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.Group;
import se.accelerateit.signup6.model.Participation;
import se.accelerateit.signup6.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
class UserFilterTest extends SignupDbTest {
    static final Logger logger = LoggerFactory.getLogger(UserFilterTest.class);

    private final UserFilter userFilter;

    @Autowired
    UserFilterTest(UserFilter userFilter){
        this.userFilter = userFilter;
    }


    @Test
    void getUsersToRemindTest(){
        Group group1 = new Group();
        group1.setId(-1L);

        Event event1 = new Event();
        event1.setId(-1L);
        event1.setGroup(group1);

        Group group2 = new Group();
        group2.setId(-2L);

        Event event2 = new Event();
        event2.setId(-3L);
        event2.setGroup(group2);

        Event event3 = new Event();
        event3.setId(-2L);
        event3.setGroup(group1);

        List<User> userListOne = userFilter.getUsersToRemind(event1);
        List<User> userListTwo = userFilter.getUsersToRemind(event2);
        List<User> userListThree = userFilter.getUsersToRemind(event3);

        assertEquals(2, userListOne.size());
        assertEquals(-4L, userListOne.get(0).getId());
        assertEquals(-2L, userListOne.get(1).getId());

        assertEquals(2, userListTwo.size());
        assertEquals(-3L, userListTwo.get(0).getId());
        assertEquals(-4L, userListTwo.get(1).getId());

        assertEquals(2, userListThree.size());
        assertEquals(-1L, userListThree.get(0).getId());
        assertEquals(-2L, userListThree.get(1).getId());

    }

    @Test
    void getAllUsersFromGroupTest(){
        Group group1 = new Group();
        group1.setId(-1L);

        Group group2 = new Group();
        group2.setId(-2L);

        List<User> testListOne = userFilter.getAllUsersFromGroup(group1);
        List<User> testListTwo = userFilter.getAllUsersFromGroup(group2);

        assertEquals(-1L, testListOne.get(0).getId());
        assertEquals(-2L, testListOne.get(1).getId());

        assertEquals(-3L, testListTwo.get(0).getId());
        assertEquals(-4L, testListTwo.get(1).getId());

        assertNotEquals(-3L, testListOne.get(0).getId());
        assertNotEquals(-4L, testListOne.get(1).getId());

        assertNotEquals(-1L, testListTwo.get(0).getId());
        assertNotEquals(-2L, testListTwo.get(1).getId());

        assertEquals("Fredrik", testListOne.get(0).getFirstName());
        assertEquals("Torbjörn", testListOne.get(1).getFirstName());
        assertEquals("Göran", testListTwo.get(0).getFirstName());
        assertEquals("Frodo", testListTwo.get(1).getFirstName());
    }

    @Test
    void getAllUsersFromParticipationsTest(){
        List<Participation> pList = new ArrayList<>();

        for (long i = -1; i >= -4; i--) {
            Participation part = new Participation();
            part.setId(0L);
            part.setUserId(i);
            pList.add(part);
        }

        List<User> testList = userFilter.getAllUsersFromParticipations(pList);

        assertEquals(4, testList.size());
        assertEquals(-1L, testList.get(0).getId());
        assertEquals(-2L, testList.get(1).getId());
        assertEquals(-3L, testList.get(2).getId());
        assertEquals(-4L, testList.get(3).getId());

        assertEquals("Fredrik", testList.get(0).getFirstName());
        assertEquals("Torbjörn", testList.get(1).getFirstName());
        assertEquals("Göran", testList.get(2).getFirstName());
        assertEquals("Frodo", testList.get(3).getFirstName());

        assertEquals("Unknown", testList.get(0).getLastName());
        assertEquals("Fälldin", testList.get(1).getLastName());
        assertEquals("Persson", testList.get(2).getLastName());
        assertEquals("Baggins", testList.get(3).getLastName());

        assertNotEquals(-3L, testList.get(0).getId());
        assertNotEquals(-1L, testList.get(1).getId());
        assertNotEquals(-4L, testList.get(2).getId());
        assertNotEquals(-5L, testList.get(3).getId());

        assertNotEquals("Frodo", testList.get(0).getFirstName());
        assertNotEquals("Göran", testList.get(1).getFirstName());
        assertNotEquals("Torbjörn", testList.get(2).getFirstName());
        assertNotEquals("Fredrik", testList.get(3).getFirstName());

        assertNotEquals("Baggins", testList.get(0).getLastName());
        assertNotEquals("Persson", testList.get(1).getLastName());
        assertNotEquals("Fälldin", testList.get(2).getLastName());
        assertNotEquals("Unknown", testList.get(3).getLastName());
    }

    @Test
    void getUserFromParticipationTest(){
        Participation part1 = new Participation();
        part1.setId(1L);
        part1.setUserId(-1L);

        Participation part2 = new Participation();
        part2.setId(2L);
        part2.setUserId(-2L);

        assertEquals("Fredrik", userFilter.getUserFromParticipation(part1).getFirstName());
        assertEquals("Unknown", userFilter.getUserFromParticipation(part1).getLastName());
        assertEquals(-1L, userFilter.getUserFromParticipation(part1).getId());

        assertEquals("Torbjörn", userFilter.getUserFromParticipation(part2).getFirstName());
        assertEquals("Fälldin", userFilter.getUserFromParticipation(part2).getLastName());
        assertEquals(-2L, userFilter.getUserFromParticipation(part2).getId());

        assertNotEquals("Unknown", userFilter.getUserFromParticipation(part1).getFirstName());
        assertNotEquals("Fredrik", userFilter.getUserFromParticipation(part1).getLastName());
        assertNotEquals(-2L, userFilter.getUserFromParticipation(part1).getId());

        assertNotEquals("Fälldin", userFilter.getUserFromParticipation(part2).getFirstName());
        assertNotEquals("Torbjörn", userFilter.getUserFromParticipation(part2).getLastName());
        assertNotEquals(-1L, userFilter.getUserFromParticipation(part2).getId());
    }

    @Test
    void getGroupToRemindTest(){
        Group group1 = new Group();
        group1.setId(-1L);
        logger.info("group1 = {}", group1);

        Group group2 = new Group();
        group2.setId(-2L);
        logger.info("group2 = {}", group2);

        Event event1 = new Event();
        event1.setId(-1L);
        event1.setGroup(group1);
        logger.info("event1 = {}", event1);

        Event event2 = new Event();
        event2.setId(-2L);
        event2.setGroup(group1);
        logger.info("event2 = {}", event2);

        Event event3 = new Event();
        event3.setId(-3L);
        event3.setGroup(group2);
        logger.info("event3 = {}", event3);


        assertEquals(-1L, userFilter.getGroupToRemind(event1).getId());
        assertEquals(-1L, userFilter.getGroupToRemind(event2).getId());
        assertEquals(-2L, userFilter.getGroupToRemind(event3).getId());

        assertNotEquals(-2L, userFilter.getGroupToRemind(event1).getId());
        assertNotEquals(1L, userFilter.getGroupToRemind(event1).getId());
        assertNotEquals(-2L, userFilter.getGroupToRemind(event2).getId());
        assertNotEquals(1L, userFilter.getGroupToRemind(event2).getId());
        assertNotEquals(-1L, userFilter.getGroupToRemind(event3).getId());
        assertNotEquals(2L, userFilter.getGroupToRemind(event3).getId());
    }

    @Test
    void getEventParticipationsTest(){
        Event event1 = new Event();
        event1.setId(-1L);
        logger.info("event1 = {}", event1);

        Event event2 = new Event();
        event2.setId(-2L);
        logger.info("event2 = {}", event2);

        assertEquals(4, userFilter.getEventParticipations(event1).size());
        assertEquals(0, userFilter.getEventParticipations(event2).size());

        assertNotEquals(3, userFilter.getEventParticipations(event1).size());
        assertNotEquals(3, userFilter.getEventParticipations(event2).size());
    }

}
