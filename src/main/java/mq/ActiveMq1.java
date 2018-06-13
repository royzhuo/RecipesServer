package mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by roy.zhuo on 2018/6/13.
 */


public class ActiveMq1 {

    public static ConnectionFactory connectionFactory=null;
    public static Connection connection=null;
    public static Session session=null;
    public static Destination destination=null;
    public static MessageProducer messageProducer=null;

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
        //5.创建生产者,以及消息持久化
        try {
            createMessageProducer();
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
                new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
        return connectionFactory;
    }

    /*创建connection连接。代表了应用程序和消息服务器之间的通信链路*/
    public static Connection createConnection() throws JMSException {
        connection=connectionFactory.createConnection();

        return connection;
    }

    /*创建session上下文 实质上就是发送、接受消息的一个线程，因此生产者、消费者都是Session创建的,MessageProducer/MessageConsumer*/
    public static Session createSession() throws JMSException {
        /*形参:1.是否开启事物  2.签收模式 自动签收*/
        session=connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        return session;
    }

    /*创建消息目标,就是消息发送和接受的地点，要么queue，要么topic*/
    public static Destination createDestination() throws JMSException {
        destination=session.createQueue("queue111");
        return destination;
    }

    /*创建消息生产者*/
    public static MessageProducer createMessageProducer() throws JMSException {
        messageProducer=session.createProducer(destination);
        /*设置消息持久化
        * 1.持久化:kahdb/leveldb/jdbc方式的话，意味着消息持久话
        * 2.非持久化：消息会在mq重启后消失
        * */
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        return messageProducer;
    }

    /*
    定义jms规范的类型，这里使用textMessage，由session创建。

    生产者和消费者之间传递的对象，由3个主要部分构成：
    消息头（路由）+消息属性（消息选择器，以后介绍）+消息体（JMS规范的5种类型消息）

    */
    public static  void  createSendObjAndSend() throws JMSException {
        TextMessage textMessage=session.createTextMessage();
        textMessage.setText("hello,activeMq!!!!");
        messageProducer.send(textMessage);
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
