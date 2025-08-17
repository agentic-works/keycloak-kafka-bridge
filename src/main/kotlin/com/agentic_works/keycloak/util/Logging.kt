package com.agentic_works.keycloak.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logging {
    fun getLogger(clazz: Class<*>): Logger {
        return LoggerFactory.getLogger(clazz)
    }
}