package mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import java.util.Objects;

/**
 * Created by roy.zhuo on 2018/6/14.
 */

//生产者
public class Producer1 {

    public static void main(String[] args) {
        //1.创建连接工厂
        ConnectionFactory connectionFactory= new ActiveMQConnectionFactory("user","user",ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
        Connection connection=null;
        try {
            //2..创建连接
            connection=connectionFactory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建会话 会话用于创建生产者 消费者 目的地。 不设置事物 ，自动签收
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //5.创建目的地 目的地有两种模式 队列 和主题
            /*
            * 队列：有多个消费者连接队列，队列的消息会平摊给各个消费者
            * 主题: 主题就是消息的发布和订阅  生产者就是发布消息   消费者订阅消息   消费发布上去会全部推送给各个订阅者
            *        订阅者如果在消息发布之后订阅，是没有办法接受到消息的。
            * */
            Destination destination=session.createQueue("TestQueue");
            //6.创建生产者
            MessageProducer messageProducer=session.createProducer(destination);
            //7.不设置持久话
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            for(int i=0;i<100;i++){
                //8.创建消息体
                TextMessage textMessage=new ActiveMQTextMessage();
                textMessage.setText("come on queue "+i);
                //9.发送消息
                messageProducer.send(textMessage);
                System.out.println("发送了: come on queue "+i);
            }
            System.out.println("发送成功了");


        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            //10.关闭连接
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
