package com.book.service.repository;

import com.book.service.entity.LendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LendRepository extends JpaRepository<LendEntity, Long>{

	@Query("SELECT o FROM LendEntity o WHERE o.returned= 0 and o.id=?1")
	LendEntity findActiveById(Long id);
	
	@Query("SELECT o FROM LendEntity o WHERE o.returned= 0 and o.customerName=?1")
	List<LendEntity> findActiveByCustomerName(String customerName);
	
	@Query("SELECT o FROM LendEntity o WHERE o.returned= 0")
	List<LendEntity> findActiveAll();

	@Query("SELECT o FROM LendEntity o WHERE o.returned= 0 and o.bookId=?1")
	LendEntity findActiveByBookId(Long bookId);
}
