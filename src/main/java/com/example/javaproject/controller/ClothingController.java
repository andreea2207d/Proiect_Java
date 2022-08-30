package com.example.javaproject.controller;

import com.example.javaproject.dto.ClothingDto;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.mapper.ClothingMapper;
import com.example.javaproject.model.ClothingCategory;
import com.example.javaproject.service.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("shop/clothes")
public class ClothingController {

    @Autowired
    private ClothingService clothingService;

    @Autowired
    private ClothingMapper clothingMapper;

    @PostMapping("/new/{clothingCategory}")
    public ResponseEntity<ClothingDto> addClothing(@Valid @RequestBody ClothingDto clothingDto, @PathVariable ClothingCategory clothingCategory) {
        clothingDto.setClothingCategory(clothingCategory);
        return ResponseEntity
                .ok()
                .body(clothingService.addClothing(clothingDto));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ClothingDto>> findAll() {
        return ResponseEntity
                .ok()
                .body(clothingService.findAllClothes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClothingDto> findClothingById(@PathVariable Long id) throws ClothingNotFound {
        return ResponseEntity
                .ok()
                .body(clothingService.findClothingById(id));
    }

    @GetMapping("/findAll/{clothingCategory}")
    public ResponseEntity<List<ClothingDto>> findAllByClothingCategory(@PathVariable ClothingCategory clothingCategory) {
        return ResponseEntity
                .ok()
                .body(clothingService.findClothesByCategory(clothingCategory));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClothingDto> updateClothing(@Valid @RequestBody ClothingDto clothingDto, @PathVariable Long id) throws ClothingNotFound {
        return ResponseEntity
                .ok()
                .body(clothingService.updateClothing(clothingMapper.mapToEntity(clothingDto), id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ClothingDto> deleteClothing(@PathVariable Long id) throws ClothingNotFound {
        clothingService.deleteClothingById(id);
        return ResponseEntity.noContent().build();
    }

}
