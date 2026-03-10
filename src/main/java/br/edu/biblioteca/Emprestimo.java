package br.edu.biblioteca;

import br.edu.usuario.Usuario;

public class Emprestimo {

    private final int diasPermitidos = 7;
    private final double multaPorDia = 2.0;

    private int dataEmprestimo;
    private int dataDevolucao;

    public void emprestarLivro(Livro livro, Usuario usuario, int diaAtual) {

        if (livro.ehDisponivel()) {

            dataEmprestimo = diaAtual;
            dataDevolucao = diaAtual + diasPermitidos;

            livro.retirar();

            System.out.println("Usuário: " + usuario.getNome());
            System.out.println("Livro: " + livro.getTitulo());
            System.out.println("Data do empréstimo: Dia " + dataEmprestimo);
            System.out.println("Data prevista para devolução: Dia " + dataDevolucao);

        } else {
            System.out.println("Livro não disponível para empréstimo.");
        }
    }

    public void devolverLivro(Livro livro, int diaDevolucaoReal) {

        System.out.println("Data real da devolução: Dia " + diaDevolucaoReal);

        if (diaDevolucaoReal > dataDevolucao) {

            int diasAtraso = diaDevolucaoReal - dataDevolucao;
            double multa = diasAtraso * multaPorDia;

            System.out.println("Livro devolvido com atraso.");
            System.out.println("Dias de atraso: " + diasAtraso);
            System.out.println("Multa: R$ " + multa);

        } else {
            System.out.println("Livro devolvido no prazo.");
        }

        livro.devolver();
    }
}