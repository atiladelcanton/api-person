package br.com.zensolutions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zensolutions.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long> {
}
