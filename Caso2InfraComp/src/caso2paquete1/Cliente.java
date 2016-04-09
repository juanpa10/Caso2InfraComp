package caso2paquete1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import pkgservidor.CoordinadorNS;


public class Cliente 

{
	
	private Transformacion archivoParaTransformar;
	private Socket socketCliente;
	private  PrintWriter salida;
	private BufferedReader in;
	private OutputStream outBytes;
	private Cifrado cifrado;
	private String ipServidor;
	private int puertoServidor;
	private InputStream inBytes;
	private CoordinadorNS servidor;
	
	public Cliente(String ip, int puerto,CoordinadorNS sr)
	{
		servidor=sr;
		ipServidor=ip;
		puertoServidor=puerto;
		try
		{
			socketCliente= new Socket(ipServidor, puertoServidor);
			outBytes=socketCliente.getOutputStream();
			salida=new PrintWriter(outBytes,true);
			inBytes= socketCliente.getInputStream();
			in=new BufferedReader(new InputStreamReader(inBytes));
			System.out.println("Si conecto al puerto");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		
	}

}
