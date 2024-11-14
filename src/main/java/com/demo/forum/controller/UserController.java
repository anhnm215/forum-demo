package com.demo.forum.controller;

import com.demo.forum.model.*;
import com.demo.forum.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.commons.lang3.StringUtils;

@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping(path="/register")
    public ModelAndView processRegister(User user) {
        if (userRepository.findExistByUsername(user.getUsername()) != 0) {
            ModelAndView mav = new ModelAndView("signup");
            mav.addObject("message", "Username existed");
            return mav;
        }
        else if (userRepository.findExistByEmail(user.getEmail()) != 0) {
            ModelAndView mav = new ModelAndView("signup");
            mav.addObject("message", "Email existed");
            return mav;
        }
        else if ( StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getEmail()) || StringUtils.isBlank(user.getUsername()) ) {
            ModelAndView mav = new ModelAndView("signup");
            mav.addObject("message", "Missing Username, Email or Password");
            return mav;
        }
        else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRole(1);
            userRepository.save(user);
        }        
        ModelAndView mav = new ModelAndView("logIn");
        return mav;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "logIn";
    }
}