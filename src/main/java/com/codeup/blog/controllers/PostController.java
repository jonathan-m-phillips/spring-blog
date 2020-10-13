package com.codeup.blog.controllers;

import com.codeup.blog.models.Post;
import com.codeup.blog.models.User;
import com.codeup.blog.repository.PostRepository;
import com.codeup.blog.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PostController {
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public PostController(PostRepository postRepo, UserRepository userRepo){
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public String showAllPosts(Model model) {
        model.addAttribute("posts", postRepo.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String postPost(@RequestParam(name = "title") String title,
                           @RequestParam(name = "body") String body,
                           Model model) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        postRepo.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        if (post != null) {
            postRepo.delete(post);
        }
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String showEditPost(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        if (post == null) {
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/edit")
    public String editPost(@RequestParam(name = "id") long id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "body") String body
                           ) {
        Post post = postRepo.getPostById(id);
        if (post == null) {
            return "redirect:/posts";
        }
        post.setTitle(title);
        post.setBody(body);
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("posts/hardcoded/create")
    public String createHardcodedAd() {
        Post post = new Post();
        post.setTitle("This is a hardcoded blog post");
        post.setBody("I hardcoded this blog post.");
        post.setUser(userRepo.getOne(1L));
        postRepo.save(post);
        return "redirect:/posts";
    }

}
