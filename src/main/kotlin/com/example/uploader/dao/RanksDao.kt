package com.example.uploader.dao

import javax.persistence.*

@Entity
@Table(name="ranks")
class RanksDao{

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name="Name")
    var Name: String? = null


}