package br.edu.sistema.ui;

import br.edu.sistema.biblioteca.Biblioteca;
import br.edu.sistema.biblioteca.Emprestimo;
import br.edu.sistema.biblioteca.Livro;
import br.edu.sistema.exception.ClienteNaoCadastradoException;
import br.edu.sistema.exception.LivroIndisponivelException;
import br.edu.sistema.exception.LivroNaoEncontradoException;
import br.edu.sistema.usuario.Usuario;

import java.util.List;
import java.util.Scanner;

public class ConsoleBiblioteca {

    private final Biblioteca biblioteca;
    private final Scanner scanner;

    public ConsoleBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Informe a opção escolhida: ");

            try {
                switch (opcao) {
                    case 1 -> cadastrarLivro();
                    case 2 -> cadastrarCliente();
                    case 3 -> realizarEmprestimo();
                    case 4 -> realizarDevolucao();
                    case 5 -> registrarMultaManual();
                    case 6 -> pagarMulta();
                    case 7 -> consultarHistorico();
                    case 8 -> listarLivros();
                    case 9 -> listarClientes();
                    case 0 -> System.out.println("Encerrando o sistema.");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (LivroNaoEncontradoException | LivroIndisponivelException | ClienteNaoCadastradoException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida: " + e.getMessage());
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("=== BIBLIOTECA UNIVERSITARIA ===");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Cadastrar cliente");
        System.out.println("3 - Realizar emprestimo");
        System.out.println("4 - Realizar devolucao");
        System.out.println("5 - Registrar multa");
        System.out.println("6 - Pagar multa");
        System.out.println("7 - Consultar historico de emprestimos");
        System.out.println("8 - Listar livros");
        System.out.println("9 - Listar clientes");
        System.out.println("0 - Sair");
    }

    private void cadastrarLivro() {
        System.out.print("Informe o titulo do livro: ");
        String titulo = scanner.nextLine().trim();
        Livro livro = biblioteca.cadastrarLivro(titulo);
        System.out.println("Livro cadastrado com sucesso: " + livro);
    }

    private void cadastrarCliente() {
        System.out.print("Informe o nome do cliente: ");
        String nome = scanner.nextLine().trim();
        Usuario usuario = biblioteca.cadastrarCliente(nome);
        System.out.println("Cliente cadastrado com sucesso: " + usuario);
    }

    private void realizarEmprestimo()
            throws LivroNaoEncontradoException, LivroIndisponivelException, ClienteNaoCadastradoException {
        long matricula = lerLong("Matricula do cliente: ");
        String titulo = lerTexto("Titulo do livro: ");
        int diaEmprestimo = lerInteiro("Dia atual do emprestimo: ");
        Emprestimo emprestimo = biblioteca.realizarEmprestimo(matricula, titulo, diaEmprestimo);
        System.out.println("Emprestimo realizado com sucesso.");
        System.out.println(emprestimo);
    }

    private void realizarDevolucao() throws ClienteNaoCadastradoException, LivroNaoEncontradoException {
        long matricula = lerLong("Matricula do cliente: ");
        String titulo = lerTexto("Titulo do livro devolvido: ");
        int diaDevolucao = lerInteiro("Dia real da devolucao: ");
        double multa = biblioteca.realizarDevolucao(matricula, titulo, diaDevolucao);

        if (multa > 0) {
            System.out.println("Livro devolvido com atraso. Multa gerada: R$ " + String.format("%.2f", multa));
        } else {
            System.out.println("Livro devolvido no prazo.");
        }
    }

    private void registrarMultaManual() throws ClienteNaoCadastradoException {
        long matricula = lerLong("Matricula do cliente: ");
        double valor = lerDouble("Valor da multa: ");
        double total = biblioteca.registrarMulta(matricula, valor);
        System.out.println("Multa registrada. Total pendente: R$ " + String.format("%.2f", total));
    }

    private void pagarMulta() throws ClienteNaoCadastradoException {
        long matricula = lerLong("Matricula do cliente: ");
        double valor = lerDouble("Valor do pagamento: ");
        double saldo = biblioteca.pagarMulta(matricula, valor);
        System.out.println("Pagamento registrado. Saldo pendente: R$ " + String.format("%.2f", saldo));
    }

    private void consultarHistorico() throws ClienteNaoCadastradoException {
        long matricula = lerLong("Matricula do cliente: ");
        Usuario usuario = biblioteca.buscarCliente(matricula);
        List<Emprestimo> historico = biblioteca.consultarHistorico(matricula);

        System.out.println("Historico de emprestimos de " + usuario.getNome() + ":");
        if (historico.isEmpty()) {
            System.out.println("Nenhum emprestimo registrado.");
            return;
        }

        for (Emprestimo emprestimo : historico) {
            System.out.println(emprestimo);
        }
    }

    private void listarLivros() {
        List<Livro> livros = biblioteca.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    private void listarClientes() {
        List<Usuario> usuarios = biblioteca.listarClientes();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private long lerLong(String mensagem) {
        System.out.print(mensagem);
        long valor = scanner.nextLong();
        scanner.nextLine();
        return valor;
    }

    private double lerDouble(String mensagem) {
        System.out.print(mensagem);
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        String texto = scanner.nextLine().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("O campo informado nao pode ficar em branco.");
        }
        return texto;
    }
}
