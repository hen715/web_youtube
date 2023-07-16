package DB_project.dbproject.repository;

import DB_project.dbproject.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.crypto.CipherInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MemoryCommentRepository implements CommentRepository{

    private static ArrayList<Comment> listOfComments = new ArrayList<>();
    private static long sequence = 0L;
    @Override
    public Comment save(Comment comment) {
        comment.setId(++sequence);
        comment.setNickName(comment.getNickName());
        comment.setU_id(comment.getU_id());
        comment.setVideo_id(comment.getVideo_id());
        comment.setReply(comment.getReply());
        comment.setDate(comment.getDate());
        listOfComments.add(comment);
        return comment;
    }

    @Override
    public List<Comment> findAll() {
        return listOfComments;
    }

    @Override
    public List<Comment> findByVideo_id(Long video_id) {
        return listOfComments.stream().filter(comment -> comment.getVideo_id().equals(video_id)).collect(Collectors.toList());
    }

    @Override
    public Comment update(Long id, Comment comment) {
        return null;
    }

    @Override
    public void delete(String u_id) {

    }
}
