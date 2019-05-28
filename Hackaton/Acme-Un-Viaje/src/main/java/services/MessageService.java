
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.validation.Validator;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Admin;
import domain.Complaint;
import domain.Customer;
import domain.Host;
import domain.Mailbox;
import domain.Message;
import domain.Referee;
import domain.Review;
import domain.Tag;
import domain.Transporter;
import domain.TravelAgency;

@Service
@Transactional
public class MessageService {

	//Managed repository
	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	MailboxService				mailboxService;
	@Autowired
	AdminService		administratorService;
	@Autowired
	ActorService				actorService;
	@Autowired
	TagService				tagService;
	@Autowired
	ConfigService				configService;
	@Autowired
	RefereeService				refereeService;
	@Autowired
	TravelPackService				travelPackService;
	@Autowired
	private Validator			validator;

	@Autowired
	private CustomerService		customerService;

	public Message reconstruct(final Message message, final BindingResult binding) {
		Message result;
		final Collection<Mailbox> boxes = new ArrayList<>();
		Collection<Tag> tags = new ArrayList<Tag>();
		
		UserAccount user = LoginService.getPrincipal();
		Actor a = actorService.getActorByUserId(user.getId());

		if (message.getId() == 0) {
			message.setMoment(LocalDateTime.now().toDate());
			message.setMailboxes(boxes);
			message.setSender(a.getEmail());
			result = message;
		} else {
			result = this.messageRepository.findOne(message.getId());
			message.setId(result.getId());
			message.setVersion(result.getVersion());
			message.setBody(result.getBody());
			message.setEmailReceiver(result.getEmailReceiver());
			message.setMoment(result.getMoment());
			message.setSubject(result.getSubject());
			message.setSender(result.getSender());

			result = message;
		}
		System.out.println(message.getMoment());
		this.validator.validate(message, binding);
		return result;
	}
	public Message create() {
		final Collection<Mailbox> boxes = new ArrayList<>();
		final Message m = new Message();
		m.setMoment(LocalDateTime.now().toDate());
		m.setBody("");
		m.setMailboxes(boxes);
		return m;
	}

	public Message exchangeMessage(final Message message, final Integer receiverId) {
		//this.checkSuspicious(message);
		
		Assert.isTrue(message.getSubject() != null);

		final UserAccount userSender = LoginService.getPrincipal();
		final Actor sender = this.actorService.findByUserAccountId(userSender.getId());
		final Mailbox outBoxSender = this.mailboxService.getOutBoxActor(sender.getId());

		if (!outBoxSender.getMessages().contains(message) && !message.getMailboxes().contains(outBoxSender)) {
			outBoxSender.getMessages().add(message);
			message.getMailboxes().add(outBoxSender);
		}
		Mailbox boxReceiver = null;

			boxReceiver = this.mailboxService.getInBoxActor(receiverId);
			boxReceiver.getMessages().add(message);
			message.getMailboxes().add(boxReceiver);
		
			List<Tag> tags = (List<Tag>) message.getTags();
			Tag old = tags.get(0);
			old.setActorId(sender.getId());
			
			
			
			Tag tag = tagService.create();
			tag.setActorId(receiverId);
			tag.setTag(old.getTag());
			
			if (message.getTags().size() == 1) {
			message.setTags(new ArrayList<Tag>());
			Collection<Tag> tagsActor = new ArrayList<Tag>();
			tagsActor.add(old);
			tagsActor.add(tag);
			message.setTags(tagsActor);
			
			}else {
				message.getTags().add(tag);
			}
			
			if (checkSuspiciousWithBoolean(message)) {
				Tag spam = tagService.create();
				spam.setActorId(receiverId);
				spam.setTag("SPAM");
				message.getTags().add(spam);
			}
				
			
		return message;
	}
	
