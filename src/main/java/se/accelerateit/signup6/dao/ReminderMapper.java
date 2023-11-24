package se.accelerateit.signup6.dao;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import se.accelerateit.signup6.model.Reminder;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReminderMapper {


  @Result(property = "eventId", column = "event")
  @Result(property = "dateToRemind", column = "datex")
  @Result(property = "id", column = "id")
  @Select(
    "select * from reminders where datex=#{dateToRemind}"
  )
  List<Reminder> findByDate(@Param(("dateToRemind")) LocalDate dateToRemind);


  @Result(property = "eventId", column = "event")
  @Result(property = "dateToRemind", column = "datex")
  @Result(property = "id", column = "id")
  @Select(
    "select * from reminders where event=#{eventId}"
  )
  List<Reminder> findByEventId(@Param(("eventId")) Long eventId);





// select * from reminders where datex <= #{currentDate}

  @Result(property = "eventId", column = "event")
  @Result(property = "dateToRemind", column = "datex")
  @Result(property = "id", column = "id")
  @Select(
    "select * from reminders where datex <= #{currentDate}"
  )
  List<Reminder> findDueReminders(@Param(("currentDate")) LocalDate currentDate);



  @Insert("""
    insert into reminders (id, event, datex)
    values (#{id}, #{eventId}, #{dateToRemind})
    """)
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void create(Reminder reminder);


  //add delete
  @Delete(
    "delete from reminders where id=#{id}"
  )
  void delete(Reminder reminder);

}
