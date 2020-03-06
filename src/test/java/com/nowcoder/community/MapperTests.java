package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public  void testSelectUser(){
        User user = userMapper.selectById(101);
        //System.out.println(user);

        User liubei = userMapper.selectByName("liubei");
        System.out.println(liubei);
    }

    @Test
    public  void  testselectposts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(149, 0, 10);
        for(DiscussPost posts : list){
            System.out.println(posts);
        }

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(101);
        ticket.setTicket("abc");
        ticket.setStatus(0);
        ticket.setExpired(new Date(System.currentTimeMillis() +1000 * 60 *10));

        loginTicketMapper.insertLoginTicket(ticket);
    }

    @Test
    public void TestSelectLoginTicket(){
        LoginTicket ticket = loginTicketMapper.selectByTicket("abc");
        System.out.println(ticket);

        loginTicketMapper.updateStatus("abc",1);
        LoginTicket abc = loginTicketMapper.selectByTicket("abc");
        System.out.println(abc);
    }

}
