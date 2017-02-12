package com.github.laysakura.test.simplesample.server

import java.time.LocalDateTime

import com.github.laysakura.simplesample.db.dao.UserDao
import com.github.laysakura.simplesample.db.models.User
import com.github.laysakura.simplesample.domain.values.Gender
import com.github.laysakura.simplesample.idl.VerboseService
import com.github.laysakura.simplesample.modules._
import com.github.laysakura.simplesample.server.VerboseServiceServer
import com.twitter.finatra.thrift.{EmbeddedThriftServer, ThriftClient}
import com.twitter.inject.app.TestInjector
import com.twitter.inject.server.FeatureTest
import com.twitter.util.{Await, Future}
import org.scalatest.BeforeAndAfter

class EmbeddedVerboseServiceTest extends FeatureTest with BeforeAndAfter {
  override val injector = TestInjector(
    modules = Seq(
      new ClientIdModule("VerboseServiceTest"),
      VerboseServiceModule,
      VerboseServerModule,
      QuillContextModule
    )
  )

  override val server = new EmbeddedThriftServer(
    twitterServer = new VerboseServiceServer
  ) with ThriftClient

  lazy private val client = server.thriftClient[VerboseService[Future]]("EmbeddedVerboseServiceTest")

  private val dao = injector.instance[UserDao]
  val user = User(0, "Yui", "Aragaki", Gender.Female.genderId, 28, LocalDateTime.MIN, LocalDateTime.MIN)

  after {
    Await.result(dao.deleteAll)
  }

  test("successfully call echo() API") {
    val userId = Await.result(dao.insert(user))
    val res = Await.result(client.echo(userId, "hi :D"))
    res shouldBe "Hi Ms., you said: hi :D"
  }
}
