package com.github.laysakura.simplesample.domain.values

import com.github.laysakura.simplesample.db.{models => m}

case class User(
  firstName: String,
  lastName: String,
  gender: Gender,
  age: Int
) {
  def getSirName: String = gender match {
    case Gender.Male => "Mr."
    case Gender.Female => "Ms."
  }
}

object User {
  def fromModel(x: m.User): User = User(
    x.firstName,
    x.lastName,
    Gender.fromGenderId(x.gender),
    x.age
  )
}
