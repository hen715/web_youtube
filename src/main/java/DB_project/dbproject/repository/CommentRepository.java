package DB_project.dbproject.repository;

import DB_project.dbproject.domain.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findAll();
    List<Comment> findByVideo_id(Long video_id);

    Comment update(Long id,Comment comment);
    void delete(String u_id);
}
