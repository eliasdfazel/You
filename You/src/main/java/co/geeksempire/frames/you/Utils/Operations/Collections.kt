/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/27/23, 5:46 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Operations

import kotlin.math.abs

fun Map<String, String>.nearestNumber(inputNumber: Double) : String {

    val inputMap = HashMap<Map.Entry<String, String>, Double>()

    this@nearestNumber.forEach {

        inputMap[it] = abs(it.key.toDouble() - inputNumber)

    }

    val sortHashMap = inputMap.entries.sortedBy {

        it.value
    }

    return sortHashMap.first().key.key
}