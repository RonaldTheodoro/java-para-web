package br.com.javaparaweb.capitulo03.conexao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        SessionFactory sessao = null;
        try {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder registradorServico;
            registradorServico = new StandardServiceRegistryBuilder();
            registradorServico.applySettings(cfg.getProperties());
            StandardServiceRegistry servico = registradorServico.build();
            sessao = cfg.buildSessionFactory(servico);
        } catch (Throwable e) {
            System.out.println(
                "Criação inicial do objeto SessionFactory falhou. Erro: "
                + e.getMessage()
            );
        }
        return sessao;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
