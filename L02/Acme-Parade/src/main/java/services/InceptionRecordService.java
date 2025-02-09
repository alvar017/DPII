
package services;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InceptionRecordRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACI�N DE LA CLASE
 */

@Service
@Transactional
public class InceptionRecordService {

	//Managed Repository -------------------	
	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;

	//Supporting services ------------------
	@Autowired
	BrotherhoodService					brotherhoodService;

	@Autowired
	HistoryService						historyService;


	//Simple CRUD Methods ------------------

	public InceptionRecord create() {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		final InceptionRecord inceptionRecord = new InceptionRecord();
		return inceptionRecord;

	}

	public Collection<InceptionRecord> findAll() {
		return this.inceptionRecordRepository.findAll();
	}

	public InceptionRecord findOne(final int id) {
		final InceptionRecord inceptionRecord = this.inceptionRecordRepository.findOne(id);
		return inceptionRecord;
	}

	public Boolean checkPhotos(final String photos) {
		Boolean res = true;
		try {
			if (photos.contains("'")) {
				final List<String> photosC = Arrays.asList(photos.split("'"));
				for (final String photo : photosC)
					new URL(photo).toURI();
			} else
				new URL(photos).toURI();
		} catch (final Exception e) {
			res = false;
		}

		return res;
	}

	public InceptionRecord save(final InceptionRecord inceptionRecord) {
		Assert.notNull(inceptionRecord, "inceptionRecordSaveService.null");
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		Assert.isTrue(this.checkPhotos(inceptionRecord.getPhotos()), "Photo are not URLs");
		InceptionRecord inceptionRecordSaved;
		// Assert inceptionRecord owner is the same that brotherhood logger
		if (brotherhood.getHistory() != null && brotherhood.getHistory().getInceptionRecord() != null) {
			/*
			 * En el caso de que el brotherhood tenga ya una inceptionRecord se comprueba que la id de la que se va a editar sea la
			 * misma que la que tiene el brotherhood logueado
			 */
			final int idHistoryBrotherhoodLogger = brotherhood.getHistory().getId();
			final History history = this.historyService.findHistoryByInceptionRecordId(inceptionRecord.getId());
			Assert.isTrue(idHistoryBrotherhoodLogger == history.getId(), "inceptionRecord.brotherhood.diferent");
		}
		final History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		inceptionRecord.setHistory(history);
		inceptionRecordSaved = this.inceptionRecordRepository.save(inceptionRecord);
		history.setInceptionRecord(inceptionRecordSaved);
		this.historyService.save(history);
		return inceptionRecordSaved;
	}

	public void flush() {
		this.inceptionRecordRepository.flush();
	}
}
