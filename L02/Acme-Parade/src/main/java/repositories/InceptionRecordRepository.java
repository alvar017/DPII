
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import domain.InceptionRecord;

/*
 * CONTROL DE CAMBIOS ProcessionRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 12:17 A�ADIDA QUERY findProcessionsByBrotherhood
 */

@Repository
public interface InceptionRecordRepository extends JpaRepository<InceptionRecord, Integer> {

}
