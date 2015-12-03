package maps

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
import slick.jdbc.GetResult

case class OrderMap(id: Option[Int], date: Option[DateTime], name: String, description: Option[String])

object Maps {

	import Converters._

	implicit val testOrderMapWrites: Writes[OrderMap] = new Writes[OrderMap] {
		def writes(map: OrderMap) = Json.obj(
			"id" -> map.id,
			"date" -> map.date,
			"name" -> map.name,
			"description" -> map.description
		)
	}

	implicit val testOrderMapReads: Reads[OrderMap] = (
		(JsPath \ "id").readNullable[Int] and
		(JsPath \ "date").readNullable[DateTime] and
		(JsPath \ "name").read[String] and
		(JsPath \ "description").readNullable[String]
	)(OrderMap)

	implicit val getOrderResult: GetResult[OrderMap] = GetResult(r => OrderMap(r.nextIntOption(), Option(r.nextString()), r.nextString(), r.nextStringOption()))

}