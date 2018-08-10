[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ShimmerLayout-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5767)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.supercharge/shimmerlayout/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/io.supercharge/shimmerlayout)
[![Android Weekly](http://img.shields.io/badge/Android%20Weekly-%23266-2CB3E5.svg?style=flat)](http://androidweekly.net/issues/issue-266)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![Build Status](https://travis-ci.org/team-supercharge/ShimmerLayout.svg?branch=master)](https://travis-ci.org/team-supercharge/ShimmerLayout)

# ShimmerLayout

🌎  Translations: [🇧🇷](https://github.com/brunomunizaf/ShimmerLayout/blob/master/README_pt-br.md) by [@brunomunizaf](https://twitter.com/brunomuniz_af)

`ShimmerLayout` can be used to add shimmer effect (like the one used at Facebook or at LinkedIn) to your Android application. Beside memory efficiency even animating a big layout, you can customize the behaviour and look of the animation. Check out the [**wiki**](https://github.com/team-supercharge/ShimmerLayout/wiki/Home) for attributes!

<p align="center">
<img src="/shimmerlayout.gif?raw=true" width="300" />
</p>

# Performance

When it comes to performance, `ShimmerLayout` is the best option available. Not only can you achieve **high frame rate** even in RecyclerViews, but it also animates views with **insignificant memory footprint**.

I conducted a benchmark to compare memory usages between different shimmer implementations. The test device I used is a HTC M8 phone. For the animations the sample application was used.

| Library used | Android Profiler result |
| --- | --- |
| Facebook shimmer library 0.1.0        | ![ShimmerLayout](benchmark_images/facebook_0_1_0.PNG)             |
| ShimmerLayout 1.2.0                   | ![ShimmerLayout](benchmark_images/shimmer_layout_1_2_0.PNG)       |
| **ShimmerLayout 2.0.0**               | ![ShimmerLayout](benchmark_images/shimmer_layout_2_0_0.PNG)       |

# Download and usage

Get the latest artifact via gradle
```groovy
implementation 'io.supercharge:shimmerlayout:2.1.0'
```

Create the layout on which you want to apply the effect and add as a child of a `ShimmerLayout`

```xml
<io.supercharge.shimmerlayout.ShimmerLayout
        android:id="@+id/shimmer_text"
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="50dp"
        android:paddingLeft="30dp"
        android:paddingRight="30dp"
        app:shimmer_animation_duration="1200">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="ShimmerLayout"
            android:textColor="@color/shimmer_background_color"
            android:textSize="26sp"/>
    </io.supercharge.shimmerlayout.ShimmerLayout>
```

Last, but not least you have to start it from your Java code
```java
ShimmerLayout shimmerText = (ShimmerLayout) findViewById(R.id.shimmer_text);
shimmerText.startShimmerAnimation();
```
# Further reading

* [The development of ShimmerLayout](https://medium.com/supercharges-mobile-product-guide/shimmerlayout-26978ab53c28)  - In this article you can read why we created this library and the technologies we used.

# License

`ShimmerLayout` is opensource, contribution and feedback are welcome!

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


```
Copyright 2017 Supercharge

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
# Author

[veghtomi](https://github.com/veghtomi)   

[![Supercharge](http://s23.postimg.org/gbpv7dwjr/unnamed.png)](http://supercharge.io/)
