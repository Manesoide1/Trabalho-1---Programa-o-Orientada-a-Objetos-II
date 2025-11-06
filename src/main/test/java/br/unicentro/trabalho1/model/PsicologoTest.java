package br.unicentro.trabalho1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PsicologoTest {

    private Psicologo psicologo;

    @BeforeEach
    void setUp() {
        psicologo = new Psicologo(0, "Teste", 0, 0);
    }

    @Test
    void testGetAndSetPsicologoID() {
        assertEquals(0, psicologo.getPsicologoID(), "O ID do psicologo deve ser 0.");
        psicologo.setPsicologoID(2);
        assertEquals(2, psicologo.getPsicologoID(), "O ID do psicologo não foi corretamente atribuído.");
    }

    @Test
    void testGetAndSetNome() {
        assertEquals("Teste", psicologo.getNome(), "O nome inicial deve ser 'Teste'.");
        psicologo.setNome("TesteAtualizado");
        assertEquals("TesteAtualizado", psicologo.getNome(), "O nome não foi corretamente atribuído.");
    }

    @Test
    void testGetAndSetCpf() {
        assertEquals(0, psicologo.getTempoAtuacao(), "O tempo de atuação inicial deve ser 0.");
        psicologo.setTempoAtuacao(0);
        assertEquals(0, psicologo.getTempoAtuacao(), "O valor do tempo de atuação não foi corretamente atribuído.");
    }

    @Test
    void testGetAndSetSalario() {
        assertEquals(0, psicologo.getSalario(), "O salario inicial do psicologo deve ser 0.");
        psicologo.setSalario(-1);
        assertEquals(-1, psicologo.getSalario(), "O salario não foi corretamente atribuído.");
    }

    @Test
    void testToString() {
        String expected = "Psicologo{codigo=0, nome=Teste, tempo de atuação=0, salario=0.0}";
        assertEquals(expected, psicologo.toString(), "O método toString() não retornou o valor esperado.");
    }
}