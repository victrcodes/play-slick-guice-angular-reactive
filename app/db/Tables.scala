package db
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Order.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Order
   *  @param id Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param date Database column DATE SqlType(TIMESTAMP)
   *  @param name Database column NAME SqlType(VARCHAR)
   *  @param description Database column DESCRIPTION SqlType(VARCHAR) */
  case class OrderRow(id: Int, date: java.sql.Timestamp, name: String, description: Option[String])
  /** GetResult implicit for fetching OrderRow objects using plain SQL queries */
  implicit def GetResultOrderRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[String], e3: GR[Option[String]]): GR[OrderRow] = GR{
    prs => import prs._
    OrderRow.tupled((<<[Int], <<[java.sql.Timestamp], <<[String], <<?[String]))
  }
  /** Table description of table order. Objects of this class serve as prototypes for rows in queries. */
  class Order(_tableTag: Tag) extends Table[OrderRow](_tableTag, "order") {
    def * = (id, date, name, description) <> (OrderRow.tupled, OrderRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(date), Rep.Some(name), description).shaped.<>({r=>import r._; _1.map(_=> OrderRow.tupled((_1.get, _2.get, _3.get, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column DATE SqlType(TIMESTAMP) */
    val date: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("DATE")
    /** Database column NAME SqlType(VARCHAR) */
    val name: Rep[String] = column[String]("NAME")
    /** Database column DESCRIPTION SqlType(VARCHAR) */
    val description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")
  }
  /** Collection-like TableQuery object for table Order */
  lazy val Order = new TableQuery(tag => new Order(tag))
}
