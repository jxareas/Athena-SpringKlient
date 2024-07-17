package com.jxareas.athenaklient.factory

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.athena.AthenaClient

object AthenaClientFactory : Factory<AthenaClient> {
    private val builder = AthenaClient.builder()
        .region(Region.US_EAST_2)
        .credentialsProvider(ProfileCredentialsProvider.create())

    override fun invoke(): AthenaClient =
        builder.build()
}