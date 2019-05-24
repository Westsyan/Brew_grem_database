package controllers

import com.google.inject.Inject
import dao.sampleDao
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

class DiversityController@Inject()(sampledao:sampleDao) extends Controller{



  def home = Action{implicit request=>
    Ok(views.html.home.homepage())
  }

  def toDiversity(proId:Int) = Action.async{implicit request=>
    sampledao.getAllSample(request.session.get("userId").get.toInt,proId).map{x=>
      if(x.length >= 2){
        Redirect(routes.DiversityController.dataBefore()).withSession(request.session + ("proId" -> proId.toString))
      }else{
        Redirect(routes.DiversityController.before16s()).withSession(request.session + ("proId" -> proId.toString))
      }
    }
  }

  def before16s = Action{implicit request=>
    Ok(views.html.diversity.diversity16s())
  }

  def dataBefore = Action{implicit request=>
    Ok(views.html.diversity.data())
  }

  def otuBefore = Action{implicit request=>
    Ok(views.html.diversity.otuPage())
  }

  def taskBefore = Action{implicit request=>
    Ok(views.html.diversity.task())
  }



}
