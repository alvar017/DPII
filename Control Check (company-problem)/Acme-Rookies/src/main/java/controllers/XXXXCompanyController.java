
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ProblemService;
import services.XXXXService;
import domain.Problem;
import domain.XXXX;

@Controller
@RequestMapping("xxxx/company")
public class XXXXCompanyController extends AbstractController {

	// Services
	@Autowired
	private XXXXService		xxxService;

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private CompanyService	companyService;


	// Methods

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;

		try {
			final Collection<XXXX> xxxxs = this.xxxService.getLoggedXXXXs();
			res = new ModelAndView("xxxx/company/list");
			res.addObject("xxxxs", xxxxs);
			res.addObject("requestURI", "xxxx/company/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(defaultValue = "-1", value = "xxxxId") final int xxxxId) {
		ModelAndView res;

		try {
			final XXXX xxxx = this.xxxService.getLoggedXXXX(xxxxId);
			res = new ModelAndView("xxxx/company/show");
			res.addObject("xxxx", xxxx);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		try {
			final XXXX xxxx = this.xxxService.create();
			res = new ModelAndView("xxxx/company/create");
			final Collection<Problem> problems = this.problemService.findAllProblemsByLoggedCompany();
			res.addObject("XXXX", xxxx);
			res.addObject("problems", problems);
			res.addObject("creating", true);
			res.addObject("URI", "xxxx/company/create.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	// Create
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createPOST(final XXXX xxxx, final BindingResult binding) {
		ModelAndView res;
		XXXX reconstructed = null;
		try {
			reconstructed = this.xxxService.reconstruct(xxxx, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		if (binding.hasErrors()) {
			res = new ModelAndView("xxxx/company/create");
			final Collection<Problem> problems = this.problemService.findAllProblemsByLoggedCompany();
			res.addObject("XXXX", xxxx);
			res.addObject("problems", problems);
			res.addObject("creating", true);
			res.addObject("URI", "xxxx/company/create.do");
		} else
			try {
				this.xxxService.save(reconstructed);
				res = new ModelAndView("redirect:/xxxx/company/list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editGET(@RequestParam(defaultValue = "-1", value = "xxxxId") final int xxxxId) {
		ModelAndView res;

		try {
			final XXXX xxxx = this.xxxService.getLoggedXXXXForEdit(xxxxId);
			res = new ModelAndView("xxxx/company/edit");
			res.addObject("XXXX", xxxx);
			res.addObject("URI", "xxxx/company/edit.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPOST(final XXXX xxxx, final BindingResult binding) {
		ModelAndView res;
		XXXX reconstructed = null;
		try {
			reconstructed = this.xxxService.reconstruct(xxxx, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		if (binding.hasErrors()) {
			res = new ModelAndView("xxxx/company/edit");
			res.addObject("XXXX", xxxx);
			res.addObject("URI", "xxxx/company/edit.do");
		} else
			try {
				this.xxxService.save(reconstructed);
				res = new ModelAndView("redirect:/xxxx/company/list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// Edit
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(defaultValue = "-1", value = "xxxxId") final int xxxxId) {
		ModelAndView res;

		try {
			this.xxxService.delete(xxxxId);
			res = new ModelAndView("redirect:/xxxx/company/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
}
