package com.example.cmu_project.contexts;

public class PrivateContext implements ContextAwareness {

    @Override
    public boolean conforms() {
        //TODO: check if access link is legit
        return false;
    }

}
