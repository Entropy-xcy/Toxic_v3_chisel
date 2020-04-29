package toxic_v3

import chisel3._
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}

class StackPeekPokeTester(c: Stack) extends PeekPokeTester(c) {
    poke(c.io.push_en, 1)
    poke(c.io.push_data, 3)
    poke(c.io.pop_en, 0)
	step(1)
    poke(c.io.push_en, 1)
    poke(c.io.push_data, 4)
    poke(c.io.pop_en, 0)

    expect(c.io.tos, 3)

    
    step(1)

	expect(c.io.tos, 4)
    expect(c.io.ntos, 3)

    poke(c.io.push_en, 1)
    poke(c.io.push_data, 3)
    poke(c.io.pop_en, 1)

    expect(c.io.tos, 4)

    step(1)

    expect(c.io.tos, 3)
    expect(c.io.ntos, 3)

    poke(c.io.push_en, 0)
    poke(c.io.push_data, 3)
    poke(c.io.pop_en, 1)

    step(1)
    expect(c.io.tos, 3)
    expect(c.io.ntos, 0)
}


// Executor
class StackSpec extends FlatSpec with Matchers {
    chisel3.iotesters.Driver.execute(Array("--backend-name", "verilator", "--is-verbose"), () => new Stack(4)) { c =>
    	new StackPeekPokeTester(c)
    } should be(true)
}

