package kodlamaio.hrms.fakeService;

public class MernisServiceFake {
	public boolean ValidateByHuman(String nationalityId, String firstName, String lastName, int yearOfBirth) {
		System.out.println("Person approved: " + firstName + " " + lastName);
		return true;
	}
}
