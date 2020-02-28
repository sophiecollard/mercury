package mercury.utils.data

import cats.Order

import scala.annotation.tailrec

final case class Heap[N](private val nodes: Vector[N])(implicit order: Order[N]) extends HeapInternals[N] {

  val isEmpty: Boolean =
    nodes.isEmpty

  lazy val head: Option[N] =
    if (nodes.isEmpty)
      None
    else
      Some(nodes(0))

  lazy val tail: Heap[N] =
    new Heap({
      val lastIndex = nodes.length - 1
      sortTopDown(nodes(lastIndex) +: nodes.slice(1, lastIndex))
    })

  def enqueue(node: N): Heap[N] =
    new Heap(sortBottomUp(nodes :+ node, nodes.length))

  def enqueueAll(nodes: N*): Heap[N] =
    nodes.foldLeft(this)(_ enqueue _)

  def dequeue: (Option[N], Heap[N]) =
    (head, tail)

  def dequeueAll: Vector[N] = {
    @tailrec
    def recurse(heap: Heap[N], nodes: Vector[N]): Vector[N] =
      heap.head match {
        case Some(n) => recurse(heap.tail, nodes :+ n)
        case None    => nodes
      }
    recurse(this, nodes)
  }

  override def toString(): String =
    s"Heap(${nodes.mkString(", ")})"

}

object Heap {

  def apply[N](nodes: N*)(implicit order: Order[N]): Heap[N] =
    empty[N].enqueueAll(nodes: _*)

  def empty[N](implicit order: Order[N]): Heap[N] =
    Heap(Vector.empty[N])

}
