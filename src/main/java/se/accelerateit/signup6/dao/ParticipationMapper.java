package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.Participation;

import java.util.Optional;

@Mapper
public interface ParticipationMapper {

  @Results({
    @Result(property = "userId", column = "userx"),
    @Result(property = "eventId", column = "event")
  })
  @Select(
    "select * from participations where userx = #{userId} and event = #{eventId}"
  )
  Optional<Participation> findByUserAndEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

  // update participation

  // create participation
}
