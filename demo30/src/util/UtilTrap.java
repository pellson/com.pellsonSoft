package util;

import java.util.*;
public class UtilTrap {
	public static HashMap map = null;
	static
	{
		map = new HashMap();
		map.put("0", "coldStart:��������˳�ʼ��");
		map.put("1", "warmStart:������������³�ʼ��");
		map.put("2","linkDown:һ���ӿڴӹ���״̬��Ϊ����״̬ ");
		map.put("3","linkUp:һ���ӿڴӹ���״̬��Ϊ����״̬");
		map.put("4","authenticationFailure:��SNMP������̽��յ�����һ����Ч��ͬ��ı���");
		map.put("5","egpNeighborLoss:һ��EGP����·������Ϊ����״̬ ");
		map.put("6","enterpriseSpecific:�����Զ�����¼�����Ҫ�ú���ġ��ض����롱��ָ��");
		
	}
	public static String parseTrapContent(String old)
	{
		System.out.println("parseTrapContent:::"+old);
		return (String)map.get(old);
	}

}
