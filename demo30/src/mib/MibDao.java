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
			//此方法利用Adventnet SNMP API类，装载MIB库文件
			mib = mibOps.loadMibModule("RFC1213-MIB.mib");					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	
	//通过IsmName查询OID
	public static String getOID(String mibName) throws Exception {
		return mib.getMibNode(mibName).getNumberedOIDString();
	}
	
	//通过OID查询IsmName
	public static String getIsmName(String oid)  {
		/*//此方法利用Adventnet SNMP API的MibTree等类，装载MIB模型，显示MIB树
		MibOperations mibOps = new MibOperations();
		MibModule mib = mibOps.loadMibModule("ism3602.mib");//进行编译的MIB库*/			
		String name = "";
		String description = "";
		
		try 
		{
			 System.out.println("赵志蓬MibDaoOid:"+oid);
			 name = mib.getMibNode(oid).toString();
			 description= mib.getMibNode(oid).getDefval();
			 System.out.println("赵志蓬MibDaoName:"+description);
		}
		
		catch (Exception e)
		{
			name = "此MID库不能解析的OID";//要装载对应的私有库
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
