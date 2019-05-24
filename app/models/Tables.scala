package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import com.github.tototoshi.slick.MySQLJodaSupport._
  import org.joda.time.DateTime
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Account.schema ++ Otu.schema ++ Sample.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Account
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param account Database column account SqlType(VARCHAR), Length(255,true)
   *  @param password Database column password SqlType(VARCHAR), Length(255,true)
   *  @param permission Database column permission SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true) */
  final case class AccountRow(id: Int, account: String, password: String, permission: Int, name: String)
  /** GetResult implicit for fetching AccountRow objects using plain SQL queries */
  implicit def GetResultAccountRow(implicit e0: GR[Int], e1: GR[String]): GR[AccountRow] = GR{
    prs => import prs._
    AccountRow.tupled((<<[Int], <<[String], <<[String], <<[Int], <<[String]))
  }
  /** Table description of table account. Objects of this class serve as prototypes for rows in queries. */
  class Account(_tableTag: Tag) extends profile.api.Table[AccountRow](_tableTag, Some("brew_grem_database"), "account") {
    def * = (id, account, password, permission, name) <> (AccountRow.tupled, AccountRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(account), Rep.Some(password), Rep.Some(permission), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> AccountRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column account SqlType(VARCHAR), Length(255,true) */
    val account: Rep[String] = column[String]("account", O.Length(255,varying=true))
    /** Database column password SqlType(VARCHAR), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
    /** Database column permission SqlType(INT) */
    val permission: Rep[Int] = column[Int]("permission")
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))

    /** Primary key of Account (database name account_PK) */
    val pk = primaryKey("account_PK", (id, account))
  }
  /** Collection-like TableQuery object for table Account */
  lazy val Account = new TableQuery(tag => new Account(tag))

  /** Entity class storing rows of table Otu
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param otuname Database column otuname SqlType(VARCHAR), Length(255,true)
   *  @param accountid Database column accountid SqlType(INT)
   *  @param projectid Database column projectid SqlType(INT)
   *  @param createdata Database column createdata SqlType(DATETIME)
   *  @param state Database column state SqlType(INT) */
  final case class OtuRow(id: Int, otuname: String, accountid: Int, projectid: Int, createdata: DateTime, state: Int)
  /** GetResult implicit for fetching OtuRow objects using plain SQL queries */
  implicit def GetResultOtuRow(implicit e0: GR[Int], e1: GR[String], e2: GR[DateTime]): GR[OtuRow] = GR{
    prs => import prs._
    OtuRow.tupled((<<[Int], <<[String], <<[Int], <<[Int], <<[DateTime], <<[Int]))
  }
  /** Table description of table otu. Objects of this class serve as prototypes for rows in queries. */
  class Otu(_tableTag: Tag) extends profile.api.Table[OtuRow](_tableTag, Some("brew_grem_database"), "otu") {
    def * = (id, otuname, accountid, projectid, createdata, state) <> (OtuRow.tupled, OtuRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(otuname), Rep.Some(accountid), Rep.Some(projectid), Rep.Some(createdata), Rep.Some(state)).shaped.<>({r=>import r._; _1.map(_=> OtuRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column otuname SqlType(VARCHAR), Length(255,true) */
    val otuname: Rep[String] = column[String]("otuname", O.Length(255,varying=true))
    /** Database column accountid SqlType(INT) */
    val accountid: Rep[Int] = column[Int]("accountid")
    /** Database column projectid SqlType(INT) */
    val projectid: Rep[Int] = column[Int]("projectid")
    /** Database column createdata SqlType(DATETIME) */
    val createdata: Rep[DateTime] = column[DateTime]("createdata")
    /** Database column state SqlType(INT) */
    val state: Rep[Int] = column[Int]("state")

    /** Primary key of Otu (database name otu_PK) */
    val pk = primaryKey("otu_PK", (id, accountid))
  }
  /** Collection-like TableQuery object for table Otu */
  lazy val Otu = new TableQuery(tag => new Otu(tag))

  /** Entity class storing rows of table Sample
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param sample Database column sample SqlType(VARCHAR), Length(255,true)
   *  @param accountid Database column accountid SqlType(INT)
   *  @param projectid Database column projectid SqlType(INT)
   *  @param createdata Database column createdata SqlType(DATETIME)
   *  @param seqs Database column seqs SqlType(INT)
   *  @param state Database column state SqlType(INT) */
  final case class SampleRow(id: Int, sample: String, accountid: Int, projectid: Int, createdata: DateTime, seqs: Int, state: Int)
  /** GetResult implicit for fetching SampleRow objects using plain SQL queries */
  implicit def GetResultSampleRow(implicit e0: GR[Int], e1: GR[String], e2: GR[DateTime]): GR[SampleRow] = GR{
    prs => import prs._
    SampleRow.tupled((<<[Int], <<[String], <<[Int], <<[Int], <<[DateTime], <<[Int], <<[Int]))
  }
  /** Table description of table sample. Objects of this class serve as prototypes for rows in queries. */
  class Sample(_tableTag: Tag) extends profile.api.Table[SampleRow](_tableTag, Some("brew_grem_database"), "sample") {
    def * = (id, sample, accountid, projectid, createdata, seqs, state) <> (SampleRow.tupled, SampleRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(sample), Rep.Some(accountid), Rep.Some(projectid), Rep.Some(createdata), Rep.Some(seqs), Rep.Some(state)).shaped.<>({r=>import r._; _1.map(_=> SampleRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column sample SqlType(VARCHAR), Length(255,true) */
    val sample: Rep[String] = column[String]("sample", O.Length(255,varying=true))
    /** Database column accountid SqlType(INT) */
    val accountid: Rep[Int] = column[Int]("accountid")
    /** Database column projectid SqlType(INT) */
    val projectid: Rep[Int] = column[Int]("projectid")
    /** Database column createdata SqlType(DATETIME) */
    val createdata: Rep[DateTime] = column[DateTime]("createdata")
    /** Database column seqs SqlType(INT) */
    val seqs: Rep[Int] = column[Int]("seqs")
    /** Database column state SqlType(INT) */
    val state: Rep[Int] = column[Int]("state")

    /** Primary key of Sample (database name sample_PK) */
    val pk = primaryKey("sample_PK", (id, accountid))
  }
  /** Collection-like TableQuery object for table Sample */
  lazy val Sample = new TableQuery(tag => new Sample(tag))
}
