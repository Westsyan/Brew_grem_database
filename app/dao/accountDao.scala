package dao

import javax.inject.Inject

import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

class accountDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  def addAccount(row:AccountRow) : Future[Unit] = {
    db.run(Account += row).map(_=>())
  }

  def validLogin(account:String,password:String) : Future[Seq[AccountRow]] ={
    db.run(Account.filter(_.account === account).filter(_.password === password).result)
  }

  def getByAccount(account:String) : Future[Seq[AccountRow]] = {
    db.run(Account.filter(_.account === account).result)
  }

  def getAllTeacher : Future[Seq[AccountRow]] = {
    db.run(Account.filter(_.permission === 2).result)
  }

  def validTeacher(name:String) : Future[Seq[Int]] ={
    db.run(Account.filter(_.name === name).map(_.id).result)
  }

  def getByName(name:String)  : Future[Int] = {
    db.run(Account.filter(_.name === name).map(_.id).result.head)
  }

  def getById(id:Int)  : Future[String] = {
    db.run(Account.filter(_.id === id).map(_.name).result.head)
  }

  def getAllById(id:Int)  : Future[AccountRow] = {
    db.run(Account.filter(_.id === id).result.head)
  }

  def resetPassword(id:Int) : Future[Unit] ={
    db.run(Account.filter(_.id === id).map(_.password).update("123456")).map(_=>())
  }

  def deleteAccount(id:Int) : Future[Unit] = {
    db.run(Account.filter(_.id === id).delete).map(_=>())
  }

  def validPassword(id:Int,password:String) : Future[Seq[AccountRow]] = {
    db.run(Account.filter(_.id === id).filter(_.password === password).result)
  }

  def changePassword(id:Int,password:String) : Future[Unit] = {
    db.run(Account.filter(_.id === id).map(_.password).update(password)).map(_=>())
  }
}