	public Message sendBroadcastWithoutAdminReview(Complaint complaint, Review review) {		
		
		Message message = create();
		
		
		message.setSubject("Notification about review done");	
		message.setBody("A review has been made on the complaint with description" + " {" + complaint.getDescription() + "} about the travel pack " + travelPackService.findFromComplaint(complaint.getId()).getName() + ". The description of the review is {" + review.getDescription() + "}.");
		
		Customer customer = complaint.getCustomer();
		TravelAgency travelAgency = complaint.getTravelAgency();
		Host host = complaint.getHost();
		Transporter transporter = complaint.getTransporter();
			
		List<String> emails = new ArrayList<String>();
		if(customer != null) {
			emails.add(customer.getEmail());
		}
		
		if(travelAgency != null) {
			emails.add(travelAgency.getEmail());
		}
		
		if(host != null) {
			emails.add(host.getEmail());
		}
		
		if(transporter != null) {
			emails.add(transporter.getEmail());
		}
		
		Referee ref = refereeService.getRefereeByUserAccountId(LoginService.getPrincipal().getId());
		
		emails.add(ref.getEmail());
		
		message.setEmailReceiver(emails);
		
		Collection<Mailbox> mailboxes = new ArrayList<Mailbox>();
		
		for (int i = 0; i < emails.size(); i++) {
			mailboxes.add(mailboxService.getInBoxActorEmail(emails.get(i)));
		}
		
		message.setTags(new ArrayList<Tag>());
		
				
			for (final Mailbox box : mailboxes) {
				message.getMailboxes().add(box);
				box.getMessages().add(message);
				Tag tag = tagService.create();
				tag.setTag("NOTIFICATION");
				Actor aacto = actorService.getActorMailbox(box.getId());
				tag.setActorId(aacto.getId());
				message.getTags().add(tag);
				
				if (checkSuspiciousWithBoolean(message)) {
					Tag spam = tagService.create();
					spam.setActorId(aacto.getId());
					spam.setTag("SPAM");
					message.getTags().add(spam);
				}
			
		}
	
			
		return message;
	}
	
	public Message sendBroadcast(final Message message) {
		final Admin a = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Mailbox> inBoxAdmin = this.mailboxService.getAdminInBox();
		final Mailbox outBoxAdmin = this.mailboxService.getOutBoxActor(a.getId());

		System.out.println(outBoxAdmin);

		message.getMailboxes().add(outBoxAdmin);
		outBoxAdmin.getMessages().add(message);
		
		Tag tagAdmin = tagService.create();
		tagAdmin.setTag("NOTIFICATION");
		tagAdmin.setActorId(a.getId());
		message.getTags().add(tagAdmin);
		
		Collection<String> emails = actorService.getEmailofActors();
		emails.remove(a.getEmail());
		
		message.setEmailReceiver(emails);
		
		
		Assert.notNull(a);

			final Collection<Mailbox> result = this.mailboxService.getInbox();

			result.removeAll(inBoxAdmin);

			for (final Mailbox mailbox : result) {
				message.getMailboxes().add(mailbox);
				mailbox.getMessages().add(message);
				Tag tag = tagService.create();
				tag.setTag("NOTIFICATION");
				Actor aacto = actorService.getActorMailbox(mailbox.getId());
				tag.setActorId(aacto.getId());
				message.getTags().add(tag);
				
				if (checkSuspiciousWithBoolean(message)) {
					Tag spam = tagService.create();
					spam.setActorId(aacto.getId());
					spam.setTag("SPAM");
					message.getTags().add(spam);
				}
			
		}
				
			
		return message;
	}
	public void delete(final Message message, final Integer mailboxId) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(user.getId());

		Assert.isTrue(message.getEmailReceiver().contains(a.getEmail()) || message.getSender().equals(a.getEmail()));
		
		Tag deleteTag = tagService.getTagByMessageDeleted(message.getId());
		
