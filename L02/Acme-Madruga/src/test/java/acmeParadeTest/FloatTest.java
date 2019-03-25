/*
 * FloatTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package acmeParadeTest;


import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.FloatService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FloatTest extends AbstractTest {

	@Autowired
	private FloatService	floatService;

	// Tests ------------------------------------------------------------------

	@Test
	public void test01() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test the creation of a float.
		 * 
		 * Information requirements
		 * 
		 *	5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures. 
		 *	   Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 *    
		 * Functional requirements
		 * 
		 * 	10. An actor who is authenticated as a brotherhood must be able to:
		 *		1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			// brotherhoodId, pictures
			{ 
				"brotherhood", 0, null
			}, { 
				"brotherhood", 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (int) testingData[i][1],(Class<?>) testingData[i][2]);
	}

	protected void checkTest(final String userName, final int pictures, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			
			domain.Float floatt = this.floatService.create();
			floatt.setTitle("El t�tulo");
			floatt.setDescription("La descripci�n");
			
			if (pictures!=0) {
				floatt.setPictures("https://www.google.es/");
			} else {
				floatt.setPictures("");
			}
			
			
			this.floatService.save(floatt);
			super.unauthenticate();
			
			this.floatService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void test02() {
		/*
		 * NEGATIVE TEST
		 * 
		 * In this test we will test the creation of a float.
		 * 
		 * Information requirements
		 * 
		 *	5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures. 
		 *	   Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 *    
		 * Functional requirements
		 * 
		 * 	10. An actor who is authenticated as a brotherhood must be able to:
		 *		1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			// brotherhoodId, titleBlank, descriptionBlank, titleNull, descriptionNull, picturesInvalid 
			{ 
				"brotherhood", 0, 0, 0, 0, 0, null
			}, { 
				"brotherhood", 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void checkTest(final String userName, final int titleBlank, final int descriptionBlank, final int titleNull, final int descriptionNull, final int  picturesInvalid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			
			domain.Float floatt = this.floatService.create();
			floatt.setTitle("El t�tulo");
			floatt.setDescription("La descripci�n");
			
			if (titleBlank!=0 && titleNull==0) {
				floatt.setTitle("");
			} else if (titleBlank==0 && titleNull!=0) {
				floatt.setTitle(null);
			} else {
				floatt.setTitle("Valid title");
			}
			
			if (descriptionBlank!=0 && descriptionNull==0) {
				floatt.setDescription("");
			} else if (descriptionBlank==0 && descriptionNull!=0) {
				floatt.setDescription(null);
			} else {
				floatt.setDescription("Valid description");
			}
			
			if (picturesInvalid!=0) {
				floatt.setPictures("URL no v�lida");
			} else {
				floatt.setPictures("https://www.myPhoto.com?id=90403/'https://www.myPhoto.com?id=90402/");
			}
			
			if (((titleBlank*titleNull) + (descriptionBlank*descriptionNull)) > 0 && picturesInvalid==0) {
				// Forzado de error ya que es un caso v�lido
				floatt.setTitle("");
			}
			
			this.floatService.save(floatt);
			super.unauthenticate();
			
			this.floatService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
//		System.out.println("Esperado: " + expected + ", capturado: " + caught + " caso: " + titleBlank + descriptionBlank + titleNull + descriptionNull + picturesInvalid);
	}

	@Test
	public void test03() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test delete a float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures. 
		 *	   Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 *    
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 *		1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			{ 
				"brotherhood2", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkDeleteM((String) testingData[i][0] ,(Class<?>) testingData[i][1]);
	}
	
	@Test
	public void test04() {
		/*
		 * NEGATIVE TEST
		 * 
		 * In this test we will test delete a float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures. 
		 *	   Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 *    
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 *		1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			{ 
				"brotherhood2", null
			}, { 
				"brotherhood", IllegalArgumentException.class
			}, { 
				"admin", IllegalArgumentException.class
			}, { 
				"chapter", IllegalArgumentException.class
			}, { 
				"member", IllegalArgumentException.class
			}, { 
				"sponsor", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkDeleteM((String) testingData[i][0] ,(Class<?>) testingData[i][1]);
	}

	protected void checkDeleteM(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			
			domain.Float floatt;
			domain.Float floattSaved;
			
			if (userName.equals("brotherhood2")) {
				floatt = this.floatService.create();
				floatt.setTitle("El t�tulo");
				floatt.setDescription("La descripci�n");
				floattSaved = this.floatService.save(floatt);
			} else {
				super.unauthenticate();
				super.authenticate("brotherhood2");
				
				floatt = this.floatService.create();
				floatt.setTitle("El t�tulo");
				floatt.setDescription("La descripci�n");
				floattSaved = this.floatService.save(floatt);				
				
				super.unauthenticate();
				super.authenticate(userName);
			}
			
			this.floatService.delete(floattSaved);
			this.floatService.flush();
			
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
