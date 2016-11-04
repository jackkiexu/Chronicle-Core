/*
 * Copyright 2016 higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.core.threads;

import java.lang.ref.WeakReference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by peter on 04/11/16.
 */
public enum ThreadLocalHelper {
    ;

    public static <T> T getTL(ThreadLocal<WeakReference<T>> threadLocal, Supplier<T> supplier) {
        WeakReference<T> ref = threadLocal.get();
        T ret = null;
        if (ref != null) ret = ref.get();
        if (ret == null) {
            ret = supplier.get();
            ref = new WeakReference<T>(ret);
            threadLocal.set(ref);
        }
        return ret;
    }

    public static <T, A> T getTL(ThreadLocal<WeakReference<T>> threadLocal, A a, Function<A, T> function) {
        WeakReference<T> ref = threadLocal.get();
        T ret = null;
        if (ref != null) ret = ref.get();
        if (ret == null) {
            ret = function.apply(a);
            ref = new WeakReference<T>(ret);
            threadLocal.set(ref);
        }
        return ret;
    }
}
