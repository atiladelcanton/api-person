package br.com.zensolutions.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.zensolutions.data.vo.v2.PersonVOV2;
import br.com.zensolutions.model.Person;

@Service
public class PersonMapper {
	public PersonVOV2 convertEntityToVo(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBrithDay(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());

		return vo;
	}

	public Person convertVoToEntity(PersonVOV2 person) {
		Person entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());

		return entity;
	}
}
