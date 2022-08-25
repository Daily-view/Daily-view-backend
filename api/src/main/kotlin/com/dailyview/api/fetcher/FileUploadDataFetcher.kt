package com.dailyview.api.fetcher

import com.dailyview.api.generated.DgsConstants
import com.dailyview.api.service.fb.FirebaseService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@DgsComponent
class FileUploadDataFetcher(val firebaseService: FirebaseService) 

fun generateUUIDFileName(file: MultipartFile): String {
    val fileName = file.name
    val ext = fileName.substring(fileName.lastIndexOf(".") + 1)
    return "${UUID.randomUUID()}.$ext"
}
