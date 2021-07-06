package com.example.personandsiblings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.personandsiblings.data.ObjectBox.boxStoreForParent
import com.example.personandsiblings.data.ObjectBox.boxStoreForSibling
import com.example.personandsiblings.data.ObjectBox.init
import com.example.personandsiblings.data.Parent
import com.example.personandsiblings.data.Parent_
import com.example.personandsiblings.data.Sibling
import com.example.personandsiblings.data.Sibling_
import io.objectbox.Box
import io.objectbox.kotlin.inValues
import io.objectbox.query.Query
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
	private var listOfUsersBetween = hashSetOf<Parent>()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initializeUI()
		initializeBoxForParent()
		initializeQueryForParent()
		initializeObserver()

		buttonClickListeners()
	}

	private fun initializeQueryForSiblings() {
		queryForSiblings = boxForSiblings.query().build()
//		boxForSiblings.query().between()
	}

	private fun buttonClickListeners() {
		buttonAdd.setOnClickListener {
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
			initializeBoxForSiblings()
			initializeQueryForSiblings()
			val strings = inputEditText.text.toString().split(" ")
			/*box.all.forEach {
			}*/
			val firstIndex = strings[0].toLong()
			val lastIndex = strings[1].toLong()
//			boxForSiblings.
			var listOfSibling = boxForSiblings.query().between(Sibling_.age,firstIndex,lastIndex).build()
			val stringBuilder = StringBuilder()
			listOfSibling.forEach {
				listOfUsersBetween.add(it.parent.target)
				/*stringBuilder.append(it.parent.target.id.toString()
						+ ". "
						+ it.parent.target.name
						+ "\n")*/
			}
			listOfUsersBetween.sortedBy {
				it.id
			}.toList().forEach {
				stringBuilder.append(it.id.toString()
						+ ". "
						+ it.name
						+ "\n")
			}

			textView.text = stringBuilder
			initializeBoxForParent()
			initializeQueryForParent()
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
			.build()
	}

	private fun initializeBoxForParent(){
//		boxStoreForSibling?.close()
		init(this,false)
		boxForParent = boxStoreForParent!!.boxFor(Parent::class.java)
	}

	private fun initializeBoxForSiblings(){
//		boxStoreForParent?.close()
		init(this,true)
		boxForSiblings = boxStoreForSibling!!.boxFor(Sibling::class.java)
	}
}