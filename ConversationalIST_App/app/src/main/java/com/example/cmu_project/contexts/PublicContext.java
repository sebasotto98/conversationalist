package com.example.cmu_project.contexts;

public class PublicContext implements ContextAwareness {

    @Override
    public boolean conforms() {
        return true;
    }

}
