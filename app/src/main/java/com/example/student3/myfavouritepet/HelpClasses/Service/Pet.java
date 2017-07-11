package com.example.student3.myfavouritepet.HelpClasses.Service;

public class Pet {

    private static String name = "250801250801";
    private static int money = 100, petIndex = -1, room, kind;

    public static void setName(String name) {
        Pet.name = name;
    }

    public static void setMoney(int money) {
        Pet.money = money;
    }

    public static void setPetIndex(int petIndex) {
        Pet.petIndex = petIndex;
    }

    public static String getName() {

        return name;
    }

    public static void setRoom(int room) {
        Pet.room = room;
    }

    public static void setKind(int kind) {
        Pet.kind = kind;
    }

    public static int getRoom() {

        return room;
    }

    public static int getKind() {
        return kind;
    }

    public static int getMoney() {
        return money;
    }

    public static int getPetIndex() {
        return petIndex;
    }

    public static void IncreaseMoney(){
        money++;
    }

    public static void IncreaseMoney(int count){
        money += count;
    }
}
