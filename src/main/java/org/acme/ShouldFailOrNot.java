package org.acme;

import javax.inject.Singleton;

@Singleton
public class ShouldFailOrNot {

    private boolean shouldFail = false;

    public boolean shouldFail(){
        return this.shouldFail;
    }

    public void swapShouldFail(){
        this.shouldFail = !shouldFail;
    }
}
