package DB_project.dbproject.repository;

import DB_project.dbproject.domain.Comment;
import DB_project.dbproject.domain.Member;
import DB_project.dbproject.domain.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public class JpaCommentRepository implements CommentRepository{
    private final EntityManager em;

    public JpaCommentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment save(Comment comment) {
        LocalDate currentDate = LocalDate.now();
        String nativeQuery = "INSERT INTO comment ( video_id,u_id, nickName, reply, date) VALUES (?, ?, ?, ?, ?)";
        Query query = em.createNativeQuery(nativeQuery);

        query.setParameter(1, comment.getVideo_id());
        query.setParameter(2, comment.getU_id());
        query.setParameter(3, comment.getNickName());
        query.setParameter(4, comment.getReply());
        query.setParameter(5, java.sql.Date.valueOf(currentDate));
        query.executeUpdate();
        return comment;
    }

    @Override
    public List<Comment> findAll() {
//        return  em.createQuery("select c from Comment c", Comment.class)
//                .getResultList();
        String nativeQuery = "SELECT * FROM comment";
        return em.createNativeQuery(nativeQuery, Comment.class)
                .getResultList();
    }

    @Override
    public List<Comment> findByVideo_id(Long video_id) {
//        return em.createQuery("select c from Comment c",Comment.class)
//                .setParameter("video_id",video_id).getResultList();
        String nativeQuery = "SELECT * FROM comment WHERE video_id = :video_id";
        List<Comment> result = em.createNativeQuery(nativeQuery, Comment.class)
                .setParameter("video_id", video_id)
                .getResultList();
        return result;

    }

    @Override
    public Comment update(Long id,Comment comment) {
        LocalDate currentDate = LocalDate.now();
        String nativeQuery = "UPDATE comment SET reply = ? , date = ? WHERE id = ?";
        Query query = em.createNativeQuery(nativeQuery);

        query.setParameter(1, comment.getReply());
        query.setParameter(2, java.sql.Date.valueOf(currentDate));
        query.setParameter(3,id);
        query.executeUpdate();
        return comment;
    }

    @Override
    public void delete(String u_id) {
        String nativeQuery = "DELETE FROM comment WHERE u_id = ?";
        Query query = em.createNativeQuery(nativeQuery);
        query.setParameter(1, u_id);
        query.executeUpdate();
    }
}
