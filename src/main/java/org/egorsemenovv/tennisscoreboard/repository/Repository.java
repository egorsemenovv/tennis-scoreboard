package org.egorsemenovv.tennisscoreboard.repository;


public interface Repository<K, E> {
    E save(E entity);
}
