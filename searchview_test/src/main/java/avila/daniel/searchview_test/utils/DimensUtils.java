package avila.daniel.searchview_test.utils;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class DimensUtils {
    private DimensUtils() {
    }

    public static int convertDpToPx(int dp, @NonNull Context context) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }

    public static float convertDpToPx(float dp, @NonNull Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
