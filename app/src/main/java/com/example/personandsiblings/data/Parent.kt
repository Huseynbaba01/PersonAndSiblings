package com.example.personandsiblings.data

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class Parent(
	@Id var id: Long? = 0,
	var name:String? = null
){


	@Backlink(to = "parent")
	lateinit var siblings: ToMany<Sibling>
}
