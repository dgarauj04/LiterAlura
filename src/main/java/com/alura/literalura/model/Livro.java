package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private String idioma;

    @Column(name = "numero_downloads")
    private Integer numeroDownloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty()
                ? dadosLivro.idiomas().get(0)
                : "Desconhecido";
        this.numeroDownloads = dadosLivro.numeroDownloads();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        String nomeAutor = autor != null ? autor.getNome() : "Desconhecido";

        return """
               ╔══════════════════════════════════╗
                           LIVRO
               ╠══════════════════════════════════╣
                Título:    %s
                Autor:     %s
                Idioma:    %s
                Downloads: %d
               ╚══════════════════════════════════╝
               """.formatted(titulo, nomeAutor, idioma,
                numeroDownloads != null ? numeroDownloads : 0);
    }
}