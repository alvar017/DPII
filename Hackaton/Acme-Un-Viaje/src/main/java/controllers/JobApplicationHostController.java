package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Host;
import domain.JobApplication;
import services.CleanerService;
import services.CurriculaService;
import services.HostService;
import services.JobApplicationService;

@Controller
@RequestMapping("/jobApplication/host")
public class JobApplicationHostController extends AbstractController {

	@Autowired
	private CleanerService cleanerService;
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private JobApplicationService	jobApplicationService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	// Default String
	
	private String redirectWelcome = "redirect:/welcome/index.do";
	
	private ModelAndView getList(ModelAndView result) {
		final Host hostLogin = this.hostService.getHostLogin();
		Collection<JobApplication> pending = this.jobApplicationService.getJobApplicationPendingByHostId(hostLogin.getId());
		Collection<JobApplication> rejected = this.jobApplicationService.getJobApplicationByStatusAndHostId(false, hostLogin.getId());
		Collection<JobApplication> accepted = this.jobApplicationService.getJobApplicationByStatusAndHostId(true, hostLogin.getId());
		Collection<JobApplication> exCleaners = this.jobApplicationService.getExCleaners(hostLogin.getId());
		result.addObject("pending", pending);
		result.addObject("rejected", rejected);
		result.addObject("accepted", accepted);
		result.addObject("exCleaners", exCleaners);
		result.addObject("numberPending", listNumber(pending.size()));
		result.addObject("hostLogin", hostLogin);
		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Host host = this.hostService.getHostLogin();
			Assert.notNull(host, "Not host found in DB");
			result = new ModelAndView("jobApplication/host/list");
			this.getList(result);
			result.addObject("requestURI", "jobApplication/host/list.do");
		} catch (final Exception e) {
			System.out.println("Error e en GET /list jobApplicationController.java: " + e);
			result = new ModelAndView(redirectWelcome);
		}
		return result;
	}
	
	private List<Integer> listNumber(int size) {
		List<Integer> res = new ArrayList<>();
		int aux = 0;
		while (aux < size) {
			res.add(aux);
			aux++;
		}
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "jobApplicationId", defaultValue = "-1") final int jobApplicationId) {
		ModelAndView result;
		try {
			this.jobApplicationService.acceptApplication(jobApplicationId);
			result = new ModelAndView("jobApplication/host/list");
			this.getList(result);
			result.addObject("requestURI", "jobApplication/host/list.do");
		} catch (final Exception e) {
			System.out.println("Error en EDIT JobApplicationHostController.java Throwable: " + e);
			result = new ModelAndView(redirectWelcome);
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(JobApplication jobApplication, final BindingResult binding) {
		ModelAndView result;
		try {
			jobApplication = this.jobApplicationService.reconstruct(jobApplication, binding);
		} catch (final Exception e) {
			System.out.println("Error e reconstruct de jobApplication: " + e);
			result = new ModelAndView(redirectWelcome);
			return result;
		}

		if (binding.hasErrors()) {
			System.out.println("Error en JobApplicationCleanerController.java, binding: " + binding);
			result = new ModelAndView("jobApplication/cleaner/create");
			result.addObject("curriculas", this.curriculaService.findAllNotCopyByCleaner(this.cleanerService.getCleanerLogin()));
			result.addObject("jobApplication", jobApplication);
		} else
			try {
				this.jobApplicationService.save(jobApplication);
				result = new ModelAndView("redirect:/jobApplication/cleaner/list.do");
				result.addObject("requestURI", "jobApplication/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE JobApplicationCleanerController.java Throwable: " + oops);
				result = new ModelAndView("jobApplication/cleaner/list");
				result.addObject("jobApplication", jobApplication);
				result.addObject("message", "jobApplication.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/drop", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "jobApplicationId", defaultValue = "-1") final int jobApplicationId) {
		ModelAndView result;
		final Host hostLogin = this.hostService.getHostLogin();

		try {
			Assert.notNull(hostLogin, "No host is login");
			this.jobApplicationService.dropUser(jobApplicationId);
			result = new ModelAndView("jobApplication/host/list");
			this.getList(result);
			result.addObject("requestURI", "jobApplication/host/list.do");
		} catch (final Throwable oops) {
			System.out.println("Error en CurriculaCleanerController.java Throwable: " + oops);
			result = new ModelAndView("redirect:/jobApplication/host/list.do");
			result.addObject("message", "curricula.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam(value = "jobApplicationId", defaultValue = "-1") final int jobApplicationId) {
		ModelAndView result;
		final Host hostLogin = this.hostService.getHostLogin();

		try {
			Assert.notNull(hostLogin, "No host is login");
			final JobApplication jobApplicationDB = this.jobApplicationService.findOne(jobApplicationId);
			Assert.notNull(jobApplicationDB, "Not found jobApplication in DB");
			result = new ModelAndView("jobApplication/host/reject");
			result.addObject("jobApplication", jobApplicationDB);
			result.addObject("host", hostLogin);
			result.addObject("requestURI", "jobApplication/host/reject.do");
		} catch (final Throwable oops) {
			System.out.println("Error en CurriculaCleanerController.java Throwable: " + oops);
			result = new ModelAndView("redirect:/jobApplication/host/list.do");
			result.addObject("message", "curricula.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView reject(JobApplication jobApplication, final BindingResult binding) {
		ModelAndView result;
		Host hostLogin = this.hostService.getHostLogin();
		try {
			Assert.notNull(hostLogin, "Host is null");
			jobApplication = this.jobApplicationService.reconstruct(jobApplication, binding);
		} catch (final Exception e) {
			System.out.println("Error e reconstruct de jobApplication: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}

		if (binding.hasErrors()) {
			System.out.println("Error en JobApplicationHostController.java, binding: " + binding);
			result = new ModelAndView("jobApplication/host/reject");
			result.addObject("jobApplication", jobApplication);
			result.addObject("host", hostLogin);
		} else
			try {
				this.jobApplicationService.rejectUser(jobApplication);
				result = new ModelAndView("redirect:/jobApplication/host/list.do");
				result.addObject("requestURI", "jobApplication/host/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE JobApplicationHostController.java Throwable: " + oops);
				result = new ModelAndView("jobApplication/host/reject");
				result.addObject("jobApplication", jobApplication);
				result.addObject("host", hostLogin);
				result.addObject("message", "jobApplication.commit.error");
			}
		return result;
	}
}
