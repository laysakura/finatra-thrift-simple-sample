package com.github.laysakura.simplesample.modules

import com.google.inject.{Singleton, Provides}
import com.twitter.inject.TwitterModule
import io.getquill.{FinagleMysqlContext, SnakeCase}

class QuillContext extends FinagleMysqlContext[SnakeCase]("quillctx")

object QuillContextModule extends TwitterModule {
  @Singleton
  @Provides
  def providesQuillDbContext: QuillContext = new QuillContext
}
