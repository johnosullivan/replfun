import org.parboiled2.ParseError
import scala.util.{ Failure, Success }
import scala.collection.mutable.{ Map => MMap }

import org.jline.reader.{Candidate, Completer, LineReader, ParsedLine}
import org.jline.utils.{AttributedStringBuilder, AttributedStyle}
import org.jline.reader.impl.{DefaultParser, LineReaderImpl}
import org.jline.reader.{Binding, EndOfFileException, LineReader, LineReaderBuilder, Macro, ParsedLine, Reference, UserInterruptException}
import org.jline.terminal.{Terminal, TerminalBuilder}

object replfun {

  val terminal: Terminal = TerminalBuilder.builder.system(true).build

  protected val parser: DefaultParser = new DefaultParser
  parser.setEofOnUnclosedQuote(true)

  protected val reader: LineReader = LineReaderBuilder.builder.terminal(terminal).parser(parser).build

  val prompt: String = new AttributedStringBuilder().style(AttributedStyle.DEFAULT).append("> ").toAnsi

  def main(args: Array[String]): Unit = {
    val mask: Character = null
    var running = true

    while (running) {
      var line: String =
      try {
        reader.readLine(prompt)
      } catch {
          case _: UserInterruptException =>
            println("Press Control-D to exit")
            ""
          case _: EndOfFileException => return
      }
      if (line != null) {
        line = line.trim

        if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
          running = false
        } else {
            //processLine(line)
        }
      }
    }
  }


}
