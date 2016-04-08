package caso2paquete1;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

public class Cifrado {

private static String CERTIFICADO = "certificado";

public static byte[] descifrar(byte [] data, Key llave, String algoritmo) throws Exception
{ 
	Cipher cipher = Cipher.getInstance(algoritmo); 
	cipher.init(Cipher.DECRYPT_MODE, llave);
	return cipher.doFinal(data); 
}

public static byte[] cifrar(byte[] data, Key llave, String algoritmo) throws Exception
{
	Cipher cipher = Cipher.getInstance(algoritmo);
	cipher.init(Cipher.ENCRYPT_MODE, llave);
	return cipher.doFinal(data);
}

//	public static X509Certificate crearCertificado(KeyPair pair) throws Exception
//	{
//		writer.println(CERTIFICADO);
//		java.security.cert.X509Certificate cert = certificado();
//		byte[] mybyte = cert.getEncoded();
//		socket.getOutputStream().write(mybyte);
//		socket.getOutoutStream().flush();
//	}
}
