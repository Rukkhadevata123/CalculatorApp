public class DoublyLinkedList {
    private static class Node {
        int data;
        Node next;
        Node prev;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head, tail = null;
    private int size = 0;

    public void addNode(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    private void deleteNode(Node del) {
        if (head == null || del == null) {
            return;
        }

        if (head == del) {
            head = del.next;
        }

        if (del.next != null) {
            del.next.prev = del.prev;
        } else {
            tail = del.prev;
        }

        if (del.prev != null) {
            del.prev.next = del.next;
        }
        size--;
    }

    public void deleteNode(int data) {
        Node del = findNode(data);
        if (del != null) {
            deleteNode(del);
        }
    }

    private Node findNode(int data) {
        Node current = head;
        while (current != null) {
            if (current.data == data) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void insertAfter(Node prevNode, int data) {
        if (prevNode == null)
            throw new IllegalArgumentException("The given previous node cannot be null");

        var newNode = new Node(data);
        newNode.next = prevNode.next;
        prevNode.next = newNode;
        newNode.prev = prevNode;

        if (newNode.next != null) {
            newNode.next.prev = newNode;
        } else {
            tail = newNode;
        }
        size++;
    }

    public void display() {
        Node current = head;
        if (head == null) {
            System.out.println("The doubly linked list is empty.");
            return;
        }
        System.out.println("Nodes of the doubly linked list: ");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        System.out.println();
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        DoublyLinkedList dll = new DoublyLinkedList();
        dll.addNode(10);
        dll.addNode(20);
        dll.addNode(30);
        dll.addNode(40);
        dll.addNode(50);

        System.out.println("Original List: ");
        dll.display();

        System.out.println("\nAfter deleting a node: ");
        dll.deleteNode(20);
        dll.display();

        System.out.println("\nSize of list: " + dll.size());
    }
}