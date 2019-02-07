package com.beskilled.controller;

import com.beskilled.entity.User;
import com.beskilled.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserRepo repo;

    @GetMapping(value = "/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        //mv.addObject("user", "Home | beskilled");
        mv.addObject("usre", new User());
        mv.setViewName("index");
        return mv;
    }

    @PostMapping(value = "/")
    public ModelAndView add(@Valid User user) {
        ModelAndView mv = new ModelAndView();
        // mv.addObject("user", "Home | beskilled");
        mv.addObject("title", "Home|beSkilled");
        if (user.getName() != null) {
            this.repo.save(user);
            System.out.println("Success");
            mv.addObject("successMsg", "Successfully save");
            mv.addObject("usre", new User());
        }
        mv.setViewName("index");
        return mv;
    }
}