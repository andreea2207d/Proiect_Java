package com.example.javaproject.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review")
    private String review;

    @ManyToOne
    @JoinColumn(name="fk_clothing_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Clothing clothing;

    @ManyToOne
    @JoinColumn(name="fk_user_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

}
