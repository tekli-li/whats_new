package com.example.whats_new.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    @NotNull
    private int id;
    @NotEmpty
    private String content;
    @NotNull
    private int articleId;
    @NotNull
    private int userId;
    private int root;
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
}
