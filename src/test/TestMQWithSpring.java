import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.entity.Producer;
import spring.service.ConsumerService;
import spring.service.SubService;

import javax.annotation.Resource;

/**
 * Created by roy.zhuo on 2018/6/21.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:consumer.xml","classpath:producer.xml"})
public class TestMQWithSpring {

    @Autowired
    private Producer producer;

    @Resource(name = "consumerService")
    private ConsumerService consumerService;

    @Resource(name = "subService")
    private SubService subService;

    @Test
    public void sendForQueue(){

        for (int i=0;i<100;i++){
            producer.send(String.valueOf(i));
        }
    }

    @Test
    public void sendForTopic(){
        for (int i=0;i<100;i++){
            producer.sendTopic(String.valueOf(i));
        }
    }

    @Test
    public void testConsumerMessage(){

    }

}
