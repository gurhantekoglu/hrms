package kodlamaio.hrms.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.abstracts.UserService;
import kodlamaio.hrms.business.abstracts.VerificationCodeService;
import kodlamaio.hrms.core.utilities.adapters.ValidationService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.core.verification.VerificationService;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.VerificationCode;

@Service
public class AuthManager implements AuthService {

	private UserService userService;
	private CandidateService candidateService;
	private EmployerService employerService;
	private VerificationCodeService verificationCodeService;
	private ValidationService validationService;
	private VerificationService verificationService;

	@Autowired
	public AuthManager(UserService userService, CandidateService candidateService, EmployerService employerService,
			VerificationCodeService verificationCodeService, ValidationService validationService,
			VerificationService verificationService) {
		super();
		this.userService = userService;
		this.candidateService = candidateService;
		this.employerService = employerService;
		this.verificationCodeService = verificationCodeService;
		this.validationService = validationService;
		this.verificationService = verificationService;
	}

	@Override
	public Result registerCandidate(Candidate candidate, String confirmPassword) {
		if (checkIfRealPerson(candidate.getNationalityId(), candidate.getFirstName(), candidate.getLastName(),
				candidate.getDateOfBirth().getYear()) == false) {
			return new ErrorResult("T.C. Kimlik Numarası doğrulanamadı.");
		}

		if (!checkIfNullInfoForCandidate(candidate, confirmPassword)) {

			return new ErrorResult("Lütfen eksik bilgilerinizi tamamlayın.");
		}

		if (!checkIfExistsTcNo(candidate.getNationalityId())) {

			return new ErrorResult(candidate.getNationalityId() + " zaten kayıtlı.");
		}

		if (!checkIfEmailExists(candidate.getEmail())) {

			return new ErrorResult(candidate.getEmail() + " zaten kayıtlı.");
		}

		candidateService.add(candidate);
		String code = verificationService.sendCode();
		verificationCodeIsGenerated(code, candidate.getId(), candidate.getEmail());
		return new SuccessResult("Kayıt başarılı.");
	}

	@Override
	public Result registerEmployer(Employer employer, String confirmPassword) {
		if (!checkIfNullInfoForEmployer(employer)) {

			return new ErrorResult("Lütfen eksik bilgilerinizi tamamlayın.");
		}

		if (!checkIfEqualEmailAndDomain(employer.getEmail(), employer.getWebsite())) {

			return new ErrorResult("Geçersiz e-posta adresi.");
		}

		if (!checkIfEmailExists(employer.getEmail())) {

			return new ErrorResult(employer.getEmail() + " zaten kayıtlı.");
		}

		if (!checkIfEqualPasswordAndConfirmPassword(employer.getPassword(), confirmPassword)) {

			return new ErrorResult("Şifreler farklı.");
		}

		employerService.add(employer);
		String code = verificationService.sendCode();
		verificationCodeIsGenerated(code, employer.getId(), employer.getEmail());
		return new SuccessResult("Kayıt Başarıyla tammalandı!");
	}

	private boolean checkIfRealPerson(String nationalityId, String firstName, String lastName, int yearOfBirth) {
		if (validationService.validateByMernis(nationalityId, firstName, lastName, yearOfBirth)) {
			return true;
		}
		return false;
	}

	private boolean checkIfNullInfoForCandidate(Candidate candidate, String confirmPassword) {
		if (candidate.getFirstName() != null && candidate.getLastName() != null && candidate.getNationalityId() != null
				&& candidate.getDateOfBirth() != null && candidate.getPassword() != null && candidate.getEmail() != null
				&& confirmPassword != null) {
			return true;

		}
		return false;
	}

	private boolean checkIfExistsTcNo(String nationalId) {
		if (this.candidateService.getCandidateByNationalityId(nationalId).getData() == null) {
			return true;
		}
		return false;
	}

	private boolean checkIfEmailExists(String email) {
		if (this.userService.getUserByEmail(email).getData() == null) {
			return true;
		}
		return false;
	}

	private boolean checkIfEqualPasswordAndConfirmPassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return false;
		}
		return true;
	}

	private boolean checkIfNullInfoForEmployer(Employer employer) {
		if (employer.getCompanyName() != null && employer.getWebsite() != null && employer.getEmail() != null
				&& employer.getPhoneNumber() != null && employer.getPassword() != null) {
			return true;
		}
		return false;
	}

	private boolean checkIfEqualEmailAndDomain(String email, String website) {
		String[] emailArr = email.split("@", 2);
		String domain = website.substring(4, website.length());

		if (emailArr[1].equals(domain)) {
			return true;
		}
		return false;
	}

	public void verificationCodeIsGenerated(String code, int id, String email) {
		VerificationCode verificationCode = new VerificationCode(id, id, code, false, null, false);
		this.verificationCodeService.add(verificationCode);
		System.out.println("Doğrulama kodu gönderildi: " + email);
	}

}
