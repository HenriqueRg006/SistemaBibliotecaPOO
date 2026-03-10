package org.example;

import br.edu.biblioteca.Emprestimo;
import br.edu.biblioteca.Livro;
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

        System.out.println();
        System.out.println("=== LIVROS DISPONÍVEIS ===");

        for (int i = 0; i < livros.length; i++) {
            System.out.println((i + 1) + " - " + livros[i].getTitulo());
        }

        System.out.println();
        System.out.print("Escolha o número do livro que deseja pegar emprestado: ");
        int opcaoLivro = scanner.nextInt();

        if (opcaoLivro < 1 || opcaoLivro > livros.length) {
            System.out.println("Opção inválida.");
            scanner.close();
            return;
        }

        Livro livroEscolhido = livros[opcaoLivro - 1];
        Emprestimo emprestimo = new Emprestimo();

        System.out.print("Digite o dia atual para realizar o empréstimo: ");
        int diaEmprestimo = scanner.nextInt();

        System.out.println();
        System.out.println(">>> REALIZANDO EMPRÉSTIMO");
        emprestimo.emprestarLivro(livroEscolhido, usuario, diaEmprestimo);

        System.out.println();
        System.out.print("Digite o dia real da devolução: ");
        int diaDevolucaoReal = scanner.nextInt();

        System.out.println();
        System.out.println(">>> REALIZANDO DEVOLUÇÃO");
        emprestimo.devolverLivro(livroEscolhido, diaDevolucaoReal);

        scanner.close();
    }
}