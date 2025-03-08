package com.ecommerce_backend_final.demo.Service;

import com.ecommerce_backend_final.demo.DTO.RazorpayTransactionDetailsDTO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RazorpayService {

    private static final String ORDER_PLACED = "PLACED";
    private static final String KEY_ID = "rzp_test_kr7Gr4OlhUH26v";
    private static final String KEY_SECRET = "4J17MDqZ6UJ4LQlPpBh3TSTw";
    private static final String CURRENCY="INR";

    public RazorpayTransactionDetailsDTO createTransaction(Long amount) throws RazorpayException, JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount",(amount*100)); //Razorpay always considers amount as paise only
        jsonObject.put("currency",CURRENCY);


        RazorpayClient razorpayClient = new RazorpayClient(KEY_ID,KEY_SECRET);

       Order order= razorpayClient.orders.create(jsonObject); // Import from com.razorpay

        RazorpayTransactionDetailsDTO razorpayTransactionDetailsDTO= new RazorpayTransactionDetailsDTO();

        razorpayTransactionDetailsDTO.setOrderid(order.get("id"));
        razorpayTransactionDetailsDTO.setAmount(order.get("amount"));
        razorpayTransactionDetailsDTO.setCurrency(order.get("currency"));
        razorpayTransactionDetailsDTO.setKey(KEY_ID);

        return razorpayTransactionDetailsDTO;













    }
}
