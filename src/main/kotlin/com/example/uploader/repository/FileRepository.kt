package com.example.uploader.repository

import com.example.uploader.dao.FileDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface FileRepository : JpaRepository <FileDao,String>{
    @Query( """
         Select id
         from FileDao
         where id = :id

                    """)
    fun deleteFileById(@Param("id")id:Int): Optional<FileDao>

    @Query( """
        select u
        from FileDao u
        where u.id = :id

                    """)
    fun findFileById(@Param("id") id: Int):FileDao











}
