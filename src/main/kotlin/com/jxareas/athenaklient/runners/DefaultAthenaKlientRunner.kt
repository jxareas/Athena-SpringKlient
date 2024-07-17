package com.jxareas.athenaklient.runners

import com.jxareas.athenaklient.annotations.KlientRunner
import com.jxareas.athenaklient.domain.AthenaQuery
import org.springframework.beans.factory.annotation.Value
import java.io.Closeable

@KlientRunner
class DefaultAthenaKlientRunner(
    @Value("\${atk.athena.default.region}")
    private val regionName: String,
    @Value("\${atk.athena.default.database}")
    private val databaseName: String,
    @Value("\${atk.athena.default.catalog}")
    private val catalogName: String,
    @Value("\${atk.athena.default.workgroup}")
    private val workgroupName: String,
    @Value("\${atk.athena.default.output.bucketName}")
    private val outputBucketName: String,
) : AthenaKlientRunner, Closeable {
    override fun submitQuery(query: AthenaQuery, vararg parameters: String): String {
        TODO("Not yet implemented")
    }

    override fun waitForQueryToComplete(queryExecutionId: String) {
        TODO("Not yet implemented")
    }

    override fun processResultRows(queryExecutionId: String) {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}