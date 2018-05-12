import org.parboiled2.ParseError
import scala.util.{ Failure, Success }
import scala.collection.mutable.{ Map => MMap }

object replfun {

  def process(input: String): Unit = {
    println("You entered: " + input)
  }


  def main(args: Array[String]): Unit = {

    if (args.length > 0) {
      process(args mkString " ")
    } else {
      /*val reader = new ConsoleReader
      reader.setPrompt("> ")
      Iterator continually {
        reader.readLine()
      } takeWhile {
        _ != null
      } foreach {
        process
      }*/
    }

    //TerminalFactory.get.restore()

  }


}
