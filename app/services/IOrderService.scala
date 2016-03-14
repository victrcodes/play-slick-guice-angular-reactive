package services

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.google.inject.ImplementedBy
import db.Tables._
import maps.Converters._
import maps.OrderMap
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

@ImplementedBy(classOf[OrderService])
trait IOrderService {

	def getOrders: Future[Seq[OrderMap]]

	def upsertOrder(order: OrderMap): Future[Int]

}

class OrderService @Inject() (dbConfigProvider: DatabaseConfigProvider) extends IOrderService {

	val dbConfig = dbConfigProvider.get[JdbcProfile]
	val db = dbConfig.db
	import dbConfig.driver.api._

	def getOrders: Future[Seq[OrderMap]] = db.run {
		Order.result.map(res => res.map(row => OrderMap(Some(row.id), Some(row.date), row.name, row.description)))
	}

	def upsertOrder(order: OrderMap): Future[Int] = db.run {
		Order.insertOrUpdate(OrderRow(order.id.getOrElse(0), order.date.getOrElse[DateTime](DateTime.now()), order.name, order.description))
	}

}