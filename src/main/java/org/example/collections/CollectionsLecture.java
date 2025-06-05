package org.example.collections;


import lombok.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

interface Payment{

   abstract void addItem(Item item);
   abstract void deleteItem(Item item);
   abstract void sort(Comparator comparator);
   abstract long calculatetotal();
}

@Getter
@Setter
class Upi implements Payment{

    public List<Item> items = new ArrayList<>();
    @Override
    public void addItem(Item item) {
        items.add(item);
    }

    public long addItem(Item item, long i) {
        item.setPrice(80);
        items.add(item);
        return i;
    }

    @Override
    public void deleteItem(Item item) {
        items.remove(item);
    }

    @Override
    public void sort(Comparator comparator) {
        items.sort(comparator);
    }

    @Override
    public long calculatetotal() {
        AtomicLong charges = new AtomicLong();
        items.stream().forEach((item)->{
            charges.set(charges.get()+item.getPrice());
        });

        return charges.get();
    }
}


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Item implements Comparable<Item>{

    private String name;
    private long price;

    @Override
    public int compareTo(Item o) {
        return o.getName().compareTo(this.name);
    }
}

public class CollectionsLecture {

    public static void main(String[] args) {
        CollectionsLecture c = new CollectionsLecture();
        c.gotoupipay();
    }

    public void gotoupipay(){
        Item item = new Item("pizza",100);
        Item item2 = new Item("sandwich",1300);
        Item item1 = new Item("burger",1200);

        Comparator<Item> comparator = (Item it1, Item it2) ->{
            return Math.toIntExact(it1.getPrice() - it2.getPrice());
        };

        Upi upipay = new Upi();
        upipay.addItem(item);
        upipay.addItem(item1);
        upipay.addItem(item2);
        long calculatetotal = upipay.calculatetotal();
        System.out.println(calculatetotal);

        upipay.sort(comparator);
        List<Item> items = upipay.getItems();
        Collections.sort(items);

        for(Item i: items){
            System.out.println(i.toString());
        }

    }


}

