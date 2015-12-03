package controllers

import play.api.mvc._

class AppController extends Controller {

	def index = Action(Ok(views.html.main.render()))

	def angular(any: Any) = index

}