package br.edu.sistema.biblioteca;

import br.edu.sistema.usuario.Usuario;

public class Emprestimo {

    private static final int DIAS_PERMITIDOS = 7;
    private static final double MULTA_POR_DIA = 2.0;

    private final Livro livro;
    private final String nomeCliente;
    private final int dataEmprestimo;
    private final int dataPrevistaDevolucao;
    private Integer dataDevolucaoReal;
    private double multaGerada;

    public Emprestimo(Livro livro, Usuario usuario, int diaAtual) {
        this.livro = livro;
        this.nomeCliente = usuario.getNome();
        this.dataEmprestimo = diaAtual;
        this.dataPrevistaDevolucao = calcularDataPrevistaDevolucao(diaAtual);
    }

    public double registrarDevolucao(int diaDevolucaoReal) {
        registrarDataDevolucao(diaDevolucaoReal);
        multaGerada = calcularMulta();
        return multaGerada;
    }

    private int calcularDataPrevistaDevolucao(int diaAtual) {
        return diaAtual + DIAS_PERMITIDOS;
    }

    private void registrarDataDevolucao(int diaDevolucaoReal) {
        this.dataDevolucaoReal = diaDevolucaoReal;
    }

    private double calcularMulta() {
        if (!houveAtraso()) {
            return 0.0;
        }
        return calcularDiasAtraso() * MULTA_POR_DIA;
    }

    private boolean houveAtraso() {
        return dataDevolucaoReal != null && dataDevolucaoReal > dataPrevistaDevolucao;
    }

    private int calcularDiasAtraso() {
        return dataDevolucaoReal - dataPrevistaDevolucao;
    }

    public Livro getLivro() {
        return livro;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public int getDataEmprestimo() {
        return dataEmprestimo;
    }

    public int getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public Integer getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public double getMultaGerada() {
        return multaGerada;
    }

    @Override
    public String toString() {
        String devolucao = dataDevolucaoReal == null ? "pendente" : "dia " + dataDevolucaoReal;
        return "Livro: " + livro.getTitulo() +
                " | Cliente: " + nomeCliente +
                " | Empréstimo: dia " + dataEmprestimo +
                " | Devolução prevista: dia " + dataPrevistaDevolucao +
                " | Devolução real: " + devolucao +
                " | Multa gerada: R$ " + String.format("%.2f", multaGerada);
    }
}
