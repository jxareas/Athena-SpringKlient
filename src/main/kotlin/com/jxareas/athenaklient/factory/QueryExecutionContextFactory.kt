package com.jxareas.athenaklient.factory

import software.amazon.awssdk.services.athena.model.QueryExecutionContext

data object QueryExecutionContextFactory {
    operator fun invoke(databaseName: String, catalogName: String): QueryExecutionContext =
        QueryExecutionContext.builder()
            .database(databaseName)
            .catalog(catalogName)
            .build()
}