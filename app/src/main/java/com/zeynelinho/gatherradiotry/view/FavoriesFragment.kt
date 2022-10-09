package com.zeynelinho.gatherradiotry.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zeynelinho.gatherradiotry.adapter.FavoriListAdapter
import com.zeynelinho.gatherradiotry.databinding.FragmentFavoriesBinding
import com.zeynelinho.gatherradiotry.model.RadioListModel
import com.zeynelinho.gatherradiotry.roomDB.RadioDao
import com.zeynelinho.gatherradiotry.roomDB.RadioDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class FavoriesFragment : Fragment(), FavoriListAdapter.Listener {


    private var _binding: FragmentFavoriesBinding? = null
    private val binding get() = _binding!!
    var radioList: ArrayList<RadioListModel>? = null
    var favFilterList : ArrayList<RadioListModel>? = null
    var favoriListAdapter: FavoriListAdapter? = null
    private val compositeDisposable = CompositeDisposable()
    //private lateinit var radioDatabase : RadioDatabase
    //private lateinit var radioDao : RadioDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriesBinding.inflate(inflater, container, false)





        binding.favRadioSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                favoriListAdapter!!.filter.filter(newText)
                return false
            }

        })



        return binding.root

    }

    private fun handleResponse (roomRadioList : List<RadioListModel>) {


        radioList = ArrayList(roomRadioList)
        favFilterList = ArrayList(roomRadioList)

        radioList!!.clear()
        favFilterList!!.clear()

        for (item in roomRadioList) {
            radioList!!.add(item)
        }


        favFilterList!!.addAll(radioList!!)

        favoriListAdapter = FavoriListAdapter(radioList!!,this,favFilterList!!)
        binding.recyclerView.adapter = favoriListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoriListAdapter!!.notifyDataSetChanged()
    }


    override fun startRadio(radio: RadioListModel) {
        super.startRadio(radio)
    }

    override fun stopRadio(radio: RadioListModel) {
        super.stopRadio(radio)
    }

    override fun delFavori(radio: RadioListModel) {
        super.delFavori(radio)

    }


}