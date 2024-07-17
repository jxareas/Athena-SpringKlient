package com.jxareas.athenaklient.factory

import com.jxareas.athenaklient.domain.AthenaQuery
import software.amazon.awssdk.services.athena.model.QueryExecutionContext
import software.amazon.awssdk.services.athena.model.ResultConfiguration
import software.amazon.awssdk.services.athena.model.StartQueryExecutionRequest

data object StartQueryExecutionRequestFactory {
    operator fun invoke(
        query: AthenaQuery,
        queryExecutionContext: QueryExecutionContext,
        workgroup: String,
        resultConfiguration: ResultConfiguration
    ): StartQueryExecutionRequest = StartQueryExecutionRequest.builder()
        .queryString(query.toString())
        .queryExecutionContext(queryExecutionContext)
        .workGroup(workgroup)
        .resultConfiguration(resultConfiguration)
        .build()

    operator fun invoke(
        query: AthenaQuery,
        database: String,
        catalog: String,
        workgroup: String,
        outputBucket: String,
    ): StartQueryExecutionRequest = StartQueryExecutionRequest.builder()
        .queryString(query.toString())
        .queryExecutionContext(QueryExecutionContextFactory(databaseName = database, catalogName = catalog))
        .workGroup(workgroup)
        .resultConfiguration(ResultConfigurationFactory(outputBucket))
        .build()
}