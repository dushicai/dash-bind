package org.dash.bind.annotation;

import android.app.Activity;
import android.view.View;

import org.dash.bind.utils.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ViewInject {

    public static void inject(Activity activity) {

        Class<?> clazz = activity.getClass();

        layoutContent(activity);

        Field[] fields = clazz.getDeclaredFields();

        Method[] methods = clazz.getDeclaredMethods();

        viewBind(fields, activity);

        viewListener(methods, activity);

    }

    private static void layoutContent(Activity activity) {

        Class<?> clazz = activity.getClass();

        Annotation[] annotations = clazz.getDeclaredAnnotations();

        Log.i("annotations.length:" + annotations.length);

        for (Annotation annotation : annotations) {

            //  LogUtils.i("annotations:" + annotations);

            if (annotation.annotationType().isAssignableFrom(Layout.class)) {

                try {

                    Layout layout = clazz.getAnnotation(Layout.class);
                    activity.setContentView(layout.value());
                    Log.i("Layout:" + layout);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("layoutContent:" + e);
                }
            }
        }
    }

    private static void viewBind(Field[] fields, Activity activity) {

        Log.i("fields.length:" + fields.length);

        for (Field field : fields) {

            //  LogUtils.i("fields:" + fields);

            Annotation[] annotations = field.getAnnotations();

            Log.i("annotations.length:" + annotations.length);

            for (Annotation annotation : annotations) {

                //    LogUtils.i("annotation:" + annotation);

                if (annotation.annotationType().isAssignableFrom(ViewBind.class)) {

                    try {

                        ViewBind viewBind = field.getAnnotation(ViewBind.class);
                        View view = activity.findViewById(viewBind.value());

                        Log.i("viewBind.value():" + viewBind.value());
                        Log.i("view:" + view);

                        if (view != null) {

                            field.setAccessible(true);
                            field.set(activity, view);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void viewListener(Method[] methods, final Activity activity) {

        Log.i("methods.length:" + methods.length);

        for (final Method method : methods) {

            //    LogUtils.i("methods:" + methods);

            Annotation[] annotations = method.getDeclaredAnnotations();

            Log.i("annotations.length:" + annotations.length);


            for (Annotation annotation : annotations) {

                //   LogUtils.i("annotations:" + annotations);

                if (annotation.annotationType().isAssignableFrom(ViewListener.class)) {

                    try {

                        ViewListener viewListener = method.getAnnotation(ViewListener.class);
                        final View view = activity.findViewById(viewListener.value());

                        Log.i("viewListener.value():" + viewListener.value());
                        Log.i("view:" + view);

                        if (view != null) {

                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {

                                        method.setAccessible(true);
                                        method.invoke(activity, view);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
