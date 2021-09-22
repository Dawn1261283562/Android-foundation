package com.example.demo1;
import com.example.demo1.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class Demo1Application {

    @Autowired
    static JdbcTemplate jdbcTemplate;
    public static void main(String[] args) throws Exception {

        String beanXml = "D:/fund/codeOfDemo1/springJava/src/main/java/com/example/demo1/bean.xml";
//"com/example/demo1/bean.xml"
        //2.创建ClassPathXmlApplicationContext容器，给容器指定需要加载的bean配置文件
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
//        HelloWorld helloWorld = context.getBean("helloWorld", HelloWorld.class);
//
//        //4.使用对象
//        helloWorld.say();

        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        //User userk = context.getBean("User_1", User.class);

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

//        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext("bean.xml");
//        User me = context1.getBean("User_1", User.class);
//        System.out.print( me.getUsername()+"asdasdasdasdasdasdasd");

    }


}



