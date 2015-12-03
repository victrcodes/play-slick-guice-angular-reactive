package maps

import java.sql.Timestamp
import scala.language.implicitConversions
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{Reads, Writes}

object Converters {

	val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")

	implicit val dateReads = Reads.jodaDateReads("yyyy-MM-dd HH:mm:ss.SSSSSS")
	implicit val dateWrites = Writes.jodaDateWrites("yyyy-MM-dd HH:mm:ss.SSSSSS")

	implicit def timestampToDateTime(timestamp: Timestamp): DateTime = new DateTime(timestamp)
	implicit def dateTimeToTimestamp(dateTime: DateTime): Timestamp = new Timestamp(dateTime.getMillis)
	implicit def stringToDateTime(string: String): DateTime = DateTime.parse(string, formatter)

}