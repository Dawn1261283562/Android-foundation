package com.example.demo1;

import com.example.demo1.entity.FundHeavy;
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
        me=user;
        //me.setUsername("改个名");

        System.out.println("我也可以初始化，选一个就可以啦"+me.getUsername());
        //where id  =?or id =?or id =?or id =?
        String sql1 = "select * from m_fund_heavy ";
        //select * from d_menu where name like concat('%',?,'%')or id =?,s2or id =?
        String s1="000001.OF";String s2="000309.OF";String s3="000513.OF";String s4="000893.OF";
        List<FundHeavy> fundHeavy1 = jdbcTemplate.query(sql1, new RowMapper<FundHeavy>() {
            @Override
            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                FundHeavy fundHeavy = new FundHeavy();
                fundHeavy.setId(resultSet.getString("id"));

                //User user = new User(id, name, psw);
                return fundHeavy;
            }
        });//,s1,s2,s3,s4

        System.out.println("我也可以初始化，选一个就可以啦hhhsssss"+fundHeavy1);
    }
}
