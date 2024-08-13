package org.example.laptopshop.repository;


import org.example.laptopshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);

    void deleteById(long id);

    Product findById(long id);

    Page<Product> findAll(Pageable pageable);
}
