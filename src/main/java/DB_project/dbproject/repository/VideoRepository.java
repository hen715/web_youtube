package DB_project.dbproject.repository;


import DB_project.dbproject.domain.Video;

import java.util.List;
import java.util.Optional;

public interface VideoRepository {
    Video save(Video video);
    List<Video> findByTitle(String title);
    List<Video> findByu_id(String u_id);
    Optional<Video> findById(Long id);
    List<Video> findAll();
    Video update(Long id,Video video);
    void delete(Long id);


}
