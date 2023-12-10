package com.project.blogapp.service;

import com.project.blogapp.dto.PostDTO;
import com.project.blogapp.dto.PostResponse;

import java.util.List;

public interface PostSevice {

    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(long id);

    List<PostDTO> getAllPostByCategoryId(Long categoryId);

    PostDTO updatePost(long id, PostDTO postDTO);

    void deletePost(long id);
}
