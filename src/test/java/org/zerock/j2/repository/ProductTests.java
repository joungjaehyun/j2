package org.zerock.j2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.j2.entity.Product;

import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;
@SpringBootTest
public class ProductTests {
 
    @Autowired
    ProductRepository repo;

    // Insert Test
    @Test
    public void testInsert(){
        
        Product product = Product.builder()
        .pname("Test")
        .pdesc("Test")
        .writer("user00")
        .price(4000)
        .build();

        product.addImage(UUID.randomUUID().toString()+ "_aaa.jpg");
        product.addImage(UUID.randomUUID().toString()+"_bbb.jpg0");
        product.addImage(UUID.randomUUID().toString()+"_ccc.jpg");

        repo.save(product);

    }

    @Test
    @Transactional // 안걸리면 lazy exception이 걸린다.
    public void testRead1(){

        Optional<Product> result = repo.findById(1L);

        Product product = result.orElseThrow();
        System.out.println(product);
        System.out.println("---------------------");
        System.out.println(product.getImages());
    }
    // Entity Graph를 적용한 상세보기
    @Test
    public void testRead2(){

        Product product = repo.selectOne(1L);

  
        System.out.println(product);
        System.out.println("---------------------");
        System.out.println(product.getImages());
    }

    // Delete Test
    @Test
    public void testDelete(){

        repo.deleteById(1L);
    }
    
    // Modify Test
    @Commit
    @Transactional
    @Test
    public void testUpdate(){
        // 1이 삭제되었기때문
        Optional<Product> result = repo.findById(2L);

        Product product = result.orElseThrow();
         product.changePrice(6000);
         product.clearImages();

         product.addImage(UUID.randomUUID().toString()+"_newImage.jpg");
        
         repo.save(product);
         
    }

}
