/*
 * AbstractController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import utilities.Log;

@Controller
public class AbstractController {

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;
		
		Log.log.severe("PANIC");
		Log.log.severe(oops.getMessage());
		result = new ModelAndView("welcome/index");
//		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
//		result.addObject("exception", oops.getMessage());
//		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

}
