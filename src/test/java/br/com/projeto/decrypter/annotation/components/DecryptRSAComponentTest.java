package br.com.projeto.decrypter.annotation.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.util.Base64;

class DecryptRSAComponentTest {

    private static final String VALOR_CRIPTOGRAFADO = "VALOR_CRIPTOGRAFADO";
    private final String privateKey;
    private final String secretsPadding;

    DecryptRSAComponentTest() {
        privateKey = "PRIVATE_KEY";
        secretsPadding = "SECRET_PADDING";
    }

    @Test
    void execute_ValorDescriptografado_QuandoSucesso() throws Exception {
        try (MockedStatic<Base64> mockedBase64 = mockStatic(Base64.class)){
            final Base64.Decoder decoder = mock(Base64.Decoder.class);
            when(decoder.decode((byte[]) any())).thenReturn(new byte[]{});

            mockedBase64.when(Base64::getDecoder).thenReturn(decoder);

            try (MockedStatic<KeyFactory> mockedKeyFactory = mockStatic(KeyFactory.class)){
                PrivateKey privateKeyAcess = mock(PrivateKey.class);

                final KeyFactory keyFactory = mock(KeyFactory.class);
                when(keyFactory.generatePrivate(any(KeySpec.class))).thenReturn(privateKeyAcess);

                mockedKeyFactory.when(() -> KeyFactory.getInstance(anyString())).thenReturn(keyFactory);

                try (MockedStatic<Cipher> mockedCipher = mockStatic(Cipher.class)) {
                    Cipher cipher = Mockito.mock(Cipher.class);
                    doNothing().when(cipher).init(anyInt(), any(Key.class));
                    when(cipher.doFinal(any())).thenReturn(VALOR_CRIPTOGRAFADO.getBytes());

                    mockedCipher.when(() -> Cipher.getInstance(secretsPadding)).thenReturn(cipher);

                    final String retorno = new DecryptRSAComponent(privateKey, secretsPadding).execute(VALOR_CRIPTOGRAFADO);

                    assertEquals(VALOR_CRIPTOGRAFADO, retorno);
                }
            }
        }
    }
}