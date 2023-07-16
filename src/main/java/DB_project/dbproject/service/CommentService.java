package DB_project.dbproject.service;

import DB_project.dbproject.domain.Comment;
import DB_project.dbproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Long add(Comment comment){
        commentRepository.save(comment);
        return comment.getId();
    }

    public List<Comment> findComments(){
        return commentRepository.findAll();
    }

    public List<Comment> findCommentByVideo_id(Long video_id){return commentRepository.findByVideo_id(video_id);}

    public void deleteByu_id(String u_id){commentRepository.delete(u_id);}

    public void updateById(Long id,Comment comment){commentRepository.update(id,comment);}
}
