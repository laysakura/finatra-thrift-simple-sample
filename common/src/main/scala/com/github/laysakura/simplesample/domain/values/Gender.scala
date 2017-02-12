package com.github.laysakura.simplesample.domain.values

sealed abstract class Gender(val genderId: Short)

object Gender {
  case object Male extends Gender(1)
  case object Female extends Gender(2)

  def fromGenderId(genderId: Short): Gender = genderId match {
    case 1 => Male
    case 2 => Female
    case _ => throw new NotImplementedError(s"Unknown genderId: $genderId")
  }
}
