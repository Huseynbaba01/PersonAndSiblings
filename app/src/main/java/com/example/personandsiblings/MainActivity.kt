package com.example.personandsiblings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.personandsiblings.data.ObjectBox
import com.example.personandsiblings.data.ObjectBox.boxStore
import com.example.personandsiblings.data.ObjectBox.init
import com.example.personandsiblings.data.Parent
import com.example.personandsiblings.data.Sibling
import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription

class MainActivity : AppCompatActivity() {
	private lateinit var buttonAdd:Button
	private lateinit var buttonFind:Button
	private lateinit var inputEditText: EditText
	private lateinit var textView: TextView
	private lateinit var box: Box<Parent>
	private lateinit var query: Query<Parent>
	private lateinit var subscription: DataSubscription
	private var parent = Parent()
	private var sibling = Sibling()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initializeUI()
		initializeBox()
		initializeQuery()
		initializeObserver()
		buttonClickListeners()
	}
	private fun buttonClickListeners() {
		buttonAdd.setOnClickListener {
			val strings = inputEditText.text.toString().split(" ")
			parent.id = 0
			parent.name = strings[0]
			box.attach(parent)
			strings.forEach {
				sibling.id = 0
				sibling.name = it
				sibling.parent.target = parent
				parent.siblings.add(sibling)
			}


		}
		buttonFind.setOnClickListener {
			val string = inputEditText.text.toString()
		}
	}

	private fun initializeUI() {
		textView = findViewById(R.id.text_view)
		inputEditText = findViewById(R.id.input_edit_text)
		buttonAdd = findViewById(R.id.button_add_parent)
		buttonFind = findViewById(R.id.button_find_between)
	}

	private fun initializeObserver() {
		subscription = query.subscribe().observer { first ->
			val stringBuilder = StringBuilder()
			first.forEach { second ->
				stringBuilder.append(second.id.toString()+". "+second.name+"\n")
				second.siblings.forEach { third ->
					stringBuilder.append("\t"+third.id.toString()+". "+third.name+"\t")
				}
			}
			textView.text = stringBuilder
		}
	}

	private fun initializeQuery() {
		query = box
			.query()
			.build()
	}

	private fun initializeBox(){
		init(this)
		box = boxStore!!.boxFor(Parent::class.java)
	}
}