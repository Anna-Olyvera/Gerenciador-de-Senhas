package core;

// Classe responsável por gerenciar a sessão do usuário logado
public final class Sessao {
    private static Usuario usuarioLogado;

    // Construtor privado para impedir instanciação da classe
    private Sessao() {}

    // Define o usuário logado na sessão
    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    // Retorna o usuário logado atualmente
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    // Verifica se há um usuário logado
    public static boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }

    // Limpa a sessão removendo o usuário logado
    public static void limparSessao() {
        usuarioLogado = null;
    }
}
