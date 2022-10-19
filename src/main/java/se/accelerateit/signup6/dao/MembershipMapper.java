package se.accelerateit.signup6.dao;


import org.apache.ibatis.annotations.*;
import se.accelerateit.signup6.model.Membership;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MembershipMapper {

    @Result(property = "userId", column = "userx")
    @Result(property = "groupId", column = "groupx")
    @Select(
            "select * from memberships where userx=#{userId} and groupx=#{groupId}"
    )
    Optional<Membership> findByUserAndGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);



    @Result(property = "userId", column = "userx")
    @Result(property = "groupId", column = "groupx")
    @Select("select * from memberships where groupx=#{groupId}")
    List<Membership> findUsersByGroup(@Param("groupId") Long groupId);

    @Select("""
    select m.id from memberships m, events e where
      e.id = #{eventId}
      and m.groupx = e.groupx
      and m.userx = #{userId}
  """)
    Optional<Long> findMembershipForEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);


}
