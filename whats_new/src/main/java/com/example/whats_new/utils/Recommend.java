package com.example.whats_new.utils;

import com.example.whats_new.dao.UserMapper;
import com.example.whats_new.pojo.User;
import com.example.whats_new.pojo.UserArticleRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Recommend {
    @Autowired
    private UserMapper userMapper;
    private Map<Double, Integer> computeNearestNeighbor(Integer userId, List<User> users) {
        Map<Double, Integer> distances = new TreeMap<>();
        User u1 = userMapper.findUserById(userId);
        List<UserArticleRating> u1Rating = userMapper.getUserArticleRating(userId);
        for (int i = 0; i < users.size(); i++) {
            User u2 = users.get(i);
            if (!Objects.equals(u2.getId(), userId)) {
                List<UserArticleRating> u2Rating = userMapper.getUserArticleRating(u2.getId());
                double distance = pearson_dis(u2Rating, u1Rating);
                distances.put(distance, u2.getId());
            }

        }
        System.out.println("该用户与其他用户的皮尔森相关系数 -> " + distances);
        return distances;
    }

    private double pearson_dis(List<UserArticleRating> rating2, List<UserArticleRating> rating1) {
        int size = rating1.size();
        List<Map<Integer, Double>> intersection = getIntersection(rating2, rating1);
        List<Double> rating1ScoreCollect = rating1.stream().map(UserArticleRating::getRating).toList();
        List<Double> rating2ScoreCollect = rating2.stream().map(UserArticleRating::getRating).toList();
        return 0.0;
    }

    private List<Map<Integer, Double>> getIntersection(List<UserArticleRating> rating2, List<UserArticleRating> rating1) {
        List<Map<Integer, Double>> result = new ArrayList<>();
        // 提取出rating1和rating2中的articleId
        List<Integer> articleIds1 = rating1.stream()
                .map(UserArticleRating::getArticleId)
                .collect(Collectors.toList());
        List<Integer> articleIds2 = rating2.stream()
                .map(UserArticleRating::getArticleId)
                .collect(Collectors.toList());

        // 计算articleId的交集
        List<Integer> intersection = articleIds1.stream()
                .filter(articleIds2::contains)
                .collect(Collectors.toList());

        // 创建LinkedHashMap来保持键的顺序
        Map<Integer, Double> ratingMap1 = new LinkedHashMap<>();
        Map<Integer, Double> ratingMap2 = new LinkedHashMap<>();

        // 提取出交集中articleId对应的rating，并保持顺序
        for (Integer id : intersection) {
            ratingMap1.put(id, rating1.stream()
                    .filter(r -> r.getArticleId() == id)
                    .map(UserArticleRating::getRating)
                    .findFirst()
                    .orElse(0.0)); // 如果没有对应的rating，默认为0.0
            ratingMap2.put(id, rating2.stream()
                    .filter(r -> r.getArticleId() == id)
                    .map(UserArticleRating::getRating)
                    .findFirst()
                    .orElse(0.0)); // 如果没有对应的rating，默认为0.0
        }
        result.add(ratingMap2);
        result.add(ratingMap1);
        return result;
    }

}
