/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/26/23, 4:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.Structure

data class DataStructure (var frameAuthorLink: String, var frameAuthorNickname: String,
                          var frameName: String,
                          var frameTags: String,
                          var frameUrl: String,
                          var frameUrlHorizontal: String,
                          var frameTrend: Int, var frameTime: Long,
                          var backgroundColors: IntArray)