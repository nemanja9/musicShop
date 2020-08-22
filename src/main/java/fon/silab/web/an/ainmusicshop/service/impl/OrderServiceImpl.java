/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service.impl;

import fon.silab.web.an.ainmusicshop.dto.OrderDto;
import fon.silab.web.an.ainmusicshop.entity.OrderEntity;
import fon.silab.web.an.ainmusicshop.repository.OrderRepository;
import fon.silab.web.an.ainmusicshop.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lj
 */

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = new ModelMapper();
    }
    
    
    
    @Override
    public void save(OrderDto order) {
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        orderRepository.save(orderEntity);
    }

    @Override
    public List<OrderDto> getAll() {
        List<OrderEntity> ordersEnt = orderRepository.getAll();
        List<OrderDto> ordersDto = new ArrayList<>();
        for (OrderEntity orderEntity : ordersEnt) {
            OrderDto o = modelMapper.map(orderEntity, OrderDto.class);
            ordersDto.add(o);
        }
        return ordersDto;
    }

    @Override
    public OrderDto findByNumber(int numberId) {
        OrderEntity o = orderRepository.findByNumber(numberId);
        if(o != null){
            return modelMapper.map(o, OrderDto.class);
        }else{
            return null;
        }
        
    }

    @Override
    public void delete(int numberId) {
        OrderEntity o = orderRepository.findByNumber(numberId);
        if (o != null) {
            orderRepository.delete(numberId);
        }
    }

    @Override
    public void update(OrderDto order) {
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        orderRepository.update(orderEntity);
    }

    @Override
    public String getUserByOrderID(int id) {
        return orderRepository.getUserByOrderID(id);
    }

    @Override
    public List<OrderDto> getAllForUser(int id) {

        List<OrderEntity> ordersEnt = orderRepository.getAllForUser(id);
        List<OrderDto> ordersDto = new ArrayList<>();
        for (OrderEntity orderEntity : ordersEnt) {
            OrderDto o = modelMapper.map(orderEntity, OrderDto.class);
            ordersDto.add(o);
        }
        return ordersDto;    }
    
}
