package br.unicentro.trabalho1.dao;

import java.sql.SQLException;
import java.util.List;

import br.unicentro.trabalho1.model.Paciente;

public interface PacienteDAO {
    public void insere(Paciente paciente) throws SQLException;

    public void atualiza(Paciente paciente) throws SQLException;

    public void remove(Paciente paciente) throws SQLException;

    public Paciente buscaPorCodigo(int pacienteID) throws SQLException;

    public List<Paciente> listaTodos() throws SQLException;
}