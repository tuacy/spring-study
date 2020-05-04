package com.tuacy.collect.mybatis;

import com.tuacy.collect.mybatis.entity.pojo.User;
import com.tuacy.collect.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/5/1 22:09
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyBatisTest {

    private SqlSession sqlSession;

    /**
     * 在测试开始前初始化工作
     */
    @Before
    public void setup() throws Exception {
        /**
         * 从 XML 中构建 SqlSessionFactory
         */
        // mybatis-config.xml
        String resource = "mybatis-config.xml";
        // 读取配置文件
        InputStream is = Resources.getResourceAsStream(resource);
        // 构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        // 从 SqlSessionFactory 中获取 SqlSession
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void listUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.listUser();
        User user = mapper.getUserByName("wuyx");
        System.out.println("aaaa");
    }

}
