package mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Objects;

/**
 * Created by roy.zhuo on 2018/6/14.
 */


public class Customer {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("user","user",ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
        Connection connection=null;
        try {
            connection=connectionFactory.createConnection();
            connection.start();
            Session session=connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            Destination destination=session.createQueue("hello activemQ address");
            MessageConsumer messageConsumer=session.createConsumer(destination);
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    //message 接收到的信息
                    TextMessage textMessage=(TextMessage) message;
                    try {
                        System.out.println("消费者接收到的消息:"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
/*            if (Objects.nonNull(connection)){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
}