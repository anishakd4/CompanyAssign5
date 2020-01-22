package com.developer.anishakd4.halodocassignment.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.developer.anishakd4.halodocassignment.Model.HitsModel
import com.developer.anishakd4.halodocassignment.R
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerViewAdapter(val news: List<HitsModel>, val clickInterFace: ClickInterFace): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    interface ClickInterFace {
        fun onClickedInAdapter(position: Int);

        fun onBottomReached();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        (holder as RecyclerViewHolder).bind(news.get(position))
        (holder as RecyclerViewHolder).parent.setOnClickListener {
            if(clickInterFace != null){
                clickInterFace.onClickedInAdapter(position)
            }
        }

        if(position == news.size - 1){
            if(clickInterFace != null){
                clickInterFace.onBottomReached()
            }
        }
    }

    class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.title
        val author: TextView = view.author
        val points: TextView = view.points
        val parent: ConstraintLayout = view.parent_cons

        fun bind(currentNews: HitsModel) {
            title.text = currentNews.title
            author.text = currentNews.author
            points.text = currentNews.points.toString()
        }
    }

    override fun getItemCount(): Int {
        if(news != null){
            return news.size;
        }
        return 0
    }


}
