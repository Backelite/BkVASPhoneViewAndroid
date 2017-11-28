/*
 * Copyright 2017 Backelite.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.backelite.android.bkvasphoneviewandroid

/**
 * Created by jean-baptistevincey on 27/11/2017.
 */
enum class VASPhoneViewStyle(val code: Int) {
    STANDARD(0),
    FREE(1),
    CHARGEABLE(2);

    companion object {
        fun fromCode(code: Int): VASPhoneViewStyle {
            for (f in values()) {
                if (f.code == code) {
                    return f
                }
            }
            return FREE
        }
    }
}