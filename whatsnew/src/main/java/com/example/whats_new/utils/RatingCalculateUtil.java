package com.example.whats_new.utils;

import com.example.whats_new.dao.RecommendMapper;
import com.example.whats_new.pojo.UserArticleRating;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 计算用户id-文章id-评分工具类
 */
@Component
public class RatingCalculateUtil {
    private final Double VIEW_SCORE = 1.0;
    private final Double LIKE_SCORE = 2.0;
    private final Double FAVORITE_SCORE = 3.5;
    private final Double COMMENT_SCORE = 3.5;

    @Autowired
    private RecommendMapper recommendMapper;
    public void refreshRating(String actionType, int userId, int articleId) {
        UserArticleRating rating = recommendMapper.getRatingByUser(userId, articleId);
        if (rating != null) {
            calculate(actionType, rating);
            recommendMapper.updateRating(rating);
        } else {
            rating = new UserArticleRating();
            rating.setUserId(userId);
            rating.setArticleId(articleId);
            calculate(actionType, rating);
            recommendMapper.addRating(rating);
        }
    }

    private void calculate(String actionType, UserArticleRating rating) {
        if (actionType.equals("like")) {
            rating.setRating(rating.getRating() + LIKE_SCORE);
        }
        if (actionType.equals("favorite")) {
            rating.setRating(rating.getRating() + FAVORITE_SCORE);
        }
        if (actionType.equals("view")) {
            rating.setRating(rating.getRating() + VIEW_SCORE);
        }
        if (actionType.equals("comment")) {
            rating.setRating(rating.getRating() + COMMENT_SCORE);
        }
    }
}
