package caso2paquete1;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;


public class Cifrado {

private static String CERTIFICADO = "certificado";

public static byte[] descifrar(byte [] data, Key llave, String algoritmo) throws Exception
{ 
	Cipher cipher = Cipher.getInstance(algoritmo); 
	cipher.init(Cipher.DECRYPT_MODE, llave);
	return cipher.doFinal(data); 
}

public static byte[] cifrar(byte[] data, Key llave, String algoritmo) throws Exception,NoSuchAlgorithmException
{
	Cipher cipher = Cipher.getInstance(algoritmo);
	cipher.init(Cipher.ENCRYPT_MODE, llave);
	return cipher.doFinal(data);
}

public static byte[] calcularMac(byte[] mensaje, Key llave, String algortmo) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
    Mac mac = Mac.getInstance(algortmo);
    mac.init(llave);
    return mac.doFinal(mensaje);
     
}

public static byte[] cifradoSimetrico(byte mensaje[], Key llave, String algoritmo)
        throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        
		if (algoritmo.equals("DES"))
		{
			algoritmo+="/ECB/PKCS5Padding";
		}
		else if (algoritmo.equals("AES") )
		{
			algoritmo+="/ECB/PKCS5Padding";
		}
	
		
        Cipher decifrador = Cipher.getInstance(algoritmo);
        decifrador.init(Cipher.ENCRYPT_MODE, llave);
        return decifrador.doFinal(mensaje);
    }
}
