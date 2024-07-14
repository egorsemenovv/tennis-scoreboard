package org.egorsemenovv.tennisscoreboard.repository;

import lombok.Getter;
import org.egorsemenovv.tennisscoreboard.exception.RepositoryException;
import org.egorsemenovv.tennisscoreboard.model.Match;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.egorsemenovv.tennisscoreboard.util.HibernateUtil;
import java.util.List;

public class MatchRepository implements Repository<Integer, Match> {

    @Getter
    private static final MatchRepository INSTANCE = new MatchRepository();

    private MatchRepository(){}

    public Long findAllMatchesCountByPlayerName(String playerName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(m.id) from Match m " +
                                       "where m.player1.name ilike ?1 or m.player2.name ilike ?1", Long.class)
                    .setParameter(1, "%"+playerName+"%")
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new RepositoryException("Database error");
        }
    }

    public List<Match> findMatchesWithLimitAndOffsetByName(int limit, int offset, String playerName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select m from Match m " +
                                       "join fetch m.player1 p1 " +
                                       "join fetch m.player2 p2 " +
                                       "join fetch m.winner w " +
                                       "where p1.name ilike ?1 or p2.name ilike ?1", Match.class)
                    .setParameter(1, "%"+playerName+"%")
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .list();
        } catch (HibernateException e) {
            throw new RepositoryException("Database error");
        }
    }

    @Override
    public Match save(Match entity) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }catch (HibernateException e){
            throw new RepositoryException("Database error!");
        }
    }

}
