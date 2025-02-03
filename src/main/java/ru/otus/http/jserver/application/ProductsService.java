package ru.otus.http.jserver.application;

import java.util.*;

public class ProductsService {
    private List<Product> products;

    public ProductsService() {
        this.products = new ArrayList<>(Arrays.asList(
                new Product(1L, "Milk"),
                new Product(2L, "Bread"),
                new Product(3L, "Cheese")
        ));
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product getProductById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    public void createNewProduct(Product product) {
        Long newId;
        try {
            newId = products.stream().mapToLong(Product::getId).max().getAsLong() + 1;
        } catch (NoSuchElementException e) {
            newId = 1L;
        }
        products.add(new Product(newId, product.getTitle()));
    }

    public boolean updateByNewProduct(Product product) {
        Product existingProduct;
        try {
            existingProduct = products.stream().filter(p -> p.getId().equals(product.getId())).findFirst().get();
        } catch (NoSuchElementException e) {
            return false;
        }
        products.set(products.indexOf(existingProduct), new Product(product.getId(), product.getTitle()));
        return true;
    }

    public Product deleteProductById(Long id) {
        Product existingProduct;
        try {
            existingProduct = products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
            products.remove(existingProduct);
            return existingProduct;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public List<Product> deleteAllProducts() {
        List<Product> list = new ArrayList<>(products);
        products.clear();
        return list;
    }

}
