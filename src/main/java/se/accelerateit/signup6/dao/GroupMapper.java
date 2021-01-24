package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import se.accelerateit.signup6.model.Group;

import java.util.Optional;

@Mapper
public interface GroupMapper {

  @Select(
    "select * from groups where id = #{id}"
  )
  Optional<Group> findById(@Param("id") Long id);

  @Select(
    "select m.id from memberships m, events e where " +
      "e.id = #{eventId}" +
      "and m.groupx = e.groupx " +
      "and m.userx = #{userId}"
  )
  Optional<Long> findMembershipForEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);
}
