object wordCount {

  def main(args: Array[String]): Unit = {

   def compareString(name:String,name1:String)={
     if(name==name1){
       0
     }else
       1
   }

def compareStringDesc(name:String,name1:String)= {
  if (name == name1) {
    0
  }
  else {
    -1
  }
}
  val res=stirngCompar("Aparna","Apuu",compareString)
    println(res)



    }


  def stirngCompar(name:String,name1:String,fn:(String,String)=>Int)={
    fn(name,name1)
  }}
