package talex.zsw.basecore.view.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug;

/**
 * 无限自动跑马灯
 */
public class RunTextView extends android.support.v7.widget.AppCompatTextView {
    public RunTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RunTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RunTextView(Context context) {
        super(context);
    }

    /**
     * 当前并没有焦点，我只是欺骗了Android系统
     */
    @Override
    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return true;
    }
}
