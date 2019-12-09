package com.ualr.loginandregister.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class topic extends ExpandableGroup<Page> {

    public topic(String title, List<Page> items) {
        super(title, items);
    }
}
