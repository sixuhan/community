package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    //userid查询个人主页时使用
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);


    //当userid为0 的时候代表不查询
    //@Param给参数取别名,如果只有一个参数,并且再<if>里使用,泽必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);



}
