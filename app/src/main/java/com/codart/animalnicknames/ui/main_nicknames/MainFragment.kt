package com.codart.animalnicknames.ui.main_nicknames

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codart.animalnicknames.R
import com.codart.animalnicknames.data.entities.Nickname
import com.codart.animalnicknames.data.local.SelectedDB
import com.codart.animalnicknames.ui.MainViewModel
import com.codart.animalnicknames.ui.NicknamesAdapter
import com.codart.animalnicknames.utils.Resource
import com.codart.animalnicknames.utils.Values
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var txtLetter: TextView
    private lateinit var title: TextView
    private lateinit var like: ImageView
    private lateinit var rate: TextView
    private lateinit var dislike: ImageView
    private lateinit var random: ImageView
    private lateinit var star: ImageView
    private lateinit var female: AppCompatImageButton
    private lateinit var male: AppCompatImageButton
    private lateinit var top: TextView
    private lateinit var favorites: TextView
    private lateinit var recyclerView: RecyclerView
    lateinit var menu: ImageView
    private lateinit var drawerLayout: DrawerLayout
    private var androidId: String = ""
    private var letters: MutableList<TextView> = arrayListOf()
    private var letterStr: MutableList<String> = if(Values.locale == "ru-ru"){
        mutableListOf(
            "а",
            "б",
            "в",
            "г",
            "д",
            "е",
            "ж",
            "з",
            "и",
            "к",
            "л",
            "м",
            "н",
            "о",
            "п",
            "р",
            "с",
            "т",
            "у",
            "ф",
            "х",
            "ц",
            "ч",
            "Э",
            "ю",
            "а",
            "б",
            "в",
            "г",
            "д",
            "е",
            "ж",
            "з",
            "и",
            "к",
            "л",
            "м",
            "н",
            "о",
            "п",
            "р",
            "с",
            "т",
            "у",
            "ф",
            "х",
            "ц",
            "ч",
            "Э",
            "ю"
        )
    }
    else{
        mutableListOf(
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "w",
            "x",
            "y",
            "z",
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "z"
        )
    }
    private var letterPos = 0
    private var gender =0
    private var randomNickname = Nickname("0",1,"",0,"")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       androidId =  Settings.Secure.getString(requireContext().contentResolver,
            Settings.Secure.ANDROID_ID)

        initLetters()
        init()
        getRandomNickname()
        setAdapter()
        getNicknames()

        Log.d("id: ", androidId)
    }
    private fun setAdapter(){
        val adapter = NicknamesAdapter(requireContext(),viewModel,viewLifecycleOwner)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter =adapter
        recyclerView.layoutManager = linearLayoutManager
    }
    private fun getNicknames(){
        Log.d("Get nicknames","letterPos: $letterPos")
        val l = try {
            letterStr[letterPos]
        }
        catch (e:Exception){
            letterStr[0]
        }
        viewModel.getNicknamesWithFiltration(viewModel.getViewModelUser().token, l,gender).observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    val adapter = NicknamesAdapter(requireContext(),viewModel, viewLifecycleOwner)
                    adapter.setItems(it.data!!.data)
                    recyclerView.adapter= adapter
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    Log.d("Get random", "Error, ${it.message}")
                    try {
                        Log.d("Get nicknames", "Success: ${it.data!!.success}")
                        Log.d("Get nicknames", "Error: ${it.data.error}")
                    }
                    catch (e:Exception)
                    {
                        Log.d("Get nicknames", e.toString())
                    }
                }

                Resource.Status.LOADING -> {
                }
            }
        })
    }
    private fun setTextRateColor(it: Int){
        if(it < 0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rate.setTextColor(requireActivity().getColor(R.color.red_rate))
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rate.setTextColor(requireActivity().getColor(R.color.green_rate))
            }
        }
    }
    private fun getRandomNickname(){
        viewModel.getRandomNicknameWithGenderFiltration(viewModel.getViewModelUser().token, gender).observe(viewLifecycleOwner,{
            when(it.status){
                Resource.Status.SUCCESS -> {
                    randomNickname = it.data!!.data[0]
                   title.text = it.data!!.data[0].name
                    rate.text = it.data!!.data[0].rating.toString()
                   setTextRateColor(it.data.data[0].rating)
                    if(SelectedDB.checkMod(randomNickname))
                    {

                        star.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_selected))
                    }
                    else{
                        star.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star))
                    }

                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    Log.d("Get random", "Error, ${it.message}")
                    try {
                        Log.d("Get random", "Success: ${it.data!!.success}")
                        Log.d("Get random", "Error: ${it.data.error}")
                    }
                    catch (e:Exception)
                    {
                        Log.d("Get random", e.toString())
                    }
                }

                Resource.Status.LOADING -> {
                }
            }
        })
    }


    private fun init(){
        val isMenuOpen =false
        title = requireView().findViewById(R.id.nicknames_txt_random_title)
        like = requireView().findViewById(R.id.nicknames_img_random_like)
        rate = requireView().findViewById(R.id.nicknames_txt_random_rate)
        dislike = requireView().findViewById(R.id.nicknames_img_random_dislike)
        random = requireView().findViewById(R.id.nicknames_img_random)
        star = requireView().findViewById(R.id.nicknames_img_random_star)
        female = requireView().findViewById(R.id.nicknames_imgb_female)
        female.isSelected = true
        male = requireView().findViewById(R.id.nicknames_imgb_male)
        top = requireView().findViewById(R.id.nicknames_txt_top)
        favorites = requireView().findViewById(R.id.nicknames_txt_selected)
        recyclerView = requireView().findViewById(R.id.nicknames_recycler)
        menu = requireView().findViewById(R.id.nicknames_menu)
        drawerLayout = requireView().findViewById(R.id.nicknames_drawer_layout)
        requireView().findViewById<LinearLayout>(R.id.poup_subscribe).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_subscribeFragment)
        }
        menu.setOnClickListener {
            if(!isMenuOpen)
            {
                drawerLayout.openDrawer(Gravity.LEFT)
            }
            else
            {
                drawerLayout.closeDrawer(Gravity.LEFT)
            }
        }
        female.setOnClickListener{
            female.isSelected = true
            male.isSelected = false
            gender = 0
            getNicknames()
        }
        male.setOnClickListener{
            female.isSelected = false
            male.isSelected = true
            gender = 1
            getNicknames()
        }
        random.setOnClickListener{
            getRandomNickname()
        }
        like.setOnClickListener {
            Log.d("Token: ",viewModel.getViewModelUser().token)
            viewModel.addLikeToNickname(viewModel.getViewModelUser().token, randomNickname.id, androidId).observe(viewLifecycleOwner,{
                when(it.status){
                    Resource.Status.SUCCESS ->{
                        Log.d("Like","Like was add to ${randomNickname.id}")
                        rate.text = it.data!!.data.toString()
                        setTextRateColor(it.data.data)
                    }
                }
            })
        }
        dislike.setOnClickListener {
            Log.d("Token: ",viewModel.getViewModelUser().token)
            viewModel.addDislikeToNickname(viewModel.getViewModelUser().token, randomNickname.id, androidId).observe(viewLifecycleOwner,{
                when(it.status){
                    Resource.Status.SUCCESS ->{
                        Log.d("Disike","Dislike was add to ${randomNickname.id}")
                        rate.text = it.data!!.data.toString()
                        setTextRateColor(it.data.data)
                    }
                }
            })
        }
        star.setOnClickListener {
            if(SelectedDB.checkMod(randomNickname))
            {
                star.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star))
                SelectedDB.removeItem(randomNickname)
            }
            else{
              star.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_selected))
                SelectedDB.addItem(randomNickname)
            }
        }
        top.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_topFragment)
        }
        favorites.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_wishlistFragment)
        }

    }

    private fun initLetters(){
        txtLetter = requireView().findViewById(R.id.nicknames_txt_a)
        letters.add(txtLetter)
        txtLetter.isSelected = true
        txtLetter = requireView().findViewById(R.id.nicknames_txt_b)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_v)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_g)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_d)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_e)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_gy)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_z)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_u)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_k)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_l)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_m)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_n)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_o)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_p)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_r)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_s)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_t)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_y)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_f)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_h)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_c)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_cz)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_ee)
        letters.add(txtLetter)
        txtLetter = requireView().findViewById(R.id.nicknames_txt_yu)
        letters.add(txtLetter)
        setLetterClickListener()

    }
    private fun restoreLetters(){
        for(letter in letters)
        {
            letter.isSelected = false
        }
    }
    private  fun setLetterClickListener(){
        for(letter in letters){
            letter.setOnClickListener {
                restoreLetters()
                letter.isSelected = true
                letterPos = letters.indexOf(letter)
                getNicknames()
            }
        }
    }

}