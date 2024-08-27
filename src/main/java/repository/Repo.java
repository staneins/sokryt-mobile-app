package repository;

public interface Repo<T> {
    T getById(Long id);
}
