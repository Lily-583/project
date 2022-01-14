package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
	@Query("SELECT t FROM Task t WHERE t.name = ?1")
	public User findByName(String name);

}
