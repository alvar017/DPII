
package acmeParadeTest;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.SegmentRepository;
import services.ParadeService;
import services.PathService;
import services.SegmentService;
import utilities.AbstractTest;
import domain.Path;
import domain.Segment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SegmentTest extends AbstractTest {

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private SegmentService		segmentService;
	@Autowired
	private SegmentRepository	segmentRepository;

	@Autowired
	private PathService			pathService;


	/*
	 * (LEVEL B 3.3) Testing updating a path
	 * A path can be updated by adding a segment to the last segment of it, updating a segment or deleting one
	 * 
	 * Analysis of sentence coverage: 84.8% (Source: EclEmma)
	 * 
	 * Analysis of data coverage: ~60% (Source: Segment has latitude, longitude, arrivalTime and destination. I think these tests could have around 70% data coverage)
	 */
	@Test
	public void SegmentDriver1() {

		final Object testingData[][] = {
			// ======== CREATE ========
			// ----- Positive tests (insert valid latitude or longitude)
			{
				"brotherhood", 0.123456f, 0.123456f, "create", null
			}, {
				"brotherhood", 90f, 0f, "create", null
			}, {
				"brotherhood", -90f, 0f, "create", null
			}, {
				"brotherhood", 0f, 180f, "create", null
			}, {
				"brotherhood", 0f, -180f, "create", null
			}, {
				"brotherhood", 90f, 180f, "create", null
			}, {
				"brotherhood", -90f, -180f, "create", null
			}, { // This is only called internally
				"brotherhood", 0f, 0f, "internal", null
			},
			// ------ Negative tests (insert invalid latitude or longitude)
			{
				"brotherhood", 91f, 0f, "create", ConstraintViolationException.class
			}, {
				"brotherhood", -91f, 0f, "create", ConstraintViolationException.class
			}, {
				"brotherhood", 0f, 181f, "create", ConstraintViolationException.class
			}, {
				"brotherhood", 0f, -181f, "create", ConstraintViolationException.class
			}, {
				"brotherhood", 91f, 181f, "create", ConstraintViolationException.class
			}, {
				"brotherhood", -91f, -181f, "create", ConstraintViolationException.class
			},
			// ------ Negative tests (invalid user)
			{
				"member", 0f, 0f, "create", IllegalArgumentException.class
			}, {
				"sponsor", 0f, 0f, "create", IllegalArgumentException.class
			}, {
				"chapter", 0f, 0f, "create", IllegalArgumentException.class
			}, {
				"admin", 0f, 0f, "create", IllegalArgumentException.class
			},
			// ======== MODIFY ========
			// ----- Positive tests (insert valid user, latitude or longitude)
			{
				"brotherhood", 0.123456f, 0.123456f, "modify", null
			},
			// ------ Negative tests (insert valid user, invalid latitude or longitude)
			{
				"brotherhood", 91f, 0f, "modify", ConstraintViolationException.class
			}, {
				"brotherhood", 91f, -190f, "modify", ConstraintViolationException.class
			},
			// ------ Negative tests (invalid user)
			{
				"member", 0f, 0f, "modify", IllegalArgumentException.class
			}, {
				"sponsor", 0f, 0f, "modify", IllegalArgumentException.class
			}, {
				"admin", 0f, 0f, "modify", IllegalArgumentException.class
			}, {
				"chapter", 0f, 0f, "modify", IllegalArgumentException.class
			},
			// ======== DELETE ========
			// ----- Positive tests (valid user)
			{
				"brotherhood", 0f, 0f, "delete", null
			},
		//			// ------ Negative tests (invalid user)
		//			{
		//				"member", 0f, 0f, "delete", IllegalArgumentException.class
		//			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.SegmentTemplate((String) testingData[i][0], (float) testingData[i][1], (float) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void SegmentTemplate(final String userName, final float latitude, final float longitude, final String mode, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			super.authenticate(userName);

			// I need at least one parade of the logged brotherhood to make this work
			final int paradeId = this.paradeService.findAllBrotherhoodLogged().iterator().next().getId();
			Segment segment = null;
			if (mode == "create") {
				final Calendar calendar = Calendar.getInstance();
				segment = this.segmentService.create();
				segment.setLatitude(BigDecimal.valueOf(latitude));
				segment.setLongitude(BigDecimal.valueOf(longitude));
				segment.setArrivalTime(calendar.getTime());

				final Segment destination = this.segmentService.create();
				destination.setLatitude(BigDecimal.valueOf(latitude));
				destination.setLongitude(BigDecimal.valueOf(longitude));
				calendar.add(Calendar.HOUR, 1);
				destination.setArrivalTime(calendar.getTime());
				segment.setDestination(destination);

				segment = this.segmentService.save(segment, paradeId);
				this.segmentRepository.flush();

				final BindingResult binding = null;
				this.segmentService.reconstruct(segment, binding);
				Assert.notNull(this.segmentService.findOne(segment.getId()));
			} else if (mode == "modify") {
				final Calendar calendar = Calendar.getInstance();
				segment = this.segmentService.create();
				segment.setLatitude(BigDecimal.valueOf(latitude));
				segment.setLongitude(BigDecimal.valueOf(longitude));
				segment.setArrivalTime(calendar.getTime());

				final Segment destination = this.segmentService.create();
				destination.setLatitude(BigDecimal.valueOf(latitude));
				destination.setLongitude(BigDecimal.valueOf(longitude));
				calendar.add(Calendar.HOUR, 1);
				destination.setArrivalTime(calendar.getTime());
				segment.setDestination(destination);
				this.segmentService.save(segment, paradeId);
			} else if (mode == "delete") {
				final Path path = this.pathService.findFromOriginSegment(this.getEntityId("segment1"));
				final Segment lastSegment = this.segmentService.getAllSegments(path).get(this.segmentService.getAllSegments(path).size() - 1);
				this.segmentService.delete(lastSegment.getId(), paradeId);
			} else if (mode == "internal") {
				final Calendar calendar = Calendar.getInstance();
				segment = this.segmentService.create();
				segment.setLatitude(BigDecimal.valueOf(latitude));
				segment.setLongitude(BigDecimal.valueOf(longitude));
				segment.setArrivalTime(calendar.getTime());

				final Segment destination = this.segmentService.create();
				destination.setLatitude(BigDecimal.valueOf(latitude));
				destination.setLongitude(BigDecimal.valueOf(longitude));
				calendar.add(Calendar.HOUR, 1);
				destination.setArrivalTime(calendar.getTime());
				segment.setDestination(destination);
				segment = this.segmentService.save(segment, paradeId);
				this.segmentService.deleteFromDB(segment.getId());
			}

			this.segmentRepository.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
