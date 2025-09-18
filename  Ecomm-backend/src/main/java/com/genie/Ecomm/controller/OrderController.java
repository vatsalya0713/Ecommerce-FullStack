package com.genie.Ecomm.controller;

import com.genie.Ecomm.dto.OrderDTO;
import com.genie.Ecomm.model.OrderRequest;
import com.genie.Ecomm.model.User;
import com.genie.Ecomm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {
     @Autowired
    private OrderService orderService;
    @PostMapping("/place/{userid}")
    public OrderDTO placeOrder(@PathVariable Long userid , @RequestBody OrderRequest orderRequest){
       return  orderService.placeOrder(userid,orderRequest.getProductQuentities(),orderRequest.getTotalAmount());
    }
  @GetMapping("/all-orders")
    public List<OrderDTO> getAllOrders()
    {
        return orderService.getALLOrders();
    }
    public  List<OrderDTO> getOrderByUser(@PathVariable Long userId){
        return orderService.getOrderByUser(userId);
    }

}
