package br.com.projeto.decrypter.annotation.components.aes;

import br.com.projeto.decrypter.annotation.components.IDecrypt;
import br.com.projeto.decrypter.annotation.exceptions.security.DecryptAESException;
import static java.util.Objects.nonNull;
import static javax.crypto.Cipher.getInstance;
import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64URLSafe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class DecryptAESComponent implements IDecrypt {

    private final String key;
    private final String secretsPadding;
    private final String initVet;

    public DecryptAESComponent(
            @Value("${aes.key}") String key,
            @Value("${aes.secrets.padding}") String secretsPadding,
            @Value("${aes.init-vet}") String initVet
    ) {
        this.key = key;
        this.secretsPadding = secretsPadding;
        this.initVet = initVet;
    }

    @Override
    public String execute(String value) {
        try {
            Cipher cipher = getInstance(secretsPadding);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(initVet.getBytes()));

            return nonNull(value) ? new String(cipher.doFinal(decodeBase64URLSafe(value))) : null;
        } catch (Exception exception) {
            throw new DecryptAESException(exception);
        }
    }
}
