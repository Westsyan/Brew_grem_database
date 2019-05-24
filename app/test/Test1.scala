package test

import scala.collection.mutable.ArrayBuffer

/***
  * 聚类
  */

object Test1 {
  def main(args: Array[String]): Unit = {
    val seq = Array(("a",2),("c",1),("b",9),("d",6),("e",19),("f",22))

   val fil =  seq.zipWithIndex.flatMap{i=>
      seq.drop(i._2 + 1).map{j=>
        (i._1._1,j._1 ,math.abs(i._1._2-j._2))
      }
    }.filter(_._3 <= 5).map(x=>(x._1,x._2)).groupBy(_._1).toArray

    var size = fil.length
    val result = new  ArrayBuffer[Array[(String,String)]]()
    var filter = fil

    while(size > 0){
      val head = filter.head
      val ele = head._2.map(_._2)
      val r = filter.tail.filter(_._2.count(x=> ele.contains(x._1) || ele.contains(x._2)) > 0)
      result.append(head._2 ++ r.flatMap(_._2))
      filter = filter.tail.diff(r)
      size = filter.length
    }

    result.map{x=>

      println(x.mkString(","))
    }

  }
}
