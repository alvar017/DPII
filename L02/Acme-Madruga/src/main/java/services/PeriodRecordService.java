
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import domain.Brotherhood;
import domain.History;
import domain.PeriodRecord;
import repositories.PeriodRecordRepository;
import security.LoginService;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACI�N DE LA CLASE
 */

@Service
@Transactional
public class PeriodRecordService {

	//Managed Repository -------------------	
	@Autowired
	private PeriodRecordRepository		periodRecordRepository;

	//Supporting services ------------------
	@Autowired
	BrotherhoodService brotherhoodService;
	
	@Autowired
	HistoryService historyService;

	//Simple CRUD Methods ------------------

	public PeriodRecord create() {
		PeriodRecord periodRecord = new PeriodRecord();
		return periodRecord;

	}

	public Collection<PeriodRecord> findAll() {
		return this.periodRecordRepository.findAll();
	}

	public PeriodRecord findOne(final int id) {
		PeriodRecord periodRecord = this.periodRecordRepository.findOne(id);
		return periodRecord;
	}
	public PeriodRecord save(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord, "periodRecordSaveService.null");
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		PeriodRecord periodRecordSaved;
		// Assert periodRecord owner is the same that brotherhood logger
		PeriodRecord periodRecordFromDB = this.periodRecordRepository.findOne(periodRecord.getId());
		History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		if (brotherhood!=null && brotherhood.getHistory()!=null && !brotherhood.getHistory().getPeriodRecord().isEmpty() && periodRecordFromDB!=null) {
			List<PeriodRecord> periodRecordLogger = (List<PeriodRecord>)brotherhood.getHistory().getPeriodRecord();
			Assert.isTrue(periodRecordLogger.contains(periodRecordFromDB), "periodRecordServiceSave.diferentBrotherhoodLogger");
			history.getPeriodRecord().remove(periodRecordFromDB);
		} 
		periodRecordSaved = this.periodRecordRepository.save(periodRecord);
		history.getPeriodRecord().add(periodRecordSaved);
		this.historyService.save(history);
		return periodRecordSaved;
	}

	public void delete(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord, "periodRecord.null");
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		PeriodRecord periodRecordFromDB = this.periodRecordRepository.findOne(periodRecord.getId());
		List<PeriodRecord> periodRecordLogger = (List<PeriodRecord>)brotherhood.getHistory().getPeriodRecord();
		Assert.isTrue(periodRecordLogger.contains(periodRecordFromDB), "periodRecordServiceDelete.diferentBrotherhoodLogger");
		brotherhood.getHistory().getPeriodRecord().remove(periodRecordFromDB);
		this.periodRecordRepository.delete(periodRecord);
		this.historyService.save(brotherhood.getHistory());
	}
}
