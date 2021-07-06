package com.example.personandsiblings.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Sibling(
	@Id val id: Long,
	val name: String
){
	lateinit var parent: ToOne<Parent>
}
