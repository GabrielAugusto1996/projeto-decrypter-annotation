package br.com.projeto.decrypter.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "br.com.projeto.decrypter.annotation")
@EnableAspectJAutoProxy
public class ProjetoDecrypterAnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoDecrypterAnnotationApplication.class, args);
	}

}
