
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	/*
	 * CONTROL DE CAMBIOS AreaRepository.java
	 * FRAN 20/02/2019 17:31 CREACI�N DE LA CLASE
	 * FERRETE 10/03/2019 QUERY PARA AREAS SIN CHAPTER
	 */

	@Query("select a from Area a where a.chapter is null")
	Collection<Area> UnassignedAreas();

	@Query("select a from Area a where a.chapter.id=?1")
	Area findAreaChapter(int id);

	// REQUISITO 8
	@Query("select count(a) from Area a where a.chapter = null")
	Integer AreaNoChapter();

	// REQUISITO 8
	@Query("select count(a1) from Area a1")
	Integer AreaALL();

}
