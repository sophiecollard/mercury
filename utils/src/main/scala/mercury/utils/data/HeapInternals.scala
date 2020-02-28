package mercury.utils.data

import cats.Order
import cats.implicits._

import scala.annotation.tailrec

private [data] trait HeapInternals[N] {

  @tailrec
  final def sortBottomUp(
    nodes: Vector[N],
    currentIndex: Int
  )(
    implicit order: Order[N]
  ): Vector[N] =
    if (currentIndex < 1) {
      nodes
    } else {
      val parentIndex = (currentIndex + 1) / 2 - 1
      sortBottomUp(sortViaSwap(parentIndex, currentIndex, nodes), parentIndex)
    }

  @tailrec
  final def sortTopDown(
    nodes: Vector[N],
    currentDepth: Int = 0
  )(
    implicit order: Order[N]
  ): Vector[N] =
    if (currentDepth > math.log(nodes.length) / math.log(2)) {
      nodes
    } else {
      val firstIndex = math.pow(2, currentDepth).toInt - 1
      val indicesAtCurrentDepth = (firstIndex to firstIndex * 2).toList
      sortTopDown(sortAllWRTChildren(indicesAtCurrentDepth, nodes), currentDepth + 1)
    }

  @tailrec
  private def sortAllWRTChildren(indices: List[Int], nodes: Vector[N])(implicit order: Order[N]): Vector[N] =
    indices match {
      case Nil =>
        nodes
      case i :: is =>
        sortAllWRTChildren(is, sortWRTChildren(i, nodes))
    }

  private def sortWRTChildren(index: Int, nodes: Vector[N])(implicit order: Order[N]): Vector[N] = {
    val (leftChildIndex, rightChildIndex) = (2 * (index + 1) - 1, (2 * (index + 1)))
    sortViaSwap(index, rightChildIndex, sortViaSwap(index, leftChildIndex, nodes))
  }

  private def sortViaSwap(
    firstIndex: Int,
    secondIndex: Int,
    nodes: Vector[N]
  )(
    implicit order: Order[N]
  ): Vector[N] = {
    assert(firstIndex < secondIndex, "The first index must be strictly smaller than the second.")
    Option
      .when(secondIndex < nodes.length) { (nodes(firstIndex), nodes(secondIndex)) }
      .filter { case (firstValue, secondValue) => firstValue > secondValue }
      .map { case (firstValue, secondValue) =>
        val vectorBuilder = Vector.newBuilder[N]
        vectorBuilder ++= nodes.take(firstIndex)
        vectorBuilder += secondValue
        vectorBuilder ++= nodes.slice(firstIndex + 1, secondIndex)
        vectorBuilder += firstValue
        vectorBuilder ++= nodes.drop(secondIndex + 1)
        vectorBuilder.result()
      }
      .getOrElse(nodes)
  }

}
