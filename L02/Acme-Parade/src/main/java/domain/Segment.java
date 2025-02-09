
package domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	// If we create a path these attributes can't be null because this is the destination of the previous path.
	// We have to use BigDecimal in order to use @Max and @Min annotations
	private BigDecimal	latitude;
	private BigDecimal	longitude;

	private Date		arrivalTime;

	// We'll create linked paths so that the origin of the destination path is the destination of THIS path.
	// If a path has no destination, it means we are in the FINAL path.
	private Segment		destination;


	@NotNull
	@DecimalMax("90.000000")
	@DecimalMin("-90.000000")
	@Column(name = "latitude", columnDefinition = "DECIMAL(8,6)")
	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final BigDecimal latitude) {
		this.latitude = latitude;
	}

	@NotNull
	@DecimalMax("180.000000")
	@DecimalMin("-180.000000")
	@Column(name = "longitude", columnDefinition = "DECIMAL(9,6)")
	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(final BigDecimal longitude) {
		this.longitude = longitude;
	}

	@OneToOne(optional = true, cascade = {
		CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH
	})
	public Segment getDestination() {
		return this.destination;
	}

	public void setDestination(final Segment destination) {
		this.destination = destination;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(final Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}
