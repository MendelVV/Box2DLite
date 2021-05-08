package ru.mendel.apps.box2dlite.shapes

abstract class Shape {
    abstract fun isCollide(shape: Shape): Boolean
}