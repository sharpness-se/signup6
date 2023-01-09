package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import se.accelerateit.signup6.model.Event;
import se.accelerateit.signup6.model.ParticipationStatus;
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

  @Select("""
    SELECT u.*
    FROM users u, memberships m
    WHERE m.userx = u.id
      AND m.groupx = #{event.group.id}
      AND u.id NOT IN (SELECT p.userx FROM participations p WHERE p.event = #{event.id})
    ORDER BY u.first_name, u.last_name
        """
  )
  List<User> findUnregisteredMembers(@Param("event") Event event);

  @Select("""
    SELECT u.*
    FROM participations p, users u
    WHERE p.event=#{event.id}
      AND p.userx = u.id
      AND p.status=#{status}
      AND p.userx IN (
        SELECT m.userx
        FROM memberships m
        WHERE m.groupx=#{event.group.id}
      )
    """
  )
  List<User> findMembersByStatus(@Param("status") ParticipationStatus status, @Param("event") Event event);

  @Insert("""
    insert into users(first_name, last_name, comment, email, phone, permission,
    pwd, image_provider, image_version, provider_key, auth_info)
    values(
    #{firstName},
    #{lastName},
    #{comment},
    #{email},
    #{phone},
    #{permission},
    #{pwd},
    #{imageProvider},
    #{imageVersion},
    #{providerKey},
    #{authInfo})
    """
  )
  void createUser(User user);
}
