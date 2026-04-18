package br.edu.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nome;
    private String matricula;
    private double multaPendente;
    private List<String> historicoEmprestimos;

    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.multaPendente = 0.0;
        this.historicoEmprestimos = new ArrayList<>();
    }

    public void adicionarMulta(double valor) {
        multaPendente += valor;
    }

    public void pagarMulta() {
        multaPendente = 0;
    }

    public void adicionarHistorico(String registro) {
        historicoEmprestimos.add(registro);
    }

    public boolean temMultaPendente() {
        return multaPendente > 0;
    }

    public double getMultaPendente() {
        return multaPendente;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public List<String> getHistoricoEmprestimos() {
        return historicoEmprestimos;
    }
}
