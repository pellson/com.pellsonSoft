package jiangsu;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.snmp4j.CommandResponderEvent;

/**
 * 消息发送端
 * 
 * @author tianshuangren
 */
public class ActiveMQSender {
	private static final Logger logger = Logger.getLogger(ActiveMQSender.class);
	/**
	 * 连接工厂，JMS 用它创建连接
	 */
	private ConnectionFactory connectionFactory;
	/**
	 * ActiveMQ用户名
	 */
	private String activeMqUser;
	/**
	 * ActiveMQ密码
	 */
	private String activeMqPwd;
	/**
	 * ActiveMQ URL
	 */
	private String activeMqUrl;
	/**
	 * ActiveMQ队列
	 */
	private String activeMqQueue;
	
	public void sendMessage(CommandResponderEvent event) {
		Connection connection = null;
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作线程
			Session session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取消息接收地址
			Destination destination = session.createQueue(activeMqQueue);
			// 得到消息发送者
			MessageProducer producer = session.createProducer(destination);
			// 持久化
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            // 发送消息到目的地方
            ObjectMessage message = session.createObjectMessage();
            message.setObject(event);
            producer.send(message);
			
			session.commit();
		} catch (Exception e) {
			logger.error("[ActiveMQSender] Send trap message to activemq failed, error message: " + e.getMessage());
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {
			}
		}
	}

	public void init() {
		// 构造ConnectionFactory实例对象
		connectionFactory = new ActiveMQConnectionFactory(activeMqUser, activeMqPwd, activeMqUrl);
	}

	public void setActiveMqUser(String activeMqUser) {
		this.activeMqUser = activeMqUser;
	}
	public void setActiveMqPwd(String activeMqPwd) {
		this.activeMqPwd = activeMqPwd;
	}
	public void setActiveMqUrl(String activeMqUrl) {
		this.activeMqUrl = activeMqUrl;
	}
	public void setActiveMqQueue(String activeMqQueue) {
		this.activeMqQueue = activeMqQueue;
	}
}
