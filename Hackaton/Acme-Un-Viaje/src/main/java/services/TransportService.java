
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Transport;
import domain.Transporter;
import repositories.TransportRepository;
import security.Authority;
import security.LoginService;
import utilities.CommonUtils;

@Service
@Transactional
public class TransportService {

	@Autowired
	private TransportRepository	transportRepository;

	@Autowired
	private TransporterService	transporterService;

	@Autowired
	private Validator			validator;


	// ---------- public class methods
	public Transport create() {
		final Transport res = new Transport();
		return res;
	}

	public Transport save(final Transport t) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));
		Transport res = null;

		if (t.getId() == 0) {
			res = t;
			res.setTransporter(this.transporterService.getLoggedTransporter());
		} else {
			Assert.isTrue(this.loggedIsTransportOwner(t));
			res = t;
		}

		res = this.transportRepository.save(res);

		return res;
	}

	public Collection<Transport> getLoggedTransporterTransports() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final int transporterId = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Collection<Transport> res = this.transportRepository.getTransporterTransports(transporterId);

		return res;
	}

	public Collection<Transport> getLoggedTransporterTransportsFromCurrentDate() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final int transporterId = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Collection<Transport> res = this.transportRepository.getTransporterTransportsFromDate(transporterId, new Date());

		return res;
	}

	public Transport getLoggedTransporterTransport(final int transportId) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final Transport transport = this.transportRepository.findOne(transportId);
		Assert.isTrue(this.loggedIsTransportOwner(transport));

		return transport;
	}

	public Transport reconstruct(final Transport transport, final BindingResult binding) {
		Transport res = null;
		if (transport.getId() == 0)
			res = this.newTransport(transport);
		else
			res = this.editTransport(transport);

		this.validator.validate(res, binding);

		return res;
	}

	// ---------- Inner class methods
	private boolean loggedIsTransportOwner(final Transport transport) {
		final Transporter transporter = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId());
		return transport.getTransporter().equals(transporter);
	}

	private Transport newTransport(final Transport t) {
		final Transport res = t;
		return res;
	}

	private Transport editTransport(final Transport t) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.TRANSPORTER));

		final Transport dbTransport = this.transportRepository.findOne(t.getId());
		Assert.isTrue(this.loggedIsTransportOwner(dbTransport));

		final Transport res = t;
		res.setId(dbTransport.getId());
		res.setVersion(dbTransport.getVersion());

		return res;
	}

    public Collection<Transport> findAll() {
		return this.transportRepository.findAll();
	}

	public Transport findOne(final int id) {
		return this.transportRepository.findOne(id);
	}

	// -- Inner class methods
	private boolean isTransportOwner(final Transport transport) {
		final Transporter transporter = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId());
		return transport.getTransporter().equals(transporter);
	}
}
