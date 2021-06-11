package kodlamaio.hrms.core.verification;

public interface VerificationService {
	void sendVerificationLink(String email);

	String sendCode();
}
