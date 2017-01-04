package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.Play.current

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

@Singleton
class HomeController @Inject() (ws: WSClient) extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }

  def echo() = Action.async { implicit request =>
    val url = "http://127.0.0.1:8080/v1/echo"
    val holder: WSRequest = ws.url(url)

    val response = holder.withRequestTimeout(1000.seconds).get
    response.map { resp =>
      resp.status match {
        case 200 => Ok(views.html.index(resp.json.toString))
        case _=> Ok(s"$url not reachable")
      }
    }
  }
}
