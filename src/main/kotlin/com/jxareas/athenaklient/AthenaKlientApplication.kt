package com.jxareas.athenaklient

import com.jxareas.athenaklient.factory.AthenaClientFactory
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import software.amazon.awssdk.services.athena.AthenaClient
import software.amazon.awssdk.services.athena.model.*


@SpringBootApplication
class AthenaKlientApplication : CommandLineRunner {
    val logger = LoggerFactory.getLogger(AthenaKlientApplication::class.java)

    val ATHENA_DATABASE: String = "athenatest"

    val ATHENA_OUTPUT_S3_FOLDER_PATH: String = "s3://athena-poc-contactsunny/"

    val SIMPLE_ATHENA_QUERY: String = "select * from sampledata limit 10;"

    val SLEEP_AMOUNT_IN_MS: Long = 1000

    override fun run(vararg args: String?) {
        val athenaClient = AthenaClientFactory()

        val queryExecutionId: String = submitAthenaQuery(athenaClient)

        logger.info("Query submitted: " + System.currentTimeMillis())

        waitForQueryToComplete(athenaClient, queryExecutionId)

        logger.info("Query finished: " + System.currentTimeMillis())

        processResultRows(athenaClient, queryExecutionId)
    }

    private fun submitAthenaQuery(athenaClient: AthenaClient): String {
        val queryExecutionContext = QueryExecutionContext.builder()
            .database(ATHENA_DATABASE).build()

        val resultConfiguration = ResultConfiguration.builder()
            .outputLocation(ATHENA_OUTPUT_S3_FOLDER_PATH).build()

        val startQueryExecutionRequest = StartQueryExecutionRequest.builder()
            .queryString(SIMPLE_ATHENA_QUERY)
            .queryExecutionContext(queryExecutionContext)
            .resultConfiguration(resultConfiguration).build()

        val startQueryExecutionResponse = athenaClient.startQueryExecution(startQueryExecutionRequest)

        return startQueryExecutionResponse.queryExecutionId()
    }

    @Throws(InterruptedException::class)
    private fun waitForQueryToComplete(athenaClient: AthenaClient, queryExecutionId: String) {
        val getQueryExecutionRequest = GetQueryExecutionRequest.builder()
            .queryExecutionId(queryExecutionId).build()

        var getQueryExecutionResponse: GetQueryExecutionResponse

        var isQueryStillRunning = true

        while (isQueryStillRunning) {
            getQueryExecutionResponse = athenaClient.getQueryExecution(getQueryExecutionRequest)
            val queryState = getQueryExecutionResponse.queryExecution().status().state().toString()

            if (queryState == QueryExecutionState.FAILED.toString()) {
                throw RuntimeException(
                    "Query Failed to run with Error Message: " + getQueryExecutionResponse
                        .queryExecution().status().stateChangeReason()
                )
            } else if (queryState == QueryExecutionState.CANCELLED.toString()) {
                throw RuntimeException("Query was cancelled.")
            } else if (queryState == QueryExecutionState.SUCCEEDED.toString()) {
                isQueryStillRunning = false
            } else {
                Thread.sleep(SLEEP_AMOUNT_IN_MS)
            }

            logger.info("Current Status is: $queryState")
        }
    }

    private fun processResultRows(athenaClient: AthenaClient, queryExecutionId: String) {
        val getQueryResultsRequest = GetQueryResultsRequest.builder()
            .queryExecutionId(queryExecutionId).build()

        val getQueryResultsResults = athenaClient.getQueryResultsPaginator(getQueryResultsRequest)

        for (Resultresult in getQueryResultsResults) {
            val columnInfoList = Resultresult.resultSet().resultSetMetadata().columnInfo()

            val resultSize = Resultresult.resultSet().rows().size
            logger.info("Result size: $resultSize")

            val results: List<Row> = Resultresult.resultSet().rows()
            processRow(results, columnInfoList)
        }
    }

    private fun processRow(rowList: List<Row>, columnInfoList: List<ColumnInfo>) {
        val columns: MutableList<String> = ArrayList()

        for (columnInfo in columnInfoList) {
            columns.add(columnInfo.name())
        }

        for (row in rowList) {

            for ((index, datum) in row.data().withIndex()) {
                logger.info(columns[index] + ": " + datum.varCharValue())
            }

            logger.info("===================================")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<AthenaKlientApplication>(*args)
}
