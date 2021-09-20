package com.example.demo1;
import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.User;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class Demo1Application {

    @Autowired
    static JdbcTemplate jdbcTemplate;
    public static void main(String[] args) throws Exception {

        User user1 = new User();
        user1.setId(3);
        user1.setUsername("asd");
        user1.setUsername("lpl");
        //user1.toString();
        User user2 = new User();
        //UserDao userDao=new UserDao();
        //user2 = userDao.getByPhone("15361022832");
        System.out.print( user2.toString());;
//        TestInitlizationBean o=new TestInitlizationBean();
//        o.afterPropertiesSet();
;
        SpringApplication.run(Demo1Application.class, args);
//        String username="15361022832";
//        String sql = "select * from m_user where username = ?";
//        User o= jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet resultSet, int i) throws SQLException {
//                User user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setUsername(resultSet.getString("username"));
//                user.setPassword(resultSet.getString("password"));
//                return user;
//            }
//        }, username);
//        System.out.print( o.toString());

    }


}



