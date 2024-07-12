package br.com.alura.literalura.client;

import br.com.alura.literalura.entity.AutorEntity;
import br.com.alura.literalura.entity.LivroEntity;
import br.com.alura.literalura.model.Resposta;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class ClienteLiteratura {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private LivroRepository livroRepositorio;
    private AutorRepository autorRepositorio;

    public ClienteLiteratura(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void menu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    Escolha a opção desejada:
                    	1.- Buscar livro por titulo
                    	2.- Lista livros registrados
                    	3.- Lista autores registrados
                    	4.- Lista autores vivos em determinado ano
                    	5.- Listar livros por idioma
                    	0 - Sair
                    	""";
            System.out.println(menu);
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    buscarLivros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresVivo();
                    break;
                case 5:
                    buscarPorIdiomas();
                    break;
                case 0:
                    System.out.println("Até mais. Volte sempre...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }

    private void buscarLivros() {

        List<LivroEntity> livros = livroRepositorio.findAll();

        if (!livros.isEmpty()) {

            for (LivroEntity livro : livros) {
                System.out.println("\n\n---------- LIVROS -------\n");
                System.out.println(" Titulo: " + livro.getTitulo());
                System.out.println(" Autor: " + livro.getAutor().getNome());
                System.out.println(" Idioma: " + livro.getIdioma());
                System.out.println(" Downloads: " + livro.getDownloads());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- Nenhum Resultado Encontrado. ---- \n\n");
        }

    }

    private void buscarAutores() {
        List<AutorEntity> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- Autores -------\n");
                System.out.println(" Nome: " + autor.getNome());
                System.out.println(" Data de nascimento: " + autor.getDataNascimento());
                System.out.println(" Data de falecimento: " + autor.getDataFalecimento());
                System.out.println(" Livros: " + autor.getLivros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- Nenhum Resultado Encontrado. ---- \n\n");

        }

    }

    private void buscarAutoresVivo() {
        System.out.println("Insira o Ano em que deseja pesquisar autores vivos: ");
        var ano = teclado.nextInt();
        teclado.nextLine();

        List<AutorEntity> autores = autorRepositorio.findForYear(ano);

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- Autores Vivos -------\n");
                System.out.println(" Nome: " + autor.getNome());
                System.out.println(" Data de nascimento: " + autor.getDataNascimento());
                System.out.println(" Data de falecimento: " + autor.getDataFalecimento());
                System.out.println(" Livros: " + autor.getLivros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- Nenhum Resultado Encontrado. ---- \n\n");

        }
    }

    private void buscarPorIdiomas() {

        var menu = """
				Selecione um Idioma:
					1.- Español
					2.- Ingles
					3.- Português
				
					""";
        System.out.println(menu);
        var idioma = teclado.nextInt();
        teclado.nextLine();

        String opcao = "";

        if(idioma == 1) {
            opcao = "es";
        } else 	if(idioma == 2) {
            opcao = "en";
        }else 	if(idioma == 3) {
            opcao = "pt";
        }

        List<LivroEntity> livros = livroRepositorio.findForLanguaje(opcao);

        if (!livros.isEmpty()) {

            for (LivroEntity livro : livros) {
                System.out.println("\n\n---------- LIVROS POR IDIOMA-------\n");
                System.out.println(" Titulo: " + livro.getTitulo());
                System.out.println(" Autor: " + livro.getAutor().getNome());
                System.out.println(" Idioma: " + livro.getIdioma());
                System.out.println(" Downloads: " + livro.getDownloads());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- Nenhum Resultado Encontrado. ---- \n\n");
        }

    }

    private void buscarLivroWeb() {
        Resposta dados = getDadosSerie();

        if (!dados.results().isEmpty()) {

            LivroEntity livro = new LivroEntity(dados.results().get(0));
            livro = livroRepositorio.save(livro);

        }

        System.out.println("Dados: ");
        System.out.println(dados);
    }

    private Resposta getDadosSerie() {
        System.out.println("Insira o nome do Livro que deseja buscar: ");
        var titulo = teclado.nextLine();
        titulo = titulo.replace(" ", "%20");
        System.out.println("Titulo : " + titulo);
        System.out.println(URL_BASE + titulo);
        var json = consumoApi.obterDados(URL_BASE + titulo);
        System.out.println(json);
        Resposta dados = conversor.obterDados(json, Resposta.class);
        return dados;
    }

}
