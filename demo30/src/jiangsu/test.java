package jiangsu;

public class test {
	/**
	* @author 赵志蓬 E-mail:13732904468@139.com
	* @version 创建时间：2017年4月20日 下午2:48:25
	* @Description：
	* 
	*/
	
	public static void main(String[] args) {
		SnmpTrapListener trapListener = new SnmpTrapListener();
		trapListener.setPoolSize(5);
		trapListener.setListenAddress("192.168.1.51/162");
		trapListener.init();
	}
}
