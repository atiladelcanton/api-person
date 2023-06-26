package br.com.zensolutions.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.zensolutions.controllers.PersonController;
import br.com.zensolutions.data.vo.v1.PersonVO;
import br.com.zensolutions.data.vo.v2.PersonVOV2;
import br.com.zensolutions.exceptions.RequiredObjectIsNullException;
import br.com.zensolutions.exceptions.ResourceNotFoundException;
import br.com.zensolutions.mapper.DozerMapper;
import br.com.zensolutions.mapper.custom.PersonMapper;
import br.com.zensolutions.model.Person;
import br.com.zensolutions.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;

	@Autowired
	PersonMapper mapper;

	public List<PersonVO> findAll() {
		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);

		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		return persons;
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

		return vo;
	}

	public PersonVO create(PersonVO person) {
		if(person == null) throw new RequiredObjectIsNullException();
		logger.info("Crating one person");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Crating one person V2");
		var entity = mapper.convertVoToEntity(person);
		var vo = mapper.convertEntityToVo(repository.save(entity));
		
		return vo;
	}

	public PersonVO update(PersonVO person) {
		if(person == null) throw new RequiredObjectIsNullException();
		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

}
