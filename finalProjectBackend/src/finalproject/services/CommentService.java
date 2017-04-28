package finalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalproject.database.dao.CommentDAO;
import finalproject.exception.InvalidInputData;
import finalproject.model.Comment;
import finalproject.util.Util;

@Service
public class CommentService {

	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private Util util;
	
	public void validateComment(Comment comment) throws InvalidInputData {
			String date = comment.getDate();
			String text = comment.getText();
		 if(!util.isParameterValid(date) && util.isStringEmpty(text)){
			 throw new InvalidInputData();
		 }
	}
	
	public void saveComment(Comment comment, Integer userId) throws InvalidInputData{
		comment.setDate(util.getDate());
		comment.setUser_id(userId);
		validateComment(comment);
		commentDAO.save(comment);
	}
	
	
}
