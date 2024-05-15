package com.example.whats_new.utils;

import com.example.whats_new.dao.UserMapper;
import com.example.whats_new.pojo.User;
import com.example.whats_new.pojo.UserArticleRating;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Recommend {
    @Autowired
    private UserMapper userMapper;

    public Map<Integer, Double> computeNearestNeighbor(Integer userId, List<User> users) {
        Map<Integer, Double> distances = new TreeMap<>();
        User u1 = userMapper.findUserById(userId);
        List<UserArticleRating> u1Rating = userMapper.getUserArticleRating(userId);
        for (User u2 : users) {
            if (!Objects.equals(u2.getId(), userId)) {
                List<UserArticleRating> u2Rating = userMapper.getUserArticleRating(u2.getId());
                double distance = pearson_dis(u2Rating, u1Rating);
                distances.put(u2.getId(), distance);
            }
        }

        TreeMap<Integer, Double> sortedDistances = new TreeMap<>(Comparator.reverseOrder());
        sortedDistances.putAll(distances);
        System.out.println("该用户与其他用户的皮尔森相关系数 -> " + sortedDistances);
        return sortedDistances;
    }


    private double pearson_dis(List<UserArticleRating> rating2, List<UserArticleRating> rating1) {
        double correlation = 0.0;
        List<Double> rating1ScoreCollect = new ArrayList<>();
        List<Double> rating2ScoreCollect = new ArrayList<>();
        List<Map<Integer, Double>> intersection = getIntersection(rating2, rating1);
        for (Integer keys : intersection.get(0).keySet()) {
            rating2ScoreCollect.add(intersection.get(0).get(keys));
        }
        for (Integer keys : intersection.get(1).keySet()) {
            rating1ScoreCollect.add(intersection.get(1).get(keys));
        }
//        System.out.println(Arrays.toString(rating1ScoreCollect.stream().mapToDouble(Double::doubleValue).toArray()));
//        System.out.println(Arrays.toString(rating2ScoreCollect.stream().mapToDouble(Double::doubleValue).toArray()));
        double[] x = rating1ScoreCollect.stream().mapToDouble(Double::doubleValue).toArray();
        double[] y = rating2ScoreCollect.stream().mapToDouble(Double::doubleValue).toArray();
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        if (x.length > 1 && y.length > 1) {
            correlation = pearsonsCorrelation.correlation(x, y);
        }
        return correlation;
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
                    .filter(r -> Objects.equals(r.getArticleId(), id))
                    .map(UserArticleRating::getRating)
                    .findFirst()
                    .orElse(0.0)); // 如果没有对应的rating，默认为0.0
            ratingMap2.put(id, rating2.stream()
                    .filter(r -> Objects.equals(r.getArticleId(), id))
                    .map(UserArticleRating::getRating)
                    .findFirst()
                    .orElse(0.0)); // 如果没有对应的rating，默认为0.0
        }
        result.add(ratingMap2);
        result.add(ratingMap1);
        return result;
    }

}
