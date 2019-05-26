/*
 * CustomerController.java
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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AccomodationService;
import services.ConfigService;
import services.CustomerService;
import services.HostService;
import services.TransportService;
import services.TransporterService;
import services.TravelAgencyService;
import services.ValorationService;
import domain.CreditCard;
import domain.Customer;
import domain.Valoration;
import forms.RegisterActorE;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private AccomodationService	accService;

	@Autowired
	private TransportService	transportService;

	@Autowired
	private TransporterService	transporterService;

	@Autowired
	private HostService			hostService;

	@Autowired
	private TravelAgencyService	travelAgencyService;

	@Autowired
	private ValorationService	valorationService;

	@Autowired
	private ConfigService		configService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// RATE TRAVEL AGENCY
	// ---------------------------------------------------------------
	@RequestMapping(value = "/rateTravelAgency", method = RequestMethod.GET)
	public ModelAndView rateTravelAgency(@RequestParam(value = "travelAgencyId", defaultValue = "-1") final int travelAgencyId) {

		ModelAndView res;

		try {

			res = new ModelAndView("customer/rateTravelAgency");
			final Valoration valoration = this.valorationService.create();
			valoration.setCustomer(this.customerService.getCustomerByUserAccountId(LoginService.getPrincipal().getId()));
			valoration.setTravelAgency(this.travelAgencyService.findOne(travelAgencyId));
			res.addObject("valoration", valoration);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	@RequestMapping(value = "/rateTravelAgency", method = RequestMethod.POST, params = "save")
	public ModelAndView rateTravelAgency(@Valid final Valoration valoration, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			res = new ModelAndView("customer/rateTravelAgency");
			res.addObject("valoration", valoration);
		} else
			try {

				this.valorationService.save(valoration);
				res = new ModelAndView("redirect:contacts.do");
			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// RATE TRANSPORTER
	// ---------------------------------------------------------------
	@RequestMapping(value = "/rateTransporter", method = RequestMethod.GET)
	public ModelAndView rateTransporter(@RequestParam(value = "trasnporterId", defaultValue = "-1") final int transporterId) {

		ModelAndView res;

		try {

			res = new ModelAndView("customer/rateTransporter");
			final Valoration valoration = this.valorationService.create();
			valoration.setCustomer(this.customerService.getCustomerByUserAccountId(LoginService.getPrincipal().getId()));
			valoration.setTransporter(this.transporterService.findOne(transporterId));
			res.addObject("valoration", valoration);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	@RequestMapping(value = "/rateTransporter", method = RequestMethod.POST, params = "save")
	public ModelAndView rateTransporter(@Valid final Valoration valoration, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			res = new ModelAndView("customer/rateTransporter");
			res.addObject("valoration", valoration);
		} else
			try {

				this.valorationService.save(valoration);
				res = new ModelAndView("redirect:contacts.do");
			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// RATE HOST
	// ---------------------------------------------------------------
	@RequestMapping(value = "/rateHost", method = RequestMethod.GET)
	public ModelAndView rateHost(@RequestParam(value = "hostId", defaultValue = "-1") final int hostId) {

		ModelAndView res;

		try {

			res = new ModelAndView("customer/rateHost");
			final Valoration valoration = this.valorationService.create();
			valoration.setCustomer(this.customerService.getCustomerByUserAccountId(LoginService.getPrincipal().getId()));
			valoration.setHost(this.hostService.findOne(hostId));
			res.addObject("valoration", valoration);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	@RequestMapping(value = "/rateHost", method = RequestMethod.POST, params = "save")
	public ModelAndView rateHost(@Valid final Valoration valoration, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			res = new ModelAndView("customer/rateHost");
			res.addObject("valoration", valoration);
		} else
			try {

				this.valorationService.save(valoration);
				res = new ModelAndView("redirect:contacts.do");
			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// CONTACT LIST
	// ---------------------------------------------------------------
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public ModelAndView usersRelatedList() {

		ModelAndView res;

		try {

			final Customer customer = this.customerService.getCustomerByUserAccountId(LoginService.getPrincipal().getId());
			res = new ModelAndView("customer/contacts");
			res.addObject("hosts", this.accService.getAccomodationsByCustomerId(customer.getId()));
			res.addObject("transporters", this.transportService.getTransportersByCustomerId(customer.getId()));
			res.addObject("travelAgencies", this.travelAgencyService.getTravelAgenciesByCustomerId(customer.getId()));
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	// REGISTER AS CUSTOMER
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActorE registerActorE = new RegisterActorE();
			result = new ModelAndView("customer/create");
			result.addObject("registerActorE", registerActorE);
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS CUSTOMER
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActorE registerActorE, final BindingResult binding) {
		ModelAndView result = null;
		final Customer customer = this.customerService.reconstructRegisterAsCustomer(registerActorE, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("customer/create");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.customerService.saveRegisterAsCustomer(customer);
				result = new ModelAndView("welcome/index");
				final SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final String moment = formatter.format(new Date());
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// EDIT DATA PERSONAL
	// ---------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Customer customer;
		final int idUserAccount = LoginService.getPrincipal().getId();
		customer = this.customerService.getCustomerByUserAccountId(idUserAccount);
		Assert.notNull(customer);
		final CreditCard creditCard = customer.getCreditCard();
		result = new ModelAndView("customer/edit");
		result.addObject("customer", customer);
		result.addObject("creditCard", creditCard);
		final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
		result.addObject("makes", makes);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Customer customer, final BindingResult binding) {
		ModelAndView result = null;

		customer = this.customerService.reconstructEditDataPeronal(customer, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("customer/edit");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.customerService.saveRegisterAsCustomer(customer);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW CUSTOMER
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Customer registerActorE;
			registerActorE = this.customerService.getCustomerByUserAccountId(userLoggin);
			result = new ModelAndView("customer/show");
			result.addObject("registerActorE", registerActorE);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
