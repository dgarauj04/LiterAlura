package com.alura.literalura.controller;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.services.ConsumoApi;
import com.alura.literalura.services.ConverterDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LivroController {
    private static final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private ConsumoApi consumoApi;
    @Autowired
    private ConverterDados converterDados;

    public void buscarLivroPorTitulo(String titulo){
        Optional<Livro> livroExistente = livroRepository.findByTituloContainingIgnoreCase(titulo);
        if(livroExistente.isPresent()){
            System.out.println("\n Este livro já está registrado no catálogo");
            System.out.println(livroExistente.get());
            return;
        }

        String urlBusca = URL_BASE + "?search=" + titulo.replace(" ", "+");
        System.out.println("\n Consultando API:" + urlBusca);

        String json = consumoApi.obterDados(urlBusca);
        if(json == null){
            System.out.println("Falha na comunicação com a API. Verifique sua conexão.");
            return;
        }

        DadosResposta dadosResposta = converterDados.obterDados(json,  DadosResposta.class);
        if (dadosResposta == null || dadosResposta.resultados().isEmpty()) {
            System.out.println("Nenhum livro encontrado com o titulo: \"" + titulo + "\"");
            return;
        }

        DadosLivro dadosLivro = dadosResposta.resultados().get(0);

        Livro livro = new Livro(dadosLivro);

        if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
            DadosAutor dadosAutor = dadosLivro.autores().get(0);

            Optional<Autor> autorExistente = autorRepository.findByNomeIgnoreCase(dadosAutor.nome());

            Autor autor;
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
                System.out.println("ℹ Autor já cadastrado: " + autor.getNome());
            } else {
                autor = new Autor(dadosAutor);
                autorRepository.save(autor);
                System.out.println("✅ Novo autor salvo: " + autor.getNome());
            }

            livro.setAutor(autor);
        }

        livroRepository.save(livro);
        System.out.println("\n✅ Livro salvo com sucesso!");
        System.out.println(livro);
    }

    public void listarTodosOsLivros() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("\n📚 Nenhum livro registrado no catálogo ainda.");
            System.out.println("   Dica: use a opção 1 para buscar um livro!");
            return;
        }

        System.out.println("\n📚 ══════ LIVROS REGISTRADOS ══════ 📚");
        System.out.println("   Total: " + livros.size() + " livro(s)\n");
        livros.forEach(System.out::println);
    }

    public void listarTodosOsAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("\n✍ Nenhum autor registrado no catálogo ainda.");
            System.out.println("   Dica: busque um livro primeiro para cadastrar autores!");
            return;
        }

        System.out.println("\n✍ ══════ AUTORES REGISTRADOS ══════ ✍");
        System.out.println("   Total: " + autores.size() + " autor(es)\n");
        autores.forEach(System.out::println);
    }

    public void listarAutoresVivosNoAno(Integer ano) {
        List<Autor> autoresVivos = autorRepository.findAutoresVivosNoAno(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("\n❌ Nenhum autor registrado estava vivo no ano: " + ano);
            return;
        }

        System.out.println("\n✍ ══ AUTORES VIVOS NO ANO " + ano + " ══ ✍");
        System.out.println("   Total: " + autoresVivos.size() + " autor(es)\n");
        autoresVivos.forEach(System.out::println);
    }


    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findByIdioma(idioma);
        long quantidade = livroRepository.countByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("\n❌ Nenhum livro registrado no idioma: \"" + idioma + "\"");
            return;
        }

        System.out.println("\n🌍 ══ LIVROS NO IDIOMA: " + idioma.toUpperCase() + " ══ 🌍");
        System.out.println("   Quantidade total: " + quantidade + " livro(s)\n");
        livros.forEach(System.out::println);

        livros.stream()
                .mapToInt(l -> l.getNumeroDownloads() != null ? l.getNumeroDownloads() : 0)
                .summaryStatistics();

        int totalDownloads = livros.stream()
                .mapToInt(l -> l.getNumeroDownloads() != null ? l.getNumeroDownloads() : 0)
                .sum();

        int maxDownloads = livros.stream()
                .mapToInt(l -> l.getNumeroDownloads() != null ? l.getNumeroDownloads() : 0)
                .max()
                .orElse(0);

        System.out.println("   📊 Estatísticas para o idioma \"" + idioma + "\":");
        System.out.println("      - Total de downloads combinados: " + totalDownloads);
        System.out.println("      - Maior número de downloads:     " + maxDownloads);
    }
}
