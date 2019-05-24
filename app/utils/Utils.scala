package utils

import java.io.{File, FileInputStream}
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import org.apache.poi.ss.usermodel.{Cell, DateUtil}
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.joda.time.DateTime
import play.api.mvc.{AnyContent, Request}

import scala.collection.mutable.ArrayBuffer



case class bacteriaData(number: String, initials: String, glycerinPosition: String, ampoulePosition: String,
                        preservedForm: String, labNumber: String, bacteriaNameCh: String, bacteriaNameEn: String,
                        resAndCon: String, cultureMedium: String, cultureCondition: String, source: String,
                        apply: String, worksCited: String, preservedPeople: String, teacher: String,
                        preservedDate: String, isSercet: String, primerSeq: String, seq16s: String, seq18s: String,
                        seqIndex: String, bacteriaImages: String, eleImages: String, ampouleNums: String,
                        glycerinNotes: String, notes: String, isGmo: String, goverName: String, ggcc: String,
                        primer: String, length: String, tranSeq: String, outName: String, outSeq: String)

/**
  * Created by yz on 2017/6/16.
  */

object Utils {

  def random: String = {
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
    var value = ""
    for (i <- 0 to 20) {
      val ran = Math.random() * 62
      val char = source.charAt(ran.toInt)
      value += char
    }
    value
  }

  //处理excel表格文件
  def xlsx2Lines(xlsxFile: File) = {
    val is = new FileInputStream(xlsxFile.getAbsolutePath)
    val xssfWorkbook = new XSSFWorkbook(is)
    val xssfSheet = xssfWorkbook.getSheetAt(0)
    val lines = ArrayBuffer[String]()
    for (i <- 0 to xssfSheet.getLastRowNum) {
      val columns = ArrayBuffer[String]()
      val xssfRow = xssfSheet.getRow(i)
      for (j <- 0 until xssfSheet.getRow(0).getLastCellNum) {
        val cell = xssfRow.getCell(j)
        var value = "/"
        if (cell != null) {
          cell.getCellType match {
            case Cell.CELL_TYPE_STRING =>
              value = cell.getStringCellValue
            case Cell.CELL_TYPE_NUMERIC =>
              if (DateUtil.isCellDateFormatted(cell)) {
                val dateFormat = new SimpleDateFormat("yyyy/MM/dd")
                value = dateFormat.format(cell.getDateCellValue)
              } else {
                val doubleValue = cell.getNumericCellValue
                value = if (doubleValue == doubleValue.toInt) {
                  doubleValue.toInt.toString
                } else doubleValue.toString
              }
            case Cell.CELL_TYPE_BLANK =>
              value = "/"
            case _ =>
              value = "/"
          }
        }
        columns += value

      }
      val line = columns.mkString("\t")
      lines += line
    }
    xssfWorkbook.close()
    lines.filter(StringUtils.isNotBlank(_))
  }



  def dealWithTable(table: String): String = {
    val tr = table.split("\n")
    val head = "<thead><tr><th>" + tr.head.split("\t").mkString("</th><th>") + "</th></tr></thead>"

    val body = "<tbody><tr><td>" +
      tr.drop(1).map(_.split("\t").mkString("</td><td>")).mkString("</td></tr><tr><td>") + "</td></tr></tbody>"

    head + body
  }


  def htmlPath(s_HtmlPath: String): String = {
    val htmlPath = s_HtmlPath.replaceAll("\\\\", "/").split("/").dropRight(1).mkString("/") + "/new.html"
    htmlPath
  }

  def srcPath(tmpPath: String): String = {
    val file = tmpPath.replaceAll("\\\\", "/").split("/").last.split('.').head + ".files"
    file
  }

  def getTime(startTime: Long) = {
    val endTime = System.currentTimeMillis()
    (endTime - startTime) / 1000.0
  }


  def createIfNoExisit(path:String) = {
    if(!new File(path).exists()){
      new File(path).mkdirs()
    }
  }

  def deleteDirectory(direcotry: File) = {
    try {
      FileUtils.deleteDirectory(direcotry)
    } catch {
      case _ =>
    }
  }

  def deleteDirectory(tmpDir: String): Unit = {
    val direcotry = new File(tmpDir)
    deleteDirectory(direcotry)
  }

  def isDouble(value: String): Boolean = {
    try {
      value.toDouble
    } catch {
      case _: Exception =>
        return false
    }
    true
  }

  def refer(request: Request[AnyContent]): String = {
    val header = request.headers.toMap
    header.filter(_._1 == "Referer").map(_._2).head.head
  }

  def date: DateTime = {
    val now = new Date()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val time = dateFormat.format(now)
    val date = new DateTime(dateFormat.parse(time).getTime)
    return date
  }

  def date2: String = {
    val now = new Date()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    val date = dateFormat.format(now)
    date
  }


  def outPath(userId:Int,proId:Int,sampleId:Int) : String ={
    val out = path + "/data/" + userId + "/" + proId + "/data/" + sampleId
    out
  }

  def otuPath(userId:Int,proId:Int,taskId:Int) : String ={
    val out = path + "/data/" + userId + "/" + proId + "/otu/" + taskId
    out
  }

  val windowsPath = "F:\\database\\Brew_grem_database"
  val linuxPath = "/home/user/projects/Brew_grem_database"
  val path = {
    if (new File(windowsPath).exists()) windowsPath else linuxPath
  }

  val toolPath ={
    if (new File(windowsPath).exists()) windowsPath+"/tools" else linuxPath+"/tools"
  }

  val suffix = {
    if (new File(windowsPath).exists()) ".exe" else " "

  }

  val tmpPath = {
    if (new File(windowsPath).exists()) windowsPath + "/tmp" else linuxPath + "/tmp"
  }

}
