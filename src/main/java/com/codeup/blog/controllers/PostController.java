package com.codeup.blog.controllers;

import com.codeup.blog.models.Post;
import com.codeup.blog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {
    private final PostRepository postRepo;

    public PostController(PostRepository postRepo){
        this.postRepo = postRepo;
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

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    public String createPost() {
        return "posts/create";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.POST)
    public String postPost(@RequestParam(name = "title") String title,
                           @RequestParam(name = "body") String body,
                           Model model) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
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


}
