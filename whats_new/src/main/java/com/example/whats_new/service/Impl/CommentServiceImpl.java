package com.example.whats_new.service.Impl;

import com.example.whats_new.dao.CommentMapper;
import com.example.whats_new.pojo.Comment;
import com.example.whats_new.pojo.CommentTree;
import com.example.whats_new.service.CommentService;
import com.example.whats_new.utils.RatingCalculateUtil;
import com.example.whats_new.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RatingCalculateUtil ratingCalculateUtil;
    @Override
    public List<CommentTree> getCommentsByArticle(Integer articleId) {
        List<Comment> comments = commentMapper.getCommentsByArticle(articleId);
        List<CommentTree> result = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getRoot() == 0) {
                CommentTree commentTree = new CommentTree();
                commentTree.setComment(comment);
                List<Comment> subComments = commentMapper.getSubComments(comment.getId());
                if (!subComments.isEmpty()) {
                    commentTree.setSubComments(subComments);
                }
                result.add(commentTree);
            }
        }
        return result;
    }

    @Override
    public void addArticleComment(Comment comment) {
        //获取用户id
        Map<String , Object> claims = ThreadLocalUtil.get();
        String userId = String.valueOf(claims.get("id"));
        String username = (String) claims.get("username");
        comment.setUsername(username);
        comment.setUserId(Integer.parseInt(userId));
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        System.out.println(comment);
        ratingCalculateUtil.refreshRating("comment", Integer.parseInt(userId), comment.getArticleId());
        commentMapper.addArticleComment(comment);
    }

}
