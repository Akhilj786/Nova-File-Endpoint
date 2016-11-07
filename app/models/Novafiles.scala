package models;

import slick.driver.PostgresDriver.api._

class Novafiles (tag: Tag) extends Table[Novafile](tag, "novafiles") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def contenttype =column[String]("contenttype")

  def * = (id.?, name,contenttype) <> ((Novafile.apply _).tupled, Novafile.unapply _)
}

