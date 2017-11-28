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

package com.backelite.android.bkvasphoneviewandroidapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.backelite.android.bkvasphoneviewandroid.VASPhoneView
import com.backelite.android.bkvasphoneviewandroid.VASPhoneViewSize
import com.backelite.android.bkvasphoneviewandroid.VASPhoneViewStyle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearlayout: LinearLayout = findViewById(R.id.linearlayout)

        val vasPhoneView = VASPhoneView(this)
        vasPhoneView.setVASPhoneViewSize(VASPhoneViewSize.BIG)
        vasPhoneView.setVASPhoneViewStyle(VASPhoneViewStyle.CHARGEABLE)
        vasPhoneView.setVASPhoneViewPhoneNumber("0825123456")
        vasPhoneView.setVASPhoneViewFeeAmount("0,4€ / min")
        vasPhoneView.setVASPhoneViewArialAllowed(true)
        vasPhoneView.setVASPhoneViewDialOnClick(true)

        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, resources.getDimensionPixelSize(R.dimen.marginTop), 0, 0)

        linearlayout.addView(vasPhoneView, layoutParams)
    }
}
