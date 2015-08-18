package com.knol.db.repo

import com.knol.db.connection.DBComponent
import scala.concurrent.Future
import com.knol.db.connection.MySqlDBComponent

trait BankInfoRepository extends BankInfoTable { this: DBComponent =>

  import driver.api._

  def create(bankInfo: BankInfo): Future[Int] = db.run { brandTableInfoAutoInc += bankInfo }

  def update(bankInfo: BankInfo): Future[Int] = db.run { brandInfoTableQuery.filter(_.id === bankInfo.id.get).update(bankInfo) }

  def getById(id: Int): Future[Option[BankInfo]] = db.run { brandInfoTableQuery.filter(_.id === id).result.headOption }

  def getAll(): Future[List[BankInfo]] = db.run { brandInfoTableQuery.to[List].result }

  def delete(id: Int): Future[Int] = db.run { brandInfoTableQuery.filter(_.id === id).delete }

  /**
   * Get bank and info using foreign key relationship
   */
  def getBankWithInfo(): Future[List[(Bank, BankInfo)]] =
    db.run {
      (for {
        info <- brandInfoTableQuery
        bank <- info.bank
      } yield (bank, info)).to[List].result
    }

  /**
   * Get all bank and their info.It is possible some bank do not have their product
   */
  def getAllBankWithInfo(): Future[List[(Bank, Option[BankInfo])]] =
    db.run {
      brandTableQuery.joinLeft(brandInfoTableQuery).on(_.id === _.bankId).to[List].result
    }
}

private[repo] trait BankInfoTable extends BankTable { this: DBComponent =>

  import driver.api._

  private[BankInfoTable] class BankInfoTable(tag: Tag) extends Table[BankInfo](tag, "bankinfo") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val owner = column[String]("owner")
    val bankId = column[Int]("bank_id")
    val branches = column[Int]("branches")
    def bank = foreignKey("bank_product_fk", bankId, brandTableQuery)(_.id)
    def * = (owner, branches, bankId, id.?) <> (BankInfo.tupled, BankInfo.unapply)

  }

  protected val brandInfoTableQuery = TableQuery[BankInfoTable]

  protected def brandTableInfoAutoInc = brandInfoTableQuery returning brandInfoTableQuery.map(_.id)

}

object BankInfoRepository extends BankInfoRepository with MySqlDBComponent

case class BankInfo(owner: String, branches: Int, bankId: Int, id: Option[Int] = None)
