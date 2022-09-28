package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.*;
import se.accelerateit.signup6.model.Event;
import java.util.Optional;

@Mapper
public interface EventMapper {

  @Results({
    @Result(property = "group", column = "groupx", one = @One(select = "se.accelerateit.signup6.dao.GroupMapper.findById"))
  })
  @Select(
    "select * from events where id = #{id}"
  )
  Optional<Event> findById(@Param("id") Long id);
}
