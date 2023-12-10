package com.project.blogapp.service.impl;

import com.project.blogapp.dto.PostDTO;
import com.project.blogapp.dto.PostResponse;
import com.project.blogapp.exception.ResourceNotFoundException;
import com.project.blogapp.model.Category;
import com.project.blogapp.model.Post;
import com.project.blogapp.repository.CategoryRepository;
import com.project.blogapp.repository.PostRepository;
import com.project.blogapp.service.PostSevice;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostSevice {

    private PostRepository postRepository;

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDTO createPost(PostDTO createPostDTO) {

        Category category = categoryRepository.findById(createPostDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(createPostDTO.getCategoryId())));


        Post post = mapToEntity(createPostDTO);
        post.setCategory(category);

        Post savedPost = postRepository.save(post);

        PostDTO postDTO = mapToDTO(savedPost);
        return postDTO;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();

        List<PostDTO> postDTOList = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = PostResponse.builder()
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements())
                .isLast(posts.isLast())
                .data(postDTOList)
                .build();

        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        return mapToDTO(post);
    }

    @Override
    public List<PostDTO> getAllPostByCategoryId(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(categoryId)));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDTO updatePost(long id, PostDTO postDTO) {
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(postDTO.getCategoryId())));

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);

        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
        postRepository.deleteById(id);
    }

    // Map Entity to DTO
    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    // Map DTO to Entity
    private Post mapToEntity(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        return post;
    }

}
