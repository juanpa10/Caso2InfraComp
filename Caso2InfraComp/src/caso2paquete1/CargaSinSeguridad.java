package caso2paquete1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;

public class CargaSinSeguridad 
{
	private LoadGenerator generator;
	
	
	
	public CargaSinSeguridad(int numeroDeConcurrentes,int tiempoEntre)
	{
		Csv guard=new Csv(numeroDeConcurrentes,"Sin seguridad Cliente");
		Task work =createTask(numeroDeConcurrentes,guard);
		generator= new LoadGenerator("Prueba cliente servidor", numeroDeConcurrentes, work, tiempoEntre);
		for (int i = 0; i < 10; i++) {
			generator.generate();
		}
		
	}
	
	public Task createTask(int nuUs,Csv aGuardar)
	{
		ClientServerTaskSinSeguridad nuevo=  new ClientServerTaskSinSeguridad(nuUs,aGuardar);
		return nuevo;
	}
	
	
	public static void main(String[] args)
	{
		int con=400;
		int timepo=20;
		
		CargaSinSeguridad ne= new CargaSinSeguridad(con, timepo);
	}

}
