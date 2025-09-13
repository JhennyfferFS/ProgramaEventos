package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String endereco;
    private Categoria categoria;
    private LocalDateTime horario;
    private String descricao;
    private int duracaoHoras;
    private List<String> participantes;

    public Evento(String nome, String endereco, Categoria categoria,
                 LocalDateTime horario, String descricao, int duracaoHoras) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.duracaoHoras = duracaoHoras;
        this.participantes = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public Categoria getCategoria() { return categoria; }
    public LocalDateTime getHorario() { return horario; }
    public String getDescricao() { return descricao; }
    public int getDuracaoHoras() { return duracaoHoras; }
    public List<String> getParticipantes() { return participantes; }

    public boolean adicionarParticipante(String usuarioNome) {
        if (!participantes.contains(usuarioNome)) {
            participantes.add(usuarioNome);
            return true;
        }
        return false;
    }

    public boolean removerParticipante(String usuarioNome) {
        return participantes.remove(usuarioNome);
    }

    public boolean isConfirmado(String usuarioNome) {
        return participantes.contains(usuarioNome);
    }

    public String getStatus() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fim = horario.plusHours(duracaoHoras);

        if (now.isAfter(fim)) {
            return "Já ocorreu";
        } else if (!now.isBefore(horario) && !now.isAfter(fim)) {
            return "OCORRENDO AGORA";
        } else {
            return "Próximo";
        }
    }

    public boolean estaOcorrendo() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fim = horario.plusHours(duracaoHoras);
        return !now.isBefore(horario) && !now.isAfter(fim);
    }

    public boolean jaOcorreu() {
        return horario.plusHours(duracaoHoras).isBefore(LocalDateTime.now());
    }

    public boolean estaPorVir() {
        return horario.isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        DateTimeFormatter formatDisplay = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
            "\nEvento: %s\n" +
            "Status: %s\n" +
            "Categoria: %s\n" +
            "Local: %s\n" +
            "Horário: %s\n" +
            "Duração: %d horas\n" +
            "Descrição: %s\n" +
            "Participantes: %d\n" +
            "----------------------------------------",
            nome, getStatus(), categoria.getDescricao(),
            endereco, horario.format(formatDisplay),
            duracaoHoras, descricao, participantes.size()
        );
    }

    public boolean equals(Evento outro) {
        return this.nome.equalsIgnoreCase(outro.getNome());
    }
}