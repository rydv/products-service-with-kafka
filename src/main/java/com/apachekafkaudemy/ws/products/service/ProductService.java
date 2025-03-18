package com.apachekafkaudemy.ws.products.service;

import com.apachekafkaudemy.ws.products.rest.CreateProductRestModel;

public interface ProductService {

    String createProduct(CreateProductRestModel productRestModel) throws Exception;
    
}
