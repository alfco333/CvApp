package com.angelvargas.cvapp.domain

import com.angelvargas.cvapp.domain.executor.ThreadExecutor

class ImmediateThreadExecutor: ThreadExecutor {
    override fun execute(runnable: Runnable?) {
        runnable?.run()
    }
}