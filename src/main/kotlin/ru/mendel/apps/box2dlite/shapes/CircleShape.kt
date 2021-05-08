package ru.mendel.apps.box2dlite.shapes

import ru.mendel.apps.box2dlite.geometry.Point
import kotlin.math.pow

class CircleShape : Shape {

    var center: Point
    var radius: Int

    constructor(c: Point, r: Int):super(){
        center = c
        radius = r
    }

    override fun isCollide(shape: Shape): Boolean {
        when (shape){
            is CircleShape ->{
                val length = (center.x - shape.center.x).pow(2)+(center.y - shape.center.y).pow(2)
                if (length < radius + shape.radius){
                    return true
                }
            }
        }
        return false
    }

    override fun toString(): String {
        return center.toString()
    }
}