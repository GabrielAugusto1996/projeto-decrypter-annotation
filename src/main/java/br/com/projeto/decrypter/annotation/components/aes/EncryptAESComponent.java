package br.com.projeto.decrypter.annotation.components.aes;

import br.com.projeto.decrypter.annotation.components.IEncrypt;
import br.com.projeto.decrypter.annotation.exceptions.security.EncryptAESException;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptAESComponent implements IEncrypt {

    private final String key;
    private final String initVector;
    private final String secretsPadding;

    public EncryptAESComponent(
            @Value("${aes.key}") String key,
            @Value("${aes.init-vector}") String initVector,
            @Value("${aes.secrets.padding}") String secretsPadding
    ) {
        this.key = key;
        this.initVector = initVector;
        this.secretsPadding = secretsPadding;
    }

    @Override
    public String execute(String value) {
        try {
            Cipher cipher = Cipher.getInstance(secretsPadding);
            cipher.init(ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(initVector.getBytes()));

            return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
        } catch (Exception exception) {
            throw new EncryptAESException(exception);
        }
    }
}
