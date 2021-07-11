package br.com.projeto.decrypter.annotation.components.rsa;

import br.com.projeto.decrypter.annotation.components.IEncrypt;
import br.com.projeto.decrypter.annotation.exceptions.security.EncryptRSAException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class EncryptRSAComponent implements IEncrypt {

    private final String publicKey;
    private final String secretsPadding;

    public EncryptRSAComponent(
            @Value("${rsa.key.public}") String publicKey,
            @Value("${rsa.secrets.padding}") String secretsPadding
    ) {
        this.publicKey = publicKey;
        this.secretsPadding = secretsPadding;
    }

    @Override
    public String execute(final String value) {
        try {
            return Base64.getEncoder().encodeToString(encrypt(value, publicKey));
        } catch (Exception exception) {
            throw new EncryptRSAException(exception);
        }
    }

    private PublicKey getPublicKey(String base64PublicKey) {
        try {
            return KeyFactory
                    .getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes())));
        } catch (Exception exception) {
            throw new EncryptRSAException(exception);
        }
    }

    private byte[] encrypt(String data, String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(secretsPadding);

        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));

        return cipher.doFinal(data.getBytes());
    }
}