/**
 * 
 */
/**
 * @author 王中秋
 一、AMQP协议是一个高级抽象层消息通信协议，RabbitMQ是AMQP协议的实现
 二、详细说明
1.Server(broker): 接受客户端连接，实现AMQP消息队列和路由功能的进程。
2.Virtual Host:其实是一个虚拟概念，类似于权限控制组，一个Virtual Host里面可以有若干个Exchange和Queue，但是权限控制的最小粒度是Virtual Host
3.Exchange:接受生产者发送的消息，并根据Binding规则将消息路由给服务器中的队列。
4.Message Queue：消息队列，用于存储还未被消费者消费的消息。
5.Message:Header是由生产者添加的各种属性的集合，包括Message是否被持久化、由哪个Message Queue接受、优先级是多少等。Body是真正需要传输的APP数据。
6.Binding:Binding联系了Exchange与Message Queue。
7.Connection:连接，对于RabbitMQ而言，其实就是一个位于客户端和Broker之间的TCP连接。
8.Channel:信道，仅仅创建了客户端到Broker之间的连接后，客户端还是不能发送消息的。
需要为每一个Connection创建Channel，AMQP协议规定只有通过Channel才能执行AMQP的命令。
一个Connection可以包含多个Channel。之所以需要Channel，是因为TCP连接的建立和释放都是十分昂贵的，
如果一个客户端每一个线程都需要与Broker交互，如果每一个线程都建立一个TCP连接，暂且不考虑TCP连接是否浪费，就算操作系统也无法承受每秒建立如此多的TCP连接。
RabbitMQ建议客户端线程之间不要共用Channel，至少要保证共用Channel的线程发送消息必须是串行的，但是建议尽量共用Connection。
9.Command:AMQP的命令，客户端通过Command完成与AMQP服务器的交互来实现自身的逻辑。
三、一对多场景注意事项
1、公平调度。channel.basicQos(1)。每个消费者只能同时处理一个消息
2、持久化。boolean durable = true;channel.queueDeclare(channelName, durable, false, false, null);
3、防止接收端在处理消息时down掉，只有在消息处理完成后才发送ack消息。
  //取消 autoAck  
  boolean autoAck = false ;
  channel.basicConsume(channelName, autoAck, consumer) ; 
  //确认消息，已经收到  
  channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
四、发布订阅场景
 */
package zhongqiu.rabbitmq_demo;