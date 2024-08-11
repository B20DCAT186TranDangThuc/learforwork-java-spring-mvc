package org.example.laptopshop.service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import org.example.laptopshop.domain.Cart;
import org.springframework.stereotype.Service;

import org.example.laptopshop.domain.CartDetail;
import org.example.laptopshop.domain.Product;
import org.example.laptopshop.domain.User;
import org.example.laptopshop.repository.CartDetailRepository;
import org.example.laptopshop.repository.CartRepository;
import org.example.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository,
                          CartRepository cartRepository,
                          CartDetailRepository cartDetailRepository,
                          UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void saveProduct(Product product) {
        this.productRepository.save(product);
    }

    public Product getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        // check user
        User user = userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = cartRepository.findByUser(user);
            if (cart == null) {
                // create new cart
                cart = new Cart();
                cart.setUser(user);
                cart.setSum(0);

                cart = cartRepository.save(cart);
            }
            // save cart
            // find product by id
            Product product = getProductById(productId);
            if (product != null) {
                // check exist
                CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart, product);

                if (cartDetail == null) {
                    cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(product);
                    cartDetail.setPrice(product.getPrice());
                    cartDetail.setQuantity(1);

                    // update cart (sum)
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                }

                cartDetailRepository.save(cartDetail);
            }
        }
    }

    public Cart fetchByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            } else {
                // delete cart (sum = 1)
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }


    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }
}
