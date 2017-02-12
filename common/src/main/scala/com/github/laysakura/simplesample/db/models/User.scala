package com.github.laysakura.simplesample.db.models

import java.time.LocalDateTime

case class User(
  id: Long,
  firstName: String,
  lastName: String,
  gender: Short,
  age: Int,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime
)
