package ru.mendel.apps.box2dlite.physics

abstract class CollideAbstract {

    abstract fun startCollide(body1: Body, body2: Body)
    abstract fun endCollide(body1: Body, body2: Body)

}