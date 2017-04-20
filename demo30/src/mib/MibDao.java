package mib;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.adventnet.snmp.mibs.MibException;
import com.adventnet.snmp.mibs.MibModule;
import com.adventnet.snmp.mibs.MibOperations;
import com.adventnet.snmp.ui.MibTree;

public class MibDao {
	public static MibOperations mibOps;
	private static MibModule mib;
	
	static {
		mibOps = new MibOperations();
		 try {
			//�˷�������Adventnet SNMP API�࣬װ��MIB���ļ�
			mib = mibOps.loadMibModule("RFC1213-MIB.mib");					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	
	//ͨ��IsmName��ѯOID
	public static String getOID(String mibName) throws Exception {
		return mib.getMibNode(mibName).getNumberedOIDString();
	}
	
	//ͨ��OID��ѯIsmName
	public static String getIsmName(String oid)  {
		/*//�˷�������Adventnet SNMP API��MibTree���࣬װ��MIBģ�ͣ���ʾMIB��
		MibOperations mibOps = new MibOperations();
		MibModule mib = mibOps.loadMibModule("ism3602.mib");//���б����MIB��*/			
		String name = "";
		String description = "";
		
		try 
		{
			 System.out.println("��־��MibDaoOid:"+oid);
			 name = mib.getMibNode(oid).toString();
			 description= mib.getMibNode(oid).getDefval();
			 System.out.println("��־��MibDaoName:"+description);
		}
		
		catch (Exception e)
		{
			name = "��MID�ⲻ�ܽ�����OID";//Ҫװ�ض�Ӧ��˽�п�
		}
		return name;
	}
	
	public static void main(String args[])
	{
		try
		{
		  System.out.println(MibDao.getIsmName(".1.3.6.1.2.1.2.2.1.1.67"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
