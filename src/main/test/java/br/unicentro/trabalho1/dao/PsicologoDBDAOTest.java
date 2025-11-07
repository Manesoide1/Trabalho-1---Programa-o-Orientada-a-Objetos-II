package br.unicentro.trabalho1.dao;

import br.unicentro.trabalho1.model.Psicologo;
import org.junit.jupiter.api.Test;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PsicologoDBDAOTest {

    private final PsicologoDBDAO dao = new PsicologoDBDAO();

    @Test
    void insere() throws SQLException {
        Psicologo p = new Psicologo(0, "Teste", 0, 0);
        dao.insere(p);
        Psicologo buscado = dao.buscaPorCodigo(0);
        assertAll("Verificação completa do psicólogo",
                () -> assertEquals(0, buscado.getPsicologoID(), "ID incorreto"),
                () -> assertEquals("Teste", buscado.getNome(), "Nome incorreto"),
                () -> assertEquals(0, buscado.getTempoAtuacao(), "Tempo incorreto"),
                () -> assertEquals(0, buscado.getSalario(), "Salario incorreto")
        );
        dao.remove(buscado);
    }

    @Test
    void atualiza() throws SQLException {
        Psicologo p = new Psicologo(0, "Teste", 0, 0);
        dao.insere(p);
        p.setNome("TesteAtualizado");
        dao.atualiza(p);
        Psicologo buscado = dao.buscaPorCodigo(0);
        assertAll("Verificação completa do psicólogo",
                () -> assertEquals(0, buscado.getPsicologoID(), "ID incorreto"),
                () -> assertEquals("TesteAtualizado", buscado.getNome(), "Nome incorreto"),
                () -> assertEquals(0, buscado.getTempoAtuacao(), "Tempo incorreto"),
                () -> assertEquals(0, buscado.getSalario(), "Salario incorreto")
        );
        dao.remove(buscado);
    }

    @Test
    void remove() throws SQLException {
        Psicologo p = new Psicologo(0, "Teste", 0, 0);
        dao.insere(p);
        dao.remove(p);
        assertThrows(SQLDataException.class, () -> dao.buscaPorCodigo(0), "Psicologo não foi removido.");
    }

    @Test
    void buscaPorCodigo() throws SQLException {
        Psicologo p = new Psicologo(0, "Teste", 0, 0);
        dao.insere(p);
        Psicologo buscado = dao.buscaPorCodigo(0);
        assertAll("Verificação completa do psicólogo",
                () -> assertEquals(0, buscado.getPsicologoID(), "ID incorreto"),
                () -> assertEquals("Teste", buscado.getNome(), "Nome incorreto"),
                () -> assertEquals(0, buscado.getTempoAtuacao(), "Tempo incorreto"),
                () -> assertEquals(0, buscado.getSalario(), "Salario incorreto")
        );
        dao.remove(p);
    }

    @Test
    void listaTodos() throws SQLException {
        Psicologo p = new Psicologo(0, "Teste", 0, 0);
        dao.insere(p);
        List<Psicologo> lista = dao.listaTodos();
        assertFalse(lista.isEmpty(), "Lista voltou vazia.");
        dao.remove(p);
    }
}