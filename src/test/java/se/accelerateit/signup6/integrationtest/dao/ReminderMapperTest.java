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
public class ReminderMapperTest extends SignupDbTest {

  static final Logger logger = LoggerFactory.getLogger(ReminderMapperTest.class);

  private final ReminderMapper reminderMapper;

  @Autowired
  ReminderMapperTest(ReminderMapper reminderMapper) {
    this.reminderMapper = reminderMapper;
  }

  @Test
  void create() {
    LocalDate date = LocalDate.now();
    Reminder testReminder = new Reminder();
    testReminder.setId(-10L);
    testReminder.setEventId(-9L);
    testReminder.setDateToRemind(date);
    logger.info("REMINDER TO INSERT: " + testReminder);

    reminderMapper.create(testReminder);

    List <Reminder> dbResponse = reminderMapper.findByDate(date);
    Reminder responseReminder = dbResponse.get(0);
    logger.info("REMINDER FROM DB: " + responseReminder.toString());

    assertEquals(-10, responseReminder.getId());
    assertEquals(-9, responseReminder.getEventId());
    assertEquals(date, responseReminder.getDateToRemind());

  }

  @Test
  void findByDate() {
    LocalDate date = LocalDate.parse("2022-11-15");

    List <Reminder> dbResponse = reminderMapper.findByDate(date);
    Reminder responseReminder = dbResponse.get(0);
    logger.info("REMINDER FROM DB: " + responseReminder.toString());

    assertEquals(-1, responseReminder.getId());
    assertEquals(-9, responseReminder.getEventId());
    assertEquals(date, responseReminder.getDateToRemind());
  }

  @Test
  void delete() {

    LocalDate date = LocalDate.parse("2019-11-18");

    Reminder testReminder = new Reminder();
    testReminder.setId(-10L);
    testReminder.setEventId(-9L);
    testReminder.setDateToRemind(date);
    logger.info("REMINDER TO INSERT: " + testReminder);

    reminderMapper.create(testReminder);

    List <Reminder> dbResponse = reminderMapper.findByDate(date);
    Reminder responseReminder = dbResponse.get(0);
    logger.info("REMINDER FROM DB: " + responseReminder.toString());

    assertFalse(dbResponse.isEmpty());
    assertEquals(-10L, responseReminder.getId());

    reminderMapper.delete(responseReminder);

    List<Reminder> afterDelete = reminderMapper.findByDate(date);

    assertTrue(afterDelete.isEmpty());
  }

  @Test
  void findDueReminders() {
    Reminder testReminder1 = new Reminder();
    testReminder1.setId(-10L);
    testReminder1.setEventId(-9L);
    testReminder1.setDateToRemind(LocalDate.of(2000, 12, 12));

    Reminder testReminder2 = new Reminder();
    testReminder2.setId(-11L);
    testReminder2.setEventId(-9L);
    testReminder2.setDateToRemind(LocalDate.of(2001, 12, 20));

    reminderMapper.create(testReminder1);
    reminderMapper.create(testReminder2);

    List<Reminder> dueReminderList = reminderMapper.findDueReminders(LocalDate.of(2010, 1, 1));
    logger.info("List size: " + dueReminderList.size());

    assertEquals(dueReminderList.get(0), testReminder1);
    assertEquals(dueReminderList.get(1), testReminder2);
    logger.info("due 1" + dueReminderList.get(0));
    logger.info("due 2" + dueReminderList.get(1));
  }

  @Test
  void findByEventId() {
    List<Reminder> reminderList = reminderMapper.findByEventId(-9L);
    logger.info("reminders = {}", reminderList);
    assertEquals(4, reminderList.size());
  }


}