		if(deleteTag != null) {
			if(message.getMailboxes().size() > 1) {
				Mailbox m = mailboxService.findOne(mailboxId);
				m.getMessages().remove(message);
				message.getMailboxes().remove(m);				
				List<Tag> tags = (List<Tag>) tagService.getTagByMessage(message.getId());
				for (int i = 0; i < tags.size(); i++) {
					message.getTags().remove(tags.get(i));
					tagService.delete(tags.get(i));
				}
			}else {
				Mailbox m = mailboxService.findOne(mailboxId);
				m.getMessages().remove(message);
				message.getMailboxes().remove(m);
				List<Tag> tags = (List<Tag>) tagService.getTagByMessage(message.getId());
				for (int i = 0; i < tags.size(); i++) {
					message.getTags().remove(tags.get(i));
					tagService.delete(tags.get(i));
				}
				messageRepository.delete(message.getId());
			}
		}else {
			Tag tag = tagService.create();
			tag.setActorId(a.getId());
			tag.setMessageId(message.getId());
			tag.setTag("DELETED");
			message.getTags().add(tag);
			
		}
		
	}
	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}
	
	private Boolean checkSuspiciousWithBoolean(final Message msg) {

		Boolean res = false;
		final Collection<String> spamWords = this.configService.getConfiguration().getSpamList();

		for (final String word : spamWords)
			if (msg.getBody().contains(word)) {
				res = true;
				break;
			}
		return res;
	}

	// spammerFlag = true if condition is fulfilled
	private Boolean spammerFlagCheck(final UserAccount uacc) {

		Boolean res = false;

		if (uacc.getMsgCounter() != 0) {

			final Double ratiospam = uacc.getSpamMsgCounter() / uacc.getMsgCounter();
			final Double percentage = uacc.getMsgCounter() * 0.1;
			res = ratiospam > percentage;
		}

		return res;
	}

	public Message save(final Message message) {
		//Capturo actor logeado segun su Username
		final UserAccount uacc = LoginService.getPrincipal();
		Integer id = uacc.getId();
		final Actor actor = this.actorService.getActorByUserId(id);
		//Actualizo contador total de msg
		actor.getUserAccount().setMsgCounter(uacc.getMsgCounter() + 1.);
		//Actualizo contador de msg de spam
		if (this.checkSuspiciousWithBoolean(message) == true)
			actor.getUserAccount().setSpamMsgCounter(uacc.getSpamMsgCounter() + 1.);
		//Calculo el spammerFlag del UserAcc
		actor.getUserAccount().setSpammerFlag(this.spammerFlagCheck(actor.getUserAccount()));
		//Guardo Actor con el UserAcc modificado
		this.actorService.save(actor);
		//Guardo el Msg
		return this.messageRepository.save(message);
	}
	
	public void editMailbox(final Message message) {
		System.out.println("new Boxes");
		System.out.println(message.getMailboxes());

		final List<Mailbox> listNewBoxes = new ArrayList<>();
		listNewBoxes.addAll(message.getMailboxes());

		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(login.getId());

		final Message oldMessage = this.findOne(message.getId());
		System.out.println("antiguas Boxes");
		System.out.println(oldMessage.getMailboxes());

		final Collection<Mailbox> boxesJsp = message.getMailboxes();

		message.getMailboxes().addAll(oldMessage.getMailboxes());

		System.out.println(message.getMailboxes());

		System.out.println("nuevas");
		System.out.println(listNewBoxes);

		for (final Mailbox mailbox : oldMessage.getMailboxes()) {
			System.out.println(sender.getMailboxes().contains(mailbox));
			System.out.println(boxesJsp.contains(mailbox));
			System.out.println(!listNewBoxes.contains(mailbox));
			if (sender.getMailboxes().contains(mailbox) && boxesJsp.contains(mailbox) && !(listNewBoxes.contains(mailbox)))
				message.getMailboxes().remove(mailbox);
		}
		System.out.println(sender.getName());
		System.out.println(sender.getEmail());
	}
}