package br.unicentro.trabalho1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente(0, "Teste", "00000000000", 0);
    }

    @Test
    void testGetAndSetPacienteID() {
        assertEquals(0, paciente.getPacienteID(), "O ID do paciente deve ser 0.");
        paciente.setPacienteID(2);
        assertEquals(2, paciente.getPacienteID(), "O ID do paciente não foi corretamente atribuído.");
    }

    @Test
    void testGetAndSetNome() {
        assertEquals("Teste", paciente.getNome(), "O nome inicial deve ser 'Teste'.");
        paciente.setNome("TesteAtualizado");
        assertEquals("TesteAtualizado", paciente.getNome(), "O nome não foi corretamente atribuído.");
    }

    @Test
    void testGetAndSetCpf() {
        assertEquals("00000000000", paciente.getCpf(), "O CPF inicial deve ser 00000000000.");
        paciente.setCpf("99999999999");
        assertEquals("99999999999", paciente.getCpf(), "O valor do CPF não foi corretamente atribuído.");
    }
    
    @Test
    void testGetAndSetPsicologo_ID() {
        assertEquals(0, paciente.getPsicologo_ID(), "O ID inicial do psicologo deve ser 0.");
        paciente.setPsicologo_ID(-1);
        assertEquals(-1, paciente.getPsicologo_ID(), "O ID não foi corretamente atribuído.");
    }

    @Test
    void testToString() {
        String expected = "Paciente{codigo=0, nome=Teste, cpf=00000000000, psicologo_ID=0}";
        assertEquals(expected, paciente.toString(), "O método toString() não retornou o valor esperado.");
    }
}