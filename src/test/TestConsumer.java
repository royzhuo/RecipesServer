import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.service.ConsumerService;

import javax.annotation.Resource;

/**
 * Created by roy.zhuo on 2018/6/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:consumer.xml","classpath:producer.xml"})
public class TestConsumer {

    @Resource(name = "consumerService")
    private ConsumerService consumerService;

    @Test
    public void testReadCommint(){
    }
}
