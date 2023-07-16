package DB_project.dbproject.controller;


import DB_project.dbproject.domain.Comment;
import DB_project.dbproject.domain.Video;
import DB_project.dbproject.service.CommentService;
import DB_project.dbproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class VideoController {
    private final VideoService videoService;
    private final CommentService commentService;

    @Autowired
    public VideoController(VideoService videoService,CommentService commentService) {
        this.videoService = videoService;
        this.commentService = commentService;
    }



    @GetMapping("/uploadVideo")
    public String createForm(@RequestParam("u_id") String u_id, @RequestParam("nickName") String nickName,Model model ){
        model.addAttribute("u_id",u_id);
        model.addAttribute("nickName",nickName);
        return"videos/uploadVideoForm";
    }
    @PostMapping(value ="/uploadVideo")
    public String create(VideoForm form, @RequestParam(value = "u_id")String u_id, @RequestParam(value="nickName")String nickName,RedirectAttributes redirectAttributes){
        Video video = new Video();
        video.setU_id(u_id);
        video.setNickName(nickName);
        video.setTitle(form.getTitle());
        video.setDescription(form.getDescription());
        video.setPlayList(form.getPlayList());
        video.setCategory(form.getCategory());


        videoService.upload(video);

        redirectAttributes.addAttribute("u_id", u_id);
        redirectAttributes.addAttribute("nickName",nickName);
        return "redirect:/loginSuccess";
    }

    @PostMapping("/search")
    public String videoList(@RequestParam(value = "u_id",required = false) String u_id,@RequestParam(value = "nickName",required = false) String nickName,
                            @RequestParam(value = "search",required = false) String search,Model model,RedirectAttributes redirectAttributes){
        if(u_id==null)
            u_id="비회원";
        if(nickName==null)
            nickName="비회원";

        model.addAttribute("search",search);
        model.addAttribute("u_id",u_id);
        model.addAttribute("nickName",nickName);
        model.addAttribute("search",search);

        redirectAttributes.addAttribute("u_id",u_id);
        redirectAttributes.addAttribute("search",search);
        redirectAttributes.addAttribute("nickName",nickName);

        return "redirect:/videos";
    }

    @GetMapping("/videos")
    public String list(@RequestParam("u_id")String u_id,@RequestParam("nickName")String nickName,@RequestParam("search") String search, Model model){
        List<Video> videos = videoService.findVideosByTitle(search);
        model.addAttribute("videos",videos);
        model.addAttribute("u_id",u_id);
        model.addAttribute("nickName",nickName);
        model.addAttribute("search",search);
        return "videos/videoList";
    }

    @GetMapping("/show")
    public String show(@RequestParam("video_id") Long video_id,@RequestParam("u_id")String u_id,@RequestParam("nickName") String nickName,Model model){
        Optional<Video> video = videoService.findVideoById(video_id);
        List<Comment> comments = commentService.findCommentByVideo_id(video_id);
        model.addAttribute("video",video.orElse(null));
        model.addAttribute("comments",comments);
        model.addAttribute("u_id",u_id);
        model.addAttribute("nickName",nickName);
        return "/videos/showVideo";
    }


    @GetMapping("/newComment")
    public String comment(@RequestParam("u_id") String u_id,@RequestParam("video_id")Long video_id,@RequestParam("nickName")String nickName, Model model){
        model.addAttribute("u_id",u_id);
        model.addAttribute("video_id",video_id);
        model.addAttribute("nickName",nickName);
        return "/videos/newCommentForm";
    }

    @PostMapping(value="/newComment")
    public String newComment(@RequestParam(value = "u_id") String u_id,@RequestParam(value = "video_id") Long video_id,@RequestParam(value="nickName")String nickName,
                             CommentForm form,RedirectAttributes redirectAttributes){
        Comment comment = new Comment();
        comment.setNickName(nickName);
        comment.setU_id(u_id);
        comment.setVideo_id(video_id);
        comment.setReply(form.getReply());
        comment.setDate(String.valueOf(new Date()));

        commentService.add(comment);

        redirectAttributes.addAttribute("video_id",video_id);
        redirectAttributes.addAttribute("u_id",u_id);
        redirectAttributes.addAttribute("nickName",nickName);
        return"redirect:/show";

    }

    @GetMapping("/back")
    public String back(@RequestParam("u_id") String u_id,@RequestParam("nickName") String nickName,RedirectAttributes redirectAttributes ) {
        if (u_id.equals("비회원"))
            return "redirect:/";
        else {
            redirectAttributes.addAttribute("u_id", u_id);
            redirectAttributes.addAttribute("nickName",nickName);
            return "redirect:/loginSuccess";
        }
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("u_id") String u_id,@RequestParam("video_id") Long video_id,@RequestParam("nickName")String nickName,RedirectAttributes redirectAttributes){
        commentService.deleteByu_id(u_id);
        redirectAttributes.addAttribute("u_id",u_id);
        redirectAttributes.addAttribute("video_id",video_id);
        redirectAttributes.addAttribute("nickName",nickName);

        return"redirect:/show";
    }
    @GetMapping("/updateComment")
    public String updateCom(@RequestParam("u_id") String u_id,@RequestParam("video_id") Long video_id,@RequestParam("nickName")String nickName,@RequestParam("id") Long id,Model model){
        model.addAttribute("u_id",u_id);
        model.addAttribute("video_id",video_id);
        model.addAttribute("nickName",nickName);
        model.addAttribute("id",id);

        return"/videos/updateCommentForm";
    }
    @PostMapping(value="updateComment")
    public String updateComment(@RequestParam("u_id") String u_id,@RequestParam("video_id") Long video_id,@RequestParam("nickName")String nickName,@RequestParam("id") Long id,
                                CommentForm form,RedirectAttributes redirectAttributes){
        Comment comment = new Comment();
        comment.setReply(form.getReply());

        commentService.updateById(id,comment);
        redirectAttributes.addAttribute("u_id",u_id);
        redirectAttributes.addAttribute("video_id",video_id);
        redirectAttributes.addAttribute("nickName",nickName);

        return"redirect:/show";
    }
    @GetMapping("/deleteVideo")
    public String deleteVideo(@RequestParam("u_id") String u_id,@RequestParam("video_id") Long video_id,@RequestParam("nickName")String nickName,
                              @RequestParam("search") String search,RedirectAttributes redirectAttributes){
        videoService.deleteById(video_id);
        redirectAttributes.addAttribute("u_id",u_id);
        redirectAttributes.addAttribute("search",search);
        redirectAttributes.addAttribute("nickName",nickName);

        return"redirect:/videos";
    }
    @GetMapping("/updateVideo")
    public String updateVid(@RequestParam("u_id") String u_id,@RequestParam("video_id") Long video_id,@RequestParam("nickName")String nickName,
                            @RequestParam("search") String search,Model model){
        model.addAttribute("u_id",u_id);
        model.addAttribute("video_id",video_id);
        model.addAttribute("nickName",nickName);
        model.addAttribute("search",search);

        return"/videos/updateVideoForm";
    }

    @PostMapping(value= "/updateVideo")
    public String updateVideo(@RequestParam("u_id") String u_id,@RequestParam("video_id") Long video_id,@RequestParam("nickName")String nickName,
                              @RequestParam("search") String search,VideoForm form,RedirectAttributes redirectAttributes) {
        Video video = new Video();
        video.setTitle(form.getTitle());
        video.setDescription(form.getDescription());
        video.setPlayList(form.getPlayList());
        video.setCategory(form.getCategory());


        videoService.updateById(video_id,video);

        redirectAttributes.addAttribute("u_id", u_id);
        redirectAttributes.addAttribute("search",search);
        redirectAttributes.addAttribute("nickName",nickName);
        redirectAttributes.addAttribute("search",search);
        return "redirect:/videos";

    }



}
