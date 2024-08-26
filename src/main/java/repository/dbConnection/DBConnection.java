package repository.dbConnection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConnection {

    private Session session;

    public Session getSession(Class someClass) {
        if (session == null){
            SessionFactory sessionFactory = new Configuration()
                    .addAnnotatedClass(someClass)
                    .buildSessionFactory();

            session = sessionFactory.openSession();
        }

        return session;
    }


}