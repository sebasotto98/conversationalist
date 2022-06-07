package com.example.cmu_project.contexts;

public class GeofencedContext extends LocationContext {

    @Override
    public boolean conforms(Object... args) {
        //TODO: check if current user location inside defined radius
        return false;
    }

}
