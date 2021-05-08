package ru.mendel.apps.box2dlite.test

import ru.mendel.apps.box2dlite.physics.Body
import ru.mendel.apps.box2dlite.physics.CollideAbstract

class CollideTest: CollideAbstract() {
    override fun startCollide(body1: Body, body2: Body) {
//        println("startCollide!")
        StatisticTest.totalStartCollide++
    }

    override fun endCollide(body1: Body, body2: Body) {
//        println("endCollide!")
        StatisticTest.totalEndCollide++
    }
}