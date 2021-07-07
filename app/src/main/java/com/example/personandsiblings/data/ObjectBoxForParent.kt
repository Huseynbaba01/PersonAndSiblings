package com.example.personandsiblings.data

import android.content.Context
import android.util.Log
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import io.objectbox.android.BuildConfig


object ObjectBoxForParent {
	var boxStoreForParent: BoxStore? = null
	var boxStoreForSibling: BoxStore? = null
	fun init(context: Context/*,boolean: Boolean*/){
		if(boxStoreForParent == null /*&& !boolean*/){
			boxStoreForSibling?.close()
			boxStoreForParent = MyObjectBox
				.builder()
				.androidContext(context.applicationContext)
				.build()
//			if (BuildConfig.DEBUG) {
//				val started = AndroidObjectBrowser(boxStoreForParent).start(context)
//				Log.i("ObjectBrowser", "Started: $started")
//			}
		}

		/*if(boxStoreForSibling == null && boolean){
			boxStoreForParent?.close()
			boxStoreForSibling = MyObjectBox
				.builder()
				.androidContext(context.applicationContext)
				.build()
		}*/
	}
}