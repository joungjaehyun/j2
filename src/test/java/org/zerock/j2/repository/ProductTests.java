package org.zerock.j2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.j2.entity.Product;
import java.util.UUID;
@SpringBootTest
public class ProductTests {
 
    @Autowired
    ProductRepository repo;


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
}
