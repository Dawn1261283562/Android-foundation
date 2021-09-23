package com.example.demo1;

import com.example.demo1.entity.FundHeavy;
//import com.example.demo1.entity.Instance;
import com.example.demo1.entity.User;
//import com.springboot.demo.model.dao.Test;
//import com.springboot.demo.service.InitTestDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/*
    //////////////////////////注意： 这里是spring的初始化配置，我自己也在调研 ，可不看///////////////////////////////////
 */
@Configuration
public class InitTestDataConfig implements InitializingBean, ServletContextAware {
    /**
     * 日志
     */
    private static final Logger logger= LoggerFactory.getLogger(InitTestDataConfig.class);

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
    User me = context.getBean("User_1", User.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    User user_1;



    //public static Map<String, Test> initTestData = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("我也可以初始化，选一个就可以啦(测试)");
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
//        List<Test> datas = initTestDataService.queryTestDataAll();
//        if (datas != null && !datas.isEmpty()) {
//            for (Test data : datas)
//                initTestData.put(data.getKey(), data);
//            logger.info("***initTestData initialization successful***");
//        } else {
//            logger.info("***test DB is empty, please configure the corresponding data***");
//        }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        User user_1 = context.getBean("User_1", User.class);

        //4.使用对象
        //helloWorld.say();


        System.out.println("我也可以初始化，选一个就可以啦");
        String sql = "select * from m_user where username = ?";
        String username="15361022832";
        User user= this.jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }, username);
        user_1.setUsername(user.getUsername());
        System.out.println("user1"+user_1.getUsername());
        //me.setUsername("改个名");

        System.out.println("我也可以初始化，选一个就可以啦"+user_1.getUsername());
        //where id  =?or id =?or id =?or id =?
        String sql1 = "select * from m_fund_heavy ";
        //以下sql like 技巧
        //select * from d_menu where name like concat('%',?,'%')or id =?,s2or id =?

        //测试string
        //String s1="000001.OF";String s2="000309.OF";String s3="000513.OF";String s4="000893.OF";

        List<FundHeavy> fundHeavy1 = jdbcTemplate.query(sql1, new RowMapper<FundHeavy>() {
            @Override
            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                FundHeavy fundHeavy = new FundHeavy();
                fundHeavy.setId(resultSet.getString("id"));

                return fundHeavy;
            }
        });//,s1,s2,s3,s4

    }
}
