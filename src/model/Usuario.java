package model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String email;
    private int idade;
    private String telefone;
    private String cidade;

    public Usuario(String nome, String email, int idade, String telefone, String cidade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.telefone = telefone;
        this.cidade = cidade;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public int getIdade() { return idade; }
    public String getTelefone() { return telefone; }
    public String getCidade() { return cidade; }

    @Override
    public String toString() {
        return "Usuario [Nome: " + nome + ", Email: " + email + 
               ", Idade: " + idade + " anos, Telefone: " + telefone + 
               ", Cidade: " + cidade + "]";
    }

    public boolean equals(Usuario outro) {
        return this.nome.equalsIgnoreCase(outro.getNome());
    }
}