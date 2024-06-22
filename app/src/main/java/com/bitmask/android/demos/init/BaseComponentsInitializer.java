package com.bitmask.android.demos.init;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.bitmask.android.demos.retrofit.RetrofitManager;

import java.util.Collections;
import java.util.List;

/** 基础组件初始化 没有前向依赖*/
public class BaseComponentsInitializer implements Initializer {

    @NonNull
    @Override
    public Object create(@NonNull Context context) {

        //网络库初始化
        RetrofitManager.getInstance().initialize();
        return new Object();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
