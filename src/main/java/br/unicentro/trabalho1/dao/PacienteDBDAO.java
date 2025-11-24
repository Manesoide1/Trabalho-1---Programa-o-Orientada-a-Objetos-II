package br.unicentro.trabalho1.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.unicentro.trabalho1.model.Paciente;

public class PacienteDBDAO implements PacienteDAO {

    // REFATORAÇÃO
    // Nomenclatura centralizada das colunas
    // Usar constantes evita erros de digitação, facilita manutenção e aumenta a clareza.
    private static final String COL_ID = "paciente_id";
    private static final String COL_NOME = "nome";
    private static final String COL_CPF = "cpf";
    private static final String COL_PSICO = "psicologo_id";

    // Inserir paciente
    public void insere(Paciente paciente) throws SQLException {
        try (Connection con = Conexao.getConexao()) {

            /*
               Antes de inserir um novo paciente, o sistema realiza uma validação importante:
               garante que o CPF informado não esteja cadastrado para outra pessoa.

               Esse passo previne duplicação de registros e garante integridade lógica da tabela.
             */
            String checkSql = "SELECT paciente_id FROM paciente WHERE cpf = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, paciente.getCpf());

                try (ResultSet rs = checkStmt.executeQuery()) {
                    // Se o SELECT retornar algo, significa que já existe um paciente com esse CPF
                    if (rs.next()) {
                        throw new SQLDataException("CPF já cadastrado para outro paciente!");
                    }
                }
            }

            /*
               Com a verificação concluída, o sistema tenta inserir o novo registro.

               Observações importantes:
               • O ID é informado manualmente pelo objeto Paciente.
               • O comando utiliza ON CONFLICT(paciente_id) DO NOTHING para evitar erro caso o ID já exista.
               • RETURNING paciente_id retorna o ID realmente inserido (ou nada se houve conflito).
             */
            String sql =
                    "INSERT INTO paciente (paciente_id, nome, cpf, psicologo_id) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT (paciente_id) DO NOTHING RETURNING paciente_id";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                // Preenche os parâmetros do comando SQL com os valores do objeto Paciente
                stmt.setInt(1, paciente.getPacienteID());
                stmt.setString(2, paciente.getNome());
                stmt.setString(3, paciente.getCpf());
                stmt.setInt(4, paciente.getPsicologo_ID());

                /*
                   Como o comando possui RETURNING, utilizamos executeQuery() ao invés de executeUpdate().
                   • Se retornar um resultado, o paciente foi inserido corretamente.
                   • Caso contrário, houve conflito de ID e o registro não foi criado.
                 */
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        paciente.setPacienteID(rs.getInt(COL_ID));
                    } else {
                        // Conflito no ID — o registro não foi inserido
                        throw new SQLDataException("Paciente com ID " + paciente.getPacienteID() + " já existe.");
                    }
                }
            }
        }
    }

    // Atualizar paciente
    public void atualiza(Paciente paciente) throws SQLException {
        String sql = "UPDATE paciente SET nome = ?, cpf = ?, psicologo_id = ? WHERE paciente_id = ?";

        /*
           Esse método atualiza todos os dados do paciente — desde nome até o psicólogo responsável.

           executeUpdate() retorna o número de linhas afetadas.
           Se nenhuma linha foi alterada, significa que o ID informado não existe na tabela.
         */
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getPsicologo_ID());
            stmt.setInt(4, paciente.getPacienteID());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLDataException("Paciente com ID " + paciente.getPacienteID() + " não encontrado.");
            }
        }
    }

    /*
        REFATORAÇÃO

        Método não estava verificando primeiramente se o paciente existia na tabela.
        Agora ele verifica e retorna uma mensagem de erro caso o paciente não esteja previamente na tabela para ser excluído.
    */

    // Remover paciente
    public void remove(Paciente paciente) throws SQLException {
        // Primeiro SELECT para verificar se o paciente existe
        String checkSql = "SELECT 1 FROM paciente WHERE paciente_id = ?";
        // DELETE executado somente após a verificação
        String deleteSql = "DELETE FROM paciente WHERE paciente_id = ?";

        try (Connection con = Conexao.getConexao();
             PreparedStatement checkStmt = con.prepareStatement(checkSql)) {

            /*
               Verificação prévia:

               – Evita executar um DELETE sem sentido.
               – Permite retornar mensagens de erro mais claras ao usuário.
             */
            checkStmt.setInt(1, paciente.getPacienteID());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    // Não existe registro no banco para esse ID
                    throw new SQLDataException("Paciente com ID " + paciente.getPacienteID() + " não encontrado.");
                }
            }

            /*
               Registro confirmado — agora sim executamos o DELETE de forma segura.
             */
            try (PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, paciente.getPacienteID());
                deleteStmt.executeUpdate();
            }
        }
    }

    // REFATORAÇÃO
    // Método para preencher objeto Paciente
    private Paciente preencheDadosPaciente(ResultSet rs) throws SQLException {
        /*
            Esse método encapsula a lógica de leitura do ResultSet e preenchimento
            do objeto Paciente.

            Ele garante:
            • Centralização da lógica de conversão de dados
            • Evita repetição de código nos métodos de consulta
         */
        Paciente paciente = new Paciente();
        paciente.setPacienteID(rs.getInt(COL_ID));
        paciente.setNome(rs.getString(COL_NOME));
        paciente.setCpf(rs.getString(COL_CPF));
        paciente.setPsicologo_ID(rs.getInt(COL_PSICO));
        return paciente;
    }

    // Buscar paciente por código
    public Paciente buscaPorCodigo(int pacienteID) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE paciente_id = ?";

        /*
            Esse método faz a busca por chave primária.
            Se encontrar o registro, converte-o para um objeto Paciente.
            Se não encontrar, lança uma exceção informativa.
         */
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pacienteID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return preencheDadosPaciente(rs);
                } else {
                    throw new SQLDataException("Paciente com ID " + pacienteID + " não encontrado.");
                }
            }
        }
    }

    // Listar todos os pacientes
    public List<Paciente> listaTodos() throws SQLException {
        String sql = "SELECT * FROM paciente";
        List<Paciente> pacientes = new ArrayList<>();

        /*
           Retorna todos os pacientes armazenados na tabela.
           ExecuteQuery é usado porque o comando é SELECT.
         */
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Para cada linha retornada, cria um objeto Paciente preenchido
            while (rs.next()) {
                pacientes.add(preencheDadosPaciente(rs));
            }
        }
        return pacientes;
    }
}