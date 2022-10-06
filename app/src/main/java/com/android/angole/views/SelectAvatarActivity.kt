package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.angole.adapters.AvatarRecyclerAdapter
import com.android.angole.databinding.ActivitySelectAvatarBinding
import com.android.angole.models.AvatarItems
import com.android.angole.utils.MarginItemDecoration
import com.android.angole.viewmodels.UserViewModel

class SelectAvatarActivity : AppCompatActivity(), AvatarRecyclerAdapter.OnClickEvent {
    private var binding: ActivitySelectAvatarBinding? = null
    private var userViewModel: UserViewModel? = null
    private var avatarList = listOf<AvatarItems>()
    private var adapter: AvatarRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectAvatarBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val avatarItem = AvatarItems("", "")
        avatarList = listOf(avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem, avatarItem)

        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        binding?.rvAvatar?.layoutManager = layoutManager

        val itemDecoration = MarginItemDecoration(16, 3)
        binding?.rvAvatar?.addItemDecoration(itemDecoration)

        adapter = AvatarRecyclerAdapter(this, avatarList, this)
        binding?.rvAvatar?.adapter = adapter

        binding?.ibBack?.setOnClickListener {
            finish()
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            loadData()
        }, 1500)
    }

    private fun loadData(){
        userViewModel?.getAvatar()
        userViewModel?.avatarData?.observe(this){
            it?.let {
                if (it.data != null){
                    val status = it.data.status
                    if (status){
                        it.data.items?.let { items ->
                            avatarList = items
                            adapter = AvatarRecyclerAdapter(this, avatarList, this)
                            binding?.rvAvatar?.adapter = adapter
                        }

                    }else{
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    if (!it.message.isNullOrEmpty()) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onItemClicked(items: AvatarItems) {
        val intent = Intent()
        intent.putExtra("imageId", items._id)
        intent.putExtra("imageUrl", items.url)
        setResult(RESULT_OK, intent)

        finish()
    }
}