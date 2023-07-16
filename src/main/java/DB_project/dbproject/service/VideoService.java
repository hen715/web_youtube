package DB_project.dbproject.service;

import DB_project.dbproject.domain.Member;
import DB_project.dbproject.domain.Video;
import DB_project.dbproject.repository.MemoryVideoRepository;
import DB_project.dbproject.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }
    public Long upload(Video video) {

        videoRepository.save(video);
        return video.getId();
    }

    public List<Video> findVideos(){
        return videoRepository.findAll();
    }
    public List<Video> findVideosByTitle(String title){return videoRepository.findByTitle(title);
    }
    public List<Video> findVideosByU_id(String u_id){return videoRepository.findByu_id(u_id);}
    public Optional<Video> findVideoById(Long id){return videoRepository.findById(id);}
    public void deleteById(Long id){videoRepository.delete(id);}
    public void updateById(Long id,Video video){videoRepository.update(id,video);}
}
