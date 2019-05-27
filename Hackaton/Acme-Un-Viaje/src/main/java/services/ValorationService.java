
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ValorationRepository;
import domain.Cleaner;
import domain.Host;
import domain.JobApplication;
import domain.Valoration;

@Service
@Transactional
public class ValorationService {

	@Autowired
	private ValorationRepository	valorationRepository;

	@Autowired
	private JobApplicationService	jobAppService;

	@Autowired
	private CleanerService			cleanerService;


	public Valoration create() {

		final Valoration res = new Valoration();
		return res;
	}

	public Valoration save(final Valoration valoration) {

		return this.valorationRepository.save(valoration);
	}

	public Boolean checkValorationHostCleaner(final Host host, final Cleaner cleaner) {

		final Host logged = host;
		final Collection<JobApplication> loggedJobs = this.jobAppService.getJobApplicationByStatusAndHostId(true, logged.getId());
		final Collection<Cleaner> cleaners = this.cleanerService.getAllCleanersInJobList(loggedJobs);
		return cleaners.contains(cleaner);
	}
}
