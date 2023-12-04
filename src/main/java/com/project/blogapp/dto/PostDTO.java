package com.project.blogapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.blogapp.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "PostDTO information"
)
public class PostDTO {
    private Long id;


    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post title should at least be 2 characters long")
    private String title;


    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post description should at least be 10 characters long")
    private String description;


    @Schema(
            description = "Blog Post Content"
    )
    @NotEmpty
    private String content;


    @Schema(
            description = "Blog Post CategoryId"
    )
    @NotNull
    private Long categoryId;

    private Set<Comment> comments;
}
