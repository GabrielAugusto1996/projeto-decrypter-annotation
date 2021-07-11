package br.com.projeto.decrypter.annotation.components.rsa;

import br.com.projeto.decrypter.annotation.components.IDecrypt;
import br.com.projeto.decrypter.annotation.exceptions.security.DecryptRSAException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class DecryptRSAComponent implements IDecrypt {

    private final String privateKey;
    private final String secretsPadding;

    public DecryptRSAComponent(
            @Value("${rsa.key.private}") String privateKey,
            @Value("${rsa.secrets.padding}") String secretsPadding
    ) {
        this.privateKey = privateKey;
        this.secretsPadding = secretsPadding;
    }

    @Override
    public String execute(final String value) {
        try {
            return decrypt(Base64.getDecoder().decode(value.getBytes()), getPrivateKey(privateKey));
        } catch (Exception exception) {
            throw new DecryptRSAException(exception);
        }
    }

    private PrivateKey getPrivateKey(final String base64PrivateKey) {
        try {
            return KeyFactory
                    .getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes())));
        } catch (Exception exception) {
            throw new DecryptRSAException(exception);
        }
    }

    private String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(secretsPadding);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        final byte[] decryptBytes = cipher.doFinal(data);

        return decryptBytes != null ? new String(decryptBytes) : null;
    }
}