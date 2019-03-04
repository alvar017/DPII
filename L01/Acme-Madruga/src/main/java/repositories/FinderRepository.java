
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;
import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Procession p where (p.title like %?1% or p.description like %?1%) and p.moment between ?2 and ?3")
	Collection<Procession> findByFilterNoArea(final String keyword, final Date minDate, final Date maxDate);

	@Query("select p from Procession p join p.brotherhood b where (p.title like %?1% or p.description like %?1%) and p.moment between ?2 and ?3 and b.area=?4")
	Collection<Procession> findByFilterWithArea(final String keyword, final Date minDate, final Date maxDate, final Area area);

}
