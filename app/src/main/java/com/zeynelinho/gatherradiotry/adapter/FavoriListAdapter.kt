package com.zeynelinho.gatherradiotry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zeynelinho.gatherradiotry.R
import com.zeynelinho.gatherradiotry.databinding.RecyclerFavoriBinding
import com.zeynelinho.gatherradiotry.model.RadioListModel
import com.zeynelinho.gatherradiotry.util.MediaPlayerSingleton
import com.zeynelinho.gatherradiotry.util.imageUrl
import com.zeynelinho.gatherradiotry.util.placeHolderProgressBar
import java.util.*
import kotlin.collections.ArrayList

class FavoriListAdapter(
    private val radioList: ArrayList<RadioListModel>,
    private val listener: Listener ,
    private var favFilterList : ArrayList<RadioListModel>
) : RecyclerView.Adapter<FavoriListAdapter.FavoriHolder>(), Filterable {

    interface Listener {
        fun startRadio(radio: RadioListModel) {
        }

        fun stopRadio(radio: RadioListModel) {
        }

        fun delFavori(radio: RadioListModel) {
        }

    }

    class FavoriHolder(val binding: RecyclerFavoriBinding) : RecyclerView.ViewHolder(binding.root) {

        var player = MediaPlayerSingleton.player

        fun bind(radio: RadioListModel, listener: Listener) {


            binding.radioFavButtonRed.setOnClickListener {
                listener.delFavori(radio)
            }


            if (player != null && !player!!.isPlaying) {
                binding.radioPlayButton.background = ContextCompat.getDrawable(itemView.context, R.drawable.play)
                binding.radioPlayButton.setOnClickListener {
                    listener.startRadio(radio)
                    binding.rowPlayProgress.visibility = View.VISIBLE
                    binding.radioPlayButton.visibility = View.GONE
                }
                if (player!!.isPlaying) {
                    binding.rowPlayProgress.visibility = View.GONE
                    binding.radioPlayButton.background = ContextCompat.getDrawable(itemView.context,
                        R.drawable.pause)
                }


            }



            if (player != null && player!!.isPlaying) {
                binding.radioPlayButton.setOnClickListener {
                    listener.stopRadio(radio)
                    binding.radioPlayButton.background = ContextCompat.getDrawable(itemView.context,
                        R.drawable.play)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriHolder {
        val binding =
            RecyclerFavoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriHolder, position: Int) {
        holder.binding.radioName.text = radioList[position].name
        holder.bind(favFilterList[position], listener)

        holder.binding.radioName.text = favFilterList[position].name

        holder.binding.radioAvatar.imageUrl(
            radioList[position].imageLink!!,
            placeHolderProgressBar(holder.itemView.context)
        )
    }

    override fun getItemCount(): Int {
        return favFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("NotifyDataSetChanged")
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    favFilterList = radioList
                    notifyDataSetChanged()

                }else {
                    val resultList = ArrayList<RadioListModel>()
                    for (row in radioList) {
                        if (row.name!!.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    favFilterList = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = favFilterList
                return filterResult
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                favFilterList = results?.values as ArrayList<RadioListModel>
                notifyDataSetChanged()
            }

        }
    }
}