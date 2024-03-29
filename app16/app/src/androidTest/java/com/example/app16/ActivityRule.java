package com.example.app16;

/*
 * Class created by https://gist.github.com/JakeWharton/1c2f2cadab2ddd97f9fb by Jack
 */

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A JUnit {@link Rule @Rule} which launches an activity when your test starts. Stop extending
 * gross {@code ActivityInstrumentationBarfCase2}!
 * <p>
 * Usage:
 * <pre>{@code
 * &#064;Rule
 * public final ActivityRule<ExampleActivity> example =
 *     new ActivityRule<>(ExampleActivity.class);
 * }</pre>
 *
 * This will automatically launch the activity for each test method. The instance will also be
 * created sooner should you need to use it in a {@link Before @Before} method.
 * <p>
 * You can also customize the way in which the activity is launched by overriding
 * {@link #getLaunchIntent(String, Class)} and customizing or replacing the {@link Intent}.
 * <pre>{@code
 * &#064;Rule
 * public final ActivityRule<ExampleActivity> example =
 *     new ActivityRule<ExampleActivity>(ExampleActivity.class) {
 *       &#064;Override
 *       protected Intent getLaunchIntent(String packageName, Class<ExampleActivity> activityClass) {
 *         Intent intent = super.getLaunchIntent(packageName, activityClass);
 *         intent.putExtra("Hello", "World!");
 *         return intent;
 *       }
 *     };
 * }</pre>
 */
public class ActivityRule<T extends Activity> implements TestRule {
    private final Class<T> activityClass;

    private T activity;
    private Instrumentation instrumentation;

    public ActivityRule(Class<T> activityClass) {
        this.activityClass = activityClass;
    }

    protected Intent getLaunchIntent(String targetPackage, Class<T> activityClass) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName(targetPackage, activityClass.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * Get the running instance of the specified activity. This will launch it if it is not already
     * running.
     */
    public final T get() {
        launchActivity();
        return activity;
    }

    /** Get the {@link Instrumentation} instance for this test. */
    public final Instrumentation instrumentation() {
        launchActivity();
        return instrumentation;
    }

    @Override public final Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override public void evaluate() throws Throwable {
                launchActivity();

                base.evaluate();

                if (!activity.isFinishing()) {
                    activity.finish();
                }
                activity = null; // Eager reference kill in case someone leaked our reference.
            }
        };
    }

    private Instrumentation fetchInstrumentation() {
        Instrumentation result = instrumentation;
        return result != null ? result
                : (instrumentation = InstrumentationRegistry.getInstrumentation());
    }

    @SuppressWarnings("unchecked") // Guarded by generics at the constructor.
    private void launchActivity() {
        if (activity != null) return;

        Instrumentation instrumentation = fetchInstrumentation();

        String targetPackage = instrumentation.getTargetContext().getPackageName();
        Intent intent = getLaunchIntent(targetPackage, activityClass);

        activity = (T) instrumentation.startActivitySync(intent);
        instrumentation.waitForIdleSync();
    }
}