//
// package domain;
//
// import javax.persistence.Access;
// import javax.persistence.AccessType;
// import javax.persistence.Entity;
// import javax.persistence.Inheritance;
// import javax.persistence.InheritanceType;
// import javax.persistence.ManyToOne;
// import javax.persistence.OneToOne;
//
// @Entity
// @Access(AccessType.PROPERTY)
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// public class Enrolled extends DomainEntity {
//
// private Member member;
// private Boolean state;
// private Brotherhood brotherhood;
// private Position position;
//
//
// @OneToOne
// public Position getPosition() {
// return this.position;
// }
//
// public void setPosition(final Position position) {
// this.position = position;
// }
//
// @ManyToOne(optional = false)
// public Member getMember() {
// return this.member;
// }
//
// public void setMember(final Member member) {
// this.member = member;
// }
//
// public Boolean getState() {
// return this.state;
// }
//
// public void setState(final Boolean state) {
// this.state = state;
// }
//
// @ManyToOne(optional = true)
// public Brotherhood getBrotherhood() {
// return this.brotherhood;
// }
//
// public void setBrotherhood(final Brotherhood brotherhood) {
// this.brotherhood = brotherhood;
// }
//}
