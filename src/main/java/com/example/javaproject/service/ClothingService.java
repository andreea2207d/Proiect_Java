package com.example.javaproject.service;

import com.example.javaproject.dto.ClothingDto;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.mapper.ClothingMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.repository.ClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClothingService {
    @Autowired
    private ClothingMapper clothingMapper;

    @Autowired
    private ClothingRepository clothingRepository;

    public ClothingDto addClothing(ClothingDto clothingDto) {
        Clothing clothing = clothingMapper.mapToEntity(clothingDto);

        Clothing savedClothing = clothingRepository.save(clothing);
        return clothingMapper.mapToDto(savedClothing);
    }

    public List<ClothingDto> findAllClothes() {
        List<Clothing> clothes = clothingRepository.findAll();
        List <ClothingDto> clothesDto = clothes.stream().map(clothing -> clothingMapper.mapToDto(clothing))
                .collect(Collectors.toList());
        return clothesDto;
    }

    public ClothingDto findClothingById(Long id) throws ClothingNotFound {
        Optional<Clothing> clothing =  clothingRepository.findById(id);
        if (clothing.isEmpty())
            throw new ClothingNotFound("Clothing not found!");
        return clothingMapper.mapToDto(clothing.get());
    }

    public List<ClothingDto> findClothesByCategory(ClothingCategory clothingCategory) {
        List<Clothing> clothes = clothingRepository.findClothesByCategory(clothingCategory);
        List <ClothingDto> clothesDto = clothes.stream().map(clothing -> clothingMapper.mapToDto(clothing))
                .collect(Collectors.toList());
        return clothesDto;
    }

    public ClothingDto updateClothing(Clothing clothing, Long id) throws ClothingNotFound {
        findClothingById(id);
        clothing.setId(id);
        return clothingMapper.mapToDto(clothingRepository.save(clothing));
    }

    public Clothing findById(Long id) throws ClothingNotFound {
        Optional<Clothing> clothing =  clothingRepository.findById(id);
        if (clothing.isEmpty())
            throw new ClothingNotFound("Clothing not found!");
        return clothing.get();
    }

    public void deleteClothingById(Long id) throws ClothingNotFound {
        Clothing clothing = findById(id);

        clothingRepository.delete(clothing);
    }

}
