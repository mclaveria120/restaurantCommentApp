package finalproject.database.dao;

import org.springframework.stereotype.Service;

import finalproject.database.AbstractDAO;
import finalproject.model.Comment;

@Service
public class CommentDAO extends AbstractDAO<Comment> {

	public void save(Comment comment){
		this.persist(comment);
	}
}
