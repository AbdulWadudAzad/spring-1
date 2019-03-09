package com.beskilled.controller;


import com.beskilled.entity.Role;
import com.beskilled.entity.User;
import com.beskilled.repo.OrganizationRepository;
import com.beskilled.repo.RoleRepository;
import com.beskilled.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "/user/")
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrganizationRepository orgRepo;

    @GetMapping(value = "add")
    public String viewAdd(User user, Model model){
        model.addAttribute("rolelist", this.roleRepo.findAll());
        model.addAttribute("orgList", this.orgRepo.findAll());
        return "users/add";
    }
    @PostMapping(value = "add")
    public String add(@Valid User user, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("rolelist", this.roleRepo.findAll());
            model.addAttribute("orgList", this.orgRepo.findAll());
            return "users/add";
        }
        if(repo.existsByEmail(user.getEmail())){
            model.addAttribute("rejectMsg","Already Have This Entry");
        }else{
            user.setRegiDate(new Date());
            String username = user.getEmail().split("\\@")[0];
            user.setUserName(username);
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setConfirmationToken(UUID.randomUUID().toString());
            this.repo.save(user);
            model.addAttribute("successMsg","Successfully Saved!");
            model.addAttribute("user", new User());
            model.addAttribute("rolelist", this.roleRepo.findAll());
            model.addAttribute("orgList", this.orgRepo.findAll());
        }

        return "users/add";
    }


    @GetMapping(value = "edit/{id}")
    public String viewEdit(Model model, @PathVariable("id") Long id){
        model.addAttribute("user",repo.getOne(id));
        model.addAttribute("rolelist", this.roleRepo.findAll());
        model.addAttribute("orgList", this.orgRepo.findAll());
        return "users/edit";
    }
    @PostMapping(value = "edit/{id}")
    public String edit(@Valid User user, BindingResult result, Model model,@PathVariable("id") Long id){
        if(result.hasErrors()){
            model.addAttribute("orgList", this.orgRepo.findAll());
            return "users/edit";
        }
        Optional<User> u = this.repo.findByEmail(user.getEmail());
        if(u.get().getId() != id){
            model.addAttribute("rejectMsg","Already Have This Entry");
            return "users/edit";
        }else{
            user.setId(id);
            this.repo.save(user);
        }
        model.addAttribute("orgList", this.orgRepo.findAll());
        return "redirect:/user/list";
    }

    @GetMapping(value = "del/{id}")
    public String del(@PathVariable("id") Long id){
        if(id != null) {
            this.repo.deleteById(id);
        }
        return "redirect:/user/list";
    }

    @GetMapping(value = "list")
    public String list(Model model){
        model.addAttribute("list", this.repo.findAll());
        return "users/list";
    }
   /* @GetMapping(value = "/user-save")
    public String saveUser() {

        Set<Role> roles=new HashSet<>();
        roles.add(new Role("SUPERADMIN"));
        roles.add(new Role("ADMIN"));
        roles.add(new Role("CADMIN"));
        roles.add(new Role("PM"));
        roles.add(new Role("TEAMLEAD"));
        roles.add(new Role("DEVELOPER"));
        roles.add(new Role("USER"));
        roles.forEach((role -> {
            roleRepo.save(role);
        }));

        roles.add(new Role(1L));
        roles.add(new Role(2L));

        User user = new User("Mostafizur","Rahman","mostafiz","admin@gmail.com",new Date(),true,UUID.randomUUID().toString(),roles);
        user.setPassword(passwordEncoder.encode("mostafiz"));
        repo.save(user);
        return "success";
    }

    @GetMapping(value = "/register")
    public ModelAndView displayRegister(User user){
        ModelAndView mv=new ModelAndView();
        mv.addObject("user",user);
        mv.setViewName("signup");
        return mv;
    }*/
}