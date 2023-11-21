package se.accelerateit.signup6.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import se.accelerateit.signup6.model.LogEntry;

import java.util.List;

@Mapper
public interface LogEntryMapper {
  @Insert("INSERT INTO log_entries (event, message, whenx) VALUES (#{eventId}, #{message}, #{when})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void create(LogEntry logEntry);

  @Select("SELECT * FROM log_entries l WHERE l.event=#{eventId} ORDER BY l.id DESC")
  List<LogEntry> findByEvent(@Param("eventId") long eventId);
}
