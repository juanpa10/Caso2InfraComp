package caso2paquete1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

import org.apache.commons.io.input.ReaderInputStream;
import org.bouncycastle.x509.X509V1CertificateGenerator;

public class Cliente 

{
	
	private Socket socketCliente;
	private  PrintWriter salida;
	private BufferedReader in;
	private Cifrado cifrado;
	private String ipServidor;
	private int puertoServidor;
	private BufferedReader brSistema;
	private String  sime;
	private String aSime;
	private String firmado;
	private X509Certificate cert;
	private KeyPair keyPair;
	private String algortimoFirma;
	private X509Certificate certificadoServidor;
	private SecretKey simetrica ;
	public Cliente(String ip, int puerto,BufferedReader br)
	{
		ipServidor=ip;
		puertoServidor=puerto;
		try
		{
			socketCliente= new Socket(ipServidor, puertoServidor);
			//socketCliente.setSoTimeout(10000);
			
			salida=new PrintWriter(socketCliente.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			brSistema=br;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	private void inicarComunicacion(String posicion) throws Exception
	{
		salida.println(Protocolo.HOLA);
		String delServidor= in.readLine();
		System.out.println("Del servidor "+delServidor);
		if(delServidor.equals(Protocolo.INICIO))
		{
			 sime=seleccionarAlgorimoSimetrico();
			 aSime=seleccionarAlgorimoASimetrico();
			 firmado=seleccionarAlgorimodeFirmado();
			 
			String[] aEnviar= new String[4];
			aEnviar[0]=Protocolo.ALGORITMOS;
			aEnviar[1]=sime;
			aEnviar[2]=aSime;
			aEnviar[3]=firmado;
		algortimoFirma=firmado.substring(4,firmado.length())+"with"+aSime;
		String rta=Protocolo.concatnenar(aEnviar);
		
		System.out.println("Al servidor "+rta);
		salida.println(rta);
		delServidor= in.readLine();
		System.out.println("Del servidor "+delServidor);
		String[] estado=delServidor.split(Protocolo.SEPARADOR);
		
		if(estado[1].equals(Protocolo.OK))
		{
			salida.println(Protocolo.CERCLNT);
			cert=certificado();
			byte[] certBytes=cert.getEncoded();
						
			socketCliente.getOutputStream().write(certBytes);
			socketCliente.getOutputStream().flush();
			delServidor= in.readLine();
			System.out.println("Del servidor "+delServidor);
			if(delServidor.equals(Protocolo.CERTSRV))
			{
				int offset=0;
	            byte[] certificadoServidorBytes= new byte[520];
	            int numBytesLeidos = socketCliente.getInputStream().read(certificadoServidorBytes, offset, 520 - offset);
	            System.out.println("Numero de bytes leidos "+numBytesLeidos);
	            CertificateFactory creador = CertificateFactory.getInstance("X.509");
	            InputStream ent = new ByteArrayInputStream(certificadoServidorBytes);
	            certificadoServidor = (X509Certificate)creador.generateCertificate(ent);
	            salida.println(Protocolo.ESTADO+Protocolo.SEPARADOR+Protocolo.OK);
	           
	            delServidor= in.readLine();
				System.out.println("Del servidor "+delServidor);
				String llave=delServidor.split(Protocolo.SEPARADOR)[1];
	            byte []llaveSimetrica=Transformacion.destransformar(llave);
	            byte[] llaveDescifrada= Cifrado.descifrar(llaveSimetrica, keyPair.getPrivate(), aSime);
	            
	            
	            simetrica=new SecretKeySpec(llaveDescifrada, 0, llaveDescifrada.length, sime);
	            byte[] llaveSimetricaCifrada=Cifrado.cifrar(simetrica.getEncoded(), certificadoServidor.getPublicKey(), aSime);
	            String llaveSimetricaTexto= Transformacion.transformar(llaveSimetricaCifrada);
	            String llaveSImetricaAEnviar=Protocolo.DATA+Protocolo.SEPARADOR+llaveSimetricaTexto;
	            salida.println(llaveSImetricaAEnviar);
	            System.out.println("Al servidor "+llaveSImetricaAEnviar);
	            delServidor= in.readLine();
				System.out.println("Del servidor "+delServidor);
	            if(delServidor.split(Protocolo.SEPARADOR)[1].equals(Protocolo.OK))
	            {
	            	String aEnviarACT1=Protocolo.ACT1+Protocolo.SEPARADOR;
	            	byte[] posicionEncriptada=Cifrado.cifradoSimetrico(posicion.getBytes(), simetrica, sime);
	            	aEnviarACT1+=Transformacion.transformar(posicionEncriptada);
	            	salida.println(aEnviarACT1);
		            System.out.println("Al servidor "+aEnviarACT1);
	            	
		            String aEnviarACT2=Protocolo.ACT2+Protocolo.SEPARADOR;
		            byte hash[]=Cifrado.cifrar(posicion.getBytes(), certificadoServidor.getPublicKey(), aSime);
		            String encodingAct2=Transformacion.transformar(hash);
		            aEnviarACT2+=encodingAct2;
		            salida.println(encodingAct2);
		            System.out.println("Al servidor "+encodingAct2);
		            
		            delServidor= in.readLine();
					System.out.println("Del servidor "+delServidor);
					if(delServidor.equals(Protocolo.RTA+Protocolo.SEPARADOR+Protocolo.OK))
					{
						System.out.println("Correcot");
						socketCliente.close();
					}
					else
					{
						System.out.println("Error ");
						socketCliente.close();
					}
	            }
	            else
	            {
	            	System.out.println("Ocurrio un error con la llave simétrica");
	            }
			}
			else
			{
				System.out.println("ERROR");
			}
		}
		else
		{
			System.out.println("ERROR");
		}
		
		
		}
		
	}
	
	private String seleccionarAlgorimoSimetrico() throws IOException
	{
		
		String rta="";
		while(true)
		{
		System.out.println("Cifrado simétrico");
		System.out.println("Ingrese el número de la opción deseada");
		System.out.println("Opción 1: \n DES. Modo ECB, esquema de relleno PKCS5, llave de 64 bits");
		System.out.println("Opción 2: \n AES. Modo ECB, esquema de relleno PKCS5, llave de 128 bits");
		System.out.println("Opción 3: \n Blowfish. Cifrado por bloques, llave de 128 bits.");
		System.out.println("Opción 4: \n RC4. Cifrado por flujo, llave de 128 bits.");
		System.out.println();
		String linea=brSistema.readLine();
		if(linea.equals("1"))
		{
			rta=Protocolo.DES;
			break;
		}
		else if(linea.equals("2"))
		{
			rta=Protocolo.AES;
			break;
		}
		else if(linea.equals("3"))
		{
			rta=Protocolo.BLOWFISH;
			break;
		}
		else if(linea.equals("4"))
		{
			rta=Protocolo.RC4;
			break;
		}
		else
		{
			System.out.println("Opción Invalida");
		}
		
		}
		System.out.println("Ud selecciono para cifrado simétrico "+rta);
		return rta;
	}
	private String seleccionarAlgorimoASimetrico() throws IOException
	{
		
		String rta="";
		while(true)
		{
		System.out.println("Cifrado asimétrico");
		System.out.println("Ingrese el número de la opción deseada");
		System.out.println("Opción 1: \n RSA. Cifrado por bloques, llave de 1024 bits.");
		System.out.println();
		String linea=brSistema.readLine();
		if(linea.equals("1"))
		{
			rta=Protocolo.RSA;
			break;
		}
		else
		{
			System.out.println("Opción invalida vuelva a intentarlo");
		}
		}
		System.out.println("Ud selecciono para cifrado asimétrico "+rta);
		return rta;
	}
	private String seleccionarAlgorimodeFirmado() throws IOException
	{
		
		String rta="";
		while(true)
		{
		System.out.println("Algoritmo de firmado");
		System.out.println("Ingrese el número de la opción deseada");
		System.out.println("Opción 1: \n HmacMD5");
		System.out.println("Opción 2: \n HmacSHA1");
		System.out.println("Opción 3: \n HmacSHA256");
		System.out.println();
		String linea=brSistema.readLine();
		if(linea.equals("1"))
		{
			rta=Protocolo.HMACMD5;
			break;
		}
		else if(linea.equals("2"))
		{
			rta=Protocolo.HMACSHA1;
			break;
		}
		else if(linea.equals("3"))
		{
			rta=Protocolo.HMACSHA256;
			break;
		}

		else
		{
			System.out.println("Opción Invalida");
		}

		}
		System.out.println("Ud selecciono para firmar el algoritmo "+rta);
		return rta;
	}
	
	private X509Certificate certificado() throws NoSuchAlgorithmException
	{
		if(cert==null)
		{
		Date startDate =new Date(2016, 1, 1);              // time from which certificate is valid
		Date expiryDate = new Date(2018, 1, 1);             // time after which certificate is not valid
		BigInteger serialNumber = new BigInteger("1");     // serial number for certificate
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(aSime);
		keyGen.initialize(1024);
		keyPair = keyGen.generateKeyPair();
		X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
		X500Principal              dnName = new X500Principal("CN=Test CA Certificate");
		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(dnName);
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		certGen.setSubjectDN(dnName);                       // note: same as issuer
		certGen.setPublicKey(keyPair.getPublic());
		certGen.setSignatureAlgorithm(algortimoFirma);
		X509Certificate cert;
		
		try {
			cert = certGen.generate(keyPair.getPrivate(), "BC");
			return cert;
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{
			return cert;
		}
		return null;
	}
	
	
	public static void main(String[] args) 
	{
		
		System.gc();
        try{
        	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Ip del servidor");
            String s = br.readLine();
            System.out.print("Puerto del servidor");
            System.out.print("Puerto del servidor");
            int i = Integer.parseInt(br.readLine());
            Cliente cliente= new Cliente(s, i,br);
            cliente.inicarComunicacion("41 24.2028, 2 10.4418");
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

