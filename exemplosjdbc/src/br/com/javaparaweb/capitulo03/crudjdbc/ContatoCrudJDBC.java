package br.com.javaparaweb.capitulo03.crudjdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContatoCrudJDBC {

    public void salvar(Contato contato) {
        Connection conexao = this.geraConexao();
        PreparedStatement insereSt = null;
        String sql = "INSERT INTO contato"
            + "(nome, telefone, email, dt_cad, obs) VALUES (?, ?, ?, ?, ?)";
        try {
            insereSt = conexao.prepareStatement(sql);
            insereSt.setString(1, contato.getNome());
            insereSt.setString(2, contato.getTelefone());
            insereSt.setString(3, contato.getEmail());
            insereSt.setDate(4, contato.getDt_cad());
            insereSt.setString(5, contato.getObs());
            insereSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(
                "Erro ao incluir contato. Messagem: " + e.getMessage()
            );
        } finally {
            try {
                insereSt.close();
                conexao.close();
            } catch (Throwable e) {
                System.out.println(
                    "Erro ao fechar operações de inserção. Messagem: "
                    + e.getMessage()
                );
            }
        }
    }

    public void atualizar(Contato contato) {
        Connection conexao = this.geraConexao();
        PreparedStatement atualizaSt = null;

        String sql = "UPDATE contato SET"
            + "nome = ?, telefone = ?, email = ?, obs = ? WHERE codigo = ?";

        try {
            atualizaSt = conexao.prepareStatement(sql);
            atualizaSt.setString(1, contato.getNome());
            atualizaSt.setString(2, contato.getTelefone());
            atualizaSt.setString(3, contato.getEmail());
            atualizaSt.setString(4, contato.getObs());
            atualizaSt.setInt(5, contato.getCodigo());
            atualizaSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(
                "Erro ao atualizar contato. Mensagem: " + e.getMessage()
            );
        } finally {
            try {
                atualizaSt.close();
                conexao.close();
            } catch (Throwable e) {
                System.out.println(
                    "Erro ao fechar operações de atualização. Mensagem: "
                    + e.getMessage()
                );
            }
        }
    }
    
    public void excluir(Contato contato) {
        Connection conexao = this.geraConexao();
        PreparedStatement excluiSt = null;

        String sql = "DELETE FROM contato WHERE codigo = ?";

        try {
            excluiSt = conexao.prepareStatement(sql);
            excluiSt.setInt(1, contato.getCodigo());
            excluiSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir contato. Mensagem: "
                    + e.getMessage());
        } finally {
            try {
                excluiSt.close();
                conexao.close();
            } catch (Throwable e) {
                System.out
                        .println("Erro ao fechar operações de exclusão. Mensagem: "
                                + e.getMessage());
            }
        }
    }

    public List<Contato> listar() {
        Connection conexao = this.geraConexao();
        List<Contato> contatos = new ArrayList<>();
        Statement consulta = null;
        ResultSet resultado = null;
        Contato contato = null;
        String sql = "SELECT * FROM contato";
        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            while (resultado.next()) {
                contato = new Contato();
                contato.setCodigo(resultado.getInt("codigo"));
                contato.setNome(resultado.getString("nome"));
                contato.setTelefone(resultado.getString("telefone"));
                contato.setEmail(resultado.getString("email"));
                contato.setDt_cad(resultado.getDate("dt_cad"));
                contato.setObs(resultado.getString("obs"));
                contatos.add(contato);
            }
        } catch (SQLException e) {
            System.out.println(
                "Erro ao buscar código do contato. Messagem: "
                + e.getMessage()
            );
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close();
            } catch (Throwable e) {
                System.out.println(
                    "Erro ao fechar operações de inserção. Messagem: "
                    + e.getMessage()
                );
            }
        }
        return contatos;
    }

    public Contato buscaContato(int valor) {
        Connection conexao = this.geraConexao();
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        Contato contato = null;

        String sql = "SELECT * FROM contato WHERE codigo = ?";

        try {
            consulta = conexao.prepareStatement(sql);
            consulta.setInt(1, valor);
            resultado = consulta.executeQuery();

            if (resultado.next()) {
                contato = new Contato();
                contato.setCodigo(resultado.getInt("codigo"));
                contato.setNome(resultado.getString("nome"));
                contato.setTelefone(resultado.getString("telefone"));
                contato.setEmail(resultado.getString("email"));
                contato.setDt_cad(resultado.getDate("dt_cad"));
                contato.setObs(resultado.getString("obs"));
            }
        } catch (SQLException e) {
            System.out.println(
                "Erro ao buscar código do contato. Mensagem: "
                 + e.getMessage()
            );
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close();
            } catch (Throwable e) {
                System.out.println(
                    "Erro ao fechar operações de consulta. Mensagem: "
                    + e.getMessage()
                );
            }
        }
        return contato;
    }

    public Connection geraConexao() {
        Connection conexao = null;

        try {
            String url = "jdbc:postgresql://172.17.0.2:5432/app";
            String usuario = "postgres";
            String senha = "asdf1234";
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro de SQL. Erro: " + e.getMessage());
        }
        return conexao;
    }

    public static void main(String[] args) {
        ContatoCrudJDBC contatoCrudJDBC = new ContatoCrudJDBC();

        Contato beltrano = new Contato();
        beltrano.setNome("Beltrano Solar");
        beltrano.setTelefone("(47)5555-3333");
        beltrano.setEmail("beltrano@teste.com");
        beltrano.setDt_cad(new Date(System.currentTimeMillis()));
        beltrano.setObs("Novo Cliente");
        contatoCrudJDBC.salvar(beltrano);

        Contato fulano = new Contato();
        fulano.setNome("Fulano Lunar");
        fulano.setTelefone("(47)7777-2222");
        fulano.setEmail("fulano@teste.com");
        fulano.setDt_cad(new Date(System.currentTimeMillis()));
        fulano.setObs("Novo Contato - possível cliente");
        contatoCrudJDBC.salvar(fulano);

        System.out.println(
            "Contatos cadastrados: " + contatoCrudJDBC.listar().size()
        );
    }

}
