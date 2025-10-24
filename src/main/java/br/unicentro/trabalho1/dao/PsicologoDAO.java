package br.unicentro.trabalho1.dao;

import java.sql.SQLException;
import java.util.List;
import br.unicentro.trabalho1.model.Psicologo;

public interface PsicologoDAO {
    public void insere(Psicologo psicologo) throws SQLException;
    public void atualiza(Psicologo psicologo) throws SQLException;
    public void remove(Psicologo psicologo) throws SQLException;
    public Psicologo buscaPorCodigo(int codigo) throws SQLException;
    public List<Psicologo> listaTodos() throws SQLException;
}