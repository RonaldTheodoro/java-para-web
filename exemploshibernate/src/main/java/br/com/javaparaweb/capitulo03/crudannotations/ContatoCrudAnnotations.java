package br.com.javaparaweb.capitulo03.crudannotations;

import java.sql.Date;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import br.com.javaparaweb.capitulo03.conexao.HibernateUtil;

public class ContatoCrudAnnotations {
    private Session sessao;

    public ContatoCrudAnnotations(Session sessao) {
        this.sessao = sessao;
    }

    public void salvar(Contato contato) {
        sessao.save(contato);
    }

    public void atualizar(Contato contato) {
        sessao.update(contato);
    }

    public void excluir(Contato contato) {
        sessao.delete(contato);
    }

    public List<Contato> listar() {
        Query consulta = sessao.createQuery("FROM Contato");
        List<Contato> list = consulta.list();
        return list;
    }

    public Contato buscarContato(int codigo) {
        String hql = "FROM Contato WHERE codigo = :parametro";
        Query consulta = sessao.createQuery(hql);
        consulta.setParameter("parametro", codigo);
        return (Contato) consulta.uniqueResult();
    }

    public static void main(String[] args) throws SecurityException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        ContatoCrudAnnotations contatoCrud;
        contatoCrud = new ContatoCrudAnnotations(sessao);

        Contato contato01 = new Contato();
        contato01.setNome("Solanu");
        contato01.setTelefone("(99)3333-4444");
        contato01.setEmail("solanu@javaparaweb.com");
        contato01.setDataCadastro(new Date(System.currentTimeMillis()));
        contato01.setObservacao("Novo cliente");
        contatoCrud.salvar(contato01);
        contato01.setObservacao("Retomar contato");
        contatoCrud.atualizar(contato01);

        Contato contato02 = new Contato();
        contato02.setNome("Lunare");
        contato02.setTelefone("(99)7777-5555");
        contato02.setEmail("lunare@javaparaweb.com");
        contato02.setDataCadastro(new Date(System.currentTimeMillis()));
        contato02.setObservacao("Cliente em dia");
        contatoCrud.salvar(contato02);

        System.out.println(
            "Total de registros cadatrados: " + contatoCrud.listar().size()
        );
        contatoCrud.excluir(contato02);
        transacao.commit();
        System.out.println(
            "Total de registros cadatrados: " + contatoCrud.listar().size()
        );
    }

}
