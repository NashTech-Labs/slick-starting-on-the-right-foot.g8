package com.knol.db.connection

import scala.slick.driver.JdbcProfile

trait DBComponent {
  
  val driver: JdbcProfile

  import driver.api._

  val db: Database

}
