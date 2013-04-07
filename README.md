Machine Lift Module
==================

This module provides "a state machine that allows you to define transition rules and events.  You have to add a single column to keep track of the state of the item and machine takes care of the rest."

For use with Mapper.

To include this module in your Lift project, update your `libraryDependencies` in `build.sbt` to include:

*Lift 2.5.x* for Scala 2.9 and 2.10:

    "net.liftmodules" %% "machine_2.5" % "1.2"

*Lift 3.0.x* for Scala 2.10:

    "net.liftmodules" %% "machine_3.0" % "1.2-SNAPSHOT"


Documentation
=============

[Lift in Action](http://www.manning.com/perrett/) uses the module in chapter 5.

**Note:** The module package changed from `net.liftweb.machine` to `net.liftmodules.machine` in May 2012.  Please consider this when referencing documentation written before that date.


Notes for module developers
===========================

* The [Jenkins build](https://liftmodules.ci.cloudbees.com/job/machine/) is triggered on a push to master.

