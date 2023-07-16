package DB_project.dbproject.repository;

import DB_project.dbproject.domain.Member;
import DB_project.dbproject.domain.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class JpaVideoRepository implements VideoRepository{

    private final EntityManager em;

    public JpaVideoRepository(EntityManager em) {
        this.em = em;
    }



    @Override
    public Video save(Video video) {
        String nativeQuery = "INSERT INTO video (u_id, nickName,title,description,playList, category) VALUES (?, ?, ?, ?, ?,?)";
        Query query = em.createNativeQuery(nativeQuery);
        query.setParameter(1,video.getU_id());
        query.setParameter(2, video.getNickName());
        query.setParameter(3, video.getTitle());
        query.setParameter(4, video.getDescription());
        query.setParameter(5, video.getPlayList());
        query.setParameter(6, video.getCategory());
        query.executeUpdate();
        return video;
    }

    @Override
    public List<Video> findByTitle(String title) {
//        List<Video> result = em.createQuery("select v from Video v",Video.class)
//                .setParameter("title",title).getResultList();

        String nativeQuery = "SELECT * FROM video WHERE title LIKE CONCAT('%', :title, '%')";
        List<Video> result = em.createNativeQuery(nativeQuery, Video.class)
                .setParameter("title", title)
                .getResultList();

        nativeQuery = "SELECT * FROM video WHERE nickName = :title";
        List<Video> result2 = em.createNativeQuery(nativeQuery, Video.class)
                .setParameter("title", title)
                .getResultList();

        result.addAll(result2);
        result.addAll(result2);

        List<Video> distinctResult = result.stream()
                .distinct()
                .collect(Collectors.toList());

        return distinctResult;
    }

    @Override
    public List<Video> findByu_id(String u_id) {
//        List<Video> result = em.createQuery("select v from Video v",Video.class)
//                .setParameter("u_id",u_id).getResultList();
        String nativeQuery = "SELECT * FROM video WHERE u_id = :u_id";
        List<Video> result = em.createNativeQuery(nativeQuery, Video.class)
                .setParameter("u_id", u_id)
                .getResultList();
        return result;
    }

    @Override
    public Optional<Video> findById(Long id) {
//        List<Video> result = em.createQuery("select v from Member v",Video.class)
//                .setParameter("id",id).getResultList();
        String nativeQuery = "SELECT * FROM video WHERE id = :id";
        List<Video> result = em.createNativeQuery(nativeQuery, Video.class)
                .setParameter("id", id)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Video> findAll() {
        String nativeQuery = "SELECT * FROM video";
        return em.createNativeQuery(nativeQuery, Video.class)
                .getResultList();
    }

    @Override
    public Video update(Long id, Video video) {
        String nativeQuery = "UPDATE video SET title = ? , description = ?, playList= ? ,category= ?  WHERE id = ?";
        Query query = em.createNativeQuery(nativeQuery);

        query.setParameter(1, video.getTitle());
        query.setParameter(2, video.getDescription());
        query.setParameter(3,video.getPlayList());
        query.setParameter(4,video.getCategory());
        query.setParameter(5,id);
        query.executeUpdate();
        return video;
    }

    @Override
    public void delete(Long id) {
        String nativeQuery = "DELETE FROM comment WHERE video_id = ?";
        Query query = em.createNativeQuery(nativeQuery);
        query.setParameter(1, id);
        query.executeUpdate();
        nativeQuery = "DELETE FROM video WHERE id = ?";
        query = em.createNativeQuery(nativeQuery);
        query.setParameter(1, id);
        query.executeUpdate();

    }
}
