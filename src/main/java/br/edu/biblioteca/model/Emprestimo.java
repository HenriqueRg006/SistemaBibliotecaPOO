package br.edu.biblioteca.model;

import br.edu.biblioteca.excecao.LivroIndisponivelException;
import br.edu.biblioteca.excecao.MultaPendenteException;

public class Emprestimo {

    private final int diasPermitidos = 7;
    private final double multaPorDia = 2.0;

    private int dataEmprestimo;
    private int dataDevolucao;

    public void emprestarLivro(Livro livro, Usuario usuario, int diaAtual)
            throws LivroIndisponivelException, MultaPendenteException {

        if (usuario.temMultaPendente()) {
            throw new MultaPendenteException(
                    "O cliente " + usuario.getNome() + " possui multa pendente de R$ "
                    + usuario.getMultaPendente() + ". Efetue o pagamento antes de realizar um novo empréstimo.");
        }

        if (!livro.ehDisponivel()) {
            throw new LivroIndisponivelException(
                    "O livro \"" + livro.getTitulo() + "\" não está disponível para empréstimo.");
        }

        dataEmprestimo = diaAtual;
        dataDevolucao = diaAtual + diasPermitidos;

        livro.retirar();
        usuario.adicionarHistorico("Empréstimo - Livro: " + livro.getTitulo()
                + " | Dia " + dataEmprestimo
                + " | Devolução prevista: Dia " + dataDevolucao);
    }

    public ResultadoDevolucao devolverLivro(Livro livro, Usuario usuario, int diaDevolucaoReal) {
        int diasAtraso = Math.max(0, diaDevolucaoReal - dataDevolucao);
        double multa = diasAtraso * multaPorDia;

        livro.devolver();

        if (multa > 0) {
            usuario.adicionarMulta(multa);
        }

        usuario.adicionarHistorico("Devolução - Livro: " + livro.getTitulo()
                + " | Dia " + diaDevolucaoReal
                + (diasAtraso > 0
                        ? " | Atraso: " + diasAtraso + " dia(s) | Multa: R$ " + multa
                        : " | No prazo"));

        return new ResultadoDevolucao(diaDevolucaoReal, diasAtraso, multa);
    }

    public int getDataEmprestimo() {
        return dataEmprestimo;
    }

    public int getDataDevolucao() {
        return dataDevolucao;
    }

    public static class ResultadoDevolucao {
        private final int diaDevolucaoReal;
        private final int diasAtraso;
        private final double multa;

        public ResultadoDevolucao(int diaDevolucaoReal, int diasAtraso, double multa) {
            this.diaDevolucaoReal = diaDevolucaoReal;
            this.diasAtraso = diasAtraso;
            this.multa = multa;
        }

        public int getDiaDevolucaoReal() { return diaDevolucaoReal; }
        public int getDiasAtraso()       { return diasAtraso; }
        public double getMulta()          { return multa; }
    }
}
