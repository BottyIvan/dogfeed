package com.botty.dogfeed.Font;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.botty.dogfeed.Font.Typefaces;

/**
 * Created by ivanbotty on 13/07/14.
 */
public class style extends TextView {

    public style(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    public style(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public style(Context context)
    {
        super(context);
        init(context);
    }

    private void init(Context c)
    {
        setTypeface(Typefaces.get(getContext(), "font/Ubuntu-B.ttf"));
    }

}