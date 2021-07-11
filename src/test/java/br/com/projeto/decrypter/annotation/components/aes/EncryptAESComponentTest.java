package br.com.projeto.decrypter.annotation.components.aes;

import org.apache.tomcat.util.codec.binary.Base64;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import javax.crypto.Cipher;
import java.security.Key;

class EncryptAESComponentTest {
    public static final String VALOR_CRIPTOGRAFADO = "VALOR_CRIPTOGRAFADO";
    public static final String VALOR_SEM_CRIPTOGRAFIA = "VALOR_SEM_CRIPTOGRAFIA";
    private final String key;
    private final String secretsPadding;
    private final String initVet;

    EncryptAESComponentTest() {
        key = "PRIVATE_KEY";
        secretsPadding = "SECRET_PADDING";
        initVet = "encryptionIntVec";
    }

    @Test
    void execute_ValorCriptografado_QuandoSucesso() throws Exception {
        try (MockedStatic<Base64> mockedBase64 = mockStatic(Base64.class)){
            mockedBase64.when(() -> Base64.encodeBase64URLSafeString(any())).thenReturn(VALOR_CRIPTOGRAFADO);

            try (MockedStatic<Cipher> mockedCipher = mockStatic(Cipher.class)) {
                Cipher cipher = Mockito.mock(Cipher.class);
                doNothing().when(cipher).init(anyInt(), any(Key.class));
                when(cipher.doFinal(any())).thenReturn(VALOR_CRIPTOGRAFADO.getBytes());

                mockedCipher.when(() -> Cipher.getInstance(secretsPadding)).thenReturn(cipher);

                final String retorno = new EncryptAESComponent(key, secretsPadding, initVet).execute(VALOR_SEM_CRIPTOGRAFIA);

                assertEquals(VALOR_CRIPTOGRAFADO, retorno);
            }
        }
    }
}