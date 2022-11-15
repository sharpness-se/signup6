package se.accelerateit.signup6.integrationtest.dao;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.accelerateit.signup6.dao.ReminderMapper;
import se.accelerateit.signup6.integrationtest.SignupDbTest;
import se.accelerateit.signup6.model.Reminder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTest.Initializer.class})
public class ReminderMapperTest extends SignupDbTest{

  static final Logger logger = LoggerFactory.getLogger(EventMapperTest.class);

  private final ReminderMapper reminderMapper;

  @Autowired
  ReminderMapperTest(ReminderMapper reminderMapper) {
    this.reminderMapper = reminderMapper;
  }

  @Test
  void findByDate() {
    List<Reminder> reminderList;
  }

  @Test
  void create() {
    LocalDate testDate = LocalDate.now().plusDays(1);
    Reminder testReminder = new Reminder();
    testReminder.setId(-1L);
    testReminder.setEventId(-2L);
    testReminder.setDateToRemind(testDate);

    int noOfRows = reminderMapper.create(testReminder);
    logger.info("create = {}", noOfRows);

    List<Reminder> dbResponse = reminderMapper.findByDate(testDate);
    logger.info("reminder = {}", dbResponse);
    Reminder testResponse = dbResponse.get(0);

    assertEquals(-1L, testResponse.getId());
    assertEquals(-2L, testResponse.getEventId());
    assertEquals(testDate, testResponse.getDateToRemind());


  }



}
