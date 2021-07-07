package com.example.personandsiblings

import android.content.Context
import com.example.personandsiblings.data.MyObjectBox
import io.objectbox.BoxStore

object ObjectBoxForSiblings {
	var boxStoreForSibling: BoxStore? = null
	fun init(context: Context){
		if(boxStoreForSibling == null){
//			ObjectBoxForSiblings.boxStoreForParent?.close()
			boxStoreForSibling = MyObjectBox.builder()
				.androidContext(context)
				.build()
		}
	}
}