package repository;

import entity.Poem;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.dbConnection.DBConnection;

public class PoemRepo implements Repo<Poem>{

    private final DBConnection dbConnection;

    public PoemRepo(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    @Override
    public Poem getById(Long id) {

        try (Session session = dbConnection.getSession(Poem.class)) {

            Query<Poem> query = session
                    .createQuery("select sup from Poem sup where id = :poemId", Poem.class)
                    .setParameter("poemId", id);

            Poem singleResult = query.getSingleResult();
            return singleResult;
        }
    }
}
