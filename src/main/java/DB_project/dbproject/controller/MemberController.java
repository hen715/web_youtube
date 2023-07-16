package DB_project.dbproject.controller;

import DB_project.dbproject.domain.Member;
import DB_project.dbproject.repository.MemberRepository;
import DB_project.dbproject.repository.MemoryMemberRepository;
import DB_project.dbproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {
    private MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }



    @GetMapping("/members/new")
    public String createForm(){
        return"members/createMemberForm";
    }
    @PostMapping(value ="/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setU_id(form.getU_id());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setPhone(form.getPhone());
        member.setNickName(form.getNickName());


        memberService.join(member);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "u_id",required = false) String u_id, @RequestParam(value = "password",required = false) String password, RedirectAttributes redirectAttributes){

        if(memberService.login(u_id, password)) {
            redirectAttributes.addAttribute("u_id", u_id);
            Optional<Member> member = memberService.findByu_id(u_id);
            String nick = member.get().getNickName();
            redirectAttributes.addAttribute("nickName",nick);
           return "redirect:/loginSuccess";
       } else
           return "loginFail";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
    @GetMapping("/updateMember")
    public  String updateMem(@RequestParam("u_id") String u_id,Model model){
        model.addAttribute("u_id",u_id);
        return"/members/updateMemberForm";
    }
    @PostMapping(value = "/updateMember")
    public String updateMember(@RequestParam(value = "u_id") String u_id,MemberForm form,RedirectAttributes redirectAttributes){
        Member member = new Member();
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setPhone(form.getPhone());
        member.setNickName(form.getNickName());


        memberService.update(u_id,member);
        Optional<Member> member1 = memberService.findByu_id(u_id);
        redirectAttributes.addAttribute("u_id",member1.get().getU_id());
        redirectAttributes.addAttribute("nickName",member1.get().getNickName());

        return "redirect:/loginSuccess";
    }
    @GetMapping("/deleteMember")
    public String deleteMember(@RequestParam("u_id") String u_id,RedirectAttributes redirectAttributes){
        memberService.delete(u_id);
        return "redirect:/";
    }


}
