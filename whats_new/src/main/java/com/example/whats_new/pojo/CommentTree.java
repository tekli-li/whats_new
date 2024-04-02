package com.example.whats_new.pojo;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

@Data
public class CommentTree {
    Comment comment;
    List<Comment> subComments;
}
