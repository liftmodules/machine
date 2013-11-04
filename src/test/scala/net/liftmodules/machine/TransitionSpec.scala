package net.liftmodules.machine

import org.specs2.mutable.Specification
import net.liftweb.util.Helpers.TimeSpan
import org.specs2.specification.AroundContextExample
import net.liftmodules.machine.TestMachines.Machine.FirstEvent

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


class TransitionSpec extends Specification with AroundContextExample[InMemoryDB] {

  sequential

  def aroundContext = new InMemoryDB()

  "After" should {
    "support duration-based state transitions" in {

      import TestMachines._, Machine.TimerEvent

      // After construction, we are in Active state:
      val machine = Machine.newInstance(FirstEvent)
      machine.state must_== States.Active

      // Once the timer runs, we're in Expired state:
      machine.processEvent(TimerEvent(durationBeforeExpiry))
      machine.state must_== States.Expired

    }
  }


}
