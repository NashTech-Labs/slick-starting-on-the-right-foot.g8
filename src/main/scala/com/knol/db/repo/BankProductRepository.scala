package com.knol.db.repo

import com.knol.db.connection.DBComponent
import scala.concurrent.Future
import com.knol.db.connection.MySqlDBComponent

trait BankProductRepository extends BankProductTable { this: DBComponent =>

  import driver.api._

  def create(bankProduct: BankProduct): Future[Int] = db.run { brandProductTableAutoInc += bankProduct }

  def update(bankProduct: BankProduct): Future[Int] = db.run { brandProductTableQuery.filter(_.id === bankProduct.id.get).update(bankProduct) }

  def getById(id: Int): Future[Option[BankProduct]] = db.run { brandProductTableQuery.filter(_.id === id).result.headOption }

  def getAll(): Future[List[BankProduct]] = db.run { brandProductTableQuery.to[List].result }

  def delete(id: Int): Future[Int] = db.run { brandProductTableQuery.filter(_.id === id).delete }

  /**
   * Get bank and product using foreign key relationship
   */
  def getBankWithProduct(): Future[List[(Bank, BankProduct)]] =
    db.run {
      (for {
        product <- brandProductTableQuery
        bank <- product.bank
      } yield (bank, product)).to[List].result
    }

  /**
   * Get all bank and their product.It is possible some bank do not have their product
   */
  def getAllBankWithProduct(): Future[List[(Bank, Option[BankProduct])]] =
    db.run {
      brandTableQuery.joinLeft(brandProductTableQuery).on(_.id === _.bankId).to[List].result
    }

}

private[repo] trait BankProductTable extends BankTable { this: DBComponent =>

  import driver.api._

  private[BankProductTable] class BankProductTable(tag: Tag) extends Table[BankProduct](tag, "bankproduct") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")
    val bankId = column[Int]("bank_id")
    def bank = foreignKey("bank_product_fk", bankId, brandTableQuery)(_.id)
    def * = (name, bankId, id.?) <> (BankProduct.tupled, BankProduct.unapply)

  }

  protected val brandProductTableQuery = TableQuery[BankProductTable]

  protected def brandProductTableAutoInc = brandProductTableQuery returning brandProductTableQuery.map(_.id)

}

object BankProductRepository extends BankProductRepository with MySqlDBComponent

case class BankProduct(name: String, bankId: Int, id: Option[Int] = None)
