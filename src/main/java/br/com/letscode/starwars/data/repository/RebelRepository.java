package br.com.letscode.starwars.data.repository;

import br.com.letscode.starwars.data.domain.Rebel;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RebelRepository extends CrudRepository<Rebel, Long> {

    @Override
    @Cacheable("rebels")
    Optional<Rebel> findById(Long id);

    @Override
    @CacheEvict(value = "rebels", allEntries = true)
    <S extends Rebel> S save(S entity);

}
