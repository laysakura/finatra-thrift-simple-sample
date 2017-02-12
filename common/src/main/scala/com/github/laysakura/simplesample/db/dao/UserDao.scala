package com.github.laysakura.simplesample.db.dao

import com.github.laysakura.simplesample.db.models.User
import com.github.laysakura.simplesample.modules.QuillContext
import com.google.inject.{Inject, Singleton}
import com.twitter.util.Future

@Singleton
class UserDao @Inject() (
  ctx: QuillContext
) {
  import ctx._

  implicit private val insertMeta = ctx.insertMeta[User](_.createdAt, _.updatedAt)

  def find(userId: Long): Future[User] =
    run(quote(query[User]).
      filter(_.id == lift(userId))
    ).map(_.head)

  def insert(user: User): Future[Long] =
    run(quote(query[User].
      insert(lift(user)).returning(_.id)
    ))

  def deleteAll: Future[Unit] =
    run(quote(query[User].
      delete
    )).unit
}
