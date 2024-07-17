package com.jxareas.athenaklient.exception

import com.jxareas.athenaklient.extension.stateChangeReason
import software.amazon.awssdk.services.athena.model.GetQueryExecutionResponse

class AthenaQueryException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    object Message {
        private fun queryFailed(errorMessage: String) =
            "Query failed to run with error message: $errorMessage"

        fun queryFailed(queryExecutionResponse: GetQueryExecutionResponse) =
            queryFailed(queryExecutionResponse.stateChangeReason)

        fun cancelledQuery() =
            "Athena Query was cancelled."

    }
}