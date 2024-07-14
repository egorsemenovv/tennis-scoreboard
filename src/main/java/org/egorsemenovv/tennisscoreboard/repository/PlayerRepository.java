package org.egorsemenovv.tennisscoreboard.repository;

import lombok.Getter;
import org.egorsemenovv.tennisscoreboard.exception.RepositoryException;
import org.egorsemenovv.tennisscoreboard.model.Player;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.egorsemenovv.tennisscoreboard.util.HibernateUtil;
import java.util.Optional;

public class PlayerRepository implements Repository<Integer, Player> {

    @Getter
    private static final PlayerRepository INSTANCE = new PlayerRepository();

    private PlayerRepository(){}

    @Override
    public Player save(Player entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RepositoryException("Player with name: ".concat(entity.getName()));
        }
        return entity;
    }

    public Optional<Player> findByName(String playerName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Optional<Player> player = session.createQuery("select p from Player p " +
                                                          "where p.name = :name", Player.class)
                    .setParameter("name", playerName)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return player;
        } catch (HibernateException e) {
            throw new RepositoryException("Database error!");
        }
    }
}
