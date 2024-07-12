package br.com.alura.literalura.literalura.principal;

import br.com.alura.literalura.literalura.model.Autor;
import br.com.alura.literalura.literalura.model.Livro;
import br.com.alura.literalura.literalura.repository.AutorRepository;
import br.com.alura.literalura.literalura.repository.LivroRepository;
import br.com.alura.literalura.literalura.service.ConsultaAPI;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final ConsultaAPI consulta;
    private final Scanner leia= new Scanner(System.in);

    public Principal(AutorRepository autorRepository,LivroRepository livroRepository, ConsultaAPI consulta) {
        this.livroRepository=livroRepository;
        this.autorRepository=autorRepository;
        this.consulta=consulta;
    }


    public void exibeMenu(){
        var op=-1;
        while(op!=0){
            var menu = """
                    --------------------------------------
                    Escolha o número com a opção desejada
                    1- buscar livro pelo título
                    2- listar livros registrados
                    3- listar autores registrados
                    4- listar autores vivos em um determinado ano
                    5- listar livros em um determinado idioma
                    0- sair
                    """;
            System.out.println(menu);
            op=leia.nextInt();
            leia.nextLine();
            switch (op){
                case 1:
                    buscarLivroPorTitulo();//usa api aqui
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosIdioma();
            }
        }
    }

    private void listarLivrosIdioma() {
        System.out.println("Escolha o idioma que deseja pesquisar livros");
        var opIdioma = """
                    es- espanhol
                    en- Inglês
                    fr- francês
                    pt- português
                    """;
        System.out.println(opIdioma);
        var idiomaBuscado=leia.nextLine();
        List<Livro>livrosEncontrados=livroRepository.findByIdiomasContains(idiomaBuscado.toLowerCase());
        if (livrosEncontrados.isEmpty()){
            System.out.println("Nenhum livro foi encontrado em nossos registros com o idioma buscado");
        }else{
            System.out.println("\nLivros em "+idiomaBuscado+" :");
            livrosEncontrados.forEach(System.out::println);
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano que deseja pesquisar: ");
        var anoBuscado=leia.nextInt();
        leia.nextLine();
        List<Autor>autoresVivos=autorRepository.findByNascimentoLessThanEqualAndFalecimentoGreaterThan(anoBuscado, anoBuscado);
        if (autoresVivos.isEmpty()){
            System.out.println("\nNenhum autor vivo foi encontrado em nossos registros para o ano buscado");
        } else{
            System.out.println("\nAutores vivos nesse ano: ");
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores =autorRepository.findAll();
        if (autores.isEmpty()){
            System.out.println("\nNenhum autor registrado");
        }else{
            autores.forEach(System.out::println);
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro>livros=livroRepository.findAll();
        if (livros.isEmpty()){
            System.out.println("\nNenhum livro registrado");
        }else{
            livros.forEach(System.out::println);
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o título: ");
        var titulo= leia.nextLine();
        try{
            Optional <Livro> livrobuscado= Optional.ofNullable(consulta.buscaLivro(titulo));
            if (livrobuscado.isPresent()){
                System.out.println(livrobuscado.get());
            }else{
                System.out.println("\nNenhum encontrado");

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();}

   }
}
