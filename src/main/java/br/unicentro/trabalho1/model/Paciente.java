package br.unicentro.trabalho1.model;

import java.util.Objects;

public class Paciente {
    private int pacienteID;
    private String nome;
    private String cpf;
    private int psicologo_ID;

    public Paciente() {}

    public Paciente(int pacienteID, String nome, String cpf, int psicologo_ID) {
        this.pacienteID = pacienteID;
        this.nome = nome;
        this.cpf = cpf;
        this.psicologo_ID = psicologo_ID;
    }

    public int getPacienteID() {
        return pacienteID;
    }

    public void setPacienteID(int pacienteID) {
        this.pacienteID = pacienteID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getPsicologo_ID() {
        return psicologo_ID;
    }

    public void setPsicologo_ID(int psicologo_ID) {
        this.psicologo_ID = psicologo_ID;
    }

    @Override
    public String toString() {
        return "Paciente{" + "codigo=" + pacienteID + ", nome=" + nome + ", cpf=" + cpf + ", psicologo_ID=" + psicologo_ID + '}';
    }
}