package views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.good.gd.example.skeleton.R;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by developer01 on 29/06/2017.
 */
public class FontCache {

    private Map<String, Typeface> cache;
    private static FontCache singleton;

    private FontCache() {
        cache = new WeakHashMap<String, Typeface>();
    }

    public synchronized static FontCache getInstance() {
        if(singleton == null) {
            singleton = new FontCache();
        }
        return singleton;
    }

    /**
     * <p>Fetches the specified font asset.</p>
     * <p>If the font specified by <code>assetPath</code> has already been loaded and not garbage collected, the existing {@link Typeface} will be returned.
     * Otherwise, it is loaded from the application assets folder.</p>
     * @param context used to locate the font if it is not already cached
     * @param assetPath path relative to the <code>assets</code> folder from which to load the font
     * @return the typeface, or <code>null</code> if the typeface could not be loaded
     */
    public Typeface getFont(Context context, String assetPath) {
        Typeface typeface = cache.get(assetPath);
        if(typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), assetPath);
            }catch(Exception e) {

            }
            if(typeface != null) {
                cache.put(assetPath, typeface);
            }
        }
        return typeface;
    }
}
