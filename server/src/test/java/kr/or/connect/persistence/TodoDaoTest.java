package kr.or.connect.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.persistence.Todo;
import kr.or.connect.todo.persistence.TodoDao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //해당 클래스가 트랜잭션 안에서 실행되는 것을 표시
public class TodoDaoTest {

	@Autowired
	private TodoDao dao;
	
	@Test
	public void shouldSelectAll() {
		List<Todo> allBooks = dao.selectAll();
		assertThat(allBooks, is(notNullValue()));
	}
}
