package com.example.personandsiblings.data

import android.content.Context
import io.objectbox.BoxStore

object ObjectBox {
	var boxStoreForParent: BoxStore? = null
	var boxStoreForSibling: BoxStore? = null
	fun init(context: Context,boolean: Boolean){
		if(boxStoreForParent == null && !boolean){
			boxStoreForSibling?.close()
			boxStoreForParent = MyObjectBox
				.builder()
				.androidContext(context.applicationContext)
				.build()
		}

		if(boxStoreForSibling == null && boolean){
			boxStoreForParent?.close()
			boxStoreForSibling = MyObjectBox
				.builder()
				.androidContext(context.applicationContext)
				.build()
		}
	}
}