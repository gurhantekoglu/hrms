package kodlamaio.hrms.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;

@RestController
@RequestMapping("/api/verify")
public class AuthsController {

	private AuthService authService;

	@Autowired
	public AuthsController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/registerCandidate")
	public Result registerCandidate(Candidate candidate, String confirmPassword) {
		return authService.registerCandidate(candidate, confirmPassword);
	}

	@PostMapping("/registerEmployer")
	public Result registerEmployer(Employer employer, String confirmPassword) {
		return authService.registerEmployer(employer, confirmPassword);
	}
}
