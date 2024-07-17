package com.jxareas.athenaklient.factory

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.athena.AthenaClient

data object AthenaClientFactory {
    operator fun invoke(region: Region): AthenaClient =
        AthenaClient.builder()
            .region(region)
            .build()

    fun withRegionName(regionName: String): AthenaClient =
        this(Region.of(regionName))
}