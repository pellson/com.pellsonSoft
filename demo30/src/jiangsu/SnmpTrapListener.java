package jiangsu;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

/**
 * SNMPTrap告警监听�?
 * @author tianshuangren
 */
public class SnmpTrapListener implements Runnable {
	private static Logger log = Logger.getLogger(SnmpTrapListener.class);
	private int poolSize;
	private String listenAddress;
	private ActiveMQSender activeMqSender;
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	public void setListenAddress(String listenAddress) {
		this.listenAddress = listenAddress;
	}

	/**
	 * 监听器初始化方法，调用其启动监听线程
	 */
	public void init() {
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void run() {
		log.info("[SnmpTrapListener] Snmptrap listener init start.");
		ThreadPool threadPool = ThreadPool.create("Trap ", poolSize);
		Address address = GenericAddress.parse(listenAddress);
        TransportMapping<?> transport = null;
        try {
	        if(address  instanceof  UdpAddress){  
	            transport = new DefaultUdpTransportMapping((UdpAddress)address);  
	        } else {  
	            transport = new DefaultTcpTransportMapping((TcpAddress)address);  
	        }
	        MultiThreadedMessageDispatcher dispatcher = new  MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl()); 
	        Snmp snmp = new Snmp(dispatcher, transport);  
	        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());  
	        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());   
			snmp.listen();
			SnmpTrapCommandResponder responder = new SnmpTrapCommandResponder();
			responder.setActiveMqSender(activeMqSender);
			snmp.addCommandResponder(responder);
			log.info("[SnmpTrapListener] Snmptrap listener init successful.");
		} catch (IOException e) {
			log.error("Snmptrap listener init error, error message: " + e.getMessage());
		}
	}
	public void setActiveMqSender(ActiveMQSender activeMqSender) {
		this.activeMqSender = activeMqSender;
	}
}
