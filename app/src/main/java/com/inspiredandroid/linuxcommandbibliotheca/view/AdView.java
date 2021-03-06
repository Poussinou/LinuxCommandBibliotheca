package com.inspiredandroid.linuxcommandbibliotheca.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.inspiredandroid.linuxcommandbibliotheca.misc.AppManager;
import com.inspiredandroid.linuxcommandbibliotheca.misc.Utils;

/**
 * Created by simon on 23/01/17.
 */
public class AdView extends AppCompatImageView {

    public AdView(Context context) {
        super(context);
        init();
    }

    public AdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Hide ads if remote is already installed
        if (AppManager.getHideAdvertising(getContext()) || Utils.isAppInstalled(getContext(), Utils.PACKAGE_LINUXREMOTE) || Utils.isAppInstalled(getContext(), Utils.PACKAGE_LINUXREMOTE_PRO)) {
            setVisibility(View.GONE);
        } else {
            setOnClickListener(view -> startRemoteControl());
        }
    }

    private void startRemoteControl() {
        final String appPackageName = Utils.PACKAGE_LINUXREMOTE;
        try {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
