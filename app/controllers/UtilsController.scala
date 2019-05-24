package controllers

import java.io.File
import java.nio.file.Files
import javax.inject.Inject

import play.api.mvc.{Action, Controller}
import utils.Utils

class UtilsController @Inject()() extends Controller {

  def getImageByPhotoId(id:String,name:String) = Action { implicit request =>
    val ifModifiedSinceStr = request.headers.get(IF_MODIFIED_SINCE)

    val path = Utils.path + "/images/" + id  + name

    val file = new File(path)

    val lastModifiedStr = file.lastModified().toString
    val MimeType = "image/jpg"
    val byteArray = Files.readAllBytes(file.toPath)
    if (ifModifiedSinceStr.isDefined && ifModifiedSinceStr.get == lastModifiedStr) {
      NotModified
    } else {
      Ok(byteArray).as(MimeType).withHeaders(LAST_MODIFIED -> file.lastModified().toString)
    }
  }



  def downloadExample = Action { implicit request =>
    val filename = "example.xlsx"
    Ok.sendFile(new File(Utils.path + "/example/example.xlsx")).withHeaders(
      //缓存
      CACHE_CONTROL -> "max-age=3600",
      CONTENT_DISPOSITION -> ("attachment; filename=" + filename),
      CONTENT_TYPE -> "application/x-download"
    )
  }

}
