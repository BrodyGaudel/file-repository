package repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on files.
 *
 * @param <T> the type of the entity to be handled by the repository
 * @param <I> the type of the identifier of the entity
 */
public interface FileRepository<T, I> {

    /**
     * Saves a given entity.
     *
     * @param object the entity to save
     * @return the saved entity
     */
    T save(T object);

    /**
     * Saves all given entities.
     *
     * @param objects the entities to save
     * @return the saved entities
     */
    List<T> saveAll(List<T> objects);

    /**
     * Deletes the entity with the given identifier.
     *
     * @param id the identifier of the entity to delete
     */
    void deleteById(I id);

    /**
     * Deletes all given entities.
     *
     * @param objects the entities to delete
     */
    void deleteAll(List<T> objects);

    /**
     * Deletes all entities.
     */
    void deleteAll();

    /**
     * Retrieves an entity by its identifier.
     *
     * @param id the identifier of the entity to retrieve
     * @return the entity with the given identifier or {@link Optional#empty()} if none found
     */
    Optional<T> findById(I id);

    /**
     * Retrieves all entities.
     *
     * @return all entities
     */
    List<T> findAll();
}


