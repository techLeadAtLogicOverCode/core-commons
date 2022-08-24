package com.logicovercode.ccommons

import com.logicovercode.cadts.{IDependency, Version}

trait DependencyValidation {

  def isExplicitVersionAllowed: Boolean = true

  def findVersion[T <: IDependency](dependency: T, supportedVersion: Version): T = {
    dependency
  }
}
