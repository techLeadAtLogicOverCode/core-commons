package com.logicovercode.ccommons

import com.logicovercode.cadts.IDependency

import scala.meta._

case class DependencyMeta[T <: IDependency](methodName: String, dependency: T) {
  def jDefinitions(): Seq[Defn.Def] = {

    import dependency._

    val v = version.version

    val dependencyClassName = dependency.getClass.getSimpleName
    println(s"simple name is $dependencyClassName")

    val name = methodName.trim

    println("name is >" + name + "<")

    val defDefn1: Defn.Def = {
      val statements = List(
        q"""JDependency($organization, $artifactId, $v)"""
      )

      q"""
   def ${Term.Name(name)}() : JDependency = {
      ..${statements}
   }
 """
    }

    val defDefn2: Defn.Def = {
      val statements = List(
        q"""val dep = JDependency($organization, $artifactId, version)""",
        q"""findVersion(dep, Version($v))"""
      )

      q"""
   def ${Term.Name(name)}(version : String) : JDependency = {
      ..${statements}
   }
 """
    }

    Seq(defDefn1, defDefn2)
  }

  def sDefinitions(): Seq[Defn.Def] = {

    import dependency._

    val v = version.version

    val dependencyClassName = dependency.getClass.getSimpleName
    println(s"simple name is $dependencyClassName")

    val name = methodName.trim

    println("name is >" + name + "<")

    val defDefn1: Defn.Def = {
      val statements = List(
        q"""SDependency($organization, $artifactId, $v)"""
      )

      q"""
   def ${Term.Name(name)}() : SDependency = {
      ..${statements}
   }
 """
    }

    val defDefn2: Defn.Def = {
      val statements = List(
        q"""val dep = SDependency($organization, $artifactId, version)""",
        q"""findVersion(dep, Version($v))"""
      )

      q"""
   def ${Term.Name(name)}(version : String) : SDependency = {
      ..${statements}
   }
 """
    }

    Seq(defDefn1, defDefn2)
  }
}
