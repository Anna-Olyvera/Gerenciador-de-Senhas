package core;

public class Usuario {
    private String login;
    private String email;
    private String telefone;
    private String salt;
    private String hash;

    public Usuario() {
        // Construtor vazio necessário para Firebase
    }

public Usuario(String login) {
    this.login = login;
}

public Usuario(String login, String email, String telefone, String salt, String hash) {
    this.login = login;
    this.email = email;
    this.telefone = telefone;
    this.salt = salt;
    this.hash = hash;
}

    // Getters e setters

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
