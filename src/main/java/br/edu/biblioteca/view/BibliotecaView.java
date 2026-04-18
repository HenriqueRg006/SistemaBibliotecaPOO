package br.edu.biblioteca.view;

import br.edu.biblioteca.model.Emprestimo;
import br.edu.biblioteca.model.Livro;
import br.edu.biblioteca.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class BibliotecaView {

    private final Scanner scanner = new Scanner(System.in);

    public String lerNome() {
        System.out.print("Digite o nome do usuário: ");
        return scanner.nextLine();
    }

    public String lerMatricula() {
        System.out.print("Digite a matrícula do usuário: ");
        return scanner.nextLine();
    }

    public int lerOpcaoMenu() {
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // consumir newline
        return opcao;
    }

    public int lerOpcaoLivro() {
        System.out.print("Escolha o número do livro: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public int lerDiaAtual() {
        System.out.print("Digite o dia atual: ");
        int dia = scanner.nextInt();
        scanner.nextLine();
        return dia;
    }

    public int lerDiaDevolucao() {
        System.out.print("Digite o dia real da devolução: ");
        int dia = scanner.nextInt();
        scanner.nextLine();
        return dia;
    }

    public void exibirBoasVindas() {
        System.out.println("=== BIBLIOTECA UNIVERSITÁRIA ===");
        System.out.println();
    }

    public void exibirMenu() {
        System.out.println();
        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("1 - Listar livros disponíveis");
        System.out.println("2 - Realizar empréstimo");
        System.out.println("3 - Realizar devolução");
        System.out.println("4 - Pagar multa");
        System.out.println("5 - Consultar histórico de empréstimos");
        System.out.println("6 - Sair");
    }

    public void exibirLivros(Livro[] livros, boolean mostrarStatus) {
        System.out.println();
        System.out.println("=== LIVROS DISPONÍVEIS ===");
        for (int i = 0; i < livros.length; i++) {
            if (mostrarStatus) {
                String status = livros[i].ehDisponivel() ? "Disponível" : "Indisponível";
                System.out.println((i + 1) + " - " + livros[i].getTitulo() + " [" + status + "]");
            } else {
                System.out.println((i + 1) + " - " + livros[i].getTitulo());
            }
        }
    }

    public void exibirResultadoEmprestimo(Livro livro, Usuario usuario, Emprestimo emprestimo) {
        System.out.println();
        System.out.println(">>> EMPRÉSTIMO REALIZADO COM SUCESSO");
        System.out.println("ID do Livro: " + livro.getId());
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Data do empréstimo: Dia " + emprestimo.getDataEmprestimo());
        System.out.println("Data prevista para devolução: Dia " + emprestimo.getDataDevolucao());
    }

    public void exibirResultadoDevolucao(Livro livro, Emprestimo.ResultadoDevolucao resultado) {
        System.out.println();
        System.out.println(">>> DEVOLUÇÃO REALIZADA");
        System.out.println("ID do Livro: " + livro.getId());
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Data real da devolução: Dia " + resultado.getDiaDevolucaoReal());

        if (resultado.getDiasAtraso() > 0) {
            System.out.println("Livro devolvido com atraso.");
            System.out.println("Dias de atraso: " + resultado.getDiasAtraso());
            System.out.println("Multa: R$ " + resultado.getMulta());
        } else {
            System.out.println("Livro devolvido no prazo.");
        }
    }

    public void exibirHistorico(Usuario usuario) {
        System.out.println();
        System.out.println("=== HISTÓRICO DE EMPRÉSTIMOS DE " + usuario.getNome().toUpperCase() + " ===");
        List<String> historico = usuario.getHistoricoEmprestimos();
        if (historico.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado.");
        } else {
            for (int i = 0; i < historico.size(); i++) {
                System.out.println((i + 1) + ". " + historico.get(i));
            }
        }
    }

    public void exibirPagamentoMulta(Usuario usuario, boolean tinhaMulta, double valorPago) {
        System.out.println();
        System.out.println(">>> PAGAMENTO DE MULTA");
        if (tinhaMulta) {
            System.out.println("Multa de R$ " + valorPago + " paga com sucesso.");
        } else {
            System.out.println("Nenhuma multa pendente.");
        }
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirErro(String mensagem) {
        System.out.println("Erro: " + mensagem);
    }

    public void fechar() {
        scanner.close();
    }
}
