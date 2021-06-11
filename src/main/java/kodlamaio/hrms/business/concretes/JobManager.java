package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobService;
import kodlamaio.hrms.dataAccess.abstracts.JobTitleDao;
import kodlamaio.hrms.entities.concretes.JobTitle;

@Service
public class JobManager implements JobService {

	private JobTitleDao jobDao;

	@Autowired
	public JobManager(JobTitleDao jobDao) {
		super();
		this.jobDao = jobDao;
	}

	@Override
	public List<JobTitle> getAll() {
		return this.jobDao.findAll();
	}

}
