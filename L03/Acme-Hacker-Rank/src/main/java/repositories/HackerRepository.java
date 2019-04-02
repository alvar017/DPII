
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	@Query("select h from Hacker h join h.userAccount hua where hua.id=?1")
	Hacker getHackerByUserAccountId(int userAccountId);

	@Query("select m from Hacker m join m.userAccount mua where mua.id=?1")
	Hacker findByUserAccountId(int userAccountId);

	@Query("select ch from Curricula c join c.hacker ch where c.id=?1")
	Hacker getHackerByCurriculaId(int curriculaId);
}
