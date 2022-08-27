package com.dailyview

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

fun MultipartFile.getUUIDFileName(): String {
    val fileName = this.name
    val ext = fileName.substring(fileName.lastIndexOf(".") + 1)
    return "${UUID.randomUUID()}.$ext"
}

fun String.convertPublicURL(): String =
    this.replace("https://storage.googleapis.com/download/storage/v1", "https://firebasestorage.googleapis.com/v0")
