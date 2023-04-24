@file:JvmName("LoggingUtils")
@file:JvmMultifileClass

package su.skaard.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun getLogger(clazz: Class<*>): Logger {
    return LoggerFactory.getLogger(clazz)
}
