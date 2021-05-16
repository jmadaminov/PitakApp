package com.novatec.epitak.util

import com.novatec.epitak.App
import com.novatec.epitak.R

object PostUtils {

    fun timeFromDayParts(firstPart: Boolean,
                         secondPart: Boolean,
                         thirdPart: Boolean,
                         fourthPart: Boolean): String {
        if (firstPart && secondPart && thirdPart && fourthPart) {
            return App.INSTANCE.getString(R.string.anytime)
        } else if (firstPart && secondPart && thirdPart && !fourthPart) {
            return "00:00 - 18:00"
        } else if (firstPart && secondPart && !thirdPart && !fourthPart) {
            return "00:00 - 12:00"
        } else if (firstPart && !secondPart && !thirdPart && !fourthPart) {
            return "00:00 - 6:00"
        } else if (!firstPart && secondPart && thirdPart && fourthPart) {
            return "6:00 - 00:00"
        } else if (!firstPart && !secondPart && thirdPart && fourthPart) {
            return "12:00 - 00:00"
        } else if (!firstPart && !secondPart && !thirdPart && fourthPart) {
            return "18:00 - 00:00"
        } else if (firstPart && !secondPart && !thirdPart && fourthPart) {
            return "00:00 - 6:00, 18:00-00:00"
        } else if (!firstPart && secondPart && thirdPart && !fourthPart) {
            return "6:00 - 18:00"
        } else if (firstPart && !secondPart && thirdPart && fourthPart) {
            return "00:00 - 6:00, 12:00 - 00:00"
        } else if (firstPart && secondPart && !thirdPart && fourthPart) {
            return "00:00 - 12:00, 18:00 - 00:00"
        } else if (firstPart && !secondPart && thirdPart && !fourthPart) {
            return "00:00 - 6:00, 12:00 - 18:00"
        } else if (!firstPart && secondPart && !thirdPart && fourthPart) {
            return "6:00 - 12:00, 18:00 - 00:00"
        } else if (!firstPart && !secondPart && thirdPart && !fourthPart) {
            return "12:00 - 18:00"
        } else if (!firstPart && secondPart && !thirdPart && !fourthPart) {
            return "6:00 - 12:00"
        }
        return "error time "
    }
}