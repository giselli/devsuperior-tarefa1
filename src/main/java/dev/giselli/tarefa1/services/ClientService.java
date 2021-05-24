package dev.giselli.tarefa1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.giselli.tarefa1.dto.ClientDTO;
import dev.giselli.tarefa1.entities.Client;
import dev.giselli.tarefa1.repositories.ClientRepository;
import dev.giselli.tarefa1.services.exceptions.DatabaseException;
import dev.giselli.tarefa1.services.exceptions.ResourceNotFoundException;


@Service
public class ClientService {
	

		@Autowired
		private ClientRepository repository;

		@Transactional(readOnly = true) 
		
		public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
			
			Page<Client> list = repository.findAll(pageRequest);

			return list.map(x -> new ClientDTO(x));
			
		}

		@Transactional(readOnly = true)
		public ClientDTO findById(Long id) {
			Optional<Client> obj = repository.findById(id);
			
			Client entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));
			return new ClientDTO(entity);
		}

		@Transactional
		public ClientDTO insert(ClientDTO dto) {
			Client entity = new Client();
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setCpf(dto.getCpf());;
			entity.setIncome(dto.getIncome());;
			entity.setBirthDate(dto.getBirthDate());
			entity.setChildren(dto.getChildren());

			entity = repository.save(entity);
			return new ClientDTO(entity);
		}

		
		@Transactional
		public ClientDTO update(Long id, ClientDTO dto) {
			try {
				Client entity = repository.getOne(id); // getOne do not access database until you save the entity
				entity.setName(dto.getName());
				entity = repository.save(entity);

				return new ClientDTO(entity);
			} catch (EntityNotFoundException e) {
				throw new ResourceNotFoundException(String.format("ID not found [%d]", id));
			}
		}

		// não tem o Transactional
		public void delete(Long id) {
			try {
				repository.deleteById(id);
			} catch (EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException("ID not found" + id);
			} catch (DataIntegrityViolationException e) {
				
				throw new DatabaseException("Integrity violaton");
			}
		}

}
