package com.example.cmu_project.contexts;

public class PrivateContext extends LocationContext {

    @Override
    public boolean conforms(Object... args) {
        //TODO: check if access link is legit
        return false;
    }

}
