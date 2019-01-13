package cn.lilyhuli.mapper;

import cn.lilyhuli.bean.MyUserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MyUserMapper {
    @Select("select * from user where username = #{username}")
    MyUserBean selectByUsername(@Param("username") String username);
}
