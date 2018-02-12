package br.com.javaparaweb.capitulo03.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaPostgres {

    public static void main(String[] args) {
        Connection conexao = null;
        try {
            String url = "jdbc:postgresql://172.17.0.2:5432/app";
            String usuario = "postgres";
            String senha = "asdf1234";
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conectou!");
            conexao.close();
        } catch (SQLException e) {
            System.out.println(
                "Ocorreu um erro ao criar a conexão. Erro: " + e.getMessage()
            );
        }
    }

}
