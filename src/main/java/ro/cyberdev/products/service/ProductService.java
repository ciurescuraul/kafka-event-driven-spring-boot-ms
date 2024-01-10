package ro.cyberdev.products.service;

import ro.cyberdev.products.rest.CreateProductRestModel;

public interface ProductService {
    String createProduct(CreateProductRestModel productRestModel) throws Exception;
}
