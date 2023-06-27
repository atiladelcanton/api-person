package br.com.zensolutions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zensolutions.model.Book;


public interface BookRepository extends JpaRepository<Book, Long> {

}
