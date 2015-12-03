package controllers

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import maps.Maps._
import maps.OrderMap
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BodyParsers, Controller}
import services.IOrderService

@Singleton
class OrderController @Inject() (orderService: IOrderService) extends Controller {

	def getOrders = Action.async {
		orderService.getOrders.map(data => Ok(Json.toJson(data)))
	}

	def upsertOrder = Action.async(BodyParsers.parse.json) { request =>
		val json = request.body.validate[OrderMap]
		json match {
			case JsError(e) => Future(BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(e))))
			case JsSuccess(order, _) =>
				orderService.upsertOrder(order).map { count =>
					if (count == 1) Ok(Json.obj("status" -> "success", "message" -> "order saved"))
					else BadRequest(Json.obj("status" -> "error", "message" -> "unknown error occurred"))
				}
		}
	}

}