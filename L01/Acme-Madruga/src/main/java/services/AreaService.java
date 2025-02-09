
package services;

/*
 * CONTROL DE CAMBIOS AdministratorService.java
 * FRAN 19/02/2019 11:36 CREACI�N DE LA CLASE
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import security.LoginService;
import domain.Administrator;
import domain.Area;

@Service
@Transactional
public class AreaService {

	//Managed Repository -------------------	
	@Autowired
	private AreaRepository			areaRepository;

	//Supporting services ------------------
	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	//Simple CRUD Methods ------------------

	public Area create() {

		// "Check that an Admin is creating the new Area"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");
		// "New Area Creation"
		final Area area = new Area();
		// "Return new Area"
		return area;
	}

	public Area save(final Area area) {

		if (area.getPictures().size() != 0) {
			final List<String> s = new ArrayList<String>(area.getPictures());
			final String[] pictures = s.get(0).split("'");
			final List<String> s2 = Arrays.asList(pictures);
			area.setPictures(s2);
		}
		
		// "Check that an Admin is saving the new Area"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");
		
		return this.areaRepository.save(area);
	}

	public Area update(final Area area) {

		// "Check that an Admin is updating the Area"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");
		// "Return new Area"
		return this.update(area);
	}

	//Other Methods ------------------

	public Collection<Area> findAll() {

		final List<Area> res = this.areaRepository.findAll();
		return res;
	}

	public Area findOne(final int areaId) {

		final Area res = this.areaRepository.findOne(areaId);
		return res;
	}

	public void delete(final int areaId) {

		// findOne Area with areaId and check it for null
		final Area area = this.findOne(areaId);
		Assert.notNull(area, "areaExist.error");
		// check if Area has a Brotherhood settled
		Assert.isTrue(this.brotherhoodService.findByAreaId(areaId).size() == 0, "areaUsed.error");
		// delete the Area
		this.areaRepository.delete(areaId);
	}
}
