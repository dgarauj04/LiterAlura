package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {


    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByIdioma(String idioma);

    long countByIdioma(String idioma);

    Optional<Livro> findByTituloIgnoreCase(String titulo);

    @Query("SELECT l FROM Livro l ORDER BY l.numeroDownloads DESC LIMIT 10")
    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();
}