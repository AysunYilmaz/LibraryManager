package com.book.service.repository;

import com.book.service.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long>{

	@Query("SELECT o FROM BookEntity o WHERE o.isActive= 1 and o.id=?1")
	BookEntity findActiveById(Long id);

	@Query("SELECT o FROM BookEntity o WHERE o.isActive= 1 and o.name=?1")
	BookEntity findActiveByName(String name);
	
	@Query("SELECT o FROM BookEntity o WHERE o.isActive= 1 ")
	List<BookEntity> findActiveAll();
}
