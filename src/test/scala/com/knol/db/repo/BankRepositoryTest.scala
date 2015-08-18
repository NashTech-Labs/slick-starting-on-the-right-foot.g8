package com.knol.db.repo

import org.scalatest.FunSuite
import com.knol.db.connection.H2DBComponent
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{ Millis, Seconds, Span }

/**
 * @author sky
 */
class BankRepositoryTest extends FunSuite with BankRepository with H2DBComponent with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  test("Add new bank ") {
    val response = create(Bank("ICICI bank"))
    whenReady(response) { bankId =>
      assert(bankId === 3)
    }
  }

  test("Update  SBI bank  ") {
    val response = update(Bank("SBI Bank", Some(1)))
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Delete SBI bank  ") {
    val response = delete(2)
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Get bank list") {
    val bankList = getAll()
    whenReady(bankList) { result =>
      assert(result === List(Bank("SBI bank", Some(1)), Bank("PNB bank", Some(2))))
    }
  }

}