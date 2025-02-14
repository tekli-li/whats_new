package com.example.whats_new.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotEmpty(groups = {Update.class, Add.class})
    private String categoryName;//分类名称
    @NotEmpty(groups = {Update.class, Add.class})
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    public interface Add {

    }

    public interface Update {

    }
}
