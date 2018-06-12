package com.example.uploader.dao

import javax.persistence.*

@Entity
@Table(name="rank_user")
class RanksUserDao{
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "id_usr",nullable = false)
    var id_usr: Int? = null

    @Column(name = "id_rank",nullable = false)
    var id_rank: Int? = null

}