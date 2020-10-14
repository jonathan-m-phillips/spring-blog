package com.codeup.blog.controllers;

import com.codeup.blog.models.Post;
import com.codeup.blog.models.User;
import com.codeup.blog.repository.PostRepository;
import com.codeup.blog.repository.UserRepository;
import com.codeup.blog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PostController {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
//    private final EmailService emailService;

    public PostController(PostRepository postRepo, UserRepository userRepo, EmailService emailService){
        this.postRepo = postRepo;
        this.userRepo = userRepo;
//        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String showAllPosts(Model model) {
        List<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        if (post.getUser() == null) {
            List<User> users = userRepo.findAll();
            post.setUser(users.get(0));
        }
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }


    @PostMapping("/posts/create")
    public String postPost(@ModelAttribute Post post) {
        postRepo.save(post);
//        emailService.prepareAndSend(post,
//                "Created Post: " + post.getTitle(),
//                post.getTitle() + "\n\n" + post.getBody());
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        postRepo.delete(post);
//            emailService.prepareAndSend(post,
//                    "Deleted Post: " + post.getTitle(),
//                    post.getTitle() + "\n\n" + post.getBody());
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String editPost(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        model.addAttribute("post", post);
        return "posts/create";
    }

    @PostMapping("/posts/edit")
    public String updatePost(@ModelAttribute Post post) {
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }

}
