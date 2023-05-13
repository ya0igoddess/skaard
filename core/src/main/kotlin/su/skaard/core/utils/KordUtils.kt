package su.skaard.core.utils

import dev.kord.common.entity.Snowflake

val Snowflake.lvalue
    get() = this.value.toLong()