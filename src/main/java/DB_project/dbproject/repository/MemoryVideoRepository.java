package DB_project.dbproject.repository;


import DB_project.dbproject.domain.Video;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class MemoryVideoRepository implements VideoRepository{
    private static ArrayList<Video> listOfVideos = new ArrayList<Video>();
    private static long sequence = 0L;

    public Video save(Video video) {
        video.setId(++sequence);
        video.setNickName(video.getNickName());
        video.setU_id(video.getU_id());
        video.setTitle(video.getTitle());
        video.setDescription(video.getDescription());
        video.setPlayList(video.getPlayList());
        video.setCategory(video.getCategory());
        listOfVideos.add(video);
        return video;
    }
    @Override
    public List<Video> findByu_id(String u_id) {
        return listOfVideos.stream().filter(video->video.getU_id().equals(u_id)).collect(Collectors.toList());//아이디와 일치하는 모든 비디오 리스트 리턴
    }

    @Override
    public List<Video> findByTitle(String title) {
        if (title.isEmpty()) {
            return Collections.emptyList(); // 빈 문자열인 경우 빈 리스트 반환
        }
        return listOfVideos.stream().filter(video->video.getTitle().contains(title)).collect(Collectors.toList());//제목을 담고있는 모든 비디오 리스트 리턴
    }

    @Override
    public Optional<Video> findById(Long id) {

        return listOfVideos.stream().filter(video -> video.getId().equals(id)).findAny();
    }

    @Override
    public List<Video> findAll() {
        return listOfVideos;
    }

    @Override
    public Video update(Long id, Video video) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
