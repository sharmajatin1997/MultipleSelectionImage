package com.example.selectimage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var gallery: TextView
    lateinit var camera: TextView
    var PICK_IMAGES_CODE = 1

    lateinit var recyclerView: RecyclerView
    lateinit var imageAdapter: ImageAdapter
    lateinit var items: MutableList<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items = arrayListOf()
        gallery = findViewById(R.id.tv_gallery)
        camera = findViewById(R.id.tv_camera)
        recyclerView = findViewById(R.id.rv_recyclerView)
        initAdapter()

        gallery.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Image(s)"),
                PICK_IMAGES_CODE
            )

        }
        camera.setOnClickListener {
            
        }
    }

    private fun initAdapter() {
        imageAdapter = ImageAdapter(this, items)
        val ll = GridLayoutManager(this, 4)
        recyclerView.layoutManager = ll
        recyclerView.adapter = imageAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data!!.clipData != null) {

            val count = data.clipData!!.itemCount
            for (i in 0 until count) {
                val imageUri = data.clipData!!.getItemAt(i).uri
                items.add(imageUri)
                imageAdapter.notifyDataSetChanged()
            }

        } else {
            val imageUri = data.data

        }
    }
}