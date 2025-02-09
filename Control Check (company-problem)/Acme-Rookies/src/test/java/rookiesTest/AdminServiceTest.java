
package rookiesTest;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.MessageService;
import services.PositionService;
import services.RookieService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Message;
import domain.Rookie;
import forms.ActorForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			msgService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private CompanyService			companyService;


	@Test
	/*
	 * Test SPAMMER checks for finding actors which send spam;
	 * 
	 * POSITIVE TEST
	 * Actor sends normal message and is not setted to spammer.
	 * 
	 * NEGATIVE TEST
	 * Actor sends spam and is setted to spammer.
	 * 
	 * Requirement under test: 24 (Acme-Rookie-Rank)
	 * 
	 * Analysis of sentence coverage: 90%
	 * Analysis of data coverage: 27%
	 */
	public void actorSpammer() {

		final Object testingData[][] = {

			{
				0, null
			}, {
				1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.actorSpammer((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	/*
	 * Test BAN actor;
	 * 
	 * POSITIVE TEST
	 * Logged Admin tries to ban a spammer.
	 * 
	 * NEGATIVE TEST
	 * Logged Admin tries to ban an "good" actor.
	 * Logged User (NON-Admin) tries to ban.
	 * 
	 * Requirement under test: 24 (Acme-Rookie-Rank)
	 * 
	 * Analysis of sentence coverage: 90%
	 * Analysis of data coverage: 75%
	 */
	public void adminBanActor() {
		final Object testingData[][] = {

			{
				"admin", 0, IllegalArgumentException.class
			}, {
				"rookieuser", 0, IllegalArgumentException.class
			}, {
				"rookieuser", 1, IllegalArgumentException.class
			}, {
				"admin", 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminBanActor((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	/*
	 * Test UNBAN actor;
	 * 
	 * POSITIVE TEST
	 * Logged Admin tries to unban a spammer.
	 * 
	 * NEGATIVE TEST
	 * Logged User(NON-Admin) tries to unban.
	 * 
	 * Requirement under test: 24 (Acme-Rookie-Rank)
	 * 
	 * Analysis of sentence coverage: 80%
	 * Analysis of data coverage: 75%
	 */
	public void adminUnbanActor() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"rookieuser", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminUnbanActor((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	/*
	 * Test Reconstruct Admin;
	 * 
	 * POSITIVE TEST
	 * Logged Admin tries to register a new admin.
	 * 
	 * NEGATIVE TEST
	 * Logged User (NON-Admin) tries to register a new admin.
	 * 
	 * Requirement under test: 11 (Acme-Rookie-Rank)
	 * 
	 * Analysis of sentence coverage: 75%
	 * Analysis of data coverage: 75%
	 */
	public void adminReconstructCreate() {

		final Object testingData[][] = {

			{
				"admin", "name", "surname", "formUsername", "password", "password", "address", "adminemail@validEmail.com", "123456789", "holder", "VISA", "1234123412341234", "234", 0, true, null
			}, {
				"rookieuser", "name", "surname", "formUsername", "password", "password", "address", "adminemail@validEmail.com", "123456789", "holder", "VISA", "1234123412341234", "234", 0, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminReconstructCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (int) testingData[i][13], (Boolean) testingData[i][14],
				(Class<?>) testingData[i][15]);
	}

	@Test
	/*
	 * Test Reconstruct Admin;
	 * 
	 * POSITIVE TEST
	 * Logged Admin tries to register a new admin.
	 * 
	 * NEGATIVE TEST
	 * Logged User (NON-Admin) tries to register a new admin.
	 * 
	 * Requirement under test: 11 (Acme-Rookie-Rank)
	 * 
	 * Analysis of sentence coverage: 75%
	 * Analysis of data coverage: 75%
	 */
	public void adminCreate() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"rookieuser", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminUnbanActor((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	@Test
	/*
	 * Test para probar el save del create de un admin (Este test junto con el de create comprueban el caso de uso al completo)
	 */
	public void adminSave() {

		final Object testingData[][] = {

			{
				"emailvalido@valido.com", null
			}, {
				"emailnovalido", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminSave((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected void adminSave(final String email, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			this.authenticate("admin");
			final Administrator admin = this.adminService.create();
			admin.setName("newAdmin");
			admin.setSurname("newShur");
			admin.getUserAccount().setUsername("newAdmin");
			admin.getUserAccount().setPassword("password");
			admin.setAddress("calle de los 2 vasos de agua");
			admin.setEmail(email);
			admin.setPhone("1234");
			admin.setVatNumber("hh33h");
			admin.getCreditCard().setHolder("holder");
			admin.getCreditCard().setMake("VISA");
			admin.getCreditCard().setNumber("123412341234");
			admin.getCreditCard().setCVV("123");
			admin.getCreditCard().setExpiration("03/2020");
			this.adminService.saveCreate(admin);

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	protected void adminCreate(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			if (username != "admin") {

				final Rookie rookie = this.rookieService.create();
				rookie.setAddress("calle rookie test");
				rookie.setCreditCard(null);
				rookie.setEmail("elcejas@rookie.com");
				rookie.setName("rookiename");
				rookie.setPhone("123456789");
				rookie.setSurname("rookiesurname");
				rookie.setVatNumber("dd33e");

				rookie.getUserAccount().setUsername("rookieuser");
				rookie.getUserAccount().setPassword("rookiepass");
				this.rookieService.saveCreate(rookie);
			}

			this.authenticate(username);
			this.adminService.create();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	protected void adminReconstructCreate(final String username, final String name, final String surname, final String formUsername, final String password, final String confirmPassword, final String address, final String email, final String phone,
		final String holder, final String make, final String number, final String cvv, final int expDate, final Boolean agreed, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			if (username != "admin") {

				final Rookie rookie = this.rookieService.create();
				rookie.setAddress("calle rookie test");
				rookie.setCreditCard(null);
				rookie.setEmail("elcejas@rookie.com");
				rookie.setName("rookiename");
				rookie.setPhone("123456789");
				rookie.setSurname("rookiesurname");
				rookie.setVatNumber("dd33f");
				rookie.getUserAccount().setUsername("rookieuser");
				rookie.getUserAccount().setPassword("rookiepass");
				this.rookieService.saveCreate(rookie);
			}

			final ActorForm form = new ActorForm();
			form.setName(name);
			form.setSurname(surname);
			form.setUserName(formUsername);
			form.setPassword(password);
			form.setConfirmPassword(confirmPassword);
			form.setAddress(address);
			form.setEmail(email);
			form.setPhone(phone);
			form.setHolder(holder);
			form.setMake(make);
			form.setVatNumber("dd32f");
			form.setNumber(number);
			form.setCVV(cvv);
			if (expDate == 0)
				form.setExpiration("03/22");
			else
				form.setExpiration("03/22");
			form.setAccept(agreed);

			this.authenticate(username);
			final Administrator recons = this.adminService.reconstructCreate(form, null);
			this.adminService.saveCreate(recons);

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}
	protected void actorSpammer(final int spammer, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setSurname("rookiesurname");
			rookie.setVatNumber("aa32d");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			final Rookie savedRookie = this.rookieService.saveCreate(rookie);
			this.authenticate("rookieuser");

			final Message msg = this.msgService.create();
			msg.setSubject("subject");
			if (spammer == 1)
				msg.setBody("sex");
			else
				msg.setBody("no spam");
			final Collection<String> tags = new ArrayList<String>();
			tags.add("<tag>");
			final Collection<String> recipient = new ArrayList<String>();
			recipient.add("admin@admin.com");
			msg.setRecipient(recipient);
			this.msgService.save(msg);

			Assert.isTrue(!savedRookie.getUserAccount().getSpammerFlag());
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminBanActor(final String username, final int spammer, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setSurname("rookiesurname");
			rookie.setVatNumber("hh33h");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			if (spammer == 1)
				rookie.getUserAccount().setSpammerFlag(true);
			final Rookie savedRookie = this.rookieService.saveCreate(rookie);

			this.authenticate(username);
			this.actorService.banByActorId(savedRookie);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminUnbanActor(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setSurname("rookiesurname");
			rookie.setVatNumber("hh33h");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			rookie.getUserAccount().setSpammerFlag(true);
			final Rookie savedRookie = this.rookieService.saveCreate(rookie);

			this.authenticate(username);
			this.actorService.unbanByActorId(savedRookie);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	/**
	 * ACME-ROOKIES R4.3: Launch a process to compute an audit score for every company.
	 * Sentence coverage: ~80%
	 * Data coverage: ~20%
	 */
	@Test
	public void calculateCompanyScore() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"company", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.calculateCompanyScore((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	private void calculateCompanyScore(final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(user);
			this.adminService.calculateCompaniesScore();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * ACME-ROOKIES R4.4: Display a dashboard with the following information
	 * Sentence coverage: ~80%
	 * Data coverage: ~20%
	 */
	@Test
	public void dashboard4_4() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"company", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboard4_4((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	private void dashboard4_4(final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(user);
			this.positionService.avgMinMaxStddevPositionAuditScore();
			this.companyService.avgMinMaxStddevCompanyAuditScore();
			this.companyService.getCompaniesWithHighestAuditScore();
			this.companyService.avgSalaryOfCompanyHighestScore();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
