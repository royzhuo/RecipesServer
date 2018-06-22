package spring.serviceImpl;

import org.springframework.stereotype.Service;
import spring.service.ConsumerService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Created by roy.zhuo on 2018/6/21.
 */

@Service("consumerService")
public class ConsumerServiceImpl implements ConsumerService {
    @Override
    public void onMessage(Message message) {
        //instanceof 二元运算符，作用是判断左边对象是否等于右边类的实力 ，返回ture、false
        if (message instanceof TextMessage){
            TextMessage textMessage=(TextMessage) message;
            try {
                String text= textMessage.getText();
                System.out.println("消费者消费了 消费队列的消息："+text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
