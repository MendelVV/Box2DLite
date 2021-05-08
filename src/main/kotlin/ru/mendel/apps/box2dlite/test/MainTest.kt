package ru.mendel.apps.box2dlite.test

import ru.mendel.apps.box2dlite.World
import ru.mendel.apps.box2dlite.geometry.Point
import ru.mendel.apps.box2dlite.geometry.Vector2D
import ru.mendel.apps.box2dlite.shapes.CircleShape
import kotlin.random.Random

fun generateBodies(world: World, n: Int){
    val allPoints = arrayListOf<Point>()
    val startX = 0f
    val startY = 0f
    val step = world.sectorW
    val rows = world.size
    val columns = world.size
    var y = startY
    for (i in 0 until rows){
        var x = startX
        for (j in 0 until columns){
            val dx = 4- Random.nextInt(9)
            val dy = 4- Random.nextInt(9)
            val point = Point(x+step/2+dx, y+step/2+dy)
            allPoints.add(point)
            x+=step
        }
        y+=step
    }
    val radius = 10
    for (i in 0 until n){
        val pos = Random.nextInt(allPoints.size)
        val body = world.createBody()
        body.shape = CircleShape(allPoints[pos], radius)
        allPoints.removeAt(pos)
    }
}

fun runWorld(world: World, time: Long){
//    val start = System.currentTimeMillis()
    val start = System.nanoTime()
    var last = start
    var now = 0L

    var i = 0
    while (last-start<time){
//        now = System.currentTimeMillis()
        now = System.nanoTime()
        val delta = (now-last).toFloat()/100000f
        if (delta<=0) continue
//        println("now=$now last=$last")
//        println("$i delta=$delta")
        world.act(delta)
//        showCurrentPosition(world)
//        last = System.currentTimeMillis()
        last = System.nanoTime()
//        Thread.sleep(1)
//        Thread.sleep(0,1)
        i++
    }
    println("iterations = $i")
}

fun showCurrentPosition(world: World){
    for (body in world.bodies){
        var res = "c=${(body.shape as CircleShape).center}"
        res+=" v="+body.linearVelocity
        println("current=$res")
    }
}

fun main(){
    val world = World(900f, 900f, 30)
    world.collide = CollideTest()
    generateBodies(world, 400)
    world.setRandomVelocity(50f)


    runWorld(world, 1000000000)
    StatisticTest.showResults()

}