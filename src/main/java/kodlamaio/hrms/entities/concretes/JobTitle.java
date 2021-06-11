package kodlamaio.hrms.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "job_titles")
public class JobTitle {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "create_date")
	private LocalDate createDate;

	@Column(name = "isActive")
	private boolean isActive;

}
