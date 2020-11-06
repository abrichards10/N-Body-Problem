
//Angela Richards - NBody to NBodyProblem

// add all the methods to the NBody
public interface NBody<E> {
    E get(int position);
    boolean add(E value);
    E remove(int position);
    int get_size();
}

//create a new class that deals with an ArrayList if that's what is
// written on the file
// implement all the methods of NBody
class ArrayList<E> implements NBody<E>{
    // array and size
    E[] arr;
    int size;

    // make a new array with 15 elements
    public ArrayList(){
        arr = (E[])new Object[15];
        size = 0;
    }

    // get method for size
    public int get_size(){
        return size;
    }

    // get method with a given position
    public E get(int position){
        // if it's less than 0 but greater than the size of the array
        if(position < 0 || position >= size){
            // it's not in a valid position
            System.out.println("Invalid Position");
            return null;
        }
        // otherwise the array is returned with the new position
        return arr[position];
    }

    // create a new array with the size of the length *2
    // basically if we need to grow the array to compensate for size, we use this
    // I keep getting generify options, but it compiles fine so I just left it
    private void grow_array(){
        E [] new_array = (E[])new Object[arr.length * 2];
        for(int i = 0; i < arr.length; i++){
            new_array[i] = arr[i];
        }
        arr = new_array;
    }

    // add function with a given value
    public boolean add(E value){
        if(size == arr.length){
            grow_array();
        }
        // if we get to the end we have accomplished the adding process
        arr[size++] = value;
        return true;
    }

    // remove function of a certain index
    public E remove(int position){
        if(position < 0 && position >= size){
            System.out.println("Invalid index");
        }
        // create a new value at the index
        E value = arr[position];
        // start at the next index over from the original
        // if it's less than the size of the array, continue
        for(int i = position + 1; i < size; i++){
            // move the elements so the ones we looped over are copied onto the new array
            // excluding the removed value
            arr[i-1] = arr[i];
        }
        // minimize the size by one
        size--;
        return value;
    }
}

// in case it is a LinkedList written on the first line, implement the methods
class LinkedList<E> implements NBody<E>{
    // create head
    Node<E> head;
    int size;

    // Node class containing nerdy node info
    private static class Node<E>{
        // data and the next (pointer) is stored in the node
        E data;
        Node<E> next;

        // the data is assigned the node value
        // and the next (pointer) is null
        public Node(E value){
            data = value;
            next = null;
        }
    }

    // Constructor for a linked list
    public LinkedList(){
        head = null;
        size = 0;
    }

    // get method for size of the linked list
    public int get_size(){
        return size;
    }

    // get method given a position
    public E get(int position){
        if(position < 0 || position >= size){
            System.out.println("Invalid Index");
            return null;
        }

        // assign the current to the head
        Node<E> current = head;
        // then loop through, going to the next item with current.next
        for(int i = 0; i < position; i++){
            current = current.next;
        }
        // return the data from that node
        return current.data;
    }

    // add a given item to the linked list
    public boolean add(E item){
        // if the list is empty, create a new node with that item in it
        if(head == null){
            head = new Node<E>(item);
            ++size;
            return true;
        }
        // otherwise assign the previous value to be the head,
        // create a new node with the item inside, assign it to the next node from the previous one
        // then increase the size
        else{
            Node<E> previous = head;
            while(previous.next != null){
                previous = previous.next;
            }
            Node<E> node = new Node<E>(item);
            previous.next = node;
            ++size;
            return true;
        }
    }

    // add method with a given item and position
    public void add(E item, int position){
        // if the index is 0, we do the same process of creating a node with the value
        if(position == 0){
            Node<E> node = new Node<E>(item);
            node.next = head;
            head = node;
            ++size;
        }
        else{
            // otherwise we assign the previous to the head and
            // loop through --> as long as the we are still one above the index
            Node<E> previous = head;
            for(int i = 0; i < position - 1; i++){
                previous = previous.next;
            }
            // create a new node with the item
            Node<E> node = new Node<E>(item);
            // assign the next value to the previous one
            node.next = previous.next;
            previous.next = node;
            ++size;
        }
    }

    // remove function
    public E remove(int position){
        if(position < 0 || position >= size){
            System.out.println("Invalid index");
            return null;
        }
        // if the index is 0, we just remove the head
        if(position == 0){
            Node<E> node = head;
            head = head.next;
            --size;
            return node.data;
        }
        else{
            // otherwise we loop through until we find index-1 and
            // remove the node after that one (which is really the one we are looking for)
            Node<E> previous = head;
            for(int i = 0; i < position-1; i++){
                previous = previous.next;
            }
            Node<E> node = previous.next;
            previous.next = node.next;
            --size;
            return node.data;
        }
    }

    // reverse function with a given node
    public Node<E> reverse(Node<E> head){
        Node<E> previous = null;
        Node<E> current = head;
        Node<E> next = null;
        while(current != null){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        head = previous;
        return head;
    }

}
