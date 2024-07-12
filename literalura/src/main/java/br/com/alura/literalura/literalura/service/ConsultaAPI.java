package br.com.alura.literalura.literalura.service;
import br.com.alura.literalura.literalura.model.Autor;
import br.com.alura.literalura.literalura.model.Livro;
import br.com.alura.literalura.literalura.repository.AutorRepository;
import br.com.alura.literalura.literalura.repository.LivroRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ConsultaAPI {


        private static final String API_URL = "https://gutendex.com/books/";
        private final AutorRepository autorRepository;
        private final LivroRepository livroRepository;

    public ConsultaAPI(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository=livroRepository;
    }

    public Livro buscaLivro(String nomeBusca) throws IOException, InterruptedException {
            String url = API_URL + "?search=" + nomeBusca.replace(" ", "+");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return converteLivro(response.body());
            } else {
                throw new IOException("Falha ao encontrar título na API");
            }
        }

        private Livro converteLivro(String responseBody) {
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray resultados = jsonObject.getAsJsonArray("results");

            if (resultados.size() == 0) {
                return null;
            }

            JsonObject resultado = resultados.get(0).getAsJsonObject();

            Livro livro = new Livro();
            livro.setTitulo(resultado.get("title").getAsString());

            JsonArray languages = resultado.getAsJsonArray("languages");
            ArrayList<String> idiomas = new ArrayList<>();
            for (int j = 0; j < languages.size(); j++) {
                idiomas.add(languages.get(j).getAsString());
            }
            livro.setIdiomas(idiomas);

            livro.setNumeroDownloads(resultado.get("download_count").getAsInt());

            JsonArray resultadoAutores = resultado.getAsJsonArray("authors");
            JsonObject autorObj = resultadoAutores.get(0).getAsJsonObject();

            String nome = autorObj.get("name").getAsString();

            Optional<Autor> autorVerifica = autorRepository.findByNome(nome);
            Autor autor;
            if (autorVerifica.isEmpty()) {
                Integer dataMorte=0;
                if(autorObj.get("death_year")!=null){
                    dataMorte=autorObj.get("death_year").getAsInt();
                }
                autor = new Autor(nome, autorObj.get("birth_year").getAsInt(), dataMorte);
                autorRepository.save(autor);
            }
            else {
                autor=autorVerifica.get();
            }
            livro.setAutor(autor);
            autor.getLivros().add(livro);
            livroRepository.save(livro);
            return livro;

        }
}
