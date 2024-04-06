package com.example.whats_new.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;

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
