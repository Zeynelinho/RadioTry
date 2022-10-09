package com.zeynelinho.gatherradiotry.view



import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.tabs.TabLayoutMediator
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.zeynelinho.gatherradiotry.R
import com.zeynelinho.gatherradiotry.adapter.RadioListAdapters
import com.zeynelinho.gatherradiotry.databinding.ActivityMainBinding
import com.zeynelinho.gatherradiotry.preferences.Shared
import com.zeynelinho.gatherradiotry.roomDB.RadioDatabase
import com.zeynelinho.gatherradiotry.util.MediaPlayerSingleton
import io.reactivex.rxjava3.disposables.CompositeDisposable




class MainActivity : AppCompatActivity(), RadioListAdapters.Listener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        //action bar initialize
        setSupportActionBar(binding.appBarMain.toolbar)

        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        //fullscreen display
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        //get and set token
        val token = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        Shared.Constant.setToken(this, token)

        //fragment initialize

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val playerFragment = PlayerFragment()
        fragmentTransaction.add(R.id.frameLayout, playerFragment).commit()

        val layout = binding.slidingLayout

        //Sliding Panel initialize

        layout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                when (newState) {
                    SlidingUpPanelLayout.PanelState.DRAGGING -> {
                        binding.frameLayout.visibility = View.VISIBLE
                        binding.playerBottomBar.visibility = View.GONE
                    }
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        binding.frameLayout.visibility = View.GONE
                        binding.playerBottomBar.visibility = View.VISIBLE
                    }
                    SlidingUpPanelLayout.PanelState.HIDDEN -> {
                        binding.playerBottomBar.visibility = View.VISIBLE
                        binding.frameLayout.visibility = View.GONE
                    }

                    else -> {

                    }
                }

            }

        })


        //download app link initialize

        binding.gatherDownload.movementMethod = LinkMovementMethod.getInstance()
        binding.gatherDownload.setLinkTextColor(R.color.black)


        //Navigation View , click items initiliaze

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.kullanim_kosullari -> Toast.makeText(
                    this, "Clicked Item 1",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.geri_bildirim -> Toast.makeText(
                    this, "Clicked Item 2",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.puanla -> Toast.makeText(
                    this, "Clicked Item 3",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.payla_ -> Toast.makeText(
                    this, "Clicked Item 4",
                    Toast.LENGTH_SHORT
                ).show()
            }

            true
        }


        // ViewPager and TabLayout initialize

        val pager = binding.viewPager2
        val tabLayout = binding.tabLayout


        pager.adapter =
            com.zeynelinho.gatherradiotry.adapter.PagerAdapter(supportFragmentManager, lifecycle)


        TabLayoutMediator(tabLayout, pager) { tab, position ->
            pager.setCurrentItem(tab.position, true)

            when (position) {
                0 -> {
                    tab.setText(R.string.tum_kanallar)
                }
                1 -> {
                    tab.setText(R.string.favoriler)
                }
                else -> {
                    tab.setText(R.string.tum_kanallar)
                }
            }


        }.attach()

        val player = MediaPlayerSingleton.player


        if(player != null && player.isPlaying) {
            binding.radioForwardButton.visibility = View.GONE
        }


    }

    // Navigation view item click function

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }




}











