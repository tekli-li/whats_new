package com.example.whats_new.dao;

import com.example.whats_new.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    public User findUserByUserName(String username);
    @Insert("insert into user(username, password, create_time, update_time, salt) VALUES (#{username},#{password},now(),now(),#{salt})")
    public void register(String username, String password, Integer salt);
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime},user_pic=#{userPic} where id=#{id}")
    void update(User user);
    @Update("update user set password=#{newPwd},update_time=now() where id=#{id}")
    void updatePwd(Integer id, String newPwd);
    @Update("update user set user_pic=#{fileUrl},update_time=now() where id=#{id}")
    void uploadAvatar(String fileUrl, Integer id);
    @Update("update user set user_pic=#{url},update_time=now() where id=#{id}")
    void updateAvatar(String url, Integer id);
}
