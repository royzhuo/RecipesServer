import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.entity.Producer;

/**
 * Created by roy.zhuo on 2018/6/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:producer.xml")
public class TestProducer {

    @Autowired
    private Producer producer;

    @Test
    public void send(){
        for (int i=0;i<100;i++){
            producer.send(String.valueOf(i));
        }
    }
}
