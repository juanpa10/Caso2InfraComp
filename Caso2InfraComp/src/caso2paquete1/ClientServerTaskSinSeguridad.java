package caso2paquete1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import uniandes.gload.core.Task;

public class ClientServerTaskSinSeguridad extends Task
{
	private int numUsAct;
	private Csv arepr;
	
	
	private int cant=0;
	public ClientServerTaskSinSeguridad(int numUs,Csv arepor)
	{
		arepr=arepor;
		numUsAct=numUs;
	}
		
	@Override
	public void fail() 
	{
		
		System.out.println(Task.MENSAJE_FAIL);

	}

	@Override
	public void success()
	{
		// TODO Auto-generated method stub
		System.out.println(Task.OK_MESSAGE);
		
	}

	@Override
	public void execute()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SinSeguridadCliente cliente= new SinSeguridadCliente("localhost", 4567, br,cant++,numUsAct,arepr);
		try {
			cliente.inicarComunicacion("Holi");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
