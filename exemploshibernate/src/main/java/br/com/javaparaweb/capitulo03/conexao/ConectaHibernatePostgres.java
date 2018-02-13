package br.com.javaparaweb.capitulo03.conexao;

import org.hibernate.Session;

public class ConectaHibernatePostgres {

    public static void main(String[] args) {
        Session sessao = null;
        sessao = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Conectou!");
        sessao.close();
    }

}
