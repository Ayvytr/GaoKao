package com.ayvytr.gaokao.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import com.ayvytr.baseadapter.CommonAdapter
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.commonlibrary.bean.AppSubject
import com.ayvytr.gaokao.R
import com.ayvytr.ktx.context.getScreenWidth

class SubjectAdapter(context: Context, subjects: MutableList<AppSubject>) :
    CommonAdapter<AppSubject>(context, R.layout.item_main_fragment, subjects) {

    override fun convert(holder: ViewHolder, t: AppSubject, position: Int) {
        holder.setText(R.id.tv_title, t.name)

        val lp = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        val width = mContext.getScreenWidth() / 3
        lp.height = width
        lp.width = width
    }

}
