package com.ecommerce_backend_final.demo.Service;

import com.ecommerce_backend_final.demo.DTO.AddProductToCartDTO;
import com.ecommerce_backend_final.demo.DTO.CartItemsDTO;
import com.ecommerce_backend_final.demo.DTO.OrderDTO;
import com.ecommerce_backend_final.demo.DTO.PlaceOrderDTO;
import com.ecommerce_backend_final.demo.Entity.*;
import com.ecommerce_backend_final.demo.Enums.OrderStatus;
import com.ecommerce_backend_final.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartItemsService {

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    public ResponseEntity<?> addProductToCart(AddProductToCartDTO addProductToCartDTO) {
        OrderEntity activeorder = orderRepository.findByUseridAndOrderStatus(addProductToCartDTO.getUserid(), OrderStatus.PENDING);


        Optional<CartItemsEntity> cartitems = cartItemsRepository.findByProductidAndOrderidAndUserid(addProductToCartDTO.getProductid(), activeorder.getId(), addProductToCartDTO.getUserid());

        if (cartitems.isPresent()) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {

            Optional<ProductEntity> product = productRepository.findById(addProductToCartDTO.getProductid());
            Optional<UserEntity> user = userRepository.findById(addProductToCartDTO.getUserid());

            if (product.isPresent() && user.isPresent()) {
                CartItemsEntity cartItemsEntity = new CartItemsEntity();
                cartItemsEntity.setProductEntity(product.get());
                cartItemsEntity.setPrice(product.get().getPrice());
                cartItemsEntity.setQuantity(1L);
                cartItemsEntity.setUserEntity(user.get());
                cartItemsEntity.setOrderEntity(activeorder);

                CartItemsDTO saveCartItem = cartItemsRepository.save(cartItemsEntity).getCartItemsDTO();

                updateOrder(addProductToCartDTO.getUserid());


                return ResponseEntity.status(HttpStatus.CREATED).body(saveCartItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product Not Found");
            }


        }


    }

    public OrderDTO getallcartitemsbyuserid(Long userid) {


        OrderEntity activeorder = orderRepository.findByUseridAndOrderStatus(userid, OrderStatus.PENDING);
        List<CartItemsDTO> getCartItemsByOrderId = cartItemsRepository.findAllByOrderidOrderByIdAsc(activeorder.getId()).stream().map(CartItemsEntity::getCartItemsDTO).collect(Collectors.toList());
        //List<CartItemsDTO> cartItemsDTOList = activeorder.getCartitems().stream().map(CartItemsEntity::getCartItemsDTO).toList();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setAmount(activeorder.getAmount());
        orderDTO.setId(activeorder.getId());
        orderDTO.setOrderStatus(activeorder.getOrderStatus());
        orderDTO.setDiscount(activeorder.getDiscount());
        orderDTO.setTotalAmount(activeorder.getTotalAmount());

        if (activeorder.getCouponEntity() != null) {
            orderDTO.setCouponname(activeorder.getCouponEntity().getName());
        }


        orderDTO.setCartitems(getCartItemsByOrderId);

        return orderDTO;


    }

    public OrderDTO applyCoupon(Long userid, String code) {
        OrderEntity activeorder = orderRepository.findByUseridAndOrderStatus(userid, OrderStatus.PENDING);

        CouponEntity couponEntity = couponRepository.findByCode(code);
        if (couponEntity != null) {
            if (couponExpired(couponEntity)) {
                return null;
            } else {
                double discountAmount = couponEntity.getDiscount() / 100.00 * activeorder.getTotalAmount();
                double netAmount = activeorder.getTotalAmount() - discountAmount;

                activeorder.setAmount((long) netAmount);
                activeorder.setDiscount((long) discountAmount);
                activeorder.setCouponEntity(couponEntity);

                return orderRepository.save(activeorder).getOrderDTO();

            }
        } else {
            return null;
        }
    }

    private Boolean couponExpired(CouponEntity couponEntity) {
        Date date = new Date();

        Date expirationdate = couponEntity.getExpirationdate();

        if (expirationdate != null && date.after(expirationdate)) {
            return true;
        } else {
            return false;
        }
    }

    public ResponseEntity<?> deleteCart(Long userid, Long productid) {

        CartItemsEntity cartItemsEntity = cartItemsRepository.findByUseridAndProductid(userid, productid);
        if (cartItemsEntity != null) {
            cartItemsRepository.delete(cartItemsEntity);
            updateOrder(userid);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public void updateOrder(Long userid) {
        OrderEntity activeorder = orderRepository.findByUseridAndOrderStatus(userid, OrderStatus.PENDING);
        activeorder.setAmount(0L);
        activeorder.setTotalAmount(0L);
        activeorder.setDiscount(0L);
        activeorder.setCouponEntity(null);
        orderRepository.save(activeorder);

        List<CartItemsDTO> cartItemsList = cartItemsRepository.findAllByOrderidOrderByIdAsc(activeorder.getId()).stream().map(CartItemsEntity::getCartItemsDTO).collect(Collectors.toList());


        for (CartItemsDTO cartItemsDTO : cartItemsList) {
            activeorder.setAmount(activeorder.getAmount() + cartItemsDTO.getPrice());
            activeorder.setTotalAmount(activeorder.getTotalAmount() + cartItemsDTO.getPrice());
            orderRepository.save(activeorder).getOrderDTO();

        }


    }

    public ResponseEntity<?> updateQuantity(AddProductToCartDTO addProductToCartDTO) {
        OrderEntity activeorder = orderRepository.findByUseridAndOrderStatus(addProductToCartDTO.getUserid(), OrderStatus.PENDING);

        CartItemsEntity cartItemsEntity = cartItemsRepository.findByUseridAndProductid(activeorder.getUserid(), addProductToCartDTO.getProductid());
        Optional<ProductEntity> productEntity = productRepository.findById(addProductToCartDTO.getProductid());


        if (cartItemsEntity.getQuantity() >= 1) {
            if (productEntity.isPresent()) {
                cartItemsEntity.setQuantity(cartItemsEntity.getQuantity() + 1);
                cartItemsEntity.setPrice(cartItemsEntity.getQuantity() * productEntity.get().getPrice());
                cartItemsRepository.save(cartItemsEntity).getCartItemsDTO();

                updateOrder(addProductToCartDTO.getUserid());

                OrderDTO orderDTO = getallcartitemsbyuserid(addProductToCartDTO.getUserid());


                return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDTO);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);


        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }


    public ResponseEntity<?> deleteQuantity(AddProductToCartDTO addProductToCartDTO) {
        OrderEntity activeorder = orderRepository.findByUseridAndOrderStatus(addProductToCartDTO.getUserid(), OrderStatus.PENDING);

        CartItemsEntity cartItemsEntity = cartItemsRepository.findByUseridAndProductid(activeorder.getUserid(), addProductToCartDTO.getProductid());
        Optional<ProductEntity> productEntity = productRepository.findById(addProductToCartDTO.getProductid());


        if (cartItemsEntity.getQuantity() > 1) {
            if (productEntity.isPresent()) {
                cartItemsEntity.setQuantity(cartItemsEntity.getQuantity() - 1);
                cartItemsEntity.setPrice(cartItemsEntity.getQuantity() * productEntity.get().getPrice());
                cartItemsRepository.save(cartItemsEntity).getCartItemsDTO();

                updateOrder(addProductToCartDTO.getUserid());

                OrderDTO orderDTO = getallcartitemsbyuserid(addProductToCartDTO.getUserid());


                return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDTO);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);


        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }


    public OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO){
        OrderEntity activeorder = orderRepository.findByUseridAndOrderStatus(placeOrderDTO.getUserid(), OrderStatus.PENDING);

        Optional<UserEntity> userEntity = userRepository.findById(activeorder.getUserid());



        if(userEntity.isPresent()){
            activeorder.setAddress(placeOrderDTO.getAddress());
            activeorder.setOrderdescription(placeOrderDTO.getDescription());
            activeorder.setOrderStatus(OrderStatus.PLACED);
            activeorder.setTrackingid(UUID.randomUUID());
            activeorder.setPayment(placeOrderDTO.getPayment());
            activeorder.setDate(new Date());
            if(Objects.equals(placeOrderDTO.getPayment(), "ONLINE")){

                activeorder.setRazorpaytransactionid(placeOrderDTO.getRazorpay_payment_id());
                activeorder.setRazorpaysignature(placeOrderDTO.getRazorpay_signature());

            }

            orderRepository.save(activeorder);

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setAmount(0L);
            orderEntity.setTotalAmount(0L);
            orderEntity.setDiscount(0L);
            orderEntity.setUserEntity(userEntity.get());
            orderEntity.setOrderStatus(OrderStatus.PENDING);
            orderRepository.save(orderEntity);

            return activeorder.getOrderDTO();



        }
        return null;
    }


    public OrderDTO getallcartitemsbyorderid(Long orderid) {


        OrderEntity activeorder = orderRepository.findById(orderid).orElseThrow();
        List<CartItemsDTO> getCartItemsByOrderId = cartItemsRepository.findAllByOrderidOrderByIdAsc(activeorder.getId()).stream().map(CartItemsEntity::getCartItemsDTO).collect(Collectors.toList());
        //List<CartItemsDTO> cartItemsDTOList = activeorder.getCartitems().stream().map(CartItemsEntity::getCartItemsDTO).toList();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setAmount(activeorder.getAmount());
        orderDTO.setId(activeorder.getId());
        orderDTO.setOrderStatus(activeorder.getOrderStatus());
        orderDTO.setDiscount(activeorder.getDiscount());
        orderDTO.setTotalAmount(activeorder.getTotalAmount());

        if (activeorder.getCouponEntity() != null) {
            orderDTO.setCouponname(activeorder.getCouponEntity().getName());
        }


        orderDTO.setCartitems(getCartItemsByOrderId);

        return orderDTO;


    }

}



