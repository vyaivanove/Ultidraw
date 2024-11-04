package com.vyaivanove.ultidraw.util

@Suppress("UNUSED")
class DoublyLinkedList<T> : Collection<T> {
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

    override var size: Int = 0

    override fun contains(element: T): Boolean = iterator().asSequence().contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = elements.all(::contains)

    override fun isEmpty(): Boolean = head == null

    override fun iterator(): Iterator<T> = iterator {
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

        size++

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

        size++

        return node
    }

    fun removeFirst() {
        head = head!!.nextNode

        if (head == null) {
            tail = null
        }

        size--
    }

    fun removeLast() {
        tail = tail!!.previousNode

        if (tail == null) {
            head = null
        }

        size--
    }

    fun clear() {
        head = null
        tail = null

        size = 0
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

        size++

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

        size++

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
        size--
    }

    fun removeAfter(node: Node<T>) {
        node as MutableNode<T>

        node.nextNode = node.nextNode!!.nextNode
        node.nextNode?.previousNode = node

        if (node.nextNode == null) {
            tail = node
        }

        size--
    }

    fun removeBefore(node: Node<T>) {
        node as MutableNode<T>

        node.previousNode = node.previousNode!!.previousNode
        node.previousNode?.nextNode = node

        if (node.previousNode == null) {
            head = node
        }

        size--
    }
}
