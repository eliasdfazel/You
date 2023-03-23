/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/23/23, 6:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.Structure

data class DataStructure (var frameAuthorLink: String, var frameAuthorNickname: String,
                          var frameName: String,
                          var frameHeight: String, var frameWidth: String, var frameRatio: String,
                          var frameUrl: String,
                          var frameUrlHorizontal: String,
                          var frameTrend: Int, var frameTime: Long,
                          var backgroundColors: IntArray
)