package com.project.blogapp.controller;

import com.project.blogapp.dto.PostDTO;
import com.project.blogapp.dto.PostResponse;
import com.project.blogapp.service.PostSevice;
import com.project.blogapp.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostSevice postSevice;

    public PostController(PostSevice postSevice) {
        this.postSevice = postSevice;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postSevice.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        return ResponseEntity.ok(postSevice.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPOstById(@PathVariable("id") long postId) {
        return ResponseEntity.ok(postSevice.getPostById(postId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") long postId, @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postSevice.updatePost(postId, postDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long postId) {
        postSevice.deletePost(postId);
        return ResponseEntity.ok("Post entity succssfully deleted");
    }

}
