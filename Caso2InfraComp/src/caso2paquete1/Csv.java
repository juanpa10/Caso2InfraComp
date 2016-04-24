package caso2paquete1;

import java.io.FileWriter;
import java.io.IOException;

public class Csv
{
	private FileWriter writer;
	private String ruta;
	public Csv(int conCurrentes,String num)
	{
		 ruta= "./data/"+num+System.currentTimeMillis()+" "+conCurrentes+".csv";
		crearEncabezado();
	}
	public synchronized void escribir(long timepoAutentic,long tiempoActu,boolean perdidad,double uso,String num)
	
	{
		try {
			writer = new FileWriter(ruta,true);
			 writer.append(timepoAutentic+"");
			    writer.append(',');
			    writer.append(tiempoActu+"");
			    writer.append(',');
			    writer.append(perdidad+"");
			    writer.append(',');
			    writer.append(uso+"");
			    writer.append(',');
			    writer.append(num+"");
			    writer.append(',');
			    writer.append('\n');
			    writer.flush();
			    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private  void crearEncabezado()
	   {
		
		    try {
				writer = new FileWriter(ruta);
				 writer.append("Tiempo de autenticación");
				    writer.append(',');
				    writer.append("Tiempo de actualización");
				    writer.append(',');
				    writer.append("Perdida");
				    writer.append(',');
				    writer.append("Uso de CPU");
				    writer.append(',');
				    writer.append("Numero thread");
				    writer.append('\n');
				   	
				    //generate whatever data you want
						
				    writer.flush();
				    writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		   
		
	   }

}
