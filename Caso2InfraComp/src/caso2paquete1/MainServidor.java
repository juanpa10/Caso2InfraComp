package caso2paquete1;

import pkgservidor.CoordinadorNS;

public class MainServidor 
{
	
	public static void main(String[] args)
	{
		String[] nuevo=new String[0] ;
		CoordinadorNS servidor= new CoordinadorNS();
		try {
			servidor.main(nuevo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
