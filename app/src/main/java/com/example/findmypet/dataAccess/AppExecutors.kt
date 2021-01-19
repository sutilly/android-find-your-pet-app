package com.example.findmypet.dataAccess

import java.util.concurrent.Executor

interface AppExecutors {

    val bgThreadExecutor: Executor
    val mainThreadExecutor: Executor
}

class LiveAppExecutors(
    override val bgThreadExecutor: Executor,
    override val mainThreadExecutor: Executor
) : AppExecutors {}