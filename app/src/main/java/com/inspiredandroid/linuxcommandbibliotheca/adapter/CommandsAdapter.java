package com.inspiredandroid.linuxcommandbibliotheca.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.inspiredandroid.linuxcommandbibliotheca.R;
import com.inspiredandroid.linuxcommandbibliotheca.misc.AppManager;
import com.inspiredandroid.linuxcommandbibliotheca.misc.Constants;
import com.inspiredandroid.linuxcommandbibliotheca.misc.Utils;
import com.inspiredandroid.linuxcommandbibliotheca.models.Command;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class CommandsAdapter extends RealmMultiAdapter<Command> implements ListAdapter {

    private String mQuery = "";
    private ArrayList<Long> mIds;

    public CommandsAdapter(Context context,
                           List<RealmResults<Command>> realmResults,
                           boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        updateBookmarkIds();
    }

    public void setSearchQuery(String searchQuery) {
        mQuery = searchQuery;
    }

    public void updateBookmarkIds() {
        mIds = AppManager.getBookmarkIds(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_command_child,
                    parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Command item = getItem(position);
        viewHolder.name.setText(Utils.highlightQueryInsideText(mContext, mQuery, item.getName()));
        viewHolder.description.setText(Utils.highlightQueryInsideText(mContext, mQuery, item.getDesc().trim()));

        if (mIds.contains((long) item.getId())) {
            viewHolder.icon.setImageResource(R.drawable.ic_bookmark_black_24dp);
        } else {
            viewHolder.icon.setImageResource(getSectionImageResource(item.getCategory()));
        }

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    /**
     * Get section icon
     *
     * @param section
     * @return
     */
    private int getSectionImageResource(int section) {
        switch (section) {
            case Constants.SECTION_GAMES:
                return R.drawable.ic_videogame_asset_black_24dp;
            case Constants.SECTION_SYSTEMADMINANDDEAMON:
                return R.drawable.ic_security_black_24dp;
            case Constants.SECTION_USERCOMMANDS:
                return R.drawable.ic_keyboard_black_24dp;
            case Constants.SECTION_SYSTEMCALLS:
                return R.drawable.ic_code_white_48dp;
            case Constants.SECTION_MISSCELANOUS:
                return R.drawable.ic_keyboard_black_24dp;
        }
        return R.drawable.ic_keyboard_black_24dp;
    }

    public class ViewHolder {
        @BindView(R.id.row_command_child_tv_title)
        TextView name;
        @BindView(R.id.row_command_child_tv_desc)
        TextView description;
        @BindView(R.id.row_command_child_iv_icon)
        ImageView icon;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}