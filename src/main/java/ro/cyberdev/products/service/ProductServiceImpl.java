package ro.cyberdev.products.service;

import org.springframework.stereotype.Service;
import ro.cyberdev.products.rest.CreateProductRestModel;

@Service
public class ProductServiceImpl implements ProductService{
    @Override
    public String createProduct(CreateProductRestModel productRestModel) {
        return null;
    }
}
