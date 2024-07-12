package br.com.alura.literalura.literalura;

import br.com.alura.literalura.literalura.principal.Principal;
import br.com.alura.literalura.literalura.repository.AutorRepository;
import br.com.alura.literalura.literalura.repository.LivroRepository;
import br.com.alura.literalura.literalura.service.ConsultaAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements                                                    CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private LivroRepository livroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsultaAPI consultaAPI =new ConsultaAPI(autorRepository, livroRepository);
		Principal principal = new Principal(autorRepository, livroRepository,consultaAPI);
		principal.exibeMenu();
	}
}
