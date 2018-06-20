package mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 订阅者
 * Created by roy.zhuo on 2018/6/20.
 */
public class TopicSub {

    public static void main(String[] args) {
        //1.创建连接工厂
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("user","user",ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
        Connection connection=null;
        try {
            //2..创建连接
            connection=connectionFactory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建会话 会话用于创建生产者 消费者 目的地。 不设置事物 ，自动签收
            /*
        * 签收？通俗点说，就是消费者接受到消息后，需要告诉消息服务器，我收到消息了。当消息服务器收到回执后，本条消息将失效。因此签收将对PTP模式产生很大影响。
        * 如果消费者收到消息后，并不签收，那么本条消息继续有效，很可能会被其他消费者消费掉！
        *
        * AUTO_ACKNOWLEDGE：表示在消费者receive消息的时候自动的签收
          CLIENT_ACKNOWLEDGE：表示消费者receive消息后必须手动的调用acknowledge()方法进行签收
          DUPS_OK_ACKNOWLEDGE：签不签收无所谓了，只要消费者能够容忍重复的消息接受，当然这样会降低Session的开销
        *CLIENT_ACKNOWLEDGE，采用手动的方式较自动的方式可能更好些，因为接收到了消息，并不意味着成功的处理了消息，假设我们采用手动签收的方式，只有在消息成功处理的前提下才进行签收，那么只要消息处理失败，
        * 那么消息还有效，仍然会继续消费，直至成功处理！
        * */
            Session session=connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            //5.创建目的地
            Destination destination=session.createTopic("TestTopic");
            //6.创建消费者
            MessageConsumer messageConsumer=session.createConsumer(destination);
            //7.创建监听
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    //message 接收到的信息
                    TextMessage textMessage=(TextMessage) message;
                    try {
                        System.out.println("订阅到的消息:"+textMessage.getText());
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
