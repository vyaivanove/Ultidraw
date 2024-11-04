package com.vyaivanove.ultidraw.util

@Suppress("UNUSED")
class DoublyLinkedList<T> {
    private var head: MutableNode<T>? = null
    private var tail: MutableNode<T>? = null

    sealed class Node<T> {
        abstract val value: T
        abstract fun next(): Node<T>?
        abstract fun previous(): Node<T>?
    }

    private class MutableNode<T>(
        var previousNode: MutableNode<T>? = null,
        var nextNode: MutableNode<T>? = null,
        override var value: T
    ) : Node<T>() {
        override fun next(): Node<T>? = nextNode
        override fun previous(): Node<T>? = previousNode
    }

    fun iterator(): Iterator<T> = iterator {
        var node = head

        while (node != null) {
            yield(node.value)
            node = node.nextNode
        }
    }

    fun addFirst(element: T): Node<T> {
        val node = MutableNode(value = element)

        node.nextNode = head
        head?.previousNode = node

        head = node

        if (tail == null) {
            tail = node
        }

        return node
    }

    fun addLast(element: T): Node<T> {
        val node = MutableNode(value = element)

        node.previousNode = tail
        tail?.nextNode = node

        tail = node

        if (head == null) {
            head = node
        }

        return node
    }

    fun removeFirst() {
        head = head!!.nextNode

        if (head == null) {
            tail = null
        }
    }

    fun removeLast() {
        tail = tail!!.previousNode

        if (tail == null) {
            head = null
        }
    }

    fun clear() {
        head = null
        tail = null
    }

    fun first(): Node<T>? = head

    fun last(): Node<T>? = tail

    fun addAfter(node: Node<T>, element: T): Node<T> {
        node as MutableNode<T>
        val newNode = MutableNode<T>(value = element)

        newNode.nextNode = node.nextNode
        newNode.previousNode = node

        newNode.nextNode?.previousNode = newNode
        node.nextNode = newNode

        if (newNode.nextNode == null) {
            tail = newNode
        }

        return newNode
    }

    fun addBefore(node: Node<T>, element: T): Node<T> {
        node as MutableNode<T>
        val newNode = MutableNode<T>(value = element)

        newNode.previousNode = node.previousNode
        newNode.nextNode = node

        node.previousNode = newNode
        newNode.previousNode?.nextNode = newNode

        if (newNode.previousNode == null) {
            head = newNode
        }

        return newNode
    }

    fun remove(node: Node<T>) {
        node.previous()?.let {
            removeAfter(it)
            return
        }

        node.next()?.let {
            removeBefore(it)
            return
        }

        head = null
        tail = null
    }

    fun removeAfter(node: Node<T>) {
        node as MutableNode<T>

        node.nextNode = node.nextNode!!.nextNode
        node.nextNode?.previousNode = node

        if (node.nextNode == null) {
            tail = node
        }
    }

    fun removeBefore(node: Node<T>) {
        node as MutableNode<T>

        node.previousNode = node.previousNode!!.previousNode
        node.previousNode?.nextNode = node

        if (node.previousNode == null) {
            head = node
        }
    }
}
