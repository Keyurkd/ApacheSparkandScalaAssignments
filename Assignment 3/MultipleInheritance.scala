trait X {
    def f = "x"
}

trait Y {
    def f = "y" 
}

class Z extends X with Y {
    override def f = super[X].f 
    def g = super.f
}

val z = new Z
println(z.f)
println(z.g)