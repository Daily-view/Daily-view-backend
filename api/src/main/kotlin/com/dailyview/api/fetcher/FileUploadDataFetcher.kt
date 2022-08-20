package com.dailyview.api.fetcher

import com.dailyview.api.generated.DgsConstants
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.springframework.web.multipart.MultipartFile

@DgsComponent
class FileUploadDataFetcher {

    @DgsMutation(field = DgsConstants.MUTATION.UploadImage)
    fun upload(@InputArgument input: MultipartFile) {
    }
}
