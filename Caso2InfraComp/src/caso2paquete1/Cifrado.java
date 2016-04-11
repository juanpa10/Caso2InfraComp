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
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
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



public static byte[] cifradoSimetrico(byte msg[], Key key, String algo)
        throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        algo = (new StringBuilder(String.valueOf(algo))).append(!algo.equals("DES") && !algo.equals("AES") ? "" : "/ECB/PKCS5Padding").toString();
        Cipher decifrador = Cipher.getInstance(algo);
        decifrador.init(Cipher.ENCRYPT_MODE, key);
        return decifrador.doFinal(msg);
    }
}
