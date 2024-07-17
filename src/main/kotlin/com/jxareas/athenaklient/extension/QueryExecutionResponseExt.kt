package com.jxareas.athenaklient.extension

import software.amazon.awssdk.services.athena.model.GetQueryExecutionResponse

val GetQueryExecutionResponse.stateChangeReason: String
    get() = queryExecution().status().stateChangeReason()
