package kodlamaio.hrms.core.verification;

import java.util.UUID;

public class VerificationManager implements VerificationService {

	@Override
	public void sendVerificationLink(String email) {
		UUID uuid = UUID.randomUUID();
		String verificationLink = "https://hrmsverificationemail/" + uuid.toString();
		System.out.println("Verification link sent: " + email);
		System.out.println("Please click the link to verify your account: " + verificationLink);
	}

	@Override
	public String sendCode() {
		UUID uuid = UUID.randomUUID();
		String verificationCode = uuid.toString();
		System.out.println("Your activation code: " + verificationCode);
		return verificationCode;
	}

}
