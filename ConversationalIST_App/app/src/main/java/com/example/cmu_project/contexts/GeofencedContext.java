package com.example.cmu_project.contexts;

public class GeofencedContext implements ContextAwareness {

    @Override
    public boolean conforms() {
        //TODO: check if current user location inside defined radius
        return false;
    }

}
