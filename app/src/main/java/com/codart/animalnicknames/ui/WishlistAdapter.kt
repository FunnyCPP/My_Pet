package com.codart.animalnicknames.ui

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.codart.animalnicknames.R
import com.codart.animalnicknames.data.entities.Nickname
import com.codart.animalnicknames.data.local.SelectedDB
import com.codart.animalnicknames.utils.Resource

class WishlistAdapter(val context: Context, val viewModel: MainViewModel, val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<WishlistViewHolder>() {
    private val items = arrayListOf<Nickname>()

    fun setItems(items: List<Nickname>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WishlistViewHolder {
        return WishlistViewHolder(itemView = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.nickname_cell,
            viewGroup,
            false
        ),context, viewModel, lifecycleOwner)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int)
    {
        holder.setIsRecyclable(false)
        holder.bind(items[position])
        val item = items[position]
        holder.star.setOnClickListener {
            if(SelectedDB.checkMod(item))
            {
                SelectedDB.removeItem(item)
            }
            else{
                SelectedDB.addItem(item)
            }
            setItems(SelectedDB.getMods())
        }
    }
}

class WishlistViewHolder(itemView: View, val context: Context, val viewModel: MainViewModel, val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(
    itemView){

    private lateinit var item: Nickname
    private val title: TextView = itemView.findViewById(R.id.nickname_txt)
    private val like: ImageView = itemView.findViewById(R.id.nickname_like)
    private val rate: TextView = itemView.findViewById(R.id.nickname_rate)
    private val dislike: ImageView = itemView.findViewById(R.id.nickname_dislike)
    val star: ImageView = itemView.findViewById(R.id.nickname_star)
    val androidId = Settings.Secure.getString(context.contentResolver,
        Settings.Secure.ANDROID_ID)

    private fun setTextRateColor(it: Int){
        if(it < 0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rate.setTextColor(context.getColor(R.color.red_rate))
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rate.setTextColor(context.getColor(R.color.green_rate))
            }
        }
    }

    fun bind(item: Nickname) {
        this.item = item
        title.text = item.name
        rate.text = item.rating.toString()
        if(item.rating < 0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rate.setTextColor(context.getColor(R.color.red_rate))
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rate.setTextColor(context.getColor(R.color.green_rate))
            }
        }
        if(SelectedDB.checkMod(item))
        {

            star.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star_selected))
        }
        else{
            star.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star))
        }
        like.setOnClickListener {
            viewModel.addLikeToNickname(viewModel.getViewModelUser().token, item.id,androidId).observe(lifecycleOwner,{
                when(it.status){
                    Resource.Status.SUCCESS ->{
                        Log.d("Like","Like was add to ${item.id}")
                        rate.text = it.data!!.data.toString()
                        setTextRateColor(it.data.data)
                    }
                }
            })
        }
        dislike.setOnClickListener {
            viewModel.addDislikeToNickname(viewModel.getViewModelUser().token, item.id, androidId).observe(lifecycleOwner,{
                when(it.status){
                    Resource.Status.SUCCESS ->{
                        Log.d("Disike","Disike was add to ${item.id}")
                        rate.text = it.data!!.data.toString()
                        setTextRateColor(it.data.data)
                    }
                }
            })
        }

    }

}
