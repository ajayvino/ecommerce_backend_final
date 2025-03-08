package com.ecommerce_backend_final.demo.Controller.Customer;

import com.ecommerce_backend_final.demo.DTO.RazorpayTransactionDetailsDTO;
import com.ecommerce_backend_final.demo.Service.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
public class RazorpayController {

    @Autowired
    private RazorpayService razorpayService;

    @GetMapping("/createTransaction/{amount}")
    public ResponseEntity<?> createTransaction(@PathVariable Long amount) throws JSONException, RazorpayException {

        RazorpayTransactionDetailsDTO razorpayTransactionDetailsDTO =razorpayService.createTransaction(amount);

      return ResponseEntity.status(HttpStatus.CREATED).body(razorpayTransactionDetailsDTO) ;







    }
}
