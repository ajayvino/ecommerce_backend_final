package com.ecommerce_backend_final.demo.Service;


import com.ecommerce_backend_final.demo.DTO.AnalyticsResponseDTO;
import com.ecommerce_backend_final.demo.DTO.OrderDTO;
import com.ecommerce_backend_final.demo.Entity.OrderEntity;
import com.ecommerce_backend_final.demo.Enums.OrderStatus;
import com.ecommerce_backend_final.demo.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDTO> getallorders (){
        return orderRepository.findAllByOrderStatusInOrderByIdAsc(List.of(OrderStatus.PLACED,OrderStatus.DELIVERED,OrderStatus.SHIPPED)).stream().map(OrderEntity:: getOrderDTO).collect(Collectors.toList());


    }

    public OrderDTO updateOrderStatus(Long orderid,String status){
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderid);

        if(orderEntity.isPresent()){
            OrderEntity updateorder = orderEntity.get();
            if(Objects.equals(status, "Shipped")){
                updateorder.setOrderStatus(OrderStatus.SHIPPED);
                return orderRepository.save(updateorder).getOrderDTO();
            }
            if(Objects.equals(status, "Delivered")){
                updateorder.setOrderStatus(OrderStatus.DELIVERED);
                return orderRepository.save(updateorder).getOrderDTO();
            }


        }
        return null;
    }
    public List<OrderDTO> getallordersbyuserid (Long userid){
        return orderRepository.findAllByUseridAndOrderStatusInOrderByIdAsc(userid,List.of(OrderStatus.PLACED,OrderStatus.DELIVERED,OrderStatus.SHIPPED)).stream().map(OrderEntity :: getOrderDTO).collect(Collectors.toList());


    }

    public OrderDTO gettrackinngbyID(UUID uuid){

        Optional<OrderDTO> orderDTO = orderRepository.findByTrackingid(uuid).map(OrderEntity::getOrderDTO);

        if(orderDTO.isPresent()){
            return orderDTO.get();
        }
        else{
            return null;
        }
    }


    public AnalyticsResponseDTO calculateAnalytics(){

        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        Long currentMonthsOrders=getTotalOrdersForMonth(currentDate.getMonthValue(),currentDate.getYear());
        Long previousMonthsOrders= getTotalOrdersForMonth(previousMonthDate.getMonthValue(),previousMonthDate.getYear());

        Long currenMonthEarnings= getTotalEarningssForMonth(currentDate.getMonthValue(),currentDate.getYear());
        Long previousMonthEarnings=getTotalEarningssForMonth(previousMonthDate.getMonthValue(),previousMonthDate.getYear());



        Long placed = orderRepository.countByOrderStatus(OrderStatus.PLACED);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.SHIPPED);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.DELIVERED);

        AnalyticsResponseDTO analyticsResponseDTO = new AnalyticsResponseDTO();

        analyticsResponseDTO.setPlaced(placed);
        analyticsResponseDTO.setShipped(shipped);
        analyticsResponseDTO.setDelivered(delivered);
        analyticsResponseDTO.setCurrentMonthOrders(currentMonthsOrders);
        analyticsResponseDTO.setPreviousMonthOrders(previousMonthsOrders);
        analyticsResponseDTO.setCurrentMonthEarnings(currenMonthEarnings);
        analyticsResponseDTO.setPreviousMonthEarnings(previousMonthEarnings);

        return analyticsResponseDTO;




    }

    public Long getTotalOrdersForMonth (int month, int year){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1); //Calender month starts from 0
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Date startofmonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        Date endOfMonth= calendar.getTime();

        List<OrderEntity> orderEntityList = orderRepository.findByDateBetweenAndOrderStatus(startofmonth,endOfMonth,OrderStatus.DELIVERED);

        return (long) orderEntityList.size();

    }

    public Long getTotalEarningssForMonth (int month, int year){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1); //Calender month starts from 0
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Date startofmonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        Date endOfMonth= calendar.getTime();

        List<OrderEntity> orderEntityList = orderRepository.findByDateBetweenAndOrderStatus(startofmonth,endOfMonth,OrderStatus.DELIVERED);

        Long sum = 0L;

        for(OrderEntity orderEntity : orderEntityList){
            sum = sum+orderEntity.getAmount();
        }
        return sum;

    }




}
