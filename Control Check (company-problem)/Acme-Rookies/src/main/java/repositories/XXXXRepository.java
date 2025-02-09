
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.XXXX;

@Repository
public interface XXXXRepository extends JpaRepository<XXXX, Integer> {

	@Query("select x from XXXX x where x.problem.company.id = ?1")
	Collection<XXXX> getLoggedXXXXs(int id);

	@Query("select x from XXXX x where x.id = ?1 and x.problem.company.id = ?2")
	XXXX getLoggedXXXX(int xxxxId, int companyId);

	@Query("select count(x) from XXXX x where x.ticker = ?1")
	int findByTicker(String ticker);

}
