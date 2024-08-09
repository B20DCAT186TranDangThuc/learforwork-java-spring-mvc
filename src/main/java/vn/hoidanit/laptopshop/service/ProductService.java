package vn.hoidanit.laptopshop.service;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

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

}
