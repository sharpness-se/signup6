package se.accelerateit.signup6.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import se.accelerateit.signup6.model.Membership;
import se.accelerateit.signup6.model.User;

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



    @Select("SELECT u.* FROM memberships m, users u WHERE m.userx=u.id AND m.groupx=#{groupId}")
    List<User> findUsersByGroup(@Param("groupId") Long groupId);

    @Select("""
    select m.id from memberships m, events e where
      e.id = #{eventId}
      and m.groupx = e.groupx
      and m.userx = #{userId}
  """)
    Optional<Long> findMembershipForEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);


}
