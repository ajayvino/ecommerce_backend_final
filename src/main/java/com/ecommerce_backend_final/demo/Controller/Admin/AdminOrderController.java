package com.ecommerce_backend_final.demo.Controller.Admin;

import com.ecommerce_backend_final.demo.DTO.OrderDTO;
import com.ecommerce_backend_final.demo.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminOrderController {


    @Autowired
    private OrderService orderService;

    @GetMapping("/allorders")
    public ResponseEntity<?> getAllOrders(){

        return ResponseEntity.ok(orderService.getallorders());



    }

    @GetMapping("/updateOrderStatus/{userid}/{status}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long userid, @PathVariable String status){
        OrderDTO orderDTO = orderService.updateOrderStatus(userid,status);
        if(orderDTO!=null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);        }
    }

    @GetMapping("/order/analytics")
    public ResponseEntity<?> getAnalyticsResponse(){
        return ResponseEntity.ok(orderService.calculateAnalytics());
    }
}
