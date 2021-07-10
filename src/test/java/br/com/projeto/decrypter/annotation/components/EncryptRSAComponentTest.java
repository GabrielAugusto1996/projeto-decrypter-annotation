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
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.util.Base64;

class EncryptRSAComponentTest {

    private static final String VALOR_CRIPTOGRAFADO = "VALOR_CRIPTOGRAFADO";
    private final String publicKey;
    private final String secretsPadding;

    EncryptRSAComponentTest() {
        publicKey = "PUBLIC_KEY";
        secretsPadding = "SECRET_PADDING";
    }

    @Test
    void execute_ValorCriptografado_QuandoSucesso() throws Exception {
        try (MockedStatic<Base64> mockedBase64 = mockStatic(Base64.class)){
            final Base64.Decoder decoder = mock(Base64.Decoder.class);
            when(decoder.decode((byte[]) any())).thenReturn(new byte[]{});

            final Base64.Encoder encoder = mock(Base64.Encoder.class);
            when(encoder.encodeToString(any())).thenReturn(VALOR_CRIPTOGRAFADO);

            mockedBase64.when(Base64::getDecoder).thenReturn(decoder);
            mockedBase64.when(Base64::getEncoder).thenReturn(encoder);

            try (MockedStatic<KeyFactory> mockedKeyFactory = mockStatic(KeyFactory.class)){
                PublicKey publicKeyAcess = mock(PublicKey.class);

                final KeyFactory keyFactory = mock(KeyFactory.class);
                when(keyFactory.generatePublic(any(KeySpec.class))).thenReturn(publicKeyAcess);

                mockedKeyFactory.when(() -> KeyFactory.getInstance(anyString())).thenReturn(keyFactory);

                try (MockedStatic<Cipher> mockedCipher = mockStatic(Cipher.class)) {
                    Cipher cipher = Mockito.mock(Cipher.class);
                    doNothing().when(cipher).init(anyInt(), any(Key.class));
                    when(cipher.doFinal(any())).thenReturn(VALOR_CRIPTOGRAFADO.getBytes());

                    mockedCipher.when(() -> Cipher.getInstance(secretsPadding)).thenReturn(cipher);

                    final String retorno = new EncryptRSAComponent(publicKey, secretsPadding).execute("VALOR_SEM_CRIPTOGRAFIA");

                    assertEquals(VALOR_CRIPTOGRAFADO, retorno);
                }
            }
        }
    }
}