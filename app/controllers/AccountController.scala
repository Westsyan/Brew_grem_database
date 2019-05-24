package controllers

import javax.inject.Inject

import dao.accountDao
import models.Tables.AccountRow
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class AccountController@Inject()(accountdao : accountDao) extends Controller{


  def accountBefore = Action{implicit request=>
    Ok(views.html.account.accountPage())
  }

  def manageBefore = Action{implicit request=>
    Ok(views.html.account.accountMange())
  }

  def newPassword = Action{implicit request=>
    Ok(views.html.password.newpassword())
  }

  case class passwordData(password:String,newPassword:String)

  val passwordForm = Form(
    mapping(
      "password" -> text,
      "newPassword" -> text
    )(passwordData.apply)(passwordData.unapply)
  )

  def changePassword = Action.async{implicit request=>
    val data = passwordForm.bindFromRequest.get
    val account = request.session.get("userId").get
    accountdao.validPassword(account.toInt,data.password).map{x=>
      if(x.nonEmpty){
        Await.result(accountdao.changePassword(account.toInt,data.newPassword),Duration.Inf)
        Redirect(routes.HomeController.loginBefore()).flashing("info" -> "修改成功，请重新登录！")
      }else{
        Redirect(routes.AccountController.newPassword()).flashing("info" -> "密码错误，请重新输入")
      }
    }
  }

  def getAllAcount = Action.async{implicit request=>
    accountdao.getAllTeacher.map{x=>
      val row = x.map{y=>

        Json.obj("id" -> y.id,"account" -> y.account,"name"->y.name,"nums" -> "12")
      }
      Ok(Json.toJson(row))
    }
  }

  case class checkAccountData(account:String)

  val checkAccountForm = Form(
    mapping(
      "account" -> text
    )(checkAccountData.apply)(checkAccountData.unapply)
  )


  def checkAccount = Action.async{implicit request=>
    val account = checkAccountForm.bindFromRequest.get.account
    accountdao.getByAccount(account).map{x=>
      val valid = if(x.isEmpty){
        "true"
      }else{
        "false"
      }
      Ok(Json.obj("valid" -> valid))
    }
  }

  case class checkNameData(name:String)

  val checkNameForm = Form(
    mapping(
      "name" -> text
    )(checkNameData.apply)(checkNameData.unapply)
  )

  def checkName = Action.async{implicit request=>
    val name = checkNameForm.bindFromRequest.get.name
    accountdao.validTeacher(name).map{x=>
      val valid =  if(x.isEmpty){
        "true"
      }else{
        "false"
      }
      Ok(Json.obj("valid" -> valid))
    }
  }

  case class accountData(account:String,name:String,password:String)

  val accountForm = Form(
    mapping(
      "account" -> text,
      "name" -> text,
      "password" -> text
    )(accountData.apply)(accountData.unapply )
  )

  def addAccount = Action.async{implicit request=>
    val data = accountForm.bindFromRequest.get
    val row = AccountRow(0,data.account,data.password,2,data.name)
    accountdao.addAccount(row).map{x=>
      Ok(Json.toJson("success"))
    }
  }

  def resetPassword(id:Int) = Action.async{implicit request=>
    accountdao.resetPassword(id).map{x=>
      Ok(Json.toJson("success"))
    }
  }

  def deleteAccount(id:Int) = Action.async{implicit request=>
    accountdao.deleteAccount(id).map{x=>
      Ok(Json.toJson("success"))
    }
  }

}
