package com.knol.db.repo

import org.scalatest.concurrent.ScalaFutures
import com.knol.db.connection.H2DBComponent
import org.scalatest.FunSuite
import org.scalatest.time.Seconds
import org.scalatest.time.Millis
import org.scalatest.time.Span

/**
 * @author sky
 */
class BankInfoRepositoryTest extends FunSuite with BankInfoRepository with H2DBComponent with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  test("Add new bank info") {
    val response = create(BankInfo("Goverment", 1000, 1))
    whenReady(response) { bankInfoId =>
      assert(bankInfoId === 2)
    }
  }

  test("Update  bank info ") {
    val response = update(BankInfo("goverment", 18989, 1, Some(1)))
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Delete  bank info  ") {
    val response = delete(1)
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Get bank info list") {
    val bankInfo = getAll()
    whenReady(bankInfo) { result =>
      assert(result === List(BankInfo("goverment", 10000, 1, Some(1))))
    }
  }

  test("Get bank and their info list") {
    val bankInfo = getBankWithInfo()
    whenReady(bankInfo) { result =>
      assert(result === List((Bank("SBI bank", Some(1)), BankInfo("goverment", 10000, 1, Some(1)))))
    }
  }

  test("Get all bank and  info list") {
    val bankInfo = getAllBankWithInfo()
    whenReady(bankInfo) { result =>
      assert(result === List((Bank("SBI bank", Some(1)), Some(BankInfo("goverment", 10000, 1, Some(1)))), (Bank("PNB bank", Some(2)), None)))
    }
  }

}
