package com.dailyview.api.controller

import com.dailyview.api.service.fb.FirebaseService
import com.dailyview.getUUIDFileName
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class FileUploadController(private val firebaseService: FirebaseService) {

    @PostMapping("/api/v1/upload")
    fun upload(@RequestPart("file") file: MultipartFile): String {
        return firebaseService.uploadFiles(file, file.getUUIDFileName())
    }
}
