package net.liftmodules.machine

import org.specs2.mutable.Specification
import net.liftweb.util.Helpers.TimeSpan
import net.liftmodules.machine.TestMachines.Machine.FirstEvent
import net.liftweb.common.Empty
import net.liftweb.util.DefaultConnectionIdentifier
import net.liftweb.db.StandardDBVendor
import net.liftweb.mapper.{DB, Schemifier}

object TestMachines {

  // In the duration test we want Active to move to Expired after `durationBeforeExpiry` (1 second)
  val durationBeforeExpiry = TimeSpan(1000L)

  object States extends Enumeration {
    val Initial, Active, Expired = Value
  }

  object Machine extends Machine with MetaProtoStateMachine[Machine, States.type] {

    def stateEnumeration = States

    case object FirstEvent extends Event

    def initialState = States.Initial

    protected def states: List[Machine.Meta#State] =
      State(States.Initial, On({ case FirstEvent => }, States.Active)) ::
      State(States.Active, After(durationBeforeExpiry, States.Expired)) ::
      Nil

    protected def globalTransitions: List[Machine.Meta#ATransition] = Nil

    protected def instantiate = new Machine()
  }

  class Machine extends ProtoStateMachine[Machine, States.type] {
    def getSingleton = Machine
  }

}


class TransitionSpec extends Specification {

  sequential

  "processEvent" should {
    "support duration-based state transitions" in {

      import TestMachines._, Machine.TimerEvent

      // Having the test DB set up here isn't ideal.
      // Specs2 `around` has changed and I've not figured out how to support 2.9.2 and 2.10.x yet
      lazy val vendr = new StandardDBVendor("org.h2.Driver", "jdbc:h2:mem:specs2;DB_CLOSE_DELAY=-1", Empty, Empty)
      DB.defineConnectionManager(DefaultConnectionIdentifier, vendr)
      Schemifier.schemify(true, Schemifier.infoF _, Machine)

      // After construction, we are in Active state:
      val machine = Machine.newInstance(FirstEvent)
      machine.state must_== States.Active

      // Once the timer runs, we're in Expired state:
      machine.processEvent(TimerEvent(durationBeforeExpiry))
      val endState = machine.state

      // Test database clean up:
      vendr.closeAllConnections_!()

      endState must_== States.Expired

    }
  }


}
