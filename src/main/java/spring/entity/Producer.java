package spring.entity;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created by roy.zhuo on 2018/6/21.
 */

//@Component
public class Producer {

    @Resource(name = "jtmQueue")
    private JmsTemplate jmsTemplate;

    @Resource(name = "jtmTopic")
    private JmsTemplate jmsTemplate1;

    public void send(String msg){
        System.out.println("目的地址:"+jmsTemplate.getDefaultDestinationName()+" 对象:"+jmsTemplate.getDefaultDestination().toString());
        //发送消息
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring 发送了 队列消息"+msg);
            }
        });
        System.out.println("发送成功了");
    }

    public void sendTopic(String msg){
        System.out.println("目的地址:"+jmsTemplate1.getDefaultDestinationName()+" 对象:"+jmsTemplate1.getDefaultDestination().toString());
        //发送消息
        jmsTemplate1.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring 发送了 主题消息"+msg);
            }
        });
        System.out.println("发送成功了");
    }
}
