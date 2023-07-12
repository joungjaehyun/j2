package org.zerock.j2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j2.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // 상세보기용을 위한 JPQL 작업
    @EntityGraph(attributePaths = "images")
    @Query("select p from Product p where p.pno = :pno ")
    Product selectOne(@Param("pno")Long pno);

}
