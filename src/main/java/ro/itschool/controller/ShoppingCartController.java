package ro.itschool.controller;

import ro.itschool.entity.Product;
import ro.itschool.entity.ShoppingCart;
import ro.itschool.repository.OrderRepository;
import ro.itschool.repository.ProductRepository;
import ro.itschool.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @PutMapping(value = "/add/{cartId}")
    public ResponseEntity addProductToShoppingCart(@PathVariable Integer cartId, @RequestParam Integer productId) {

        Product product = productRepository.findById(productId).orElseThrow();
        ShoppingCart cart = shoppingCartService.findById(cartId).orElseThrow();

        cart.addProductToShoppingCart(product);
        shoppingCartService.update(cart);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/remove/{cartId}")
    public ResponseEntity removeProductFromShoppingCart(@PathVariable Integer cartId, @RequestParam Integer productId) {

        Product product = productRepository.findById(productId).orElseThrow();
        ShoppingCart cart = shoppingCartService.findById(cartId).orElseThrow();

        cart.removeProductFromShoppingCart(product);
        shoppingCartService.update(cart);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public void convertToOrder(@RequestBody ShoppingCart shoppingCart) {
        orderRepository.save(shoppingCartService.convertShoppingCartToOrder(shoppingCart));
    }

}
