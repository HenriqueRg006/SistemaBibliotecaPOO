package br.edu.sistema.usuario;

import br.edu.sistema.biblioteca.Emprestimo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {

    private static long contadorMatricula = 1L;

    private final long matricula;
    private final String nome;
    private double multasPendentes;
    private final List<Emprestimo> historicoEmprestimos;

    public Usuario(String nome) {
        this.matricula = contadorMatricula++;
        this.nome = nome;
        this.multasPendentes = 0.0;
        this.historicoEmprestimos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public long getMatricula() {
        return matricula;
    }

    public double getMultasPendentes() {
        return multasPendentes;
    }

    public void adicionarMulta(double valor) {
        if (valor > 0) {
            multasPendentes += valor;
        }
    }

    public void pagarMulta(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para pagamento deve ser maior que zero.");
        }
        if (valor > multasPendentes) {
            throw new IllegalArgumentException("O valor informado excede o total de multas pendentes.");
        }
        multasPendentes -= valor;
    }

    public void adicionarAoHistorico(Emprestimo emprestimo) {
        historicoEmprestimos.add(emprestimo);
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return Collections.unmodifiableList(historicoEmprestimos);
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                " | Matrícula: " + matricula +
                " | Multas pendentes: R$ " + String.format("%.2f", multasPendentes);
    }
}
