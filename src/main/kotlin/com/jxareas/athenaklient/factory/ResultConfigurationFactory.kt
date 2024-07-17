package com.jxareas.athenaklient.factory

import software.amazon.awssdk.services.athena.model.ResultConfiguration

data object ResultConfigurationFactory {
    operator fun invoke(outputBucketName: String): ResultConfiguration =
        ResultConfiguration.builder()
            .outputLocation(outputBucketName)
            .build()
}