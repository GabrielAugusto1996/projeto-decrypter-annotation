package br.com.projeto.decrypter.annotation.components.aes;

import br.com.projeto.decrypter.annotation.components.IEncrypt;
import br.com.projeto.decrypter.annotation.exceptions.security.EncryptAESException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class EncryptAESComponent implements IEncrypt {

    private final String key;
    private final String secretsPadding;
    private final String initVet;

    public EncryptAESComponent(
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
            IvParameterSpec iv = new IvParameterSpec(initVet.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance(secretsPadding);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            return Base64.encodeBase64URLSafeString(cipher.doFinal(value.getBytes()));
        } catch (Exception exception) {
            throw new EncryptAESException(exception);
        }
    }
}