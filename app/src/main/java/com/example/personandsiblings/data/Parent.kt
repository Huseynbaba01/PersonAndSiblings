package com.example.personandsiblings.data

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class Parent(
	@Id val id: Long,
	val name:String
){
	@Backlink(to = "parent")
	lateinit var siblings: ToMany<Sibling>
}
