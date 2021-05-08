package ru.mendel.apps.box2dlite.geometry

import kotlin.math.*

class Vector2D {
    var x: Float
    var y: Float
    var length: Float
    var angle: Float//в радианах

    constructor(s: Point, e: Point){
        x = e.x-s.x
        y = e.y-s.y
        length = sqrt(x.pow(2)+y.pow(2))
        angle = acos(x/length)
    }

    constructor(x: Float, y: Float){
        this.x = x
        this.y = y
        length = sqrt(x.pow(2)+y.pow(2))
        angle = acos(x/length)
    }

    fun changeCoordinates(x: Float, y: Float){
        this.x = x
        this.y = y
        length = sqrt(x.pow(2)+y.pow(2))
        angle = acos(x/length)
    }

    fun changeLength(d: Float){
        length = d
        x = length*cos(angle)
        y = length*sin(angle)
    }

    override fun toString(): String {
        return "($x, $y, $angle, $length)"
    }
}