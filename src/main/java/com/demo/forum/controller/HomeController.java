package com.demo.forum.controller;

import com.demo.forum.model.*;
import com.demo.forum.repository.*;
import com.demo.forum.authen.*;

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
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

@Controller
@RequestMapping(path="/")
public class HomeController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping()
    public String listPost(Model model) {
        List<Post> lisPosts = postRepository.findAll();
        model.addAttribute("listPosts", lisPosts);
        return "index";
    }
}