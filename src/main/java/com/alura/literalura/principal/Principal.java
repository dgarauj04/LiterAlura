package com.alura.literalura.principal;

import com.alura.literalura.controller.LivroController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private LivroController livroController;

    private final Scanner scanner = new Scanner(System.in);

    public void abrirMenu() {
        int opcao = -1;

        System.out.println("""
                
                ╔═════════════════════════════════════════╗
                ║       📖 BEM-VINDO AO LITERALURA        ║
                ║          Seu Catálogo de Livros         ║
                ╚═════════════════════════════════════════╝
                """);

        while (opcao != 0) {
            exibirOpcoes();

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\n⚠ Entrada inválida! Por favor, digite um número.");
                scanner.nextLine();
                continue;
            }


            switch (opcao) {
                case 1 -> opcaoBuscarLivroPorTitulo();
                case 2 -> livroController.listarTodosOsLivros();
                case 3 -> livroController.listarTodosOsAutores();
                case 4 -> opcaoListarAutoresVivosNoAno();
                case 5 -> opcaoListarLivrosPorIdioma();
                case 0 -> System.out.println("""
                        
                        ╔══════════════════════════════════════╗
                        ║  Obrigado por usar o LiterAlura! 📚  ║
                        ║         Até a próxima leitura!       ║
                        ╚══════════════════════════════════════╝
                        """);
                default -> System.out.println("\n⚠ Opção inválida! Escolha um número entre 0 e 5.");
            }
        }

        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println("""
                
                ─────────────────────────────────────────
                  Escolha o número de sua opção:
                
                  1 - Buscar livro pelo título
                  2 - Listar livros registrados
                  3 - Listar autores registrados
                  4 - Listar autores vivos em um determinado ano
                  5 - Listar livros em um determinado idioma
                  0 - Sair
                ─────────────────────────────────────────
                """);
        System.out.print("  Sua opção: ");
    }

    private void opcaoBuscarLivroPorTitulo() {
        System.out.println("\n📖 BUSCAR LIVRO POR TÍTULO");
        System.out.print("   Digite o título do livro: ");
        String titulo = scanner.nextLine().trim();

        if (titulo.isBlank()) {
            System.out.println("⚠ O título não pode estar vazio!");
            return;
        }

        livroController.buscarLivroPorTitulo(titulo);
    }

    private void opcaoListarAutoresVivosNoAno() {
        System.out.println("\n🗓  AUTORES VIVOS EM DETERMINADO ANO");
        System.out.print("   Digite o ano de referência: ");

        Integer ano;
        try {
            ano = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("⚠ Ano inválido! Digite apenas números inteiros.");
            scanner.nextLine();
            return;
        }

        if (ano < 0 || ano > 2024) {
            System.out.println("⚠ Por favor, insira um ano válido entre 0 e 2024.");
            return;
        }

        livroController.listarAutoresVivosNoAno(ano);
    }

    private void opcaoListarLivrosPorIdioma() {
        System.out.println("""
                
                🌍 LISTAR LIVROS POR IDIOMA
                   Idiomas disponíveis (exemplos):
                
                   pt  → Português
                   en  → Inglês
                   es  → Espanhol
                   fr  → Francês
                   de  → Alemão
                   it  → Italiano
                   ru  → Russo
                   zh  → Chinês
                """);
        System.out.print("   Digite o código do idioma: ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        if (idioma.isBlank()) {
            System.out.println("⚠ O idioma não pode estar vazio!");
            return;
        }

        livroController.listarLivrosPorIdioma(idioma);
    }
}