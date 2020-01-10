package com.tuacy.uid;

import com.tuacy.uid.impl.UidGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @name: UidTest
 * @author: tuacy.
 * @date: 2020/1/10.
 * @version: 1.0
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UidTest {

    private UidGenerator uidGenerator;

    @Autowired
    public void setUidGenerator(UidGenerator uidGenerator) {
        this.uidGenerator = uidGenerator;
    }

    @Test
    public void test() {
        long uid = uidGenerator.getUID();
        System.out.println("uid = " + uid);
    }

}
