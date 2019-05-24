package controllers

import javax.inject.Inject

import dao.accountDao
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global


class HomeController @Inject()(accountdao: accountDao) extends Controller {


  def loginBefore = Action {implicit request=>
    Ok(views.html.login.loginPage()).withNewSession
  }

  case class loginData(account: String, password: String)

  val loginForm = Form(
    mapping(
      "account" -> text,
      "password" -> text
    )(loginData.apply)(loginData.unapply)
  )

  def login = Action.async{ implicit request =>
    val data = loginForm.bindFromRequest.get
    accountdao.validLogin(data.account, data.password).map { x =>
      if (x.isEmpty) {
        Redirect(routes.HomeController.loginBefore()).flashing("info" -> "账号或密码错误！").withNewSession
      } else {
        Redirect(routes.DiversityController.home()).withNewSession.withSession("userId" -> x.head.id.toString)
      }
    }
  }




}
