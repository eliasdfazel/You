/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/23/22, 5:47 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.blurry.effect.library;

import android.content.Context;
import android.graphics.Bitmap;

public interface BlurImpl {

	boolean prepare(Context context, Bitmap buffer, float radius);

	void release();

	void blur(Bitmap input, Bitmap output);

}
