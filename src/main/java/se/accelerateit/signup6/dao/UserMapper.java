package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import se.accelerateit.signup6.model.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

  @Select(
    "select * from users where email = #{email}"
  )
  Optional<User> findByEmail(@Param("email") String email);

  @Select(
    "select * from users where id = #{id}"
  )
  Optional<User> findById(@Param("id") Long id);

  @Select(
    "select * from users"
  )
  List<User> findAll();
}
