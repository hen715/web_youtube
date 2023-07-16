package DB_project.dbproject.controller;

import DB_project.dbproject.domain.Member;
import DB_project.dbproject.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;




@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@RequestParam("u_id") String u_id,@RequestParam("nickName") String nickName, Model model) {
        model.addAttribute("u_id", u_id);
        model.addAttribute("nickName",nickName);
        return "loginSuccess";


    }

}
