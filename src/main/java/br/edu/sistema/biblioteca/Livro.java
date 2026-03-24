package br.edu.sistema.biblioteca;

public class Livro {

    private static long cadastroLivro = 1L;

    private final long id;
    private final String titulo;
    private boolean disponivel;

    public Livro(String titulo) {
        this.id = cadastroLivro++;
        this.titulo = titulo;
        this.disponivel = true;
    }

    public void retirar() {
        disponivel = false;
    }

    public void devolver() {
        disponivel = true;
    }

    public boolean ehDisponivel() {
        return disponivel;
    }

    public String getTitulo() {
        return titulo;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", disponivel=" + disponivel +
                '}';
    }
}
