package br.edu.sistema;

import br.edu.sistema.biblioteca.Biblioteca;
import br.edu.sistema.ui.ConsoleBiblioteca;

public class Main {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        ConsoleBiblioteca consoleBiblioteca = new ConsoleBiblioteca(biblioteca);
        consoleBiblioteca.iniciar();
    }
}
