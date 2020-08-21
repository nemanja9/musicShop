/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service.impl;

import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import fon.silab.web.an.ainmusicshop.repository.UserRepository;
import fon.silab.web.an.ainmusicshop.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public void save(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);
    }

    @Override
    public List<UserDto> getAll() {
        List<UserEntity> usersEnt = userRepository.getAll();
        List<UserDto> usersDto = new ArrayList<>();
        for (UserEntity userEntity : usersEnt) {
            UserDto p = modelMapper.map(userEntity, UserDto.class);
            usersDto.add(p);
        }
        return usersDto;
    }

    @Override
    public UserDto findByNumber(int numberId) {
        UserEntity u = userRepository.findByNumber(numberId);
        if (u != null) {
            return modelMapper.map(u, UserDto.class);
        } else {
            return null;
        }
    }

    @Override
    public void delete(int numberId) {
        UserEntity u = userRepository.findByNumber(numberId);
        if(u != null){
            userRepository.delete(numberId);
        }
    }

    @Override
    public void update(UserDto user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userRepository.update(userEntity);
    }

    @Override
    public UserDto findByEmail(String name) {
        UserEntity u = userRepository.findByEmail(name);
        if(u != null){
            return modelMapper.map(u, UserDto.class);
        }else{
            return null;
        }
        
    }

 
}
