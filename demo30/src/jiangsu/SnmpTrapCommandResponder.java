/**
 * 2013-11-14下午02:38:24
 */
package jiangsu;

import org.apache.log4j.Logger;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;

/**
 * CommandResponder
 * 
 * @author tianshuangren
 */
public class SnmpTrapCommandResponder implements CommandResponder {
	private static final Logger logger = Logger.getLogger(SnmpTrapCommandResponder.class);

	private ActiveMQSender activeMqSender;
	@Override
	public void processPdu(CommandResponderEvent event) {
		if (event == null || event.getPDU() == null) {
			return;
		}
		logger.info("[SnmpTrapCommandResponder] Receive snmptrap message from " + event.getPeerAddress());
		logger.info("[SnmpTrapCommandResponder] Send snmp trap message to active mq.");
		//activeMqSender.sendMessage(event);
		logger.info("[SnmpTrapCommandResponder]"+event);
	}
	/*public void setActiveMqSender(ActiveMQSender activeMqSender) {
		this.activeMqSender = activeMqSender;
	}*/
}
