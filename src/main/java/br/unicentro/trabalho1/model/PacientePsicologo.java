package br.unicentro.trabalho1.model;

public class PacientePsicologo {
    /*
     * A classe pacientepsicologo reutiliza a classes paciente e psicologo por meio de
     * Composi��o.
     * Como exemplo: o atributo ou vari�vel de inst�ncia paciente � uma refer�ncia a
     * objetos da classe paciente
     */
    private Paciente paciente;
    private Psicologo psicologo;

    public PacientePsicologo() {}

    public PacientePsicologo(Paciente paciente, Psicologo psicologo) {
        this.paciente = paciente;
        this.psicologo = psicologo;
    }

    public Paciente getpaciente() {
        return paciente;
    }

    public void setpaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Psicologo getpsicologo() {
        return psicologo;
    }

    public void setpsicologo(Psicologo psicologo) {
        this.psicologo = psicologo;
    }

    @Override
    public String toString() {
        return "pacientepsicologo{" + "paciente=" + this.paciente + ", psicologo=" + this.psicologo + '}';
    }
}