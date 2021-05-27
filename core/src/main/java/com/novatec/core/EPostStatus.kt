package com.novatec.core

enum class EPostStatus {
    WAITING_FOR_START,
    START,
    CANCELED,
    FINISHED,
    REJECTED,
    CREATED,
    SYSTEM_REJECTED;

    fun canTakePassenger(): Boolean {
        return this == CREATED
    }

    fun canTakeParcel(): Boolean {
        return this == CREATED && this == WAITING_FOR_START
    }

}
