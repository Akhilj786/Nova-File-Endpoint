package controllers

import javax.inject.Inject
import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import play.api.db.slick.DatabaseConfigProvider

import play.api.libs.json._
import play.api.mvc._
import play.api.libs.ws._

import models.Novafile
import models.Novafiles

class Application @Inject()(ws: WSClient,dbConfigProvider: DatabaseConfigProvider) extends Controller {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val messages = TableQuery[Novafiles]
  def findQuery(id: Int) = messages.filter(_.id === id)

  /*
  * Default endpoint which gives all nova files.
  */
  def index = Action.async {
    val message = dbConfig.db.run(messages.result)
    message.map(msg => Ok(Json.obj("status" -> "Ok", "messages" -> Json.toJson(msg))))
  }


 /*
  * Phase 1 endpoint, which grabs files metadata and pass it to Phase2
  */
  def Phase1 = Action.async(parse.multipartFormData) { request =>

      request.body.file("fileUpload").map { video =>
      val videoFilename = video.filename
      val contentType = video.contentType.get

      
      val data = Json.obj("Name" -> videoFilename,
                          "ContentType" -> contentType)
      ws.url("http://localhost:9000/").withHeaders("Content-Type" -> "application/json").post(data)
      }.getOrElse {
      Redirect(routes.UpFiles.index)
      }
    Future(Ok("File uploaded"))
  }

  /*
  * Phase2 : Endpoint to put file metadata into Postgres DB.
  */
  def Phase2 = Action.async(BodyParsers.parse.json) { request => 
    val message = request.body.validate[Novafile]
    println("Inside Create"+message)
    message.fold(
      errors => Future(BadRequest(Json.obj(
          "status" -> "Parsing message failed",
          "error" -> JsError.toJson(errors)))),
      message =>{
        println("Inside messages") 
        dbConfig.db.run(messages returning messages += message).map(m =>
            Ok(Json.obj( "status" -> "Success", "message" -> Json.toJson(m)))
        )
        }
    )
  }

 /*
  * Endpoint to show individual file metadata.
  */
  def show(id: Int) = Action.async {
    val message = dbConfig.db.run(findQuery(id).result.head)
    message.map(msg => Ok(Json.obj("status" -> "Ok", "message" -> Json.toJson(msg))))
  }

 /*
  * Endpoint to update individual file metadata.
  */
  def update(id: Int) = Action.async(BodyParsers.parse.json) { request =>
    val message = request.body.validate[Novafile]
    message.fold(
      errors => Future(BadRequest(Json.obj(
          "status" -> "Message update failed",
          "error" -> JsError.toJson(errors)))),
      message => {
        dbConfig.db.run(findQuery(id).update(message))
        Future(Ok(Json.obj("status" -> "Ok", "message" -> Json.toJson(message))))
      }
    )
  }

 /*
  * Endpoint to delete individual file metadata.
  */
  def delete(id: Int) = Action.async {
    dbConfig.db.run(findQuery(id).delete).map(m => Ok(Json.obj("status" -> "Ok")))
  }

}
