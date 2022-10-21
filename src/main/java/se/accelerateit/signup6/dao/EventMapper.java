package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.*;
import se.accelerateit.signup6.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface EventMapper {

  @Result(property = "group", column = "groupx", one = @One(select = "se.accelerateit.signup6.dao.GroupMapper.findById"))
  @Select(
    "select * from events where id = #{id}"
  )
  Optional<Event> findById(@Param("id") Long id);

  @Select(
          "select * from events"
  )
  List<Event> findAll();

  // Janne: Use """ instead of +
  // Janne: naming convention CRUD
  // CRUD = Create, Read (Find...), Update, Delete
  @Insert("""
                  insert into events(name, description, start_time, end_time, groupx, last_signup_date,
                  venue, allow_extra_friends, event_status, max_participants, cancellation_reason)
                  values(
                  #{name},
                  #{description},
                  #{startTime},
                  #{endTime},
                  #{group.id},
                  #{lastSignUpDate},
                  #{venue},
                  #{allowExtraFriends},
                  #{eventStatus},
                  #{maxParticipants},
                  #{cancellationReason})
                  """
  )
  void createEvent(Event event);

  @Result(property = "group", column = "groupx", one = @One(select = "se.accelerateit.signup6.dao.GroupMapper.findById"))
  @Select(
          "select * from events where last_signup_date >= #{dateToday}"
  )
  List<Event> findAllUpcomingEvents(@Param("dateToday") LocalDate dateToday);
}
