package br.unicentro.trabalho1.model;

public class Psicologo {
    private int psicologoID;
    private String nome;
    private int tempoAtuacao;
    private double salario;

    public Psicologo() {}

    public Psicologo(int psicologoID, String nome, int tempoAtuacao, double salario) {
        this.psicologoID = psicologoID;
        this.nome = nome;
        this.tempoAtuacao = tempoAtuacao;
        this.salario = salario;
    }

    public int getPsicologoID() {
        return psicologoID;
    }

    public void setPsicologoID(int psicologoID) {
        this.psicologoID = psicologoID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTempoAtuacao() {
        return tempoAtuacao;
    }

    public void setTempoAtuacao(int tempoAtuacao) {
        this.tempoAtuacao = tempoAtuacao;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Psicologo{" + "codigo=" + psicologoID + ", nome=" + nome + ", tempo de atuação=" + tempoAtuacao + ", salario=" + salario + '}';
    }
}