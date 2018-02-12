package br.com.javaparaweb.capitulo03.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaSQLite {

    public static void main(String[] args) {
        Connection conexao = null;
        try {
            String url = "jdbc:sqlite:db.sqlite3";
            conexao = DriverManager.getConnection(url);
            System.out.println("Conectou!");
            conexao.close();
        } catch (SQLException e) {
            System.out.println(
                "Ocorreu um erro ao criar a conexão. Erro: " + e.getMessage()
            );
        }
    }

}
