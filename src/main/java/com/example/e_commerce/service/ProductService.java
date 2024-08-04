package com.example.e_commerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e_commerce.dao.ListProductDao;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product serviceGetProductById(Long id){

        return productRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    private List<Product> getAllProduct(){
        
        return productRepository.findAll();
    }

    public List<ListProductDao> serviceGetAllProducts(){
        List<ListProductDao> result=new ArrayList<>();
        List<Product> products=this.getAllProduct();

        for (Product product : products) {
            result.add(new ListProductDao
            (product.getId(), product.getName(), 
            product.getColor(), product.getPrice(), product.getAmount()));

        }

        return result;
    }


}
