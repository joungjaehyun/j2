package org.zerock.j2.service;

import org.springframework.stereotype.Service;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.entity.Product;
import org.zerock.j2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.zerock.j2.util.FileUploader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final FileUploader fileUploader;
    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {
        

        return productRepository.listWithReview(requestDTO);
    }

    @Override
    public Long register(ProductDTO productDTO) {
        
        Product product = Product.builder()
        .pname(productDTO.getPname())
        .pdesc(productDTO.getPdesc())
        .price(productDTO.getPrice())
        .build();

        productDTO.getImages().forEach(fname->{
            product.addImage(fname);
        });

        return productRepository.save(product).getPno();
        
    }

    @Override
    public ProductDTO readOne(Long pno) {

       Product product = productRepository.selectOne(pno);

       ProductDTO dto = ProductDTO.builder()
               .pno(product.getPno())
               .pname(product.getPname())
               .price(product.getPrice())
               .pdesc(product.getPdesc())
               .images(product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList()))
               .build();

       return dto;
    }

    @Override
    public void remove(Long pno) {
        // 삭제전 조회
        Product product =productRepository.selectOne(pno);

        product.changeDel(true);

        productRepository.save(product);

        List<String> fileNames =
                product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

        fileUploader.removeFiles(fileNames);

    }


}
