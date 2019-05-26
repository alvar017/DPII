
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ComplaintRepository;
import security.Authority;
import utilities.CommonUtils;
import domain.Complaint;
import domain.Customer;
import domain.TravelPack;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TravelPackService	travelPackService;


	public Collection<Complaint> getLoggedCustomerAssignedComplaints() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.complaintRepository.getLoggedCustomerAssignedComplaints(c.getId());
	}

	public Collection<Complaint> getLoggedCustomerUnassignedComplaints() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.complaintRepository.getLoggedCustomerUnassignedComplaints(c.getId());
	}

	public Complaint getLoggedCustomerComplaint(final Integer complaintId) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.complaintRepository.getLoggedCustomerComplaint(c.getId(), complaintId);
	}

	public Complaint create() {
		final Complaint res = new Complaint();
		return res;
	}

	public Complaint reconstruct(final Complaint complaint, final BindingResult binding) {
		final Complaint res = complaint;
		if (complaint.getId() != 0)
			Assert.isTrue(this.isAssigned(complaint.getId()) == false);

		res.setCustomer(this.customerService.getLoggedCustomer());
		res.setMoment(new Date());
		return res;
	}

	public void save(final Complaint reconstructedComplaint) {
		reconstructedComplaint.setMoment(new Date());

		this.complaintRepository.save(reconstructedComplaint);
	}
	
	public void saveWithoutSetMoment(final Complaint reconstructedComplaint) {
		this.complaintRepository.save(reconstructedComplaint);
	}

	private boolean isAssigned(final int complaintId) {
		return this.complaintRepository.findOne(complaintId).getReview() != null;
	}

	public void delete(final int complaintId) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		Assert.isTrue(this.isAssigned(complaintId) == false);
		final TravelPack tp = this.travelPackService.findFromComplaint(complaintId);
		tp.getComplaints().remove(this.complaintRepository.findOne(complaintId));
		this.complaintRepository.delete(complaintId);
	}
	
	public Collection<Complaint> getComplaintsWithoutReview() {
		
		return this.complaintRepository.getComplaintsWithoutReview();
	}
	
	public Complaint findOne(final Integer complaintId) {
		return this.complaintRepository.findOne(complaintId);
	}
	
	public Complaint getComplaintOfReview(int reviewId) {	
		Complaint res = complaintRepository.getComplaintOfReview(reviewId);
		return res;
	}
}
