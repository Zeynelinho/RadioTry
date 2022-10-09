package com.zeynelinho.gatherradiotry.view



import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zeynelinho.gatherradiotry.adapter.RadioListAdapters
import com.zeynelinho.gatherradiotry.databinding.FragmentMainBinding
import com.zeynelinho.gatherradiotry.model.RadioListModel
import com.zeynelinho.gatherradiotry.preferences.Shared
import com.zeynelinho.gatherradiotry.roomDB.RadioDao
import com.zeynelinho.gatherradiotry.roomDB.RadioDatabase
import com.zeynelinho.gatherradiotry.util.MediaPlayerSingleton
import com.zeynelinho.gatherradiotry.viewmodel.MainViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable


import java.io.IOException
import java.util.*




class MainFragment : Fragment(), RadioListAdapters.Listener {

    private val compositeDisposable = CompositeDisposable()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var radioListAdapters: RadioListAdapters? = null
    private lateinit var radioArrayList: ArrayList<RadioListModel>
    private lateinit var radioDatabase: RadioDatabase
    private lateinit var radioDao: RadioDao
    private lateinit var radioFilterList: ArrayList<RadioListModel>





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)


        //radioDatabase = Room.databaseBuilder(requireContext(),RadioDatabase::class.java,"Radios").build()
        //radioDao = radioDatabase.radioDao()



        binding.recyclerProgres.visibility = View.VISIBLE

        //viewModel and recyclerView initialize

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.refreshData(requireContext())
        radioListAdapters = RadioListAdapters(arrayListOf(), this, arrayListOf())
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.removeAllViews()
        binding.recyclerView.setHasFixedSize(true)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager





        //radio search filter initialize

        binding.radioSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                radioListAdapters!!.filter.filter(newText)
                return false
            }

        })

        //refresh layout on click

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.recyclerView.visibility = View.GONE
            binding.recyclerError.visibility = View.GONE
            binding.recyclerProgres.visibility = View.VISIBLE
            viewModel.refreshData(requireContext())
        }


        observeData()

        return binding.root
    }


    private fun observeData() {

        viewModel.radioData.observe(viewLifecycleOwner, Observer { radioList ->
            radioList?.let {
                radioArrayList = ArrayList(radioList)
                radioFilterList = ArrayList(radioList)


                radioArrayList.clear()
                radioFilterList.clear()
                for (item in radioList) {
                    radioArrayList.add(item)

                }
                radioFilterList.addAll(radioArrayList)



                radioListAdapters = RadioListAdapters(
                    radioArrayList, this@MainFragment, radioFilterList
                )

                binding.recyclerView.visibility = View.VISIBLE
                binding.recyclerError.visibility = View.GONE
                binding.recyclerView.adapter = radioListAdapters
                binding.recyclerProgres.visibility = View.GONE
                radioListAdapters!!.notifyDataSetChanged()


            }
        })

        viewModel.radioError.observe(viewLifecycleOwner, Observer { error ->

            error?.let {
                if (it) {
                    binding.recyclerView.visibility = View.GONE
                    binding.recyclerError.visibility = View.VISIBLE
                    binding.recyclerProgres.visibility = View.GONE
                }else {
                    binding.recyclerError.visibility = View.GONE
                }
            }

        })

        viewModel.radioLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    binding.recyclerProgres.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.recyclerError.visibility = View.GONE
                }else {
                    binding.recyclerProgres.visibility = View.GONE
                }
            }

        })



    }


    override fun startRadio(radio: RadioListModel) {
        super.startRadio(radio)

        var player = MediaPlayerSingleton.player

        binding.recyclerProgres.visibility = View.VISIBLE


        val name = radio.name
        val streamUrl = radio.streamLink
        val imageUrl = radio.imageLink
        val position = radio.id


        if (player != null && player!!.isPlaying) {
            player!!.pause()
            player!!.release()
            Shared.Constant.clearPlay(requireContext())
        }
        try {
            if (player != null) {
                player!!.release()
                Shared.Constant.clearPlay(requireContext())
            }
            player = MediaPlayer()
            player!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            player!!.setDataSource(Uri.parse(streamUrl).toString())
            player!!.setOnPreparedListener(MediaPlayer.OnPreparedListener {
                player!!.start()

                if (player!!.isPlaying) {
                    if (name != null && streamUrl != null) {
                        Shared.Constant.setPlaying(requireContext(), name, streamUrl, imageUrl!!, position!!
                        )
                        Toast.makeText(activity, "Playing ${radio.name}", Toast.LENGTH_SHORT).show()
                        binding.recyclerProgres.visibility = View.GONE
                        radioListAdapters!!.notifyData(radioFilterList)
                    }

                }

            })
            player.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                if (player.release().run { true }) return@OnCompletionListener

            })
            player.setOnErrorListener(MediaPlayer.OnErrorListener { _, _, _ ->
                player.reset()
                Shared.Constant.clearPlay(requireContext())
                true
            })
            player.prepareAsync()


        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }



    }


    override fun addFavori(radio: RadioListModel) {
        super.addFavori(radio)



    }

    override fun delFavori(radio: RadioListModel) {
        super.delFavori(radio)




    }





}






