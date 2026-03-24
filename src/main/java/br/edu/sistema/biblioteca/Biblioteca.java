package br.edu.sistema.biblioteca;

import br.edu.sistema.exception.ClienteNaoCadastradoException;
import br.edu.sistema.exception.LivroIndisponivelException;
import br.edu.sistema.exception.LivroNaoEncontradoException;
import br.edu.sistema.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Biblioteca {

    private final List<Livro> livros;
    private final List<Usuario> usuarios;
    private final List<Emprestimo> emprestimosAtivos;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimosAtivos = new ArrayList<>();
        carregarAcervoInicial();
    }

    private void carregarAcervoInicial() {
        livros.add(new Livro("Algoritmos: Teoria e Pratica - Thomas H. Cormen"));
        livros.add(new Livro("Engenharia de Software - Ian Sommerville"));
        livros.add(new Livro("Estruturas de Dados e Algoritmos em Java - Goodrich"));
        livros.add(new Livro("Clean Code - Robert C. Martin"));
        livros.add(new Livro("Harry Potter e a Pedra Filosofal"));
        livros.add(new Livro("O Senhor dos Aneis: A Sociedade do Anel"));
        livros.add(new Livro("Percy Jackson e o Ladrao de Raios"));
        livros.add(new Livro("Dom Casmurro - Machado de Assis"));
    }

    public Livro cadastrarLivro(String titulo) {
        validarTexto(titulo, "O titulo do livro nao pode ficar em branco.");
        Livro livro = new Livro(titulo.trim());
        livros.add(livro);
        return livro;
    }

    public Usuario cadastrarCliente(String nome) {
        validarTexto(nome, "O nome do cliente nao pode ficar em branco.");
        Usuario usuario = new Usuario(nome.trim());
        usuarios.add(usuario);
        return usuario;
    }

    public Emprestimo realizarEmprestimo(long matriculaCliente, String tituloLivro, int diaEmprestimo)
            throws ClienteNaoCadastradoException, LivroNaoEncontradoException, LivroIndisponivelException {
        Usuario usuario = buscarUsuarioPorMatricula(matriculaCliente);
        Livro livro = buscarLivroDisponivel(tituloLivro);
        Emprestimo emprestimo = criarEmprestimo(livro, usuario, diaEmprestimo);
        registrarEmprestimo(emprestimo, usuario);
        return emprestimo;
    }

    public double realizarDevolucao(long matriculaCliente, String tituloLivro, int diaDevolucao)
            throws ClienteNaoCadastradoException, LivroNaoEncontradoException {
        Usuario usuario = buscarUsuarioPorMatricula(matriculaCliente);
        Emprestimo emprestimo = buscarEmprestimoAtivo(usuario.getNome(), tituloLivro);
        double multa = finalizarDevolucao(emprestimo, diaDevolucao);
        registrarMultaPorAtraso(usuario, multa);
        return multa;
    }

    public double registrarMulta(long matriculaCliente, double valor) throws ClienteNaoCadastradoException {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da multa deve ser maior que zero.");
        }

        Usuario usuario = buscarUsuarioPorMatricula(matriculaCliente);
        usuario.adicionarMulta(valor);
        return usuario.getMultasPendentes();
    }

    public double pagarMulta(long matriculaCliente, double valor) throws ClienteNaoCadastradoException {
        Usuario usuario = buscarUsuarioPorMatricula(matriculaCliente);
        usuario.pagarMulta(valor);
        return usuario.getMultasPendentes();
    }

    public List<Emprestimo> consultarHistorico(long matriculaCliente) throws ClienteNaoCadastradoException {
        Usuario usuario = buscarUsuarioPorMatricula(matriculaCliente);
        return usuario.getHistoricoEmprestimos();
    }

    public Usuario buscarCliente(long matriculaCliente) throws ClienteNaoCadastradoException {
        return buscarUsuarioPorMatricula(matriculaCliente);
    }

    public List<Livro> listarLivros() {
        return Collections.unmodifiableList(livros);
    }

    public List<Usuario> listarClientes() {
        return Collections.unmodifiableList(usuarios);
    }

    private Livro buscarLivroPorTitulo(String titulo) throws LivroNaoEncontradoException {
        validarTexto(titulo, "O titulo do livro nao pode ficar em branco.");
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo.trim())) {
                return livro;
            }
        }
        throw new LivroNaoEncontradoException("O livro solicitado nao existe no sistema.");
    }

    private Livro buscarLivroDisponivel(String titulo) throws LivroNaoEncontradoException, LivroIndisponivelException {
        Livro livro = buscarLivroPorTitulo(titulo);
        validarDisponibilidade(livro);
        return livro;
    }

    private Usuario buscarUsuarioPorMatricula(long matricula) throws ClienteNaoCadastradoException {
        for (Usuario usuario : usuarios) {
            if (usuario.getMatricula() == matricula) {
                return usuario;
            }
        }
        throw new ClienteNaoCadastradoException("O cliente informado nao esta cadastrado no sistema.");
    }

    private Emprestimo criarEmprestimo(Livro livro, Usuario usuario, int diaEmprestimo) {
        return new Emprestimo(livro, usuario, diaEmprestimo);
    }

    private void registrarEmprestimo(Emprestimo emprestimo, Usuario usuario) {
        emprestimo.getLivro().retirar();
        usuario.adicionarAoHistorico(emprestimo);
        emprestimosAtivos.add(emprestimo);
    }

    private Emprestimo buscarEmprestimoAtivo(String nomeCliente, String tituloLivro) throws LivroNaoEncontradoException {
        validarTexto(tituloLivro, "O titulo do livro nao pode ficar em branco.");
        for (Emprestimo emprestimo : emprestimosAtivos) {
            if (emprestimo.getNomeCliente().equalsIgnoreCase(nomeCliente)
                    && emprestimo.getLivro().getTitulo().equalsIgnoreCase(tituloLivro.trim())) {
                return emprestimo;
            }
        }
        throw new LivroNaoEncontradoException("Nao existe emprestimo ativo para esse livro e cliente.");
    }

    private double finalizarDevolucao(Emprestimo emprestimo, int diaDevolucao) {
        double multa = emprestimo.registrarDevolucao(diaDevolucao);
        emprestimo.getLivro().devolver();
        emprestimosAtivos.remove(emprestimo);
        return multa;
    }

    private void registrarMultaPorAtraso(Usuario usuario, double multa) {
        if (multa > 0) {
            usuario.adicionarMulta(multa);
        }
    }

    private void validarDisponibilidade(Livro livro) throws LivroIndisponivelException {
        if (!livro.ehDisponivel()) {
            throw new LivroIndisponivelException("O livro solicitado nao esta disponivel.");
        }
    }

    private void validarTexto(String texto, String mensagem) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensagem);
        }
    }
}
