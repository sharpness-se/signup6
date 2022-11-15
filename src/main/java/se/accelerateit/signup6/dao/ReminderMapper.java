package se.accelerateit.signup6.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import se.accelerateit.signup6.model.Reminder;

@Mapper
public interface ReminderMapper {



  @Insert("""
    insert into reminders (event, datex)
    values (#{eventId}, #{dateToRemind})
    """)
  int create(Reminder reminder);

}
