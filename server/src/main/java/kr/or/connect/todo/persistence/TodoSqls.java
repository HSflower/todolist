package kr.or.connect.todo.persistence;

public class TodoSqls {
	//book 테이블에 id로 지정된 1건을 삭제하는 DELETE 구분을 작성하고 실행한다.
//	static final String DELETE_BY_ID = "DELETE FROM book WHERE id= :id";
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	//book 테이블을 update하는 쿼리를 작성해서 실행
	static final String UPDATE =
			"UPDATE book SET\n"
			+ "title = :title,"
			+ "author = :author,"
			+ "pages = :pages\n"
			+ "WHERE id = :id";
	static final String SELECT_BY_ID =
			"SELECT id, title, author, pages FROM book where id = :id";
	private static final String SELECT_ALL =
			"SELECT id, title, author, pages FROM book";
	
}
