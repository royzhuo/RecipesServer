import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.service.ConsumerService;

import javax.annotation.Resource;

/**
 * Created by roy.zhuo on 2018/6/22.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:consumer.xml"})
public class TestConsumer {

//    @Resource(name = "consumerService")
//    private ConsumerService consumerService;
//
//    @Test
//    public void read(){
//        System.out.println("开始读了哈........");
//    }


    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("consumer.xml");
    }
}
