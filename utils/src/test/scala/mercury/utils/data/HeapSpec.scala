package mercury.utils.data

import cats.Order
import org.specs2.mutable.Specification

final class HeapSpec extends Specification {

  private val ascendingOrder: Order[Int] = Order.fromOrdering(Ordering[Int])
  private val descendingOrder: Order[Int] = Order.fromOrdering(Ordering[Int].reverse)

  private val heap = Heap(90, 19, 36, 17, 3, 25, 1, 2, 7)(descendingOrder)

  "Heap" should {

    "sort its elements upon instantiation" in {
      Heap(5, 4, 3, 1, 2, 6)(ascendingOrder).head must beSome(1)
      Heap(5, 4, 3, 1, 2, 6)(descendingOrder).head must beSome(6)
    }

    "be comparable for equality" in {
      heap ==== Heap(90, 19, 36, 17, 3, 25, 1, 2, 7)(descendingOrder)
      heap !=== Heap(90, 19, 36, 17, 3, 25, 1, 2, 7)(ascendingOrder)
    }

    "be pretty-printed" in {
      heap.toString ==== "Heap(90, 19, 36, 17, 3, 25, 1, 2, 7)"
    }

    "return its head, i.e. the element with highest priority" in {
      heap.head must beSome(90)
    }

    "return a sorted Heap after removing the head" in {
      heap.dequeue ==== (Some(90), Heap(36, 17, 25, 7, 3, 19, 1, 2)(descendingOrder))
    }

    "return a sorted Heap after inserting a new element" in {
      heap.enqueue(21) ==== Heap(90, 21, 36, 17, 19, 25, 1, 2, 7, 3)(descendingOrder)
    }

  }

}
