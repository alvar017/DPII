
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CleaningTask;

@Repository
public interface CleaningTaskRepository extends JpaRepository<CleaningTask, Integer> {

	@Query("select c from CleaningTask c join c.accomodation a where a.host.id=?1")
	Collection<CleaningTask> getCleaningTaskHost(int hostId);

	@Query("select c from CleaningTask c where c.cleaner.id=?1")
	Collection<CleaningTask> getCleaningTaskCleaner(int cleanerId);

	@Query("select c from CleaningTask c where c.cleaner.id = ?1")
	Collection<CleaningTask> getCleanerCleaningTaks(int id);

	@Query("select c from CleaningTask c where c.accomodation.id = ?1")
	Collection<CleaningTask> getCleaningTaskAccomodation(int id);
}
