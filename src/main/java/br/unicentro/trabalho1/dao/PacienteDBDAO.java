package br.unicentro.trabalho1.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.unicentro.trabalho1.model.Paciente;

public class PacienteDBDAO implements PacienteDAO, IConst {

    public void insere(Paciente paciente) throws SQLException {
        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha)) {

            // Verifica se o CPF já existe
            String checkSql = "SELECT paciente_id FROM paciente WHERE cpf = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, paciente.getCpf());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        throw new SQLDataException("CPF já cadastrado para outro paciente!");
                    }
                }
            }

            // Inserção
            String sql = "INSERT INTO paciente (paciente_id, nome, cpf, psicologo_id) VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT (paciente_id) DO NOTHING RETURNING paciente_id";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, paciente.getPacienteID());
                stmt.setString(2, paciente.getNome());
                stmt.setString(3, paciente.getCpf());
                stmt.setInt(4, paciente.getPsicologo_ID());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        paciente.setPacienteID(rs.getInt("paciente_id"));
                    } else {
                        // O ID já existe
                        throw new SQLDataException("Paciente com ID " + paciente.getPacienteID() + " já existe.");
                    }
                }
            }
        }
    }

    // Atualizar paciente
    public void atualiza(Paciente paciente) throws SQLException {
        String sql = "UPDATE paciente SET nome = ?, cpf = ?, psicologo_id = ? WHERE paciente_id = ?";
        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
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

    // Remover paciente
    public void remove(Paciente paciente) throws SQLException {
        String sql = "DELETE FROM paciente WHERE paciente_id = ?";
        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, paciente.getPacienteID());
            stmt.executeUpdate();
        }
    }

    // Buscar paciente por c�digo
    public Paciente buscaPorCodigo(int pacienteID) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE paciente_id = ?";
        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pacienteID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setPacienteID(rs.getInt("paciente_id"));
                    paciente.setNome(rs.getString("nome"));
                    return paciente;
                } else {
                    // Não encontrou o psicólogo
                    throw new SQLDataException("Paciente com ID " + pacienteID + " não encontrado.");
                }
            }
        }
    }

    // Listar todos os pacientes
    public List<Paciente> listaTodos() throws SQLException {
        String sql = "SELECT * FROM paciente";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection con = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setPacienteID(rs.getInt("paciente_id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setPsicologo_ID(rs.getInt("psicologo_ID"));
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }
}