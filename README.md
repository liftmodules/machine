Machine Lift Module
==================

[![Build Status](https://travis-ci.org/liftmodules/machine.svg?branch=master)](https://travis-ci.org/liftmodules/machine)

This module provides "a state machine that allows you to define transition rules and events.  You have to add a single column to keep track of the state of the item and machine takes care of the rest."

For use with Mapper.

Installation
------------

To include this module in your Lift project, add the following to `build.sbt`:

    libraryDependencies += "net.liftmodules" %% "machine_3.0" % "1.3.0"

### Releases


| Lift Version | Scala Version | Module Version |
|--------------|---------------|----------------|
| 3.0.x        | 2.11, 2.10    |  1.3           |
| 2.5.x        | 2.10, 2.9     |  1.2           |


### Historic Snapshots

| Lift Version | Scala Version   | Module Version |
|--------------|-----------------|----------------|
| 2.6.x        | 2.11, 2.9, 2.10 | 1.3-SNAPSHOT   |
| 3.0.x        | 2.10            | 1.2-SNAPSHOT   |


Documentation
=============

[Lift in Action](http://www.manning.com/perrett/) uses the module in chapter 5.

**Note:** The module package changed from `net.liftweb.machine` to `net.liftmodules.machine` in May 2012.  Please consider this when referencing documentation written before that date.


Notes for module developers
===========================

Merge to master will trigger a Travis build and publish a SNAPSHOT (if the version is a -SNAPSHOT).
