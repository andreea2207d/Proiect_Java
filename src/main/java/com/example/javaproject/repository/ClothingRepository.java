package com.example.javaproject.repository;

import com.example.javaproject.model.Clothing;
import com.example.javaproject.model.ClothingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {

    List<Clothing> findAll();

    @Query(value = "select c from Clothing c where c.clothingCategory = :clothingCategory")
    List<Clothing> findClothesByCategory(ClothingCategory clothingCategory);

}
