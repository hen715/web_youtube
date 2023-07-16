package DB_project.dbproject;

import DB_project.dbproject.repository.*;
import DB_project.dbproject.service.CommentService;
import DB_project.dbproject.service.MemberService;
import DB_project.dbproject.service.VideoService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        return new JpaMemberRepository(em);
    }

    @Bean
    public VideoService videoService(){
        return new VideoService(videoRepository());
    }
    @Bean
    public VideoRepository videoRepository(){
        return new JpaVideoRepository(em);
        //return new MemoryVideoRepository();
    }

    @Bean
    public CommentService commentService(){
        return new CommentService(commentRepository());
    }

    @Bean
    public CommentRepository commentRepository(){
        return new JpaCommentRepository(em);
        //return new MemoryCommentRepository();
    }


}
