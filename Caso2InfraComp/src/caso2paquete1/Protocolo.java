package caso2paquete1;

public class Protocolo
{
	
	public static final String HOLA="HOLA";
	public static final String INICIO="INICIO";
	public static final String ALGORITMOS="ALGORITMOS";
	public static final String DES="DES";
	public static final String AES="AES";
	public static final String BLOWFISH="Blowfish";
	public static final String RC4="RC4";
	public static final String RSA="RSA";
	public static final String HMACMD5="HMACMD5";
	public static final String HMACSHA1="HMACSHA1";
	public static final String HMACSHA256="HMACSHA256";
	public static final String ESTADO="ESTADO";
	public static final String OK="OK";
	public static final String ERROR="ERROR";
	public static final String CERCLNT="CERCLNT:";
	public static final String CERTSRV="CERTSRV:";
	public static final String ACT1="ACT1";
	public static final String ACT2="ACT2";
	public static final String SEPARADOR=":";
	public static final String DATA="DATA";
	public static final String RTA="RTA";
	
	
	public static String concatnenar(String[] aConcatener)
	{
		String rta="";
		for (int i = 0; i < aConcatener.length; i++) 
		{
			String string = aConcatener[i];
			rta+=string+=SEPARADOR;
		}
		return rta.substring(0,rta.length()-1);
	}
}
