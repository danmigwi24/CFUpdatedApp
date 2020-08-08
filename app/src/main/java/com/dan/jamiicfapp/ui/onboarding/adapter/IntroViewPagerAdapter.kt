package com.dan.jamiicfapp.ui.onboarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.Introdata
import kotlinx.android.synthetic.main.layout_introviewpager.view.*

class IntroViewPagerAdapter(
    private val context: Context,
    private val introdata: ArrayList<Introdata>
) :
    PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.layout_introviewpager, null)

        view.textViewTitle.text = introdata[position].title
        view.textViewDescription.text = introdata[position].description
        view.imageView.setImageResource(introdata[position].image!!.toInt())

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = introdata.size
}