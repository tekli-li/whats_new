package com.example.whats_new.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ViewHistory {
    @NotNull
    private int id;
    @NotNull
    private int articleId;
    @NotNull
    private int userId;
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime viewTime;//创建时间
}
