package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.JobService;
import kodlamaio.hrms.entities.concretes.JobTitle;

@RestController
@RequestMapping("/api/jobtitles")
public class JobTitlesController {

	private JobService jobService;

	public JobTitlesController(JobService jobService) {
		super();
		this.jobService = jobService;
	}

	@GetMapping("/getall")
	public List<JobTitle> getAll() {
		return this.jobService.getAll();
	}
}
