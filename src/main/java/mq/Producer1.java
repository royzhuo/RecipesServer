package mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import java.util.Objects;

/**
 * Created by roy.zhuo on 2018/6/14.
 */


public class Producer1 {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory= new ActiveMQConnectionFactory("user","user",ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
        Connection connection=null;
        try {
            connection=connectionFactory.createConnection();
            connection.start();
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination=session.createQueue("hello activemQ address");
            MessageProducer messageProducer=session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage textMessage=new ActiveMQTextMessage();
            textMessage.setText("hello activeqm qqqqqhhhhh344444666");
            messageProducer.send(textMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (Objects.nonNull(connection)){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
