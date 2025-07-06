package com.prajwal.SpringEcom.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.prajwal.SpringEcom.model.Product;

public interface IProductService {

	List<Product> getAllProducts();
	Product getProductByID(int id);
	Product addorUpdateProduct(Product product, MultipartFile image) throws IOException;
	void deleteProduct(int productid);
	List<Product> searchProducts(String keyword);
}
