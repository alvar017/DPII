/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ApplicationService;
import services.CompanyService;
import services.ConfigurationService;
import services.CurriculaService;
import services.FinderService;
import services.HackerService;
import services.PositionService;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Configuration;
import domain.Hacker;
import forms.ActorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Dashboard --------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {

		final ModelAndView res = new ModelAndView("administrator/dashboard");
		res.addObject("requestURI", "administrator/dashboard.do");

		// PositionsPerCompany
		res.addObject("avgPositionPerCompany", this.positionService.avgPositionPerCompany());
		res.addObject("minPositionPerCompany", this.positionService.minPositionPerCompany());
		res.addObject("maxPositionPerCompany", this.positionService.maxPositionPerCompany());
		res.addObject("stddevPositionPerCompany", this.positionService.stddevPositionPerCompany());
		// PositionsPerCompany

		// ApplicationPerHacker
		res.addObject("avgApplicationsPerHacker", this.applicationService.avgApplicationPerHacker());
		res.addObject("minApplicationsPerHacker", this.applicationService.minApplicationPerHacker());
		res.addObject("maxApplicationsPerHacker", this.applicationService.maxApplicationPerHacker());
		res.addObject("stddevApplicationsPerHacker", this.applicationService.stddevApplicationPerHacker());
		// ApplicationPerHacker

		// CompaniesMorePositions
		res.addObject("findCompanyWithMorePositions", this.positionService.findCompanyWithMorePositions());
		// CompaniesMorePositions

		// HackerMoreApplications
		res.addObject("findHackerMoreApplications", this.applicationService.findHackerWithMoreApplications());
		// HackerMoreApplications

		// Salaries
		res.addObject("avgSalaryPerPosition", this.positionService.avgSalaryPerPosition());
		res.addObject("minSalaryPerPosition", this.positionService.minSalaryPerPosition());
		res.addObject("maxSalaryPerPosition", this.positionService.maxSalaryPerPosition());
		res.addObject("stddevSalaryPerPosition", this.positionService.stddevSalaryPerPosition());
		res.addObject("bestPosition", this.positionService.bestPosition());
		res.addObject("worstPosition", this.positionService.worstPosition());
		// Salaries
		//AM
		res.addObject("minNumberOfResultFinder", this.finderService.minNumberOfResult());
		res.addObject("maxNumberOfResultFinder", this.finderService.maxNumberOfResult());
		res.addObject("avgNumberOfResultFinder", this.finderService.avgNumberOfResult());
		res.addObject("stddevNumberOfResultFinder", this.finderService.stddevNumberOfResult());
		res.addObject("ratioResultFinder", this.finderService.ratioResult());
		res.addObject("minNumberOfHistory", this.curriculaService.minNumberOfResultHistory());
		res.addObject("maxNumberOfHistory", this.curriculaService.maxNumberOfResultHistory());
		res.addObject("avgNumberOfHistory", this.curriculaService.avgNumberOfResultHsitory());
		res.addObject("stddevNumberOfHistory", this.curriculaService.stddevNumberOfResultHistory());
		//AM
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	// CREATE ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final ActorForm actorForm = new ActorForm();
			result = new ModelAndView("administrator/create");
			result.addObject("actorForm", actorForm);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-CREATE ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result = null;

		final Administrator administrator;

		administrator = this.adminService.reconstructCreate(actorForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = new ModelAndView("administrator/create");
		} else
			try {
				System.out.println("carmen: voy a guardar");
				final Administrator a = this.adminService.saveCreate(administrator);
				System.out.println(a);
				result = new ModelAndView("welcome/index");
				final SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final String moment = formatter.format(new Date());
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(administrator, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.createEditModelAndView(administrator, "error.email");
				else
					result = this.createEditModelAndView(administrator, "error.html");
			}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final Administrator actor, final String string) {
		ModelAndView result;

		result = new ModelAndView("administrator/create");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Actor List ---------------------------------------------------------------
	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {

		final ModelAndView res;

		final Collection<Company> companies = this.companyService.findAll();
		final Collection<Hacker> hackers = this.hackerService.findAll();

		res = new ModelAndView("administrator/actorList");

		res.addObject("companies", companies);
		res.addObject("hackers", hackers);
		res.addObject("requestURI", "administrator/actorList.do");
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	// Ban/Unban ---------------------------------------------------------------

	private ModelAndView createEditModelAndView2(final Actor actor, final String string) {
		ModelAndView result;

		final Collection<Company> companies = this.companyService.findAll();
		final Collection<Hacker> hackers = this.hackerService.findAll();

		result = new ModelAndView("administrator/actorList");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("companies", companies);
		result.addObject("hackers", hackers);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/banCompany", method = RequestMethod.GET)
	public ModelAndView banMember(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.companyService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.adminService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.adminService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			final Actor actor = this.companyService.findOne(actorId);
			if (oops.getMessage() == "ban.error")
				res = this.createEditModelAndView2(actor, "ban.error");
			else
				res = new ModelAndView("redirect:../#");
		}
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());

		return res;
	}
	@RequestMapping(value = "/banHacker", method = RequestMethod.GET)
	public ModelAndView banBrotherhood(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.hackerService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.adminService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.adminService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			final Actor actor = this.hackerService.findOne(actorId);
			if (oops.getMessage() == "ban.error")
				res = this.createEditModelAndView2(actor, "ban.error");
			else
				res = new ModelAndView("redirect:../#");
		}
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());

		return res;
	}

	//FERRETE
	//CONFIGURATION
	@RequestMapping(value = "/list")
	public ModelAndView list2() {
		ModelAndView result;
		result = new ModelAndView("administrator/list");

		HashSet<String> spamWords = new HashSet<>();
		final HashSet<String> scoreWordsPos = new HashSet<>();
		final HashSet<String> scoreWordsNeg = new HashSet<>();

		//Configuration
		// ------------------------------------------------------------
		final Configuration configuration = this.configurationService.getConfiguration();

		// ------------------------------------------------------------

		//Priorities
		// ------------------------------------------------------------
		Collection<String> priorities = new HashSet<>();
		priorities = configuration.getPriorities();
		result.addObject("priorities", priorities);
		// ------------------------------------------------------------
		//Logo
		final String logo = this.getLogo();

		//Spam words

		spamWords = new HashSet<>(configuration.getSpamWords());

		//Welcome page
		final String ingles = configuration.getSystemMessageEn();
		final String spanish = configuration.getSystemMessageEs();

		//System
		final String system = this.getSystem();

		//Phone
		final String phone = configuration.getCountryCode();

		System.out.println("Carmen: Entro en el list");

		result.addObject("logo", logo);
		result.addObject("ingles", ingles);
		result.addObject("spanish", spanish);
		result.addObject("spamWords", spamWords);
		result.addObject("scoreWordsPos", scoreWordsPos);
		result.addObject("scoreWordsNeg", scoreWordsNeg);
		result.addObject("configuration", configuration);
		result.addObject("system", system);
		result.addObject("phone", phone);
		result.addObject("requestURI", "administrator/list.do");
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// PRIORITIES
	@RequestMapping(value = "/newPriority", method = RequestMethod.GET)
	public ModelAndView addPriority(@RequestParam("newPriority") final String newPriority) {
		ModelAndView result;

		this.configurationService.addPriority(newPriority);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/deletePriority", method = RequestMethod.GET)
	public ModelAndView deletePriority(@RequestParam("deletePriority") final String deletePriority) {
		ModelAndView result = new ModelAndView("administrator/list");

		try {
			System.out.println("Carmen: Voy a intentar guardar");
			this.configurationService.deletePriority(deletePriority);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noPriority.error") {
				result = this.list2();
				result.addObject("message", "noPriority.error");
			}
		}
		result.addObject("logo", this.configurationService.getConfiguration().getBanner());
		result.addObject("system", this.configurationService.getConfiguration().getSystemName());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newSpamWord", method = RequestMethod.GET)
	public ModelAndView newSpamWord(@RequestParam("newSpamWord") final String newSpamWord) {
		ModelAndView result;

		System.out.println("Carmen: Voy a intentar guardar");
		this.configurationService.newSpamWords(newSpamWord);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.configurationService.getConfiguration().getBanner());
		result.addObject("system", this.configurationService.getConfiguration().getSystemName());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/deleteSpamWord", method = RequestMethod.GET)
	public ModelAndView deleteSpamWord(@RequestParam("deleteSpamWord") final String spamWord) {
		ModelAndView result = new ModelAndView("administrator/list");

		try {
			System.out.println("Carmen: Voy a intentar guardar");
			this.configurationService.deleteSpamWords(spamWord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noSpamWord.error") {
				result = this.list2();
				result.addObject("message", "noSpamWord.error");
			}
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newWelcome", method = RequestMethod.GET)
	public ModelAndView newWelcome(@RequestParam("newIngles") final String newIngles, @RequestParam("newSpanish") final String newSpanish) {
		ModelAndView result;

		System.out.println(newIngles);
		System.out.println(newSpanish);

		System.out.println("Carmen: Voy a intentar guardar");

		this.configurationService.newE(newIngles);

		this.configurationService.newS(newSpanish);

		result = new ModelAndView("redirect:list.do");

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newSystem", method = RequestMethod.GET)
	public ModelAndView newSystem(@RequestParam("newSystem") final String newSystem) {
		ModelAndView result;

		System.out.println(newSystem);

		System.out.println("Carmen: Voy a intentar guardar");

		this.configurationService.newSystem(newSystem);

		result = new ModelAndView("redirect:list.do");

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public ModelAndView header() {
		ModelAndView result;

		final String system = this.configurationService.getConfiguration().getSystemName();

		result = new ModelAndView("master-page/header");

		result.addObject("requestURI", "master-page/header.do");
		result.addObject("system", system);

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newLogo", method = RequestMethod.GET)
	public ModelAndView newLogo(@RequestParam("newLogo") final String newLogo) {
		ModelAndView result;
		try {
			this.configurationService.newLogo(newLogo);

			System.out.println("Carmen: Voy a intentar guardar");

			result = new ModelAndView("redirect:list.do");

		} catch (final Exception e) {
			result = this.createEditModelAndView(newLogo, "logo.bad");
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final String newLogo, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("administrator/list");
		final Configuration config = this.configurationService.getConfiguration();

		//Logo
		final String logo = this.configurationService.getConfiguration().getBanner();

		//Priorities
		final HashSet<String> priorities = new HashSet<>(config.getPriorities());

		//Spam words
		final HashSet<String> spamWords = new HashSet<>(config.getSpamWords());

		System.out.println("Carmen: Esta es la lista de spam words");
		System.out.println(spamWords);

		//Welcome page
		final String ingles = config.getSystemMessageEn();
		final String spanish = config.getSystemMessageEs();

		//System
		final String system = this.configurationService.getConfiguration().getSystemName();

		//Phone
		final String phone = config.getCountryCode();

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		System.out.println("Carmen: Entro en el list");

		result.addObject("logo", logo);

		result.addObject("ingles", ingles);
		result.addObject("spanish", spanish);

		result.addObject("priorities", priorities);

		result.addObject("spamWords", spamWords);

		result.addObject("system", system);

		result.addObject("phone", phone);

		result.addObject("language", language);
		result.addObject("requestURI", "administrator/list.do");

		result.addObject("message", messageCode);

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newPhone", method = RequestMethod.GET)
	public ModelAndView newPhone(@RequestParam("newPhone") final String newPhone) {
		ModelAndView result;

		this.configurationService.newPhone(newPhone);

		System.out.println("Carmen: Voy a intentar guardar");

		result = new ModelAndView("redirect:list.do");

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	//	@RequestMapping(value = "/newPhoneCountry", method = RequestMethod.GET)
	//	public ModelAndView newPhoneCountry(@RequestParam("newPhoneCountry") final String newPhoneCountry) {
	//		ModelAndView result;
	//
	//		this.configurationService.newCountry(newPhoneCountry);
	//		result = new ModelAndView("redirect:list.do");
	//		result.addObject("logo", this.configurationService.getConfiguration().getBanner());
	//		result.addObject("system", this.configurationService.getConfiguration().getSystemName());
	//		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	//	}

}
