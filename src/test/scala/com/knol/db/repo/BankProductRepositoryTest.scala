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
class BankProductRepositoryTest extends FunSuite with BankProductRepository with H2DBComponent with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  test("Add new Product ") {
    val response = create(BankProduct("car loan", 1))
    whenReady(response) { productId =>
      assert(productId === 3)
    }
  }

  test("Update bank product ") {
    val response = update(BankProduct("Home Loan", 1, Some(1)))
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

  test("Get product list") {
    val products = getAll()
    whenReady(products) { result =>
      assert(result === List(BankProduct("home loan", 1, Some(1)), BankProduct("eduction loan", 1, Some(2))))
    }
  }

  test("Get bank and their product list") {
    val bankProduct = getBankWithProduct()
    whenReady(bankProduct) { result =>
      assert(result === List((Bank("SBI bank", Some(1)), BankProduct("home loan", 1, Some(1))), (Bank("SBI bank", Some(1)), BankProduct("eduction loan", 1, Some(2)))))
    }
  }

  test("Get all bank and  product list") {
    val bankProduct = getAllBankWithProduct()
    whenReady(bankProduct) { result =>
      assert(result === List((Bank("SBI bank", Some(1)), Some(BankProduct("home loan", 1, Some(1)))), (Bank("SBI bank", Some(1)), Some(BankProduct("eduction loan", 1, Some(2)))), (Bank("PNB bank", Some(2)), None)))
    }
  }
}
