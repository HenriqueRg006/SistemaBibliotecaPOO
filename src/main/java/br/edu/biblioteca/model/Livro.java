package br.edu.biblioteca.model;

public class Livro {

    private static int contadorId = 1;

    private int id;
    private String titulo;
    private boolean disponivel;

    public Livro(String titulo) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.disponivel = true;
    }

    public void retirar() {
        this.disponivel = false;
    }

    public void devolver() {
        this.disponivel = true;
    }

    public boolean ehDisponivel() {
        return disponivel;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }
}
