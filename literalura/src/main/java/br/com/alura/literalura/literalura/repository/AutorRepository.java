package br.com.alura.literalura.literalura.repository;

import br.com.alura.literalura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNome(String nome);
    List<Autor> findByNascimentoLessThanEqualAndFalecimentoGreaterThan(int anoBuscado, int anoBuscado1);
}
