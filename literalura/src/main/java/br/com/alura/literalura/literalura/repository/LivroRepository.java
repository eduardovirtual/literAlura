package br.com.alura.literalura.literalura.repository;

import br.com.alura.literalura.literalura.model.Autor;
import br.com.alura.literalura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository <Livro,Long> {
    List<Livro>findByIdiomasContains(String idioma);
}
