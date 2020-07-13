package com.friend.finder.controllers.rest_controller;

import com.friend.finder.models.Post;
import com.friend.finder.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/myPost")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("")
    public Post addMyPost(@RequestBody Post post){
        return postService.save(post);

    }
    @GetMapping("")
    public List<Post> showMyPost(){
        return (List<Post>) postService.findAll();
    }


}
