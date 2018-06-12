package com.example.uploader.dao

import java.math.BigInteger
import java.util.*
import javax.persistence.*


@Entity
@Table(name="files")
class FileDao {
    @Id
    @Column(name="id",nullable = false,updatable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name="id_usr")
    var id_usr: Int? = null

    @Column(name="filename")
    var filename: String? = null

    @Column(name="extension")
    var extension: String? = null

    @Column(name="size")
    var size: Int? = null

    @Column(name="date_upload")
    var dateUpload: Date? = null

}
