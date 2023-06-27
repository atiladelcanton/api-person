package br.com.zensolutions.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zensolutions.data.vo.v1.BookVO;
import br.com.zensolutions.services.BookServices;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/book/v1")
@Tag(name = "Book", description = "Endpoint for managing Books")
public class BookController {

	@Autowired
	private BookServices service;
	
	@GetMapping()
	public List<BookVO> findAll() {
		return service.findAll();
	}

	@GetMapping(value = "/{id}")
	public BookVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@PostMapping()
	public BookVO create(@RequestBody BookVO book) {
		return service.create(book);
	}

	@PutMapping()
	public BookVO update(@RequestBody BookVO book) {
		return service.update(book);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
