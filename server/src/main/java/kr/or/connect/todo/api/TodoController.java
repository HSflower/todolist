package kr.or.connect.todo.api;

import java.util.Collection;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.persistence.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	private final TodoService todoService;
	private final Logger log = LoggerFactory.getLogger(TodoController.class);
	
	@Autowired
	public TodoController(TodoService todoService){
		this.todoService = todoService;
	}
	
	@GetMapping
	Collection<Todo> readList(){
		return todoService.findAll();
	}
	@GetMapping("/{id}")
	Todo read(@PathVariable Integer id){
		return todoService.findById(id);
	}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Todo create(@RequestBody Todo todo) {
		Todo newTodo = todoService.create(todo);
		log.info("todo created : {}" , newTodo);
		return newTodo;
	}
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@PathVariable Integer id, @RequestBody Todo todo) {
		todo.setId(id);
		todoService.update(todo);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer id) {
		todoService.delete(id);
	}
	
    @GetMapping(value = "/all.json")
    public @ResponseBody List<String> viewAllTodos(){
        return todoService.allTodos();
    }

    @PostMapping(value = "/add/{todo}")
    public @ResponseBody void addTodo(@PathVariable("todo") String todo){
        todoService.addTodo(todo);
    }

    @DeleteMapping(value = "/delete/{todo}")
    public @ResponseBody void deleteTodo(@PathVariable("todo") String todo){
        todoService.deleteTodo(todo);
    }

    @DeleteMapping(value = "/deleteAll")
    public @ResponseBody void deleteAllTodo(){
        todoService.deleteAll();
    }

    @PutMapping(value="/update/{position}/{todo}")
    public @ResponseBody void updateTodo(@PathVariable("position") String position, @PathVariable("todo") String todo){
        todoService.updateTodo(Integer.valueOf(position), todo);
    }

    @RequestMapping("/layout")
    public String getTodoPartialPage() {
        return "todo/layout";
    }
}
