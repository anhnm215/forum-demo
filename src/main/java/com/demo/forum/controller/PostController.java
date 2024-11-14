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
@RequestMapping(path="/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping()
    public String listPost(Model model) {
        List<Post> lisPosts = postRepository.findAll();
        model.addAttribute("listPosts", lisPosts);
        return "index";
    }

    @GetMapping(path="/add")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        return "create_post";
    }

    @PostMapping(path="/add")
    public ModelAndView processCreatePost(Post post) {
        if ( !StringUtils.isNotBlank(post.getTitle()) ) {
            ModelAndView mav = new ModelAndView("create_post");
            mav.addObject("message", "Missing Post Title");
            return mav;
        }
        else if ( !StringUtils.isNotBlank(post.getContent()) ) {
            ModelAndView mav = new ModelAndView("create_post");
            mav.addObject("message", "Missing Post Content");
            return mav;
        }
        else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl loggedIn = (UserDetailsImpl)authentication.getPrincipal();
            post.setUserId(loggedIn.getId());
            postRepository.save(post);
            ModelAndView mav = new ModelAndView("post_details");
            mav.addObject("post", post);
            return mav;
        }        
    }

    @GetMapping(path="/details")
    public String readPost(Model model) {
        model.addAttribute("post", new Post());
        return "post_details";
    }

    @PostMapping(path="/delete")
    public String deletePost(Model model) {
        return "index";
    }
}