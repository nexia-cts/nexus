package com.nexia.nexus.api.world.entity.animal;

public interface Animal {
    boolean isInLove();

    int getLoveTime();
    void setLoveTime(int loveTime);
    void resetLove();

    boolean canMateWith(Animal animal);
}
