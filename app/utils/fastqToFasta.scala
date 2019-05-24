package utils

import java.io.File

import org.apache.commons.io.FileUtils

import scala.collection.JavaConverters._

object fastqToFasta {

  def fqToFa(fastq:String,fasta:String) = {
    val fqFile = FileUtils.readLines(new File(fastq)).asScala
    val fq = fqFile.map(_.replaceAll("@",">")).grouped(4).map(_.take(2).mkString("\n")).mkString("\n")
    FileUtils.writeStringToFile(new File(fasta),fq)
  }


  def main(args: Array[String]): Unit = {
    fqToFa("E:\\Desktop\\igv.js-master\\data/1.fastq","E:\\Desktop\\igv.js-master\\data/1.fasta")
  }

}
