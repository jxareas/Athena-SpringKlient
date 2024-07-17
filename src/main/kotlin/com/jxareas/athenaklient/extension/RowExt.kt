package com.jxareas.athenaklient.extension

import software.amazon.awssdk.services.athena.model.Row

fun List<Row>.skipColumnNames(): List<Row> =
    stream()
        .skip(1)
        .toList()