package com.jxareas.athenaklient.runners

import com.jxareas.athenaklient.domain.AthenaQuery
import com.jxareas.athenaklient.exception.AthenaQueryException

interface AthenaKlientRunner {
    fun submitQuery(query: AthenaQuery, vararg parameters: String): String

    @Throws(AthenaQueryException::class)
    fun waitForQueryToComplete(queryExecutionId: String)

    fun processResultRows(queryExecutionId: String)
}