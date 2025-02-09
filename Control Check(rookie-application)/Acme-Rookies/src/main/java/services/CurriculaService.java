
package services;

/**
 * CurriculaService.java
 * 
 * @author Alvaro de la Flor Bonilla GitHub: alvar017
 * 
 *         CONTROL:
 *         30/03/2019 14:47 Creation
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CurriculaRepository;
import security.Authority;
import security.LoginService;
import domain.Curricula;
import domain.EducationalData;
import domain.MiscellaneousAttachment;
import domain.PositionData;
import domain.Rookie;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository				curriculaRepository;

	@Autowired
	private RookieService					rookieService;

	@Autowired
	private EducationalDataService			educationalDataService;

	@Autowired
	private PositionDataService				positionDataService;

	@Autowired
	private MiscellaneousAttachmentService	miscellaneousAttachmentService;

	@Autowired
	private Validator						validator;

	@Autowired
	private ConfigurationService			configurationService;


	// CRUD Methods

	/**
	 * Create a CurriculaEntity. Must exist a {@link Rookie} login to create it.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Curricula}
	 */
	public Curricula create() {
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(this.rookieService.getRookieLogin(), "Must exist an rookie login");
		return new Curricula();
	}

	/**
	 * 
	 * Return a collection of all {@link Curricula} in database.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Curricula}>
	 */
	public List<Curricula> findAll() {
		return this.curriculaRepository.findAll();
	}

	/**
	 * Find a curricula in dataBase by id
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Curricula}
	 */
	public Curricula findOne(final int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	/**
	 * <p>
	 * Save a curricula. Check user login ({@link Rookie}) is the same that user who create the curricula in the case he want to edit it
	 * </p>
	 * Curricula must not be null<br>
	 * Must exist rookie login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return The save {@link Curricula}
	 */
	public Curricula save(final Curricula curricula) {
		Assert.notNull(curricula, "Curricula is null");
		Assert.isTrue(this.checkAnyLogger(), "Any user is login");
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		final Curricula curriculaDB = this.curriculaRepository.findOne(curricula.getId());
		if (curriculaDB != null)
			Assert.isTrue(curricula.getRookie().equals(rookieLogin), "Not allow to edit a not own curricula");

		if (curricula.getPhone().matches("^([0-9]{4,})$"))
			curricula.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + curricula.getPhone());

		return this.curriculaRepository.save(curricula);
	}

	/**
	 * Delete a curricula.<br>
	 * Check user login ({@link Rookie}) is the same that user who created the curricula<br>
	 * Must exist a {@link Rookie} login and curricula must not be null
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final Curricula curricula) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		final Curricula curriculaDB = this.curriculaRepository.findOne(curricula.getId());
		Assert.notNull(curriculaDB, "The curricula is not in DB");
		Assert.isTrue(curricula.getRookie().equals(rookieLogin), "Not allow to delete a not own curricula");
		final Collection<EducationalData> educationalDatas = this.educationalDataService.getEducationalDataFromCurricula(curriculaDB);
		if (!educationalDatas.isEmpty())
			this.educationalDataService.deleteAll(educationalDatas);
		final Collection<PositionData> positionDatas = this.positionDataService.getPositionDataFromCurricula(curriculaDB);
		if (!positionDatas.isEmpty())
			this.positionDataService.deleteAll(positionDatas);
		final Collection<MiscellaneousAttachment> miscellaneousAttachaments = this.miscellaneousAttachmentService.getMiscellaneousAttachmentFromCurricula(curriculaDB);
		if (!miscellaneousAttachaments.isEmpty())
			this.miscellaneousAttachmentService.deleteAll(miscellaneousAttachaments);
		this.curriculaRepository.delete(curricula);
	}

	// CRUD Methods

	// AUXILIAR METHODS

	/**
	 * This method reconstruct a prunned {@link Curricula} and validate it
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return The reconstruct {@link Curricula}
	 */
	public Curricula reconstruct(final Curricula curricula, final BindingResult binding) {
		Curricula result;

		if (curricula.getId() == 0) {
			curricula.setRookie(this.rookieService.getRookieLogin());
			curricula.setLinkGitHub("https://www.github.com/" + curricula.getLinkGitHub().replaceAll("https://www.github.com/", ""));
			curricula.setIsCopy(false);
			result = curricula;
		} else {
			result = this.curriculaRepository.findOne(curricula.getId());
			Assert.notNull(result, "Float is null");
			curricula.setId(result.getId());
			curricula.setVersion(result.getVersion());
			curricula.setRookie(result.getRookie());
			curricula.setLinkGitHub("https://www.github.com/" + curricula.getLinkGitHub().replaceAll("https//www.github.com/", ""));
			curricula.setIsCopy(false);
			result = curricula;
		}
		this.validator.validate(result, binding);
		return result;
	}

	/**
	 * 
	 * This methods create and save a copy of the curricula given and all of their educationalData and positionData<br>
	 * Set to true the isCopy atribute of curriculum, educationalData and positionData instances that they have been copy<br>
	 * The {@link Rookie} who create the Curricula must be login.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return The copy {@link Curricula} instance
	 */
	public Curricula createCurriculaCopyAndSave(final Curricula curricula) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.isTrue(curricula.getRookie().equals(rookieLogin), "Try to do a copy of curricula by not own rookie");
		final Curricula curriculaCopy = this.create();
		curriculaCopy.setRookie(curricula.getRookie());
		curriculaCopy.setIsCopy(true);
		curriculaCopy.setLinkGitHub(curricula.getLinkGitHub());
		curriculaCopy.setLinkLinkedin(curricula.getLinkLinkedin());
		curriculaCopy.setMiscellaneous(curricula.getMiscellaneous());
		curriculaCopy.setName(curricula.getName());
		curriculaCopy.setPhone(curricula.getPhone());
		curriculaCopy.setStatement(curricula.getStatement());
		final Curricula copy = this.save(curriculaCopy);
		this.educationalDataService.makeCopyAllEducationalDataForCurricula(curricula, copy);
		this.positionDataService.makeCopyAllPositionDataForCurricula(curricula, copy);
		this.miscellaneousAttachmentService.makeCopyAllMiscellaneousAttachmentForCurricula(curricula, copy);
		return copy;
	}

	/**
	 * @author Alvaro de la Flor Bonilla
	 */
	public void flush() {
		this.curriculaRepository.flush();
	}

	/**
	 * Check that any user is login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Boolean}<br>
	 *         True if an user is login, false in otherwise.
	 */
	public Boolean checkAnyLogger() {
		Boolean res = true;
		try {
			LoginService.getPrincipal().getId();
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	/**
	 * 
	 * Return all Curricula in dataBase of a {@link Rookie}.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Curricula}>
	 */
	public Collection<Curricula> findAllByRookie(final Rookie rookie) {
		List<Curricula> res = new ArrayList<>();
		if (rookie != null)
			res = (List<Curricula>) this.curriculaRepository.getCurriculasOfRookie(rookie.getId());
		return res;
	}

	/**
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection < {@link Curricula} > not copy mode
	 */
	public Collection<Curricula> findAllNotCopyByRookie(final Rookie rookie) {
		List<Curricula> res = new ArrayList<>();
		if (rookie != null)
			res = (List<Curricula>) this.curriculaRepository.getCurriculasNotCopyOfRookie(rookie.getId());
		return res;
	}

	public void deleteRookieCurriculas(final int rookieId) {

		final Collection<Curricula> curriculas = this.curriculaRepository.getCurriculasOfRookie(rookieId);
		if (!curriculas.isEmpty())
			for (final Curricula curricula : curriculas)
				this.delete(curricula);
	}

	public Float minNumberOfResultHistory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.curriculaRepository.minCurriculaPerRookie();
	}

	public Float maxNumberOfResultHistory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.curriculaRepository.maxCurriculaPerRookie();
	}

	public Float avgNumberOfResultHsitory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.curriculaRepository.avgCurriculaPerRookie();
	}

	public Float stddevNumberOfResultHistory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.curriculaRepository.sttdevCurriculaPerRookie();
	}

	// AUXILIAR METHODS

}
