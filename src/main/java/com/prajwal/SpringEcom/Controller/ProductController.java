package com.prajwal.SpringEcom.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prajwal.SpringEcom.model.Product;
import com.prajwal.SpringEcom.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

	@Autowired
	private ProductService prodService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<List<Product>>(prodService.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductProductByID(@PathVariable int id) {
		Product product = prodService.getProductByID(id);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("/product/{productId}/image")
	public ResponseEntity<byte[]> getImageByProductID(@PathVariable int productId) {
		Product product = prodService.getProductByID(productId);
		return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
	}

	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
		Product savedProduct = null;
		try {
			savedProduct = prodService.addorUpdateProduct(product, imageFile);
			return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
			@RequestPart MultipartFile imageFile) {
		Product updatedProduct = null;
		try {
			updatedProduct = prodService.addorUpdateProduct(product, imageFile);
			return new ResponseEntity<>("Updated", HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id) {
		Product product = prodService.getProductByID(id);
		if (product.getId() > 0) {
			prodService.deleteProduct(id);
			return new ResponseEntity<>("Deleted",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
		List<Product> products = prodService.searchProducts(keyword); 
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
}
