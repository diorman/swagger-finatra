package com.twitter.finatra.http

import com.github.xiaodongw.swagger.finatra.FinatraSwagger
import com.twitter.finatra.http.routing.AdminIndexInfo
import io.swagger.models.{Operation, Swagger}

/**
  * To work around the accessibility of RouteDSL, this class is in "com.twitter.finatra.http" package
  */
object SwaggerRouteDSL {
  implicit def convertToSwaggerRouteDSL(dsl: RouteDSL)(implicit swagger: Swagger): SwaggerRouteDSL = new SwaggerRouteDSLWapper(dsl)(swagger)
}

trait SwaggerRouteDSL {
  implicit protected val swagger: Swagger
  protected val dsl: RouteDSL

  def postWithDoc[RequestType: Manifest, ResponseType: Manifest](route: String, name: String = "", admin: Boolean = false, adminIndexInfo: Option[AdminIndexInfo] = None)
                                                                (doc: Operation => Unit)
                                                                (callback: RequestType => ResponseType): Unit = {
    registerOperation(route, "post")(doc)
    dsl.post(route, name, admin, adminIndexInfo)(callback)
  }

  def getWithDoc[RequestType: Manifest, ResponseType: Manifest](route: String, name: String = "", admin: Boolean = false, adminIndexInfo: Option[AdminIndexInfo] = None)
                                                               (doc: Operation => Unit)
                                                               (callback: RequestType => ResponseType): Unit = {
    registerOperation(route, "get")(doc)
    dsl.get(route, name, admin, adminIndexInfo)(callback)
  }

  def putWithDoc[RequestType: Manifest, ResponseType: Manifest](route: String, name: String = "", admin: Boolean = false, adminIndexInfo: Option[AdminIndexInfo] = None)
                                                               (doc: Operation => Unit)
                                                               (callback: RequestType => ResponseType): Unit = {
    registerOperation(route, "put")(doc)
    dsl.put(route, name, admin, adminIndexInfo)(callback)
  }

  def patchWithDoc[RequestType: Manifest, ResponseType: Manifest](route: String, name: String = "", admin: Boolean = false, adminIndexInfo: Option[AdminIndexInfo] = None)
                                                                 (doc: Operation => Unit)
                                                                 (callback: RequestType => ResponseType): Unit = {
    registerOperation(route, "patch")(doc)
    dsl.patch(route, name, admin, adminIndexInfo)(callback)
  }

  def headWithDoc[RequestType: Manifest, ResponseType: Manifest](route: String, name: String = "", admin: Boolean = false, adminIndexInfo: Option[AdminIndexInfo] = None)
                                                                (doc: Operation => Unit)
                                                                (callback: RequestType => ResponseType): Unit = {
    registerOperation(route, "head")(doc)
    dsl.head(route, name, admin, adminIndexInfo)(callback)
  }

  def deleteWithDoc[RequestType: Manifest, ResponseType: Manifest](route: String, name: String = "", admin: Boolean = false, adminIndexInfo: Option[AdminIndexInfo] = None)
                                                                  (doc: Operation => Unit)
                                                                  (callback: RequestType => ResponseType): Unit = {
    registerOperation(route, "delete")(doc)
    dsl.delete(route, name, admin, adminIndexInfo)(callback)
  }

  def optionsWithDoc[RequestType: Manifest, ResponseType: Manifest](route: String, name: String = "", admin: Boolean = false, adminIndexInfo: Option[AdminIndexInfo] = None)
                                                                   (doc: Operation => Unit)
                                                                   (callback: RequestType => ResponseType): Unit = {
    registerOperation(route, "options")(doc)
    dsl.options(route, name, admin, adminIndexInfo)(callback)
  }

  private def registerOperation(path: String, method: String)(doc: Operation => Unit): Unit = {
    val op = new Operation
    doc(op)

    FinatraSwagger.convertToFinatraSwagger(swagger).registerOperation(path, method, op)
  }
}

private class SwaggerRouteDSLWapper(protected val dsl: RouteDSL)(implicit protected val swagger: Swagger) extends SwaggerRouteDSL
