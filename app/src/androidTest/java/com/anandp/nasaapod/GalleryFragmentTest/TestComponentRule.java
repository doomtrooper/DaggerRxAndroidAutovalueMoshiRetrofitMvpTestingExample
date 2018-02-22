package com.anandp.nasaapod.GalleryFragmentTest;

import android.content.Context;

import com.anandp.nasaapod.NasaApodApp;
import com.anandp.nasaapod.data.Repository;
import com.anandp.nasaapod.di.DaggerRootComponent;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by @iamBedant on 20/02/18.
 */

public class TestComponentRule implements TestRule {

    private TestRootComponent mTestComponent;
    private Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public Repository getRepository() {
        return mTestComponent.getRepository();
    }

    private void setupDaggerTestComponentInApplication() {
        NasaApodApp application = ((NasaApodApp) mContext.getApplicationContext());

        mTestComponent = DaggerTestRootComponent.builder()
                .testRootModule(new TestRootModule(application))
                .build();


        application.setComponent(mTestComponent);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };
    }
}