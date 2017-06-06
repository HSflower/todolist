package kr.or.connect.todo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import kr.or.connect.todo.persistence.Todo;
import kr.or.connect.todo.persistence.TodoDao;

// @Controller가 붙은 클래스 -> @Service가 붙은 클래스 -> @Repository가 붙은 클래스로 요청의 흐름이 이어진다.
@Service
public class TodoService {
	List<String> todos = new ArrayList<String>();

	private ConcurrentMap<Integer, Todo> repo = new ConcurrentHashMap<>();
	private AtomicInteger maxId = new AtomicInteger(0);
	private TodoDao dao;
	
	public TodoService(TodoDao dao) {
		this.dao = dao;
	}
	public Todo findById(Integer id) {
		//return new Todo(1, "todo list 1", 1, "2017-06-05" );
		//return repo.get(id);
		return dao.selectById(id);
	}
	public Collection<Todo> findAll() {
//		return Arrays.asList(
//		new Todo(1, "todo number1", 1, "2017-06-04"),
//		new Todo(2, "todo number2", 0, "2017-06-05")
//		);
		//return repo.values();
		return dao.selectAll();
	}
	public Todo create(Todo todo) {
//		Integer id = maxId.addAndGet(1);
//		todo.setId(id);
//		repo.put(id, todo);
//		return todo;
		Integer id = dao.insert(todo);
		todo.setId(id);
		return todo;
	}
	public boolean update(Todo todo) {
//		Todo old = repo.put(todo.getId(), todo);
//		return old != null;
		int affected = dao.update(todo);
		return affected == 1;
	}
	
	public boolean delete(Integer id) {
//		Todo old = repo.remove(id);
//		return old != null;
		int affected = dao.deleteById(id);
		return affected == 1;
	}

	
    @PostConstruct
    public void setupTodo(){
        todos.add("Write better code");
        todos.add("Learn AngularJs");
        todos.add("Watch star wars again!!!");
    }

    public List<String> allTodos() {
        return todos;
    }

    public void addTodo(String todo) {
        if (!todos.contains(todo)){
            todos.add(todo);
        }
    }

    public void deleteTodo(String todo) {
        if (todos.contains(todo)){
            todos.remove(todo);
        }
    }

    public void deleteAll() {
        todos.clear();
    }


    public void updateTodo(int position, String todo) {
        todos.set(position, todo);
    }
}
