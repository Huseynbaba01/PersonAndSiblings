package com.example.personandsiblings.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Sibling(
	@Id var id: Long? = 0,
	var age: Long? = 0

){
	lateinit var parent: ToOne<Parent>
}
