package com.project.blogapp.service;

import com.project.blogapp.dto.PostDTO;
import com.project.blogapp.dto.PostResponse;

public interface PostSevice {

    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePost(long id, PostDTO postDTO);

    void deletePost(long id);
}
