package net.codejava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(nullable = false, unique = true, length = 45)
	private String name;
	
	@Column(name = "description", nullable = false, length = 200)
	private String description;
	
	@Column(name = "due_date", nullable = false, length = 20)
	private String dueDate;

	@Column(name = "urgent", nullable = false, length = 20)
	private String urgent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getUrgent() {
		return urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}
}
