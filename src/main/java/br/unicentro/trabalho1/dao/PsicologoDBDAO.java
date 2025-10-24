package br.unicentro.trabalho1.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.unicentro.trabalho1.model.Psicologo;

public class PsicologoDBDAO implements PsicologoDAO, IConst {

    // Inserir psicologo (ID gerado automaticamente pelo banco)
    public void insere(Psicologo psicologo) throws SQLException {
        String sql = "INSERT INTO psicologo (psicologo_id, nome, tempo_atuacao, salario) VALUES (?, ?, ?, ?) ON CONFLICT (psicologo_id) DO NOTHING RETURNING psicologo_id";
        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, psicologo.getPsicologoID());
            stmt.setString(2, psicologo.getNome());
            stmt.setInt(3, psicologo.getTempoAtuacao());
            stmt.setDouble(4, psicologo.getSalario());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Inseriu com sucesso
                    psicologo.setPsicologoID(rs.getInt("psicologo_id"));
                } else {
                    // O ID já existe
                    throw new SQLDataException("Psicólogo com ID " + psicologo.getPsicologoID() + " já existe.");
                }
            }
        }
    }

    // Atualizar psicologo
    public void atualiza(Psicologo psicologo) throws SQLException {
        String sql = "UPDATE psicologo SET nome = ?, tempo_atuacao = ?, salario = ? WHERE psicologo_id = ?";

        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            // Configura os parâmetros
            stmt.setString(1, psicologo.getNome());
            stmt.setInt(2, psicologo.getTempoAtuacao());
            stmt.setDouble(3, psicologo.getSalario());
            stmt.setInt(4, psicologo.getPsicologoID());

            // Executa o UPDATE
            int linhasAfetadas = stmt.executeUpdate();

            // Se não afetou nenhuma linha, o ID não existe
            if (linhasAfetadas == 0) {
                throw new SQLDataException("Psicólogo com ID " + psicologo.getPsicologoID() + " não encontrado.");
            }
        }
    }

    /*// Remover psicologo
    public void remove(Psicologo psicologo) throws SQLException {
        String sql = "DELETE FROM psicologo WHERE psicologo_id = ?";
        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, psicologo.getPsicologoID());
            stmt.executeUpdate();
        }
    }*/

    public void remove(Psicologo psicologo) throws SQLException {
        String checkSql = "SELECT 1 FROM psicologo WHERE psicologo_id = ?";
        String deleteSql = "DELETE FROM psicologo WHERE psicologo_id = ?";

        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement checkStmt = con.prepareStatement(checkSql)) {

            checkStmt.setInt(1, psicologo.getPsicologoID());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    // Não encontrou o psicólogo
                    throw new SQLDataException("Psicólogo com ID " + psicologo.getPsicologoID() + " não encontrado.");
                }
            }

            // Psicólogo existe, pode deletar
            try (PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, psicologo.getPsicologoID());
                deleteStmt.executeUpdate();
            }
        }
    }


    // Buscar psicologo por c�digo
    public Psicologo buscaPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM psicologo WHERE psicologo_id = ?";
        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Psicologo psicologo = new Psicologo();
                    psicologo.setPsicologoID(rs.getInt("psicologo_id"));
                    psicologo.setNome(rs.getString("nome"));
                    return psicologo;
                } else {
                        // Não encontrou o psicólogo
                        throw new SQLDataException("Psicólogo com ID " + codigo + " não encontrado.");
                }
            }
        }
    }

    // Listar todas as psicologos
    public List<Psicologo> listaTodos() throws SQLException {
        String sql = "SELECT * FROM psicologo";
        List<Psicologo> psicologos = new ArrayList<>();

        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Psicologo psicologo = new Psicologo();
                psicologo.setPsicologoID(rs.getInt("psicologo_id"));
                psicologo.setNome(rs.getString("nome"));
                psicologo.setTempoAtuacao(rs.getInt("tempo_atuacao"));
                psicologo.setSalario(rs.getDouble("salario"));
                psicologos.add(psicologo);
            }
        }
        return psicologos;
    }
}