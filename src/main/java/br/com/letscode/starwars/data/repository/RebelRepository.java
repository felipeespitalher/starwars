package br.com.letscode.starwars.data.repository;

import br.com.letscode.starwars.data.domain.Rebel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelRepository extends PagingAndSortingRepository<Rebel, Long> {
}
