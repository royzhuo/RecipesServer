package mq;

/**
 * Created by roy.zhuo on 2018/6/13.
 */

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消费者
 */
public class ActiveMqCustomer1 {

    public static ConnectionFactory connectionFactory=null;
    public static Connection connection=null;
    public static Session session=null;
    public static Destination destination=null;
    public static MessageConsumer messageConsumer=null;
    public static final String USER="admin";
    public static final String PASSWORLD="admin";
    public static void main(String[] args) {
        System.out.println("username:"+ ActiveMQConnectionFactory.DEFAULT_USER+"  pwd:"+ActiveMQConnectionFactory.DEFAULT_PASSWORD+
                " URL:"+ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);

        //生产者
        //1.创建连接工厂
        createConnectionFactory();
        //2.创建连接
        try {
            createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        //3.创建会话
        try {
            createSession();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        //4.创建消息目标
        try {
            createDestination();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        //5.创建消费者,以及消息持久化
        try {
            createMessageConsumer();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        //6.创建消息体类型和以及发送信息
        try {
            createSendObjAndSend();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        //7.关闭连接
        try {
            closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建connectionfactory连接工厂
     * @return
     */
    public static ConnectionFactory createConnectionFactory(){
        connectionFactory=
                new ActiveMQConnectionFactory(USER,PASSWORLD,ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
        return connectionFactory;
    }

    /*创建connection连接。代表了应用程序和消息服务器之间的通信链路*/
    public static Connection createConnection() throws JMSException {
        connection=connectionFactory.createConnection();
        //连接默认是关闭的，需要手动连接
        connection.start();
        return connection;
    }

    /*创建session上下文 实质上就是发送、接受消息的一个线程，因此生产者、消费者都是Session创建的,MessageProducer/MessageConsumer*/
    public static Session createSession() throws JMSException {
        /*形参:1.是否开启事物  2.签收模式 自动签收*/
        /*
        * 签收？通俗点说，就是消费者接受到消息后，需要告诉消息服务器，我收到消息了。当消息服务器收到回执后，本条消息将失效。因此签收将对PTP模式产生很大影响。如果消费者收到消息后，并不签收，那么本条消息继续有效，很可能会被其他消费者消费掉！
        *
        * AUTO_ACKNOWLEDGE：表示在消费者receive消息的时候自动的签收
          CLIENT_ACKNOWLEDGE：表示消费者receive消息后必须手动的调用acknowledge()方法进行签收
          DUPS_OK_ACKNOWLEDGE：签不签收无所谓了，只要消费者能够容忍重复的消息接受，当然这样会降低Session的开销
        *CLIENT_ACKNOWLEDGE，采用手动的方式较自动的方式可能更好些，因为接收到了消息，并不意味着成功的处理了消息，假设我们采用手动签收的方式，只有在消息成功处理的前提下才进行签收，那么只要消息处理失败，那么消息还有效，仍然会继续消费，直至成功处理！
        * */
        session=connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        return session;
    }

    /*创建消息目标,就是消息发送和接受的地点，要么queue，要么topic*/
    public static Destination createDestination() throws JMSException {
        destination=session.createQueue("queue111");
        return destination;
    }

    /*创建消息消费者*/
    public static MessageConsumer createMessageConsumer() throws JMSException {
        messageConsumer=session.createConsumer(destination);
        /*设置消息持久化
        * 1.持久化:kahdb/leveldb/jdbc方式的话，意味着消息持久话
        * 2.非持久化：消息会在mq重启后消失
        * */
        messageConsumer.receive(DeliveryMode.NON_PERSISTENT);
        return messageConsumer;
    }

    /*
    定义jms规范的类型，这里使用textMessage，由session创建。

    生产者和消费者之间传递的对象，由3个主要部分构成：
    消息头（路由）+消息属性（消息选择器，以后介绍）+消息体（JMS规范的5种类型消息）

    */
    public static  void  createSendObjAndSend() throws JMSException {
        TextMessage textMessage=session.createTextMessage();
        messageConsumer.receiveNoWait();
    }

    /**
     * 释放连接，只有关闭连接，mq才会释放资源
     * @throws JMSException
     */
    public static void closeConnection() throws JMSException {
        if (connection!=null){
            connection.close();
        }
    }
}
