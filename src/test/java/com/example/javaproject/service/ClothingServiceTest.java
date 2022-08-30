package com.example.javaproject.service;

import com.example.javaproject.dto.ClothingDto;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.mapper.ClothingMapper;
import com.example.javaproject.model.Clothing;
import com.example.javaproject.repository.ClothingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClothingServiceTest {
    private static final Long ID = 1L;
    private static final String NAME = "Rochie";
    private static final Double PRICE = 89.99;
    private static final String SIZE = "S";

    @Mock
    private ClothingMapper clothingMapper;

    @Mock
    private ClothingRepository clothingRepository;

    @InjectMocks
    private ClothingService clothingService;

    private ClothingDto ClothingDto() {
        return ClothingDto.builder()
                .name(NAME)
                .price(PRICE)
                .size((SIZE))
                .build();
    }

    private Clothing Clothing() {
        Clothing clothing = new Clothing();
        clothing.setName(NAME);
        clothing.setPrice(PRICE);
        clothing.setSize(SIZE);

        return clothing;
    }

    @Test
    @DisplayName("Add a clothing successfully")
    void addClothing() {
        ClothingDto clothingDto = ClothingDto();
        Clothing clothing = Clothing();

        Clothing savedClothing = Clothing();
        savedClothing.setId(ID);

        ClothingDto returnedClothingDto = ClothingDto();
        returnedClothingDto.setId(ID);

        when(clothingMapper.mapToEntity(clothingDto)).thenReturn(clothing);
        when(clothingRepository.save(clothing)).thenReturn(savedClothing);
        when(clothingMapper.mapToDto(savedClothing)).thenReturn(returnedClothingDto);

        ClothingDto result = clothingService.addClothing(clothingDto);

        assertNotNull(result);
        assertEquals(returnedClothingDto.getSize(), result.getSize());
        assertEquals(returnedClothingDto.getPrice(), result.getPrice());
        assertEquals(returnedClothingDto.getName(), result.getName());
        assertEquals(returnedClothingDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Find all clothes successfully")
    void findAllClothes() throws ClothingNotFound {
        List<Clothing> clothes = List.of(Clothing());
        List<ClothingDto> clothesDto =  List.of(ClothingDto());

        when(clothingRepository.findAll()).thenReturn(clothes);
        when(clothingMapper.mapToDto(clothes.get(0))).thenReturn(clothesDto.get(0));

        List<ClothingDto> result = clothingService.findAllClothes();

        assertNotNull(result);
        assertEquals(clothesDto.get(0).getSize(), result.get(0).getSize());
        assertEquals(clothesDto.get(0).getPrice(), result.get(0).getPrice());
        assertEquals(clothesDto.get(0).getName(), result.get(0).getName());
        assertEquals(clothesDto.get(0).getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("Update a clothing successfully")
    void updateClothingSuccess() throws ClothingNotFound {
        Clothing clothing = Clothing();
        Clothing clothingFind = Clothing();
        clothingFind.setId(ID);
        Optional<Clothing> optionalClothing = Optional.of(clothingFind);

        Clothing savedClothing = Clothing();
        savedClothing.setId(ID);

        ClothingDto returnedClothingDTO = ClothingDto();
        returnedClothingDTO.setId(ID);

        when(clothingRepository.findById(ID)).thenReturn(optionalClothing);
        when(clothingRepository.save(clothing)).thenReturn(savedClothing);
        when(clothingMapper.mapToDto(savedClothing)).thenReturn(returnedClothingDTO);

        ClothingDto result = clothingService.updateClothing(clothing, ID);

        assertNotNull(result);
        assertEquals(returnedClothingDTO.getSize(), result.getSize());
        assertEquals(returnedClothingDTO.getPrice(), result.getPrice());
        assertEquals(returnedClothingDTO.getName(), result.getName());
        assertEquals(returnedClothingDTO.getId(), result.getId());
    }

    @Test
    @DisplayName("Update a clothing fails")
    void updateClothingNotFound() {
        Clothing clothing = Clothing();

        when(clothingRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ClothingNotFound.class, () -> clothingService.updateClothing(clothing, ID));
    }

    @Test
    @DisplayName("Delete a clothing successfully")
    void deleteClothing() throws ClothingNotFound {
        Clothing clothing = Clothing();
        clothing.setId(ID);
        Optional<Clothing> optionalClothing = Optional.of(clothing);

        when(clothingRepository.findById(ID)).thenReturn(optionalClothing);

        clothingService.deleteClothingById(ID);

        verify(clothingRepository, times(1)).delete(clothing);
    }

    @Test
    @DisplayName("Delete a clothing fails")
    void deleteClothingNotFound()  {
        when(clothingRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ClothingNotFound.class, () -> clothingService.deleteClothingById(ID));
    }

}