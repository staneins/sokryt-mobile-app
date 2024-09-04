package repository;

import entity.Poem;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.dbConnection.DBConnection;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PoemRepo implements Repo<Poem> {

    private final DBConnection dbConnection;

    public PoemRepo(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Poem getById(Long id) {
        try (Session session = dbConnection.getSession()) {
            Query<Poem> query = session
                    .createQuery("select p from Poem p where p.id = :poemId", Poem.class)
                    .setParameter("poemId", id);

            return query.getSingleResult();
        }
    }
    public List<Poem> getAll() {
        try (Session session = dbConnection.getSession()) {
            Query<Poem> query = session.createQuery("from Poem p where p.bundle = :bundle", Poem.class);
            query.setParameter("bundle", "poem");
            List<Poem> poems = query.list();
            Collections.sort(poems, new Comparator<Poem>() {
                @Override
                public int compare(Poem p1, Poem p2) {
                    return p1.getTitle().compareTo(p2.getTitle());
                }
            });
            return poems;
        }
    }
}
