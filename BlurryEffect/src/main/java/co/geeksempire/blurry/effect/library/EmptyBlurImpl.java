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

public class EmptyBlurImpl implements BlurImpl {
	@Override
	public boolean prepare(Context context, Bitmap buffer, float radius) {
		return false;
	}

	@Override
	public void release() {

	}

	@Override
	public void blur(Bitmap input, Bitmap output) {

	}
}
