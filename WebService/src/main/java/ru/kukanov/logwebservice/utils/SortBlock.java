package ru.kukanov.logwebservice.utils;

import ru.kukanov.logwebservice.types.BlockLog;

import java.util.Comparator;


 class SortBlock implements Comparator<BlockLog> {
    @Override
    public int compare(BlockLog o1, BlockLog o2) {
        return o1.getDate().compare(o2.getDate());
    }



}
