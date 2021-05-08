package ru.mendel.apps.box2dlite

import ru.mendel.apps.box2dlite.geometry.Point
import ru.mendel.apps.box2dlite.geometry.Vector2D
import ru.mendel.apps.box2dlite.shapes.CircleShape

fun main(){
    val world = World(100f, 100f, 10)
    val body1 = world.createBody()
    body1.shape = CircleShape(Point(0f,0f), 3)
    body1.linearVelocity = Vector2D(50f,0f)

    val body2 = world.createBody()
    body2.shape = CircleShape(Point(100f,0f), 3)
    body2.linearVelocity = Vector2D(-50f,0f)

    val start = System.currentTimeMillis()
    var last = start
    var now = 0L

    var i = 0
    while (last-start<2000){
        now = System.currentTimeMillis()
        val delta = (now-last).toFloat()/1000
//        println("now=$now last=$last")
       // println("$i delta=$delta")
        world.act(delta)
        last = System.currentTimeMillis()
        Thread.sleep(1)
//        Thread.sleep(0,1)
        i++
    }
    println("iterations = $i")
}