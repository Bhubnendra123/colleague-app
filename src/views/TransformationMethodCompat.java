package views;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by developer01 on 29/06/2017.
 */
public interface TransformationMethodCompat {
    /**
     * Returns a CharSequence that is a transformation of the source text -- for example, replacing each character with a dot in a password field. Beware that the returned text must be exactly the
     * same length as the source text, and that if the source text is Editable, the returned text must mirror it dynamically instead of doing a one-time copy.
     */
    public CharSequence getTransformation(CharSequence source, View view);

    /**
     * This method is called when the TextView that uses this TransformationMethod gains or loses focus.
     */
    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect);
}
