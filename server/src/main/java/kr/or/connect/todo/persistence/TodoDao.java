package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import kr.or.connect.todo.persistence.TodoSqls;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	//아래 예제보다 같은 변환을 더 간편하게 처리할 수 있는 BeanPropertyRowMapper을 활용
	//BeanPropertyRowMapper.newInstance() 메서드를 이용해서 Book 클래스와 대응되는 RowMapper 인스턴스를 생성한다.
	private RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
	
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	//book 테이블을 update하는 쿼리를 작성해서 실행
	static final String UPDATE =
			"UPDATE todo SET\n"
			+ "todo = :todo,"
			+ "completed = :completed,"
			+ "date = :date\n"
			+ "WHERE id = :id";
	static final String SELECT_BY_ID =
			"SELECT id, todo, completed, date FROM todo where id = :id";
	private static final String SELECT_ALL =
			"SELECT id, todo, completed, date FROM todo";
	
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}
	
	public List<Todo> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(SELECT_ALL, params, rowMapper);
	}
	public Todo selectById(Integer id) {
		// 멀티스레드에서 접근해도 안전하기 때문에 아래와 같이 멤버 변수로 선언하고 바로 초기화를 해도 무방하다.
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
	}
	public Integer insert(Todo todo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(DELETE_BY_ID, params);
	}
	public int update(Todo todo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return jdbc.update(UPDATE, params);
	}
}
