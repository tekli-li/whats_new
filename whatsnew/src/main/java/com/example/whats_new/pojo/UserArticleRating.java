package com.example.whats_new.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class UserArticleRating {
    @NotNull
    private Integer id;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer articleId;
    @NotNull
    private Double rating;

    public UserArticleRating() {
        this.rating = 0.0;
    }
}
