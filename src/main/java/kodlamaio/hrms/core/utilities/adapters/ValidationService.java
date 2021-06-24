package kodlamaio.hrms.core.utilities.adapters;

public interface ValidationService {
	boolean validateByMernis(String nationalityId, String firstName, String lastName, int dateOfBirth);
}
