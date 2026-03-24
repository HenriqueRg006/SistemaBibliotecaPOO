package org.example;

import br.edu.biblioteca.Emprestimo;
import br.edu.biblioteca.Livro;
import br.edu.excecao.LivroIndisponivelException;
import br.edu.excecao.LivroNaoEncontradoException;
import br.edu.excecao.MultaPendenteException;
import br.edu.usuario.Usuario;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Livro[] livros = {
                new Livro("Algoritmos: Teoria e Pratica - Thomas H. Cormen"),
                new Livro("Engenharia de Software - Ian Sommerville"),
                new Livro("Estruturas de Dados e Algoritmos em Java - Goodrich"),
                new Livro("Clean Code - Robert C. Martin"),
                new Livro("Harry Potter e a Pedra Filosofal"),
                new Livro("O Senhor dos Aneis: A Sociedade do Anel"),
                new Livro("Percy Jackson e o Ladrao de Raios"),
                new Livro("Dom Casmurro - Machado de Assis")
        };

        System.out.println("=== BIBLIOTECA UNIVERSITÁRIA ===");
        System.out.println();

        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a matrícula do usuário: ");
        String matricula = scanner.nextLine();

        Usuario usuario = new Usuario(nome, matricula);

        Emprestimo emprestimo = new Emprestimo();
        Livro livroEmEmprestimo = null;

        int opcao = 0;

        while (opcao != 6) {

            System.out.println();
            System.out.println("=== MENU PRINCIPAL ===");
            System.out.println("1 - Listar livros disponíveis");
            System.out.println("2 - Realizar empréstimo");
            System.out.println("3 - Realizar devolução");
            System.out.println("4 - Pagar multa");
            System.out.println("5 - Consultar histórico de empréstimos");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            if (opcao == 1) {

                System.out.println();
                System.out.println("=== LIVROS DISPONÍVEIS ===");
                for (int i = 0; i < livros.length; i++) {
                    String status = livros[i].ehDisponivel() ? "Disponível" : "Indisponível";
                    System.out.println((i + 1) + " - " + livros[i].getTitulo() + " [" + status + "]");
                }

            } else if (opcao == 2) {

                System.out.println();
                System.out.println("=== LIVROS DISPONÍVEIS ===");
                for (int i = 0; i < livros.length; i++) {
                    System.out.println((i + 1) + " - " + livros[i].getTitulo());
                }

                System.out.println();
                System.out.print("Escolha o número do livro: ");
                int opcaoLivro = scanner.nextInt();

                try {

                    if (opcaoLivro < 1 || opcaoLivro > livros.length) {
                        throw new LivroNaoEncontradoException("Livro com número " + opcaoLivro + " não existe no sistema.");
                    }

                    Livro livroEscolhido = livros[opcaoLivro - 1];

                    System.out.print("Digite o dia atual: ");
                    int diaAtual = scanner.nextInt();

                    System.out.println();
                    System.out.println(">>> REALIZANDO EMPRÉSTIMO");
                    emprestimo.emprestarLivro(livroEscolhido, usuario, diaAtual);
                    livroEmEmprestimo = livroEscolhido;

                } catch (LivroNaoEncontradoException | LivroIndisponivelException | MultaPendenteException e) {
                    System.out.println("Erro: " + e.getMessage());
                }

            } else if (opcao == 3) {

                if (livroEmEmprestimo == null) {
                    System.out.println("Nenhum empréstimo ativo para devolver.");
                } else {
                    System.out.print("Digite o dia real da devolução: ");
                    int diaDevolucaoReal = scanner.nextInt();

                    System.out.println();
                    System.out.println(">>> REALIZANDO DEVOLUÇÃO");
                    emprestimo.devolverLivro(livroEmEmprestimo, usuario, diaDevolucaoReal);
                    livroEmEmprestimo = null;
                }

            } else if (opcao == 4) {

                System.out.println();
                System.out.println(">>> PAGAMENTO DE MULTA");
                usuario.pagarMulta();

            } else if (opcao == 5) {

                System.out.println();
                usuario.exibirHistorico();

            } else if (opcao == 6) {

                System.out.println("Encerrando o sistema. Até logo!");

            } else {

                System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }
}