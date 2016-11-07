package models;

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Novafile (
  id: Option[Int],
  Name: String,
  ContentType: String
)

object Novafile {
  implicit val messageReads: Reads[Novafile] = (
    (JsPath \ "id").readNullable[Int] and
    (JsPath \ "Name").read[String] and
    (JsPath \ "ContentType").read[String]  
  )(Novafile.apply _)


  implicit val messageWrites: Writes[Novafile] = (
    (JsPath \ "id").writeNullable[Int] and
    (JsPath \ "Name").write[String] and 
    (JsPath \ "ContentType").write[String]
  )(unlift(Novafile.unapply _))
}

