
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	private String	nick;
	private String	link;
	private String	name;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}
	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
