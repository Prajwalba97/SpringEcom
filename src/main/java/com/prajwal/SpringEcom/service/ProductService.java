package com.prajwal.SpringEcom.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prajwal.SpringEcom.model.Product;
import com.prajwal.SpringEcom.repo.IProductRepo;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	private IProductRepo repo;
	@Override
	public List<Product> getAllProducts(){
		return repo.findAll();
	}

	@Override
	public Product getProductByID(int id) {
		Optional<Product> product = repo.findById(id);
		return product.get();
	}

	@Override
	public Product addorUpdateProduct(Product product, MultipartFile image) throws IOException {
		product.setImageName(image.getOriginalFilename());
		product.setImagetype(image.getContentType());
		product.setImageData(image.getBytes());
		return repo.save(product);
	}

	@Override
	public void deleteProduct(int productid) {
		repo.deleteById(productid);
	}

	@Override
	public List<Product> searchProducts(String keyword) {
		List<Product> products = repo.searchProducts(keyword);
		return products;
	}
}
