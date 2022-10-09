package com.zeynelinho.gatherradiotry.adapter


import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.zeynelinho.gatherradiotry.R
import com.zeynelinho.gatherradiotry.databinding.RecyclerRowBinding
import com.zeynelinho.gatherradiotry.model.RadioListModel
import com.zeynelinho.gatherradiotry.util.MediaPlayerSingleton
import com.zeynelinho.gatherradiotry.util.MediaPlayerSingleton.player
import com.zeynelinho.gatherradiotry.util.imageUrl
import com.zeynelinho.gatherradiotry.util.placeHolderProgressBar

import java.util.*
import kotlin.collections.ArrayList


class RadioListAdapters(private var radioList: ArrayList<RadioListModel>, private val listener: Listener,private var radioFilterList : List<RadioListModel>) :
    RecyclerView.Adapter<RadioListAdapters.AdapterHolder>() , Filterable {



    interface Listener {
        fun startRadio(radio: RadioListModel) {
        }

        fun stopRadio(radio: RadioListModel) {
        }

        fun addFavori(radio: RadioListModel) {
        }

        fun delFavori(radio: RadioListModel) {

        }


    }


    class AdapterHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

        private var player = MediaPlayerSingleton.player


        fun bind(radio: RadioListModel, listener: Listener) {




            player = MediaPlayer()

                binding.radioPlayButton.setOnClickListener {
                    if (player != null && !player!!.isPlaying) {
                        itemView.setOnClickListener {
                            listener.startRadio(radio)
                            if (player!!.isPlaying) {
                                binding.radioPlayButton.setBackgroundResource(R.drawable.pause)
                            }
                        }
                    }else if (player!!.isPlaying) {
                        listener.stopRadio(radio)
                        if (!player!!.isPlaying) {
                            binding.radioPlayButton.setBackgroundResource(R.drawable.play)
                        }
                    }
                }


                binding.radioFavButton.setOnClickListener {
                    listener.addFavori(radio)
                    binding.radioFavButton.visibility = View.GONE
                    binding.radioFavButtonRed.visibility = View.VISIBLE

                }


                binding.radioFavButtonRed.setOnClickListener {
                    listener.delFavori(radio)
                    binding.radioFavButtonRed.visibility = View.GONE
                    binding.radioFavButton.visibility = View.VISIBLE


                }


            binding.radioAvatar.imageUrl(
                radio.imageLink!!,
                placeHolderProgressBar(itemView.context)
            )



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {

        holder.bind(radioFilterList[position],listener)



        holder.binding.radioName.text = radioFilterList[position].name



    }



    override fun getItemCount(): Int {
        return radioFilterList.size

    }

    fun notifyData(newRadioList : List<RadioListModel>) {

        radioList.clear()
        radioList.addAll(newRadioList)
        notifyDataSetChanged()

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("NotifyDataSetChanged")
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    radioFilterList = radioList
                    notifyDataSetChanged()

                }else {
                    val resultList = ArrayList<RadioListModel>()
                    for (row in radioList) {
                        if (row.name!!.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    radioFilterList = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = radioFilterList
                return filterResult
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                radioFilterList = results?.values as List<RadioListModel>
                notifyDataSetChanged()
            }

        }

    }




}