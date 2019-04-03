
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProblemRepository;
import security.LoginService;
import security.UserAccount;
import domain.Hacker;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private HackerService		hackerService;


	public int getProblemCount(final int positionId) {
		return this.problemRepository.getProblemCount(positionId);
	}

	// countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse ---------------------------------------------------------------
	public Integer countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse(final int id) {

		final UserAccount user = LoginService.getPrincipal();
		final Hacker hacker = this.hackerService.getHackerByUserAccountId(user.getId());
		Assert.isTrue(hacker != null);

		final Integer p = this.problemRepository.countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse(id);
		return p;
	}

	// countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse ---------------------------------------------------------------
	public Collection<Problem> allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(final int id) {

		final UserAccount user = LoginService.getPrincipal();
		final Hacker hacker = this.hackerService.getHackerByUserAccountId(user.getId());
		Assert.isTrue(hacker != null);

		final Collection<Problem> p = this.problemRepository.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(id);
		return p;
	}

}
