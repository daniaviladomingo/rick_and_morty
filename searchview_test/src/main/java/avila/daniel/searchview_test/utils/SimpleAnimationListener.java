package avila.daniel.searchview_test.utils;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author Fernando A. H. Falkiewicz
 */
public abstract class SimpleAnimationListener implements SimpleAnimationUtils.AnimationListener {
    @Override
    public boolean onAnimationStart(@NonNull View view) {
        // No action
        return false;
    }

    @Override
    public boolean onAnimationEnd(@NonNull View view) {
        // No action
        return false;
    }

    @Override
    public boolean onAnimationCancel(@NonNull View view) {
        // No action
        return false;
    }
}
