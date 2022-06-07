package com.example.cmu_project.contexts;

public class WifiContext extends BandwidthContext {
    @Override
    public boolean conforms() {
        return false;
    }
}
