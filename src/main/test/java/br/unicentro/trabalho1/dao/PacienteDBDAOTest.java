package br.unicentro.trabalho1.dao;

import br.unicentro.trabalho1.model.Paciente;
import br.unicentro.trabalho1.model.Psicologo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacienteDBDAOTest {

    private final PacienteDBDAO dao = new PacienteDBDAO();

    @BeforeEach
    void setUp() throws SQLException {
        PsicologoDBDAO dao = new PsicologoDBDAO();
        Psicologo p  = new Psicologo(0, "Teste", 0, 0);
        dao.insere(p);
    }

    @AfterEach
    void tearDown() throws SQLException {
        PsicologoDBDAO dao = new PsicologoDBDAO();
        Psicologo p  = new Psicologo(0, "Teste", 0, 0);
        dao.remove(p);
    }

    @Test
    void insere() throws SQLException {
        Paciente p = new Paciente(0, "Teste", "00000000000", 0);
        dao.insere(p);
        Paciente buscado = dao.buscaPorCodigo(0);
        assertAll("Verificação completa do paciente",
                () -> assertEquals(0, buscado.getPacienteID(), "ID incorreto"),
                () -> assertEquals("Teste", buscado.getNome(), "Nome incorreto"),
                () -> assertEquals("00000000000", buscado.getCpf(), "CPF incorreto"),
                () -> assertEquals(0, buscado.getPsicologo_ID(), "ID do psicólogo incorreto")
        );
        dao.remove(buscado);
    }

    @Test
    void atualiza() throws SQLException {
        Paciente p = new Paciente(0, "Teste", "00000000000", 0);
        dao.insere(p);
        p.setNome("TesteAtualizado");
        dao.atualiza(p);
        Paciente buscado = dao.buscaPorCodigo(0);
        assertAll("Verificação completa do paciente",
                () -> assertEquals(0, buscado.getPacienteID(), "ID incorreto"),
                () -> assertEquals("TesteAtualizado", buscado.getNome(), "Nome incorreto"),
                () -> assertEquals("00000000000", buscado.getCpf(), "CPF incorreto"),
                () -> assertEquals(0, buscado.getPsicologo_ID(), "ID do psicólogo incorreto")
        );
        dao.remove(buscado);
    }

    @Test
    void remove() throws SQLException {
        Paciente p = new Paciente(0, "Teste", "00000000000", 0);
        dao.insere(p);
        dao.remove(p);
        assertThrows(SQLDataException.class, () -> dao.buscaPorCodigo(0));
    }

    @Test
    void buscaPorCodigo() throws SQLException {
        Paciente p = new Paciente(0, "Teste", "00000000000", 0);
        dao.insere(p);
        Paciente buscado = dao.buscaPorCodigo(0);
        assertAll("Verificação completa do paciente",
                () -> assertEquals(0, buscado.getPacienteID(), "ID incorreto"),
                () -> assertEquals("Teste", buscado.getNome(), "Nome incorreto"),
                () -> assertEquals("00000000000", buscado.getCpf(), "CPF incorreto"),
                () -> assertEquals(0, buscado.getPsicologo_ID(), "ID do psicólogo incorreto")
        );
        dao.remove(p);
    }

    @Test
    void listaTodos() throws SQLException {
        Paciente p = new Paciente(0, "Teste", "00000000000", 0);
        dao.insere(p);
        List<Paciente> lista = dao.listaTodos();
        assertFalse(lista.isEmpty());
        dao.remove(p);
    }
}