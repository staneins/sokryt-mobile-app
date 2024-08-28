package repository.dbConnection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConnection {

    private SessionFactory sessionFactory;

    public DBConnection() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

