package com.ecommerce_backend_final.demo.Repository;


import com.ecommerce_backend_final.demo.Entity.OrderEntity;
import com.ecommerce_backend_final.demo.Enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    OrderEntity findByUseridAndOrderStatus(Long userid, OrderStatus orderStatus);

    List<OrderEntity> findAllByOrderStatusInOrderByIdAsc(List<OrderStatus> orderStatusList);
    List<OrderEntity> findAllByUseridAndOrderStatusInOrderByIdAsc(Long userid,List<OrderStatus> orderStatusList);

    Optional<OrderEntity> findByTrackingid(UUID trackingid);

    Long countByOrderStatus(OrderStatus orderstatus);


    List<OrderEntity> findByDateBetweenAndOrderStatus(Date startofmonth, Date endOfMonth, OrderStatus orderStatus);
}
