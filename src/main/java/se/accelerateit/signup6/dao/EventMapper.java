package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
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
  Long createEvent(Event event);

  @Delete(
    "delete from events where id=#{id}"
  )
  void deleteEvent(@Param("id") Long id);


  @Result(property = "group", column = "groupx", one = @One(select = "se.accelerateit.signup6.dao.GroupMapper.findById"))
  @Select(
    "select * from events where last_signup_date >= #{dateToday}"
  )
  List<Event> findAllUpcomingEvents(@Param("dateToday") LocalDate dateToday);


  @Result(property = "group", column = "groupx", one = @One(select = "se.accelerateit.signup6.dao.GroupMapper.findById"))
  @Select(
    "SELECT * FROM events WHERE groupx = #{groupId} ORDER BY last_signup_date DESC"
  )
  List<Event> findAllEventsByGroup(@Param("groupId") long groupId);

  @Result(property = "group", column = "groupx", one = @One(select = "se.accelerateit.signup6.dao.GroupMapper.findById"))
  @Select(
    "SELECT * FROM events WHERE last_signup_date >= #{dateToday} AND groupx = #{groupId} ORDER BY last_signup_date DESC"
  )
  List<Event> findAllUpcomingEventsByGroup(@Param("dateToday") LocalDate dateToday, @Param("groupId") long Long);


  @Result(property = "group", column = "groupx", one = @One(select = "se.accelerateit.signup6.dao.GroupMapper.findById"))
  @Select("""
      SELECT e.*
      FROM events e, memberships m
      WHERE e.groupx = m.groupx AND m.userx = #{userId} AND e.start_time >= #{dateToday} AND e.event_status != 'Cancelled'
      UNION
      SELECT e.*
      FROM events e, participations p
      WHERE p.event=e.id AND p.userx=#{userId} AND e.start_time >= #{dateToday} AND e.event_status != 'Cancelled'
      ORDER BY last_signup_date DESC
    """)
  List<Event> findUpcomingEventsByUser(@Param("dateToday") LocalDate today, @Param("userId") Long user);

}
