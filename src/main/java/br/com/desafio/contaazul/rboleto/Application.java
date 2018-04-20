package br.com.desafio.contaazul.rboleto;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
    }
}

