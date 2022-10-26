package se.accelerateit.signup6.dao;

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

  @Select("select * from groups")
  List<Group> findAllGroups();

  @Insert("""
            insert into groups(name, description, mail_from, mail_subject_prefix)
            values(
            #{name},
            #{description},
            #{mailFrom},
            #{mailSubjectPrefix})
            """
  )
  void createGroup(Group group);
}
