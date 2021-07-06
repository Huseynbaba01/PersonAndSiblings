package com.example.personandsiblings.data

import android.content.Context
import io.objectbox.BoxStore

object ObjectBox {
	var boxStore: BoxStore? = null
	fun init(context: Context){
		if(boxStore == null){
			boxStore = MyObjectBox
				.builder()
				.androidContext(context.applicationContext)
				.build()
		}
//				boxStore = MyObjectBox

	}
}