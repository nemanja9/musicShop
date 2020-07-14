/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service.impl;

import fon.silab.web.an.ainmusicshop.dto.ProductDto;
import fon.silab.web.an.ainmusicshop.entity.ProductEntity;
import fon.silab.web.an.ainmusicshop.repository.ProductRepository;
import fon.silab.web.an.ainmusicshop.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lj
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public void save(ProductDto productDto) {
        ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductDto> getAll() {
        List<ProductEntity> productsEnt = productRepository.getAll();
        List<ProductDto> productsDto = new ArrayList<>();
        for (ProductEntity productEntity : productsEnt) {
            ProductDto p = modelMapper.map(productEntity, ProductDto.class);
            productsDto.add(p);
        }
        return productsDto;
    }
    
    @Override
    public List<ProductDto> getSome(String category, String orderBy, String manufacturer, String max, String min) {
        List<ProductEntity> productsEnt = productRepository.getSome(category, orderBy, manufacturer, max, min);
        List<ProductDto> productsDto = new ArrayList<>();
        for (ProductEntity productEntity : productsEnt) {
            ProductDto p = modelMapper.map(productEntity, ProductDto.class);
            productsDto.add(p);
        }
        return productsDto;
    }
    
    @Override
    public ArrayList<String> getAllManufacturers() {
                ArrayList<String> list = productRepository.getManufacturers();
                return list;
    }

    
    @Override
    public List<ProductDto> getCategory(String category) {
        List<ProductEntity> productsEnt = productRepository.getCategory(category);
        List<ProductDto> productsDto = new ArrayList<>();
        for (ProductEntity productEntity : productsEnt) {
            ProductDto p = modelMapper.map(productEntity, ProductDto.class);
            productsDto.add(p);
        }
        return productsDto;
    }

    @Override
    public ProductDto getOne(int numberId) {
        ProductEntity p = productRepository.findByNumber(numberId);
        if (p != null) {
            return modelMapper.map(p, ProductDto.class);
        } else {
            return null;
        }
    }

    @Override
    public void delete(int numberId) {
        ProductEntity p = productRepository.findByNumber(numberId);
        if (p != null) {
            productRepository.delete(numberId);
        }
    }

    @Override
    public void update(ProductDto product) {
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        productRepository.update(productEntity);
    }

    @Override
    public ProductDto findByName(String name) {
        ProductEntity p = productRepository.findByName(name);
        if (p != null) {
            return modelMapper.map(p, ProductDto.class);
        } else {
            return null;
        }

    }

    @Override
    public int getMax() {
        int p = productRepository.findMax();
        return p;
    }

    @Override
    public int getHighestPrice() {
        return productRepository.getHighestPrice();
    }

    

    
    

}
