package br.edu.biblioteca.controller;

import br.edu.biblioteca.excecao.LivroIndisponivelException;
import br.edu.biblioteca.excecao.LivroNaoEncontradoException;
import br.edu.biblioteca.excecao.MultaPendenteException;
import br.edu.biblioteca.model.Emprestimo;
import br.edu.biblioteca.model.Livro;
import br.edu.biblioteca.model.Usuario;
import br.edu.biblioteca.view.BibliotecaView;

public class BibliotecaController {

    private final BibliotecaView view;

    private final Livro[] livros = {
            new Livro("Algoritmos: Teoria e Pratica - Thomas H. Cormen"),
            new Livro("Engenharia de Software - Ian Sommerville"),
            new Livro("Estruturas de Dados e Algoritmos em Java - Goodrich"),
            new Livro("Clean Code - Robert C. Martin"),
            new Livro("Harry Potter e a Pedra Filosofal"),
            new Livro("O Senhor dos Aneis: A Sociedade do Anel"),
            new Livro("Percy Jackson e o Ladrao de Raios"),
            new Livro("Dom Casmurro - Machado de Assis")
    };

    private Usuario usuario;
    private final Emprestimo emprestimo = new Emprestimo();
    private Livro livroEmEmprestimo = null;

    public BibliotecaController(BibliotecaView view) {
        this.view = view;
    }

    public void iniciar() {
        view.exibirBoasVindas();

        String nome = view.lerNome();
        String matricula = view.lerMatricula();
        usuario = new Usuario(nome, matricula);

        int opcao = 0;
        while (opcao != 6) {
            view.exibirMenu();
            opcao = view.lerOpcaoMenu();

            switch (opcao) {
                case 1 -> listarLivros();
                case 2 -> realizarEmprestimo();
                case 3 -> realizarDevolucao();
                case 4 -> pagarMulta();
                case 5 -> consultarHistorico();
                case 6 -> view.exibirMensagem("Encerrando o sistema. Até logo!");
                default -> view.exibirMensagem("Opção inválida.");
            }
        }

        view.fechar();
    }

    private void listarLivros() {
        view.exibirLivros(livros, true);
    }

    private void realizarEmprestimo() {
        view.exibirLivros(livros, false);

        try {
            int opcaoLivro = view.lerOpcaoLivro();

            if (opcaoLivro < 1 || opcaoLivro > livros.length) {
                throw new LivroNaoEncontradoException(
                        "Livro com número " + opcaoLivro + " não existe no sistema.");
            }

            Livro livroEscolhido = livros[opcaoLivro - 1];
            int diaAtual = view.lerDiaAtual();

            emprestimo.emprestarLivro(livroEscolhido, usuario, diaAtual);
            livroEmEmprestimo = livroEscolhido;

            view.exibirResultadoEmprestimo(livroEscolhido, usuario, emprestimo);

        } catch (LivroNaoEncontradoException | LivroIndisponivelException | MultaPendenteException e) {
            view.exibirErro(e.getMessage());
        }
    }

    private void realizarDevolucao() {
        if (livroEmEmprestimo == null) {
            view.exibirMensagem("Nenhum empréstimo ativo para devolver.");
            return;
        }

        int diaDevolucaoReal = view.lerDiaDevolucao();
        Emprestimo.ResultadoDevolucao resultado = emprestimo.devolverLivro(livroEmEmprestimo, usuario, diaDevolucaoReal);

        view.exibirResultadoDevolucao(livroEmEmprestimo, resultado);
        livroEmEmprestimo = null;
    }

    private void pagarMulta() {
        boolean tinhaMulta = usuario.temMultaPendente();
        double valorPago = usuario.getMultaPendente();
        usuario.pagarMulta();
        view.exibirPagamentoMulta(usuario, tinhaMulta, valorPago);
    }

    private void consultarHistorico() {
        view.exibirHistorico(usuario);
    }
}
