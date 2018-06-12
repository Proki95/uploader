package com.example.uploader.controllers

import com.example.uploader.dao.FileDao
import com.example.uploader.repository.FileRepository
import com.example.uploader.repository.UserRepository
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


@Controller
class FileController(val fileRepository: FileRepository,val userRepository: UserRepository){

    val rootLocation = Paths.get("filestorage")



    @PostMapping("/upload")
    @ResponseBody
    //@PreAuthorize("hasAnyAuthority('user')")
    fun singleFileUpload(@RequestPart("file") multipartFile: MultipartFile, redirectAttributes: RedirectAttributes): String {
        if(multipartFile.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Wybierz plik do wrzucenia")
        }
        try{
            val newFileName = ""+1+"_"+multipartFile.originalFilename
            Files.copy(multipartFile.getInputStream(), this.rootLocation.resolve(newFileName))
            //if(newFileName == FileDao().filename)
                //tu przerywamy i oddajemy błąd
            val date = Date()
            val name = multipartFile.originalFilename
            val size = multipartFile.size
            val extension = multipartFile.contentType

            val newFile = FileDao().also {
                //it.id_usr=jwt.accountId.toInt()
                it.filename=name
                it.extension = extension
                it.size = size.toInt()
                it.dateUpload = date

            }
            fileRepository.save(newFile)

        }
        catch (e:IOException) {
            e.printStackTrace()
        }

        return "uploadSucessFully"
    }

    @GetMapping("/download/{filename}")
    fun downloadFile(@PathVariable filename:String): ResponseEntity<Resource> {
        val file = rootLocation.resolve(filename)
        val resource = UrlResource(file.toUri())
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource)
    }




    @PostMapping("/deleteFile/{id}")
    //@PreAuthorize("hasAnyAuthority('admin','user')")
    fun deleteFile(@PathVariable id:Int):String {
        fileRepository.delete(fileRepository.findFileById(id))
        return "adminfiles"
    }

    // nie wyświetla zawartości, ale powinno działac ?
    @GetMapping("/showFiles")
    //@PreAuthorize("hasAnyAuthority('admin')")
    fun showFiles(map: ModelMap): String {
        map.addAttribute("file",fileRepository.findAll())
        return "adminfiles"
    }

    // NAPISA REPOSYTORIUM DO POBRANIA PLIKU DLA ID USERA !!!!
    @GetMapping("/showUserFiles")
    @PreAuthorize("hasAnyAuthority('user')")
    fun showUserFiles(map: ModelMap) {
        map.addAttribute("file",fileRepository.findAll())
    }


}