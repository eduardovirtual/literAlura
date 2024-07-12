package br.com.alura.literalura.literalura.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    public Autor(String nome, Integer nascimento, Integer falecimento) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.falecimento = falecimento;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autor_id;

    @Column(unique = true)
    private String nome;

    private Integer nascimento;
    private Integer falecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros= new ArrayList<>();

    public Autor() {

    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNascimento() {
        return nascimento;
    }

    public void setNascimento(Integer nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getFalecimento() {
        return falecimento;
    }

    public void setFalecimento(Integer falecimento) {
        this.falecimento = falecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    @Override
    public String toString() {
        return
                "\nAutor: " + nome+
                "\nAno de nascimento: " + nascimento +
                "\nAno de falecimento: " + falecimento +
                "\nLivros: " + livros.stream().map(Livro::getTitulo).collect(Collectors.joining(", "));
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}
