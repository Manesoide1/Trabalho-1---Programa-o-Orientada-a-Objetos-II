package br.unicentro.trabalho1.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.unicentro.trabalho1.model.Psicologo;

public class PsicologoDBDAO implements PsicologoDAO {

    // REFATORAÇÃO
    // Nomenclatura centralizada das colunas
    // Essas constantes ajudam a evitar erros de digitação, facilitam refatorações e
    // tornam claro quais nomes de coluna realmente existem na tabela.
    // Se algum nome de coluna mudar no banco, basta ajustar aqui.
    private static final String COL_ID = "psicologo_id";
    private static final String COL_NOME = "nome";
    private static final String COL_TEMPO = "tempo_atuacao";
    private static final String COL_SALARIO = "salario";

    // Inserir psicologo (ID gerado automaticamente pelo banco)
    public void insere(Psicologo psicologo) throws SQLException {
        // A instrução SQL utiliza as constantes declaradas acima, garantindo consistência.
        // O "ON CONFLICT DO NOTHING" evita que seja inserido um psicólogo com ID já existente.
        // O "RETURNING" devolve o ID gerado ou confirmado.
        String sql = "INSERT INTO psicologo (" + COL_ID + ", " + COL_NOME + ", " + COL_TEMPO + ", " + COL_SALARIO +
                ") VALUES (?, ?, ?, ?) ON CONFLICT (" + COL_ID + ") DO NOTHING RETURNING " + COL_ID;

        // Uso de try-with-resources garante fechamento automático da conexão e do statement.
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            // Preenchimento dos parâmetros da instrução SQL
            stmt.setInt(1, psicologo.getPsicologoID());
            stmt.setString(2, psicologo.getNome());
            stmt.setInt(3, psicologo.getTempoAtuacao());
            stmt.setDouble(4, psicologo.getSalario());

            // Execução e captura do resultado.
            // Como há RETURNING, utilizamos executeQuery().
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Atualiza o ID no objeto caso o banco o retorne
                    psicologo.setPsicologoID(rs.getInt(COL_ID));
                } else {
                    // Caso o insert não aconteça (por conflito),
                    // lançamos uma exceção indicando o motivo.
                    throw new SQLDataException("Psicólogo com ID " + psicologo.getPsicologoID() + " já existe.");
                }
            }
        }
    }

    // Atualizar psicologo
    public void atualiza(Psicologo psicologo) throws SQLException {
        // Atualiza apenas as colunas nome, tempo e salário com base no ID
        String sql = "UPDATE psicologo SET " +
                COL_NOME + " = ?, " +
                COL_TEMPO + " = ?, " +
                COL_SALARIO + " = ? WHERE " +
                COL_ID + " = ?";

        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            // Preenchendo os parâmetros do UPDATE
            stmt.setString(1, psicologo.getNome());
            stmt.setInt(2, psicologo.getTempoAtuacao());
            stmt.setDouble(3, psicologo.getSalario());
            stmt.setInt(4, psicologo.getPsicologoID());

            // executeUpdate retorna o número de linhas alteradas
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                // Se nenhuma linha for afetada, significa que o ID não existe
                throw new SQLDataException("Psicólogo com ID " + psicologo.getPsicologoID() + " não encontrado.");
            }
        }
    }

    // Remover psicologo
    public void remove(Psicologo psicologo) throws SQLException {
        // Primeiro SELECT para verificar se o registro existe antes de tentar remover.
        // Isso permite dar uma mensagem de erro mais clara.
        String checkSql = "SELECT 1 FROM psicologo WHERE " + COL_ID + " = ?";
        // Comando definitivo de remoção
        String deleteSql = "DELETE FROM psicologo WHERE " + COL_ID + " = ?";

        try (Connection con = Conexao.getConexao();
             PreparedStatement checkStmt = con.prepareStatement(checkSql)) {

            // Verificação da existência do psicólogo
            checkStmt.setInt(1, psicologo.getPsicologoID());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    // Caso o SELECT não retorne nada, o ID não existe
                    throw new SQLDataException("Psicólogo com ID " + psicologo.getPsicologoID() + " não encontrado.");
                }
            }

            // Caso exista, então executamos o DELETE
            try (PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, psicologo.getPsicologoID());
                deleteStmt.executeUpdate();
            }
        }
    }

    // REFATORAÇÃO
    // Método para preencher objeto Psicologo
    // Este método encapsula toda a lógica de leitura dos campos do ResultSet e montagem
    // do objeto correspondente, evitando repetição de código em diversos pontos.
    private Psicologo preencheDadosPsicologo(ResultSet rs) throws SQLException {
        Psicologo psicologo = new Psicologo();
        psicologo.setPsicologoID(rs.getInt(COL_ID));
        psicologo.setNome(rs.getString(COL_NOME));
        psicologo.setTempoAtuacao(rs.getInt(COL_TEMPO));
        psicologo.setSalario(rs.getDouble(COL_SALARIO));
        return psicologo;
    }

    // Buscar psicologo por código
    public Psicologo buscaPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM psicologo WHERE " + COL_ID + " = ?";

        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Uso do método que preenche o objeto a partir do ResultSet
                    return preencheDadosPsicologo(rs);
                } else {
                    // Caso não exista registro com esse ID
                    throw new SQLDataException("Psicólogo com ID " + codigo + " não encontrado.");
                }
            }
        }
    }

    // Listar todos
    public List<Psicologo> listaTodos() throws SQLException {
        String sql = "SELECT * FROM psicologo";

        // Lista onde todos os psicólogos encontrados serão armazenados
        List<Psicologo> psicologos = new ArrayList<>();

        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Percorre todos os registros retornados pela consulta
            while (rs.next()) {
                psicologos.add(preencheDadosPsicologo(rs));
            }
        }

        return psicologos;
    }
}