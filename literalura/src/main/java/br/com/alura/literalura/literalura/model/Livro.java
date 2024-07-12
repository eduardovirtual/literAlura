package br.com.alura.literalura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livro_id;
    private String titulo;
    //    @ManyToMany
//    @JoinTable(
//            name = "livro_autor",
//            joinColumns = @JoinColumn(name = "livro_id"),
//            inverseJoinColumns = @JoinColumn(name = "autor_id")
//    )
    @ManyToOne
    private Autor autor;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas=new ArrayList<>();
    private Integer numeroDownloads;

    public Long getId() {
        return livro_id;
    }

    public void setId(Long id) {
        this.livro_id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

//    public List<Autor> getAutores() {
//        return autores;
//    }
//
//    public void setAutores(List<Autor> autores) {
//        this.autores = autores;
//    }


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return
                "\n-------LIVRO-------"+
                "\nTítulo: " + titulo +
                "\nAutor: " + autor.getNome() +
                "\nIdiomas: " + idiomas +
                "\nNúmero de Downloads: " + numeroDownloads;
    }
}
