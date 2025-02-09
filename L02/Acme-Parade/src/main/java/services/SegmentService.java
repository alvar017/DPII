
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SegmentRepository;
import security.LoginService;
import domain.Parade;
import domain.Path;
import domain.Segment;

@Service
public class SegmentService {

	@Autowired
	private SegmentRepository	segmentRepository;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private Validator			validator;

	@Autowired
	private PathService			pathService;


	public Segment create() {
		final Segment res = new Segment();

		return res;
	}

	public Segment save(Segment segment, final int paradeId) {
		// We need the paradeId to check if we are the owner of the parade
		this.assertParadeOwner(paradeId);
		Assert.isTrue(segment.getArrivalTime().before(segment.getDestination().getArrivalTime()));
		segment = this.segmentRepository.save(segment);
		return segment;
	}
	public List<Segment> getAllSegments(final Path path) {
		List<Segment> fullPath = null;
		Segment next = null;
		if (path != null) {
			next = path.getOrigin();
			fullPath = new ArrayList<>();
			while (next != null) {
				fullPath.add(next);
				next = next.getDestination();
			}
		}
		return fullPath;
	}

	public Segment findOne(final int id) {
		return this.segmentRepository.findOne(id);
	}

	public Segment reconstruct(final Segment segment, final BindingResult binding) {
		// This is only called when we have a path, segment exists
		Assert.notNull(segment.getDestination());
		final Segment res = this.create();

		final Segment aux = this.segmentRepository.findOne(segment.getId());

		final Path path = this.pathService.findFromOriginSegment(aux.getId());
		if (path != null)
			res.setArrivalTime(segment.getArrivalTime());
		else
			res.setArrivalTime(aux.getArrivalTime());

		res.setId(aux.getId());
		res.setVersion(aux.getVersion());
		res.setLatitude(segment.getLatitude());
		res.setLongitude(segment.getLongitude());

		if (aux.getDestination() != null)
			res.setDestination(aux.getDestination());
		else
			res.setDestination(this.create());

		res.getDestination().setLatitude(segment.getDestination().getLatitude());
		res.getDestination().setLongitude(segment.getDestination().getLongitude());
		res.getDestination().setArrivalTime(segment.getDestination().getArrivalTime());

		this.validator.validate(res, binding);
		// We have to also validate the destination
		this.validator.validate(res.getDestination(), binding);
		return res;
	}

	public void delete(final int segmentId, final int paradeId) {
		this.assertParadeOwner(paradeId);
		final Segment segment = this.segmentRepository.findOne(segmentId);

		// We don't delete this segment, we assign this one the destination attributes if any
		if (segment.getDestination() != null) {
			System.out.println("Tenemos destination... " + segment.getDestination());
			final int segmentToDelete = segment.getDestination().getId();
			segment.setArrivalTime(segment.getDestination().getArrivalTime());
			segment.setLatitude(segment.getDestination().getLatitude());
			segment.setLongitude(segment.getDestination().getLongitude());
			segment.setDestination(segment.getDestination().getDestination());
			// We delete the segment which was the destination
			this.segmentRepository.delete(segmentToDelete);
		} else {
			System.out.println("NO Tenemos destination... " + segment.getDestination());
			// We want to delete the last segment of a path, which is this one, 
			// so we have to dereference it on the parent segment
			final Segment parent = this.segmentRepository.findParent(segmentId);
			if (parent != null)
				parent.setDestination(null);
			this.segmentRepository.delete(segmentId);
		}
	}

	// =============== PRIVATE METHODS

	private void assertParadeOwner(final int paradeId) {
		final int loggedId = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Parade parade = this.paradeService.findOne(paradeId);
		Assert.isTrue(loggedId == parade.getBrotherhood().getId());
	}

	public void deleteFromDB(final int id) {
		// This is only called internally
		this.segmentRepository.delete(id);

	}

}
