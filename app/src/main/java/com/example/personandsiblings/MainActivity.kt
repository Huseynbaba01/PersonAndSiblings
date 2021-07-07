package com.example.personandsiblings

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.personandsiblings.ObjectBoxForSiblings.boxStoreForSibling
import com.example.personandsiblings.data.ObjectBoxForParent.boxStoreForParent
import com.example.personandsiblings.data.ObjectBoxForParent.init
import com.example.personandsiblings.data.Parent
import com.example.personandsiblings.data.Sibling
import com.example.personandsiblings.data.Sibling_
import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.query.QueryBuilder
import io.objectbox.reactive.DataSubscription


class MainActivity : AppCompatActivity() {
	private lateinit var buttonAdd:Button
	private lateinit var buttonFind:Button
	private lateinit var inputEditText: EditText
	private lateinit var textView: TextView
	private lateinit var boxForParent: Box<Parent>
	private lateinit var boxForSiblings: Box<Sibling>
	private lateinit var queryForParent: Query<Parent>
	private lateinit var queryForSiblings: Query<Sibling>
	private lateinit var subscription: DataSubscription
	private var parent = Parent()
	private var sibling = Sibling()
//	private var listOfUsersBetween = hashSetOf<Parent>()
	private var firstIndex = 0.toLong()
	private var lastIndex = 0.toLong()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initializeUI()
		initializeBoxForParent()
		initializeQueryForParent()
		initializeObserver()
//		initializeBoxForSiblings()
//		initializeQueryForSiblings()

		buttonClickListeners()
	}
	private fun initializeQueryForSiblings() {
		queryForSiblings = boxForSiblings.query().build()
//		boxForSiblings.query().between()
	}

	private fun buttonClickListeners() {
		buttonAdd.setOnClickListener {
			/*if(boxStoreForParent!!.isClosed){
				init(this*//*,false*//*)
				initializeBoxForParent()
				initializeQueryForParent()
				initializeObserver()
			}*/
			var strings = inputEditText.text.toString().split(" ")
			parent.id = 0
			parent.name = strings[0]
			boxForParent.attach(parent)
			strings = strings.subList(1,strings.size)
			strings.forEach {
				sibling.id = 0
				sibling.age = it.toLong()
				sibling.parent.target = parent
				parent.siblings.add(Sibling(0,it.toLong()))
			}
			boxForParent.put(parent)


		}
		buttonFind.setOnClickListener {
			/*if(boxStoreForSibling!!.isClosed){
				init(this,true)
				initializeBoxForSiblings()
				initializeQueryForSiblings()
			}*/
			val strings = inputEditText.text.toString().split(" ")
			/*box.all.forEach {
			}*/
			firstIndex = strings[0].toLong()
			lastIndex = strings[1].toLong()
//			boxForSiblings.
			/*val listOfSibling = boxForSiblings.query().between(Sibling_.age,firstIndex,lastIndex).build()

			listOfSibling.forEach {
				listOfUsersBetween.add(it.parent.target)
				*//*stringBuilder.append(it.parent.target.id.toString()
						+ ". "
						+ it.parent.target.name
						+ "\n")*//*
			}
			listOfUsersBetween.sortedBy {
				it.id
			}.*/
			val stringBuilder = StringBuilder()
			/////////////////////////////
			/*val builder = boxForParent
				.query()
			builder.backlink(Sibling_.parent)
				.between(Sibling_.age,firstIndex,lastIndex)
			val list = builder
				.build()
				.find()*/
			///////////////////////////
			val list = withQuery()
//			val list = withFilter()
			list.forEach {
				stringBuilder.append(it.id.toString()
						+ ". "
						+ it.name
						+ "\n")
			}


			textView.text = stringBuilder
			//After finishing work with the
//			init(this,false)
		}
	}
	private fun withQuery(): List<Parent>{
		val builder = boxForParent
			.query()
		builder.backlink(Sibling_.parent)
			.between(Sibling_.age,firstIndex,lastIndex)
		return builder
			.build()
			.find()

	}
	private fun withFilter(): List<Parent>{
		return boxForParent.all.filter { parent ->
			parent.siblings.hasA { sibling ->
				sibling.age!! in firstIndex..lastIndex
			}
		}
	}

	private fun initializeUI() {
		textView = findViewById(R.id.text_view)
		inputEditText = findViewById(R.id.input_edit_text)
		buttonAdd = findViewById(R.id.button_add_parent)
		buttonFind = findViewById(R.id.button_find_between)
	}
	private fun initializeObserver() {
		subscription = queryForParent.subscribe().observer { first ->
			val stringBuilder = StringBuilder()
			first.forEach { second ->
				stringBuilder.append(second.id.toString()+". "+second.name+"\n")
				second.siblings.forEach { third ->
					stringBuilder.append("\t"+third.id.toString()+". "+third.age+"\n")
				}
			}
			textView.text = stringBuilder
		}
	}
	private fun initializeQueryForParent() {
		queryForParent = boxForParent
			.query()
			/*.backlink(Sibling_.parent)
			.between(Sibling_.age,firstIndex,lastIndex)*/
			.build()
		val builder = boxForParent.query()
// ...which are linked from a Person named "Elmo"
		builder.backlink(Sibling_.parent).between(Sibling_.age,firstIndex,lastIndex)
		val sesameStreetsWithElmo = builder.build().find()
	}
	/*var filteredParents: List<Parent> = boxForParent.query()
		.
		.find()*/
	private fun initializeBoxForParent(){
//		boxStoreForSibling?.close()
		init(this/*,false*/)
		//if the second argument (boolean) is false it will open a (the) box for the parent else for the siblings
		boxForParent = boxStoreForParent!!.boxFor(Parent::class.java)
	}

	private fun initializeBoxForSiblings(){
//		boxStoreForParent?.close()
		ObjectBoxForSiblings.init(this/*,true*/)
		//if the second argument (boolean) is false it will open a (the) box for the parent else for the siblings
		boxForSiblings = boxStoreForSibling!!.boxFor(Sibling::class.java)
	}
}