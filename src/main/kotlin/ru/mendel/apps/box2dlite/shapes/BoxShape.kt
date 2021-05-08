package ru.mendel.apps.box2dlite.shapes

class BoxShape : Shape() {
    override fun isCollide(shape: Shape): Boolean {
        return false
    }
}