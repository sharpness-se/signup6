package se.accelerateit.signup6.dao;

import lombok.Builder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import se.accelerateit.signup6.model.Group;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GroupMapper {

  @Select(
    "select * from groups where id = #{id}"
  )
  Optional<Group> findById(@Param("id") Long id);

  //TODO Should this be in MembershipMapper? Yes. Should we refactor EventValidator to MembershipMapper dependency?
  @Select("""
    select m.id from memberships m, events e where
      e.id = #{eventId}
      and m.groupx = e.groupx
      and m.userx = #{userId}
  """)
  Optional<Long> findMembershipForEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

  @Select("select * from groups")
  List<Group> findAllGroups();

  @Insert(
    "insert into groups(name, description, mail_from, mail_subject_prefix)" +
            "values(" +
            "#{name}," +
            "#{description}," +
            "#{mailFrom}," +
            "#{mailSubjectPrefix})"
  )
  void insertGroup(Group group);
}
