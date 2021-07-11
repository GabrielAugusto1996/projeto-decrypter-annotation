package br.com.projeto.decrypter.annotation.components.aes;

import br.com.projeto.decrypter.annotation.components.IDecrypt;
import br.com.projeto.decrypter.annotation.exceptions.security.DecryptAESException;
import static javax.crypto.Cipher.DECRYPT_MODE;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DecryptAESComponent implements IDecrypt {

    private final String key;
    private final String initVector;
    private final String secretsPadding;

    public DecryptAESComponent(
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
            cipher.init(DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(initVector.getBytes()));

            return new String(cipher.doFinal(Base64.decodeBase64(value)));
        } catch (Exception exception) {
            throw new DecryptAESException(exception);
        }
    }
}
