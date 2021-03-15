package com.rails.purchaseplatform.common.multiplecolumnslist;


import android.content.Context;
import android.view.View;

import java.util.List;


/**
 * 多列ListView适配器
 * 集成抽象的多列ListView适配器
 */
public class MultipleColumnsListAdapter extends AbsMultipleColumnsListAdapter<String> {

    public MultipleColumnsListAdapter(Context context, List<String> data, int layoutId, int setcolumn) {
        super(context, data, layoutId, setcolumn);
    }

    @Override
    public void convert(MultipleColumnsListViewHolder holder, int[] positions, List<String> models) {
        for (int i = 0; i < positions.length; i++) {
            switch (i) {
//                case 0:
//                    holder.getLinearLayout(R.id.ll_layout_1).setVisibility(View.VISIBLE);
//                    holder.setText(R.id.tv_zuo, models.get(i).getSchool_id());
//                    break;
//                case 1:
//                    holder.getLinearLayout(R.id.ll_layout_2).setVisibility(View.VISIBLE);
//                    holder.setText(R.id.tv_you, models.get(i).getSchool_id());
//                    break;
//                case 2:
//                    holder.getLinearLayout(R.id.ll_layout_3).setVisibility(View.VISIBLE);
//                    holder.setText(R.id.tv_san, models.get(i).getSchool_id());
//                    break;
                default:
                    break;
            }
        }
    }
}
