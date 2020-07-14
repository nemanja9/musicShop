/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service.impl;

import fon.silab.web.an.ainmusicshop.dto.OrderItemDto;
import fon.silab.web.an.ainmusicshop.entity.OrderItemEntity;
import fon.silab.web.an.ainmusicshop.repository.OrderItemRepository;
import fon.silab.web.an.ainmusicshop.service.OrderItemService;
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
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public void save(OrderItemDto orderItem) {
        OrderItemEntity orderItemEntity = modelMapper.map(orderItem, OrderItemEntity.class);
        orderItemRepository.save(orderItemEntity);
    }

    @Override
    public List<OrderItemDto> getAll() {
        List<OrderItemEntity> ordersEnt = orderItemRepository.getAll();
        List<OrderItemDto> ordersDto = new ArrayList<>();
        for (OrderItemEntity orderItemEntity : ordersEnt) {
            OrderItemDto o = modelMapper.map(orderItemEntity, OrderItemDto.class);
            ordersDto.add(o);
        }
        return ordersDto;
    }

    @Override
    public OrderItemDto findByNumber(int numberId) {
        OrderItemEntity o = orderItemRepository.findByNumber(numberId);
        if (o != null) {
            return modelMapper.map(o, OrderItemDto.class);
        } else {
            return null;
        }

    }

    @Override
    public void delete(int numberId) {
        OrderItemEntity o = orderItemRepository.findByNumber(numberId);
        if (o != null) {
            orderItemRepository.delete(numberId);
        }
    }

    @Override
    public void update(OrderItemDto orderItem) {
        OrderItemEntity orderItemEntity = modelMapper.map(orderItem, OrderItemEntity.class);
        orderItemRepository.update(orderItemEntity);
    }
}
