package com.example.calculatorconverter

import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImage(int: Int){
   Glide.with(context)
       .load(int)
       .into(this)
}