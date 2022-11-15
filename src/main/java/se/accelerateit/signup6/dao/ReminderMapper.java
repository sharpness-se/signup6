package se.accelerateit.signup6.dao;


import org.apache.ibatis.annotations.*;
import se.accelerateit.signup6.model.Reminder;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReminderMapper {


  @Result(property = "eventId", column = "event")
  @Select(
    "select * from reminders where datex=#{dateToRemind}"
  )
  List<Reminder> findByDate(@Param(("dateToRemind"))LocalDate dateToRemind);


  @Insert("""
    insert into reminders (event, datex)
    values (#{eventId}, #{dateToRemind})
    """)
  int create(Reminder reminder);

}
