
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * Transport
 */
@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TravelPack extends DomainEntity {

	// --- Attributes
	private String							name;
	private Collection<BookingAccomodation>	accomodations;
	private Collection<BookingTransport>	transports;
	private Customer						customer;
	private TravelAgency					travelAgency;
	private Boolean							draft;
	private Double							price;
	private Collection<Complaint>			complaints;
	private Boolean							status;
	private Warranty						warranty;


	@ManyToOne(optional = true)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	public Boolean getDraft() {
		return this.draft;
	}

	public void setDraft(final Boolean draft) {
		this.draft = draft;
	}

	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<BookingAccomodation> getAccomodations() {
		return this.accomodations;
	}

	public void setAccomodations(final Collection<BookingAccomodation> accomodations) {
		this.accomodations = accomodations;
	}
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<BookingTransport> getTransports() {
		return this.transports;
	}

	public void setTransports(final Collection<BookingTransport> transports) {
		this.transports = transports;
	}
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}
	@ManyToOne(optional = false)
	public TravelAgency getTravelAgency() {
		return this.travelAgency;
	}

	public void setTravelAgency(final TravelAgency travelAgency) {
		this.travelAgency = travelAgency;
	}
	@Transient
	public Double getPrice() {
		Double d = 0d;
		for (final BookingTransport bt : this.transports)
			d += bt.getTransport().getPrice();
		for (final BookingAccomodation ba : this.accomodations)
			d += ba.getAccomodation().getPricePerNight();

		return d;
	}
	public void setPrice(final Double price) {
		this.price = price;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(final Boolean status) {
		this.status = status;
	}

}
