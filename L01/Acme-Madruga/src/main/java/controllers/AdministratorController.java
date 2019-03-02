/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AdministratorService;
import services.BrotherhoodService;
import services.MemberService;
import services.ProcessionService;
import services.RequestService;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;
import domain.Procession;
import forms.RegistrationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	BrotherhoodService		brotherhoodService;

	@Autowired
	ProcessionService		processionService;

	@Autowired
	RequestService			requestService;

	@Autowired
	MemberService			memberService;

	@Autowired
	ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final RegistrationForm registrationForm = new RegistrationForm();
		result = new ModelAndView("administrator/create");
		result.addObject("registrationForm", registrationForm);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;

		final Administrator administrator;

		administrator = this.administratorService.reconstructR(registrationForm, binding);

		if (binding.hasErrors())
			result = new ModelAndView("administrator/create");
		else
			try {
				this.administratorService.save(administrator);
				result = new ModelAndView("welcome/index");
			} catch (final Throwable oops) {
				result = new ModelAndView("administrator/create");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Administrator administrator;

		final int idUserAccount = LoginService.getPrincipal().getId();

		administrator = this.administratorService.getAdministratorByUserAccountId(idUserAccount);
		Assert.notNull(administrator);

		result = new ModelAndView("administrator/edit");

		result.addObject("administrator", administrator);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveE(Administrator admin, final BindingResult binding) {
		ModelAndView result;

		admin = this.administratorService.reconstruct(admin, binding);

		if (binding.hasErrors())
			result = new ModelAndView("administrator/edit");
		else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(admin, "email.wrong");
				else
					result = this.createEditModelAndView(admin, "error.email");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Administrator administrator, final String string) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("message", string);
		result.addObject("administrator", administrator);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final int userLoggin = LoginService.getPrincipal().getId();
		final Administrator administrator;
		administrator = this.administratorService.getAdministratorByUserAccountId(userLoggin);
		Assert.isTrue(administrator != null);

		result = new ModelAndView("administrator/show");
		result.addObject("administrator", administrator);

		result.addObject("requestURI", "administrator/show.do");

		return result;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result;

		final int userLoggin = LoginService.getPrincipal().getId();
		final Administrator administrator;
		administrator = this.administratorService.getAdministratorByUserAccountId(userLoggin);
		Assert.isTrue(administrator != null);

		final String largestBrotherhood = this.brotherhoodService.largestBrotherhood();
		final String smallestBrotherhood = this.brotherhoodService.smallestBrotherhood();
		final Collection<Procession> processionOrganised = this.processionService.processionOrganised();
		final Double getRatioRequestStatusTrue = this.requestService.getRatioRequestStatusTrue();
		final Double getRatioRequestStatusFalse = this.requestService.getRatioRequestStatusFalse();
		final Double getRatioRequestStatusNull = this.requestService.getRatioRequestStatusNull();
		final Collection<Member> lisMemberAccept = this.memberService.lisMemberAccept();
		final Double getRatioRequestProcessionStatusTrue = this.requestService.getRatioRequestProcessionStatusTrue();
		final Double getRatioRequestProcessionStatusFalse = this.requestService.getRatioRequestProcessionStatusFalse();
		final Double getRatioRequestProcessionStatusNull = this.requestService.getRatioRequestProcessionStatusNull();

		final Float maxNumberOfMemberPerBrotherhood = this.memberService.maxNumberOfMemberPerBrotherhood();
		final Float minNumberOfMemberPerBrotherhood = this.memberService.minNumberOfMemberPerBrotherhood();
		final Float avgNumberOfMemberPerBrotherhood = this.memberService.avgNumberOfMemberPerBrotherhood();
		final Float desviationOfNumberOfMemberPerBrotherhood = this.memberService.desviationOfNumberOfMemberPerBrotherhood();
		final String minProcession = this.processionService.minProcession();
		final String maxProcession = this.processionService.maxProcession();
		final Integer minProcessionN = this.processionService.minProcessionN();
		final Integer maxProcessionN = this.processionService.maxProcessionN();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("largestBrotherhood", largestBrotherhood);
		result.addObject("getRatioRequestProcessionStatusTrue", getRatioRequestProcessionStatusTrue);
		result.addObject("getRatioRequestProcessionStatusFalse", getRatioRequestProcessionStatusFalse);
		result.addObject("getRatioRequestProcessionStatusNull", getRatioRequestProcessionStatusNull);

		result.addObject("maxNumberOfMemberPerBrotherhood", maxNumberOfMemberPerBrotherhood);
		result.addObject("minNumberOfMemberPerBrotherhood", minNumberOfMemberPerBrotherhood);
		result.addObject("avgNumberOfMemberPerBrotherhood", avgNumberOfMemberPerBrotherhood);
		result.addObject("desviationOfNumberOfMemberPerBrotherhood", desviationOfNumberOfMemberPerBrotherhood);

		result.addObject("minProcession", minProcession);
		result.addObject("maxProcession", maxProcession);
		result.addObject("minProcessionN", minProcessionN);
		result.addObject("maxProcessionN", maxProcessionN);

		result.addObject("smallestBrotherhood", smallestBrotherhood);
		result.addObject("processionOrganised", processionOrganised);
		result.addObject("lisMemberAccept", lisMemberAccept);
		result.addObject("getRatioRequestStatusTrue", getRatioRequestStatusTrue);
		result.addObject("getRatioRequestStatusFalse", getRatioRequestStatusFalse);
		result.addObject("getRatioRequestStatusNull", getRatioRequestStatusNull);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}

	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {

		final ModelAndView res;

		final Collection<Member> members = this.memberService.findAll();

		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();

		res = new ModelAndView("administrator/actorList");

		res.addObject("members", members);
		res.addObject("brotherhoods", brotherhoods);
		res.addObject("requestURI", "administrator/actorList.do");

		return res;
	}

	@RequestMapping(value = "/banMember", method = RequestMethod.GET)
	public ModelAndView banMember(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.memberService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.actorService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.actorService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:../#");
		}

		return res;
	}

	@RequestMapping(value = "/banBrotherhood", method = RequestMethod.GET)
	public ModelAndView banBrotherhood(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.brotherhoodService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.actorService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.actorService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:../#");
		}

		return res;
	}
}
