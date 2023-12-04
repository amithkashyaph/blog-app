package com.project.blogapp.controller;

import com.project.blogapp.dto.PostDTO;
import com.project.blogapp.dto.PostResponse;
import com.project.blogapp.service.PostSevice;
import com.project.blogapp.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD APIs for Post Resource"
)
public class PostController {

    private PostSevice postSevice;

    public PostController(PostSevice postSevice) {
        this.postSevice = postSevice;
    }


    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post to a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postSevice.createPost(postDTO), HttpStatus.CREATED);
    }



    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Posts REST API is used to get all posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        return ResponseEntity.ok(postSevice.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }


    @Operation(
            summary = "Get PostById REST API",
            description = "Get PostById REST API is used to get single post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPOstById(@PathVariable("id") long postId) {
        return ResponseEntity.ok(postSevice.getPostById(postId));
    }


    @Operation(
            summary = "Get Post By CategoryId REST API",
            description = "Get Post By CategoryId REST API is used to get all posts related to a category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostsByCategoryId(@PathVariable Long categoryId) {
        return new ResponseEntity<>(postSevice.getAllPostByCategoryId(categoryId), HttpStatus.OK);
    }



    @Operation(
            summary = "Update Post By Id REST API",
            description = "Update Post By Id REST API is used to update a specific post in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @PathVariable("id") long postId, @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postSevice.updatePost(postId, postDTO), HttpStatus.OK);
    }



    @Operation(
            summary = "Delete Post By Id REST API",
            description = "Delete Post By Id REST API is used to delete a specific post in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long postId) {
        postSevice.deletePost(postId);
        return ResponseEntity.ok("Post entity succssfully deleted");
    }

}
