/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;

import java.io.File;

public interface DiskCache {

    File get(Key var1);

    void put(Key var1,Writer var2);

    void delete(Key var1);

    void clear();

    public interface Writer {

        boolean write(File var1);
    }

    public interface Factory {

        int    DEFAULT_DISK_CACHE_SIZE=262144000;
        String DEFAULT_DISK_CACHE_DIR ="image_manager_disk_cache";

        DiskCache build();
    }
}
