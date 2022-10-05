package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import se.accelerateit.signup6.model.Participation;
import se.accelerateit.signup6.model.ParticipationStatus;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ParticipationMapper {

  @Results({
    @Result(property = "userId", column = "userx"),
    @Result(property = "eventId", column = "event")
  })
  @Select(
    "select * from participations where userx=#{userId} and event=#{eventId}"
  )
  Optional<Participation> findByUserAndEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

  @Select(
          "select * from participations where event=#{eventId}"
  )
  List<Participation> findByEventId(@Param("eventId") Long eventId);


  @Results({
    @Result(property = "eventId", column = "event"),
    @Result(property = "status", column = "status")
  })
  @Select(
    "select * from participations where event=#{eventId} and status=#{status}"
  )
  List<Participation> findByStatusAndEventId(@Param("eventId") Long eventId, @Param("status") ParticipationStatus status);

  @Insert("""
    insert into participations (status, comment, userx, event, number_of_participants, signup_time)
      values (#{status}, #{comment}, #{userId}, #{eventId}, #{numberOfParticipants}, #{signUpTime})
  """)
  int create(Participation participation);

  @Update("""
    update participations set status = #{status}, comment = #{comment}, number_of_participants = #{numberOfParticipants}, signup_time = #{signUpTime}
      where userx=#{userId} and event=#{eventId}
  """)
  int update(Participation participation);
}
