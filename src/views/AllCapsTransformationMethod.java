package views;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * Created by developer01 on 29/06/2017.
 */
public class AllCapsTransformationMethod implements TransformationMethodCompat2 {

    private static final String TAG = "AllCapsTransformationMethod";

    private boolean mEnabled;
    private Locale mLocale;

    public AllCapsTransformationMethod(Context context)
    {
        mLocale = context.getResources().getConfiguration().locale;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view)
    {
        if (mEnabled)
        {
            return source != null ? source.toString().toUpperCase(mLocale) : null;
        }
        return source;
    }

    @Override
    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect)
    {
    }

    @Override
    public void setLengthChangesAllowed(boolean allowLengthChanges)
    {
        mEnabled = allowLengthChanges;
    }
}
