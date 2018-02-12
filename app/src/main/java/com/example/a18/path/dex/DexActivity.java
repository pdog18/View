package com.example.a18.path.dex;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a18.path.R;

import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;

public class DexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex);

        try {
            getDexElements(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }




    private static final String DEXCLASSLOADER_NAME = "dalvik.system.BaseDexClassLoader";
    private static final String DEX_PATHLIST = "pathList";
    private static final String DEX_ELEMENTS = "dexElements";
    private static final String OPT_DIR = "odex";

    /**
     * 通过反射获得DexElments数组
     *
     * @param c 需要上下文获得classloader
     * @return classloadr 中的成员变量 pathList 成员变量的 dexElements 数组
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("all")
    private static Object getDexElements(Context c)
        throws NoSuchFieldException, IllegalAccessException {

        Context context = c;
        String DEXPATHLIST = DEX_PATHLIST;
        String DEXELEMENTS = DEX_ELEMENTS;

        // 获得ClassLoader ,这里获得的是 dalvik.system.PathClassLoader
        ClassLoader appClassLoader = context.getClassLoader();


        // pathList字段
        Class<?> dexclazz = BaseDexClassLoader.class;
        Field pathListField = dexclazz.getDeclaredField(DEXPATHLIST);
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(appClassLoader);


        //获取Element
        Class<?> pathListClass = pathList.getClass();
        Field elementField = pathListClass.getDeclaredField(DEXELEMENTS);
        elementField.setAccessible(true);
        Object dexElements = elementField.get(pathList);

        return dexElements;
    }
}
