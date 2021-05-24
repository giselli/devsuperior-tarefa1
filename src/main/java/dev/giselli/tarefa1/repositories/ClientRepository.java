package dev.giselli.tarefa1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.giselli.tarefa1.entities.Client;

	@Repository
	public interface ClientRepository extends JpaRepository<Client, Long> {


}
