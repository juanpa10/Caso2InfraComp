package caso2paquete1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;

public class Carga 
{
	private LoadGenerator generator;
	
	
	
	public Carga(int numeroDeConcurrentes,int tiempoEntre)
	{
		Csv guard=new Csv(numeroDeConcurrentes,"Cliente");
		Task work =createTask(numeroDeConcurrentes,guard);
		generator= new LoadGenerator("Prueba cliente servidor", numeroDeConcurrentes, work, tiempoEntre);
		generator.generate();
	}
	
	public Task createTask(int nuUs,Csv aGuardar)
	{
		ClientServerTask nuevo=  new ClientServerTask(nuUs,aGuardar);
		return nuevo;
	}
	
	
	public static void main(String[] args)
	{
		int con=10;
		int timepo=1000;
		
		Carga ne= new Carga(con, timepo);
	}

}
