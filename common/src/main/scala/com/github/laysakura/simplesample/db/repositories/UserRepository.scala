package com.github.laysakura.simplesample.db.repositories

import com.github.laysakura.simplesample.db.dao.UserDao
import com.github.laysakura.simplesample.db.models.User
import com.google.inject.{Inject, Singleton}
import com.twitter.util.Future

@Singleton
class UserRepository @Inject() (
  userDao: UserDao
) {
  def getUser(userId: Long): Future[User] = userDao.find(userId)
}
