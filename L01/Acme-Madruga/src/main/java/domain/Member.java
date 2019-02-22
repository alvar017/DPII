/*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Member extends Actor {

	//	private Collection<Enrolled>	enrolleds;
	//
	//
	//	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	//	public Collection<Enrolled> getEnrolleds() {
	//		return this.enrolleds;
	//	}
	//
	//	public void setEnrolleds(final Collection<Enrolled> enrolleds) {
	//		this.enrolleds = enrolleds;
	//	}

}
