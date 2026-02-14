# 📚 LiterAlura — Catálogo de Livros

> Desafio Back-End | Alura + Oracle ONE  
> Catálogo interativo de livros via console, integrado à API Gutendex e banco de dados PostgreSQL.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Configuração e Instalação](#configuração-e-instalação)
- [Como Usar](#como-usar)
- [Exemplos de Uso](#exemplos-de-uso)
- [API Gutendex](#api-gutendex)
- [Banco de Dados](#banco-de-dados)

---

## Sobre o Projeto

O **LiterAlura** é um catálogo de livros desenvolvido em Java com Spring Boot. A aplicação roda inteiramente via console e permite ao usuário buscar livros pelo título através da [API Gutendex](https://gutendex.com), persistir os dados em um banco PostgreSQL e consultá-los de diversas formas.

> ⚠️ A API Gutendex é baseada no **Project Gutenberg** e disponibiliza apenas obras em **domínio público** (geralmente publicadas antes de 1928). Livros modernos com direitos autorais ativos não serão encontrados.

---

## Funcionalidades

| Opção | Descrição |
|-------|-----------|
| `1` | Buscar livro pelo título na API e salvar no banco |
| `2` | Listar todos os livros registrados |
| `3` | Listar todos os autores registrados |
| `4` | Listar autores vivos em um determinado ano |
| `5` | Listar livros em um determinado idioma + estatísticas |
| `0` | Sair da aplicação |

---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Jackson** (desserialização de JSON)
- **HttpClient** nativo do Java 11+ (requisições HTTP)
- **Maven** (gerenciamento de dependências)

---

## Estrutura do Projeto

```
literalura/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── resources/
        │   └── application.properties
        └── java/com/alura/literalura/
            ├── LiteraluraApplication.java       ← Classe principal (CommandLineRunner)
            │
            ├── principal/
            │   └── Principal.java               ← Menu interativo via Scanner
            │
            ├── services/
            │   ├── ConsumoApi.java              ← Requisições HTTP (HttpClient)
            │   └── ConverteDados.java           ← Conversão JSON→Java (Jackson)
            │
            ├── model/
            │   ├── DadosAutor.java              ← Record de desserialização (autor)
            │   ├── DadosLivro.java              ← Record de desserialização (livro)
            │   ├── DadosResposta.java           ← Record de desserialização (resposta)
            │   ├── Autor.java                   ← Entidade JPA (@Entity)
            │   └── Livro.java                   ← Entidade JPA (@Entity)
            │
            ├── controller/
            │   └── LivroController.java         ← Lógica de negócio
            │
            └── repository/
                ├── AutorRepository.java         ← JpaRepository + Derived Queries
                └── LivroRepository.java         ← JpaRepository + Derived Queries
```

---

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Java 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [PostgreSQL 14+](https://www.postgresql.org/download/) com pgAdmin 4
- Uma IDE de sua preferência (IntelliJ IDEA recomendada)

---

## Configuração e Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/literalura.git
cd literalura
```

### 2. Crie o banco de dados no PostgreSQL

Abra o pgAdmin 4 ou o terminal do PostgreSQL e execute:

```sql
CREATE DATABASE literalura;
```

### 3. Configure o `application.properties`

Edite o arquivo `src/main/resources/application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
```

> O Hibernate criará as tabelas automaticamente na primeira execução graças ao `ddl-auto=update`.

### 4. Execute a aplicação

**Pela IDE:** Execute a classe `LiteraluraApplication.java`

**Pelo terminal:**
```bash
mvn spring-boot:run
```

---

## Como Usar

Ao iniciar, a aplicação exibe o menu no console:

```
╔═════════════════════════════════════════╗
║        📖  BEM-VINDO AO LITERALURA       ║
║         Catálogo de Livros Gutendex      ║
╚═════════════════════════════════════════╝

─────────────────────────────────────────
  Escolha o número de sua opção:

  1 - Buscar livro pelo título
  2 - Listar livros registrados
  3 - Listar autores registrados
  4 - Listar autores vivos em um determinado ano
  5 - Listar livros em um determinado idioma
  0 - Sair
─────────────────────────────────────────
  Sua opção:
```

---

## Exemplos de Uso

### Buscando um livro (Opção 1)

```
Sua opção: 1
Digite o título do livro: Pride and Prejudice

✅ Livro salvo com sucesso!

╔══════════════════════════════════╗
            LIVRO
╠══════════════════════════════════╣
 Título:    Pride and Prejudice
 Autor:     Austen, Jane
 Idioma:    en
 Downloads: 62591
╚══════════════════════════════════╝
```

### Listando autores vivos em um ano (Opção 4)

```
Sua opção: 4
Digite o ano de referência: 1800

✍ ══ AUTORES VIVOS NO ANO 1800 ══ ✍
   Total: 1 autor(es)

╔══════════════════════════════════╗
           AUTOR
╠══════════════════════════════════╣
 Nome:           Austen, Jane
 Ano Nascimento: 1775
 Ano Falecimento:1817
╚══════════════════════════════════╝
```

### Listando livros por idioma (Opção 5)

```
Sua opção: 5
Digite o código do idioma: en

🌍 ══ LIVROS NO IDIOMA: EN ══ 🌍
   Quantidade total: 3 livro(s)

   📊 Estatísticas para o idioma "en":
      - Total de downloads combinados: 135420
      - Maior número de downloads:     62591
```

---

## API Gutendex

A aplicação consome a API pública **Gutendex**, que disponibiliza dados do [Project Gutenberg](https://www.gutenberg.org).

| Detalhe | Valor |
|---------|-------|
| URL Base | `https://gutendex.com/books/` |
| Busca por título | `https://gutendex.com/books/?search=titulo` |
| Autenticação | Não requerida |
| Formato de resposta | JSON |

**Livros recomendados para teste** (domínio público):

| Título | Idioma |
|--------|--------|
| Pride and Prejudice | en |
| Frankenstein | en |
| Dracula | en |
| Moby Dick | en |
| The Adventures of Tom Sawyer | en |
| Dom Casmurro | pt |
| Don Quixote | es |
| Crime and Punishment | en |

---

## Banco de Dados

O Hibernate gerencia o schema automaticamente. As tabelas criadas são:

### Tabela `autores`

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `id` | BIGINT (PK) | Identificador único |
| `nome` | VARCHAR | Nome do autor |
| `ano_nascimento` | INTEGER | Ano de nascimento |
| `ano_falecimento` | INTEGER | Ano de falecimento (nullable) |

### Tabela `livros`

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `id` | BIGINT (PK) | Identificador único |
| `titulo` | VARCHAR (UNIQUE) | Título do livro |
| `idioma` | VARCHAR | Código do idioma (ex: `en`, `pt`) |
| `numero_downloads` | INTEGER | Total de downloads |
| `autor_id` | BIGINT (FK) | Referência ao autor |

### Diagrama de Relacionamento

```
┌─────────────┐         ┌──────────────┐
│   autores   │         │    livros    │
├─────────────┤         ├──────────────┤
│ id (PK)     │◄────────│ autor_id(FK) │
│ nome        │  1    N │ id (PK)      │
│ ano_nasc.   │         │ titulo       │
│ ano_falec.  │         │ idioma       │
└─────────────┘         │ downloads    │
                        └──────────────┘
```

---
