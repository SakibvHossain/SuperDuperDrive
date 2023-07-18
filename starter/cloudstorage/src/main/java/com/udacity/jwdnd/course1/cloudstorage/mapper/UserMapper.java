package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    UserModel getUser(String username);

    @Insert("INSERT INTO USERS(firstname, lastname, username, password, salt) VALUES(#{firstname}, #{lastname}, #{username}, #{password}, #{salt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserModel userModel);
}
