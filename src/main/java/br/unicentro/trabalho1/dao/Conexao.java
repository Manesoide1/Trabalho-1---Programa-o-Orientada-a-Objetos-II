package br.unicentro.trabalho1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    REFATORAÇÃO

    Fim da Interface IConst, apenas servia para armazenar às variáveis stringDeConexao, usuario, senha.
    Fica mais legível com tudo na Classe Conexao.

    Remoção dos parâmetros no método getConexao, isso tornou a conexão nos métodos muito mais limpa e
    legível. Sem a necessidade de passar parâmetros que no fim eram sempre os mesmos.
 */

public class Conexao {

    // Dados de conexão fixos para o banco PostgreSQL utilizado pelo sistema.
    // Como são final e static, representam constantes compartilhadas entre todas
    // as instâncias, protegendo contra alterações acidentais.
    public static final String stringDeConexao = "jdbc:postgresql://localhost:5432/Clinica_de_Psicologos";

    // Nome de usuário do banco de dados.
    public static final String usuario = "postgres";

    // Senha do banco de dados.
    public static final String senha = "postgres";

    /*
        Método responsável por abrir a conexão com o banco de dados.

        Observações importantes:

        - O método é estático, o que permite ser chamado diretamente pela classe
          sem precisar instanciar um objeto Conexao.

        - DriverManager.getConnection(...) retorna um objeto do tipo Connection,
          que representa o canal ativo de comunicação com o banco de dados.

        - Foi retirado o recebimento de parâmetros como antes acontecia (stringDeConexao,
          usuário e senha). Agora, tudo é lido diretamente das constantes definidas acima,
          garantindo maior simplicidade e consistência: todos os DAOs utilizam exatamente a
          mesma configuração.

        - Caso ocorra qualquer erro ao tentar conectar ao banco, o SQLException é capturado
          e convertido em uma RuntimeException. Isso evita que cada classe que chama
          getConexao seja obrigada a tratar essa exceção manualmente, deixando o código
          mais limpo nos DAOs.
     */
    public static Connection getConexao() {
        try {
            // Tentativa de estabelecer a conexão utilizando os dados fornecidos acima.
            return DriverManager.getConnection(stringDeConexao, usuario, senha);
        } catch (SQLException e) {
            // Converte para RuntimeException para simplificar a propagação do erro
            // sem exigir throws em cada método DAO.
            throw new RuntimeException(e);
        }
    }
}