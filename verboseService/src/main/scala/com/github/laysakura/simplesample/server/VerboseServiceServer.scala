package com.github.laysakura.simplesample.server

import com.github.laysakura.simplesample.controllers.VerboseServiceController
import com.github.laysakura.simplesample.modules.{ClientIdModule, QuillContextModule}
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift
import com.twitter.finatra.thrift.routing.ThriftRouter

class VerboseServiceServer extends ThriftServer
{
  private val THRIFT_PORT = 4000
  private val ADMIN_PORT = 4001

  override val name = "verbose-service-server"
  override val defaultFinatraThriftPort: String = s":$THRIFT_PORT"
  override def defaultHttpPort: Int = ADMIN_PORT

  override val modules = Seq(
    new ClientIdModule("VerboseServiceServer"),
    QuillContextModule
  )

  override def configureThrift(router: ThriftRouter) {
    router
      .filter[thrift.filters.LoggingMDCFilter]
      .filter[thrift.filters.TraceIdMDCFilter]
      .filter[thrift.filters.ThriftMDCFilter]
      .filter[thrift.filters.AccessLoggingFilter]
      .filter[thrift.filters.StatsFilter]
      .add[VerboseServiceController]
  }
}

object VerboseServiceServerMain extends VerboseServiceServer
