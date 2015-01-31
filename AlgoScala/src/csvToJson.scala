import org.apache.spark._
import java.io._
	def getExtension(name:String):String = {
	var ret = ""
		for(i<- name.lastIndexOf('.')+1 to name.length-1 ){
		ret += name.charAt(i);
		}
	return ret;
	}


/*
*	Use it in Spark-shell
*
*/
	def csvToJson( sc:SparkContext ,input: String, output: String ):Boolean ={ // Return true if a new file is created
	
	val file = sc.textFile(input)
	
	if(!getExtension(input).equals("csv")){// If input isn't a .csv file
		println("Error : the input file must be a CSV file -> Aborted")
		return false
	}
	
	val array = file.map(line => line.split(";")).collect()
	val writer = new PrintWriter(new File(output))
	
	
	for(i <- 1 to array.length -1){
		writer.write("{");
		for(j <- 0 to array(0).length -1){
		writer.write('"'+array(0)(j)+"\": \""+array(i)(j)+"\",\n");
		}
		writer.write("}");
	}
	writer.close()
	return true
	}	
	
	
