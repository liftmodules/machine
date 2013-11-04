package net.liftmodules.machine

import net.liftweb.mapper.{Schemifier, DB, StandardDBVendor}
import net.liftweb.db.DefaultConnectionIdentifier
import org.specs2.mutable.Around
import org.specs2.execute.{Result, ResultExecution, AsResult}
import net.liftweb.common.Empty

class InMemoryDB extends Around {

  lazy val vendr = new StandardDBVendor("org.h2.Driver", "jdbc:h2:mem:specs2;DB_CLOSE_DELAY=-1", Empty, Empty)

  def initDb() = {

    // Can be useful in debugging database issues:
    // Logger.setup = Full(net.liftweb.util.LoggingAutoConfigurer())
    // Logger.setup.foreach { _.apply() }

    DB.defineConnectionManager(DefaultConnectionIdentifier, vendr)

    Schemifier.schemify(true, Schemifier.infoF _, TestMachines.Machine)
  }

  def around[T: AsResult](spec: => T): Result = {
    initDb()
    try {
      ResultExecution.execute(AsResult(spec))
    } finally {
     vendr.closeAllConnections_!()
    }
  }

}

